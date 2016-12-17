package si.unisanta.tcc.unisantaapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.ServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.SyncServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class SyncTest extends ApplicationTestCase<Application> {

    private ISubjectRepository subjectRepository;

    public SyncTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        subjectRepository = RepositoryFactory.getSubjectRepository();

        Thread.sleep(3000);

        clearData();
        firstSync();
    }

    public void firstSync() {
        User.init(null, null, null, "ALGUM RA AQUI", "SENHA AQUI");
        SyncServiceFactory.createSyncService(SyncServiceFactory.FIRST_LOGIN).execute();
    }

    public void testSyncCourseware() {
        Subject subject = subjectRepository.findByName("AUDITORIA E SEGURANÇA DA INFORMAÇÃO I").get(0);

        SyncServiceFactory.createSyncCoursewareData(subject);
    }

    public void clearData() {
        ServiceFactory.createClearDataService().clear();
    }
}
