package tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebCoursewareData;
import tests.mocks.pages.si.CoursewareSubjectPageMock;
import tests.mocks.repositories.CoursewareMockRepository;

public class SyncMockTest {
    private SyncWebCoursewareData syncWebCoursewareData;
    private CoursewareMockRepository coursewareRepository;
    private Subject subjectTest;

    @Before
    public void setUp() throws Exception {
        subjectTest = CoursewareSubjectPageMock.getSubjectMock();
        coursewareRepository = new CoursewareMockRepository();

        CoursewareSubjectPageMock coursewareSubjectPage = new CoursewareSubjectPageMock(subjectTest);
        syncWebCoursewareData = new SyncWebCoursewareData(coursewareSubjectPage, coursewareRepository);
    }

    @Test
    public void testCoursewareSubjectPageSync() throws Exception {
        syncWebCoursewareData.sync();

        List<Courseware> lst = coursewareRepository.findBySubject(subjectTest);
        Assert.assertEquals(4, lst.size());
    }
}
