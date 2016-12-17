package si.unisanta.tcc.unisantaapp;


import junit.framework.Assert;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.SyncServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ICoursewareRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebCoursewareData;

public class SyncCoursewareTest extends SyncLoginTestBase {

    private ISubjectRepository subjectRepository;
    private ICoursewareRepository coursewareRepository;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        subjectRepository = RepositoryFactory.getSubjectRepository();
        coursewareRepository = RepositoryFactory.getCoursewareRepository();
    }

    public void testSyncCourseware() throws Exception{
        Subject subject = subjectRepository.findByName("AUDITORIA E SEGURANÇA DA INFORMAÇÃO I").get(0);
        Assert.assertNotNull(subject);
        coursewareRepository.deleteAll();

        SyncWebCoursewareData syncCourseware = SyncServiceFactory.createSyncCoursewareData(subject);
        syncCourseware.sync();

        List<Courseware> ret = coursewareRepository.findBySubject(subject);

        Assert.assertEquals(
                3,
                coursewareRepository.findBySubject(subject).size());
    }

}
