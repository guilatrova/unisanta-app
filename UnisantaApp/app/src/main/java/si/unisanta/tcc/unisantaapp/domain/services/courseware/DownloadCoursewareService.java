package si.unisanta.tcc.unisantaapp.domain.services.courseware;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.exceptions.NotConnectedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UserChangedPasswordException;
import si.unisanta.tcc.unisantaapp.domain.services.ConnectionService;
import si.unisanta.tcc.unisantaapp.domain.services.IUserLoginService;
import si.unisanta.tcc.unisantaapp.domain.services.sync.ISyncCancelableProgressListener;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpRequest;

public class DownloadCoursewareService extends AsyncTask<Courseware, Integer, String> {
    private Context context;
    private Exception exceptionThrown;
    private HttpRequest httpRequest;
    private IUserLoginService loginService;
    private ConnectionService connectionService;
    private Courseware currentDownload;
    private ISyncCancelableProgressListener listener;
    private CoursewarePathStrategy coursewarePathStrategy;

    public DownloadCoursewareService(IUserLoginService loginService, ConnectionService connectionService, HttpRequest httpRequest, CoursewarePathStrategy coursewarePathStrategy) {
        this.loginService = loginService;
        this.connectionService = connectionService;
        this.httpRequest = httpRequest;
        this.coursewarePathStrategy = coursewarePathStrategy;
    }

    public void setListener(ISyncCancelableProgressListener listener) {
        this.listener = listener;
    }

    private void createFolder(String folderPath) throws CantCreateFolderPathException {
        File folder = new File(folderPath);
        if (folder.mkdirs() || folder.isDirectory())
          return; //Criou ou já existe, tudo sussa!

        throw new CantCreateFolderPathException(folderPath);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onStarting();
        }
    }

    @Override
    protected String doInBackground(Courseware... coursewares) {

        try {
            login();
        }
        catch (Exception e) {
            UnisantaApplication.Log_e("Erro no login/conexão", e);
            exceptionThrown = e;
            return "";
        }

        if (isCancelled()) return "";

        for (Courseware download : coursewares) {
            currentDownload = download;
            try {
                InputStream is = httpRequest.getFileFrom(download.getDownloadLink());
                saveFile(is, download);
                return coursewarePathStrategy.getAbsolutePath(download);
            }
            catch (IOException e) {
                exceptionThrown = e;
            }

            if (isCancelled()) break;
        }

        return coursewarePathStrategy.getAbsolutePath(currentDownload);
    }

    private void login() throws NotConnectedException, UserChangedPasswordException {
        if (!connectionService.isOnline()) {
            throw new NotConnectedException();
        }

        User user = User.getInstance();
        if (!loginService.doLogin(user.getRA(), user.getPassword())) {
            throw new UserChangedPasswordException();
        }
    }

    private void saveFile(InputStream is, Courseware courseware) throws IOException {
        BufferedInputStream input = null;
        OutputStream output = null;

        try {
            createFolder(coursewarePathStrategy.getFolderPath(courseware));
            String filePath = coursewarePathStrategy.getAbsolutePath(courseware);
            UnisantaApplication.Log_i("Caminho do arquivo a ser salvo: " + filePath);

            input = new BufferedInputStream(is);
            output = new FileOutputStream(filePath);

            byte[] buffer = new byte[courseware.calcSizeInBytes()];
            int read;
            int totalRead = 0;
            int maxSize = courseware.calcSizeInBytes();

            while ((read = input.read(buffer)) != -1) {
                if (isCancelled()) return;

                output.write(buffer, 0, read);
                totalRead += read;
                publishProgress(100 * totalRead / maxSize);
            }
            output.flush();
        }
        catch (CantCreateFolderPathException e)
        {
            UnisantaApplication.Log_e("Não foi possível criar as pastas:" + e.getPath(), e);
            exceptionThrown = e;
        }
        finally
        {
            UnisantaApplication.Log_i("Fechando stream");
            try {
                if (input != null) input.close();
                if (output != null) output.close();
            }
            catch (IOException e) {
                UnisantaApplication.Log_e(e.getMessage(), e);
                exceptionThrown = e;
            }
        }
    }

    private void deleteFile(String path) {
        if (!path.isEmpty()) {
            UnisantaApplication.Log_i("Deletando " + path);
            File file = new File(path);
            file.delete();
        }
    }

    @Override
    protected void onCancelled(String s) {
        deleteFile(s);
        if (listener != null)
            listener.onCancel();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if (listener != null) {
            listener.onMessageChange(UnisantaApplication.getInstance()
                    .getString(R.string.downloading, currentDownload.getFileName()));
            listener.onProgressChange(values[0]);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (listener != null) {
            if (exceptionThrown == null) {
                listener.onSuccess(currentDownload);
            } else {
                listener.onFailure(exceptionThrown);
            }
        }
    }
}
