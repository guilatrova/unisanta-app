package tests.factories;

import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebClassesData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebGradesData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebSubjectsData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebUserData;
import tests.mocks.pages.pedagogia.ClassesSchedulePageMock;
import tests.mocks.pages.pedagogia.CoursewarePageMock;
import tests.mocks.pages.pedagogia.GradesPageMock;
import tests.mocks.pages.pedagogia.MainPageMock;
import tests.mocks.repositories.ClassScheduleMockRepository;
import tests.mocks.repositories.GradeMockRepository;
import tests.mocks.repositories.SubjectMockRepository;
import tests.mocks.repositories.TeacherMockRepository;
import tests.mocks.repositories.UserMockRepository;

public class PedagogiaFactory implements ICourseFactory{
    @Override
    public SyncWebUserData createSyncUserData() {
        return new SyncWebUserData(
                new GradesPageMock(),
                new MainPageMock(),
                "123", "123",
                new UserMockRepository()
        );
    }

    @Override
    public SyncWebSubjectsData createSyncSubjectsData() {
        return new SyncWebSubjectsData(
                new CoursewarePageMock(),
                new SubjectMockRepository(),
                new TeacherMockRepository()
        );
    }

    @Override
    public SyncWebGradesData createSyncGradesData() {
        return new SyncWebGradesData(
                new GradesPageMock(),
                new GradeMockRepository(),
                new SubjectMockRepository()
        );
    }

    @Override
    public SyncWebClassesData createSyncWebClassesData() {
        return new SyncWebClassesData(
                new ClassesSchedulePageMock(),
                new ClassScheduleMockRepository(),
                new SubjectMockRepository(),
                new TeacherMockRepository()
        );
    }
}
