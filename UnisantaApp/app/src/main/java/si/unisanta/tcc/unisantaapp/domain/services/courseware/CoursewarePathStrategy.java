package si.unisanta.tcc.unisantaapp.domain.services.courseware;

import android.os.Environment;

import java.io.File;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;

public class CoursewarePathStrategy {
    public String getFolderPath(Courseware courseware) {
        String path = getBestPath() + courseware.getSubject().getName() + File.separator;
        UnisantaApplication.Log_i("SALVAR EM:" + path);
        return path;
        //sd/unisantaapp/material/NOME DA MATÉRIA/
    }

    private String getExternalPath() {
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator +
                "unisantaapp" + File.separator +
                "material" + File.separator;
    }

    private String getInternalPath() {
        return UnisantaApplication.getInstance().getApplicationContext()
                .getFilesDir().getAbsolutePath() + File.separator +
                "material" + File.separator;
    }

    private String getBestPath() {
        if (isExternalStorageWritable())
            return getExternalPath();
        return getInternalPath();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public String getAbsolutePath(Courseware courseware) {
        return getFolderPath(courseware) + courseware.getFileName();
    }
}
