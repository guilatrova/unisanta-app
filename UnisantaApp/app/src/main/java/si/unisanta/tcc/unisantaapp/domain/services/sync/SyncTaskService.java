package si.unisanta.tcc.unisantaapp.domain.services.sync;

import android.os.AsyncTask;

import com.google.common.collect.Lists;
import com.orm.SugarTransactionHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.dto.SyncDTO;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.exceptions.LoginFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.NotConnectedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UserChangedPasswordException;
import si.unisanta.tcc.unisantaapp.domain.factories.ServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.SyncServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.services.ConnectionService;
import si.unisanta.tcc.unisantaapp.domain.services.IUserLoginService;
import si.unisanta.tcc.unisantaapp.domain.services.SqliteHelper;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class SyncTaskService extends AsyncTask<Void, String, Void> implements SugarTransactionHelper.Callback {
    private ConnectionService connectionService;
    private IUserLoginService loginService;
    private Exception exceptionThrown;
    private boolean firstLogin;
    private ISyncProgressListener listener;
    private ArrayList<SyncBaseData> toSync;
    private int totalToSync;
    private int doneSync;

    public SyncTaskService(boolean firstLogin, IUserLoginService loginService, ConnectionService connectionService, SyncBaseData... syncData) {
        this.firstLogin = firstLogin;
        this.loginService = loginService;
        this.connectionService = connectionService;
        this.toSync = Lists.newArrayList(syncData);
        this.totalToSync = toSync.size();
    }

    public SyncTaskService setListener(ISyncProgressListener listener) {
        this.listener = listener;
        return this;
    }

    public int getTotalToSync() {
        return totalToSync;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        exceptionThrown = null;
        doneSync = 0;

        if (listener != null)
            listener.onStarting();
    }

    private void beforeStart() throws NotConnectedException, LoginFailedException, UserChangedPasswordException {
        publishProgress(UnisantaApplication.getInstance().getString(R.string.checking_connection_message));
        if (!connectionService.isOnline())
            throw new NotConnectedException();

        publishProgress(UnisantaApplication.getInstance().getString(R.string.logging_in_message));
        User user = User.getInstance();
        if (!loginService.doLogin(user.getRA(), user.getPassword())) {
            if (firstLogin)
                throw new LoginFailedException();
            else
                throw new UserChangedPasswordException();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            beforeStart();

            SugarTransactionHelper.doInTransaction(this);

            SqliteHelper.vacuum();
        }
        catch (Exception e) {
            exceptionThrown = e;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (listener != null) {
            if (exceptionThrown != null)
                listener.onFailure(exceptionThrown);
            else
                listener.onSuccess(aVoid);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        if (listener != null) {
            listener.onMessageChange(values[0]);
            listener.onProgressChange(doneSync);
        }
    }

    @Override
    public void manipulateInTransaction() {
        for (int i = 0; i < toSync.size(); i++) {
            SyncBaseData data = toSync.get(i);
            try {
                publishProgress("Atualizando " + data.getMessage());
                data.sync();
                doneSync++;
            }
            catch (UnavailableDataException e) {
                for (SyncBaseData dependency : data.getDependents()) {
                    toSync.remove(dependency);
                    UnisantaApplication.Log_d("Removido: " + dependency.getMessage());
                    doneSync++;
                }
            }
            catch (SyncFailedException e) {
                e.printStackTrace();
                exceptionThrown = e;
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    public static class Builder {
        public static final int SYNC_USER = -1;
        public static final int SYNC_SUBJECTS = 0;
        public static final int SYNC_CLASSES = 1;
        public static final int SYNC_GRADES = 2;
        public static final int SYNC_COURSEWARE = 3;
        public static final int SYNC_TESTS = 4;

        private TreeSet<SyncDTO> syncList;
        private boolean syncAllSemesters;
        private boolean isFirstLogin;

        public Builder() {
            SyncDTO.clearCache();
            syncList = new TreeSet<>();
            syncAllSemesters = false;
            isFirstLogin = false;
        }

        public Builder add(int sync) {
            return this.add(sync, SchoolYear.getCurrent());
        }

        public Builder add(int sync, SchoolYear schoolYear) {
            if (sync == SYNC_USER || sync == SYNC_CLASSES || sync == SYNC_TESTS) {
                if (!schoolYear.equals(SchoolYear.getCurrent()))
                    return this;
            }

            syncList.add(new SyncDTO(sync, schoolYear));
            return this;
        }

        public Builder add(Subject subject) {
            syncList.add(new SyncDTO(SYNC_COURSEWARE, subject));
            return this;
        }

        public Builder setSyncAllSemesters(boolean allSemesters) {
            this.syncAllSemesters = allSemesters;
            return this;
        }

        public Builder setIsFirstLogin(boolean isFirstLogin) {
            this.isFirstLogin = isFirstLogin;
            return this;
        }

        public SyncTaskService build() {
            List<SyncBaseData> createSyncList = new ArrayList<>();

            if (syncList.contains(new SyncDTO(SYNC_USER))) {
                createSyncList.add(SyncServiceFactory.createSyncUserData());
            }

            for (SchoolYear schoolYear : getSchoolYears()) {
                Collections.addAll(createSyncList, createForSemester(getListForSchoolYear(schoolYear), schoolYear));
            }

            return new SyncTaskService(
                    isFirstLogin,
                    ServiceFactory.createUserLoginService(),
                    ServiceFactory.createConnectionService(),
                    createSyncList.toArray(new SyncBaseData[createSyncList.size()])
            );
        }

        private SchoolYear[] getSchoolYears() {
            if (syncAllSemesters) {
                List<SchoolYear> schoolYearList = new ArrayList<>();
                for (SchoolYear schoolYear = User.getInstance().getFirstSchoolYear();
                     schoolYear.isBefore(SchoolYear.getCurrent()) || schoolYear.equals(SchoolYear.getCurrent());
                     schoolYear = schoolYear.next()) {

                    schoolYearList.add(schoolYear);
                }

                return schoolYearList.toArray(new SchoolYear[schoolYearList.size()]);
            }
            return SyncDTO.getSchoolYears();
        }

        private List<SyncDTO> getListForSchoolYear(SchoolYear schoolYear) {
            List<SyncDTO> aux = new ArrayList<>();

            for (SyncDTO syncDTO : syncList) {
                if (syncAllSemesters || syncDTO.getExtraSchoolYear().equals(schoolYear))
                    aux.add(new SyncDTO(syncDTO.getWhat(), schoolYear));
            }

            return aux;
        }

        private SyncBaseData[] createForSemester(List<SyncDTO> syncList, SchoolYear schoolYear) {
            List<SyncBaseData> aux = new ArrayList<>();
            SyncBaseData syncSubjects = null;

            if (syncList.contains(new SyncDTO(SYNC_SUBJECTS, schoolYear))) {
                syncSubjects = SyncServiceFactory.createSyncSubjectsData(schoolYear);
                aux.add(syncSubjects);
            }

            SyncDTO[] coursewareToSyncArray = findAllCoursewareFrom(schoolYear);
            for (SyncDTO courseware : coursewareToSyncArray) {
                SyncBaseData coursewareToSync;
                Subject subject = courseware.getExtraSubject();

                if (subject == null) {
                    coursewareToSync = SyncServiceFactory.createSyncCousewareSemesterData(schoolYear);
                    if (syncSubjects != null)
                        syncSubjects.addDependent(coursewareToSync);
                }
                else {
                    coursewareToSync = SyncServiceFactory.createSyncCoursewareData(subject);
                }
                aux.add(coursewareToSync);
            }

            if (syncList.contains(new SyncDTO(SYNC_GRADES, schoolYear))) {
                SyncBaseData syncGrades = SyncServiceFactory.createSyncGradesData(schoolYear);
                if (syncSubjects != null)
                    syncSubjects.addDependent(syncGrades);
                aux.add(syncGrades);
            }

            SyncBaseData syncClasses = null;
            if (syncList.contains(new SyncDTO(SYNC_CLASSES, schoolYear))) {
                syncClasses = SyncServiceFactory.createSyncClassesData();
                if (syncSubjects != null)
                    syncSubjects.addDependent(syncClasses);
                aux.add(syncClasses);
            }

            if (syncList.contains(new SyncDTO(SYNC_TESTS, schoolYear))) {
                SyncBaseData syncTests = SyncServiceFactory.createSyncTestsData();
                if (syncSubjects != null)
                    syncSubjects.addDependent(syncTests);
                if (syncClasses != null)
                    syncClasses.addDependent(syncTests);
                aux.add(syncTests);
            }

            return aux.toArray(new SyncBaseData[aux.size()]);
        }

        private SyncDTO[] findAllCoursewareFrom(SchoolYear schoolYear) {
            List<SyncDTO> syncDTOList = new ArrayList<>();
            for (SyncDTO isCourseware : syncList) {
                if (isCourseware.getWhat() == SYNC_COURSEWARE &&
                        isCourseware.getExtraSchoolYear().equals(schoolYear)) {
                    syncDTOList.add(isCourseware);
                }
            }
            return syncDTOList.toArray(new SyncDTO[syncDTOList.size()]);
        }
    }
}
