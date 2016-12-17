package si.unisanta.tcc.unisantaapp.domain.factories;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;
import si.unisanta.tcc.unisantaapp.domain.model.IGradeRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITeacherRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITestRepository;
import si.unisanta.tcc.unisantaapp.domain.model.IUserRepository;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncTaskService;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.ClassesSchedulePage;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.CoursewarePage;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.CoursewareSubjectPage;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.GradesPage;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.MainPage;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.TestsPage;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebClassesData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebCoursewareData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebCoursewareSemesterData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebGradesData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebSubjectsData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebTestsData;
import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncWebUserData;

public class SyncServiceFactory {
    public static final int FIRST_LOGIN = 1;

    public static SyncTaskService createSyncService(int type){
        switch (type) {
            case FIRST_LOGIN:
                return new SyncTaskService.Builder()
                        .setIsFirstLogin(true)
                        .setSyncAllSemesters(false)
                        .add(SyncTaskService.Builder.SYNC_USER)
                        .add(SyncTaskService.Builder.SYNC_SUBJECTS)
                        .add(SyncTaskService.Builder.SYNC_CLASSES)
                        .add(SyncTaskService.Builder.SYNC_GRADES)
                        .add(SyncTaskService.Builder.SYNC_COURSEWARE)
                        .add(SyncTaskService.Builder.SYNC_TESTS)
                        .build();

            default:
                return null;
        }
    }

    public static SyncWebUserData createSyncUserData() {
        GradesPage gradesPage = new GradesPage(SchoolYear.getCurrent().toString());
        MainPage mainPage = new MainPage();
        User user = User.getInstance();
        IUserRepository userRepository = RepositoryFactory.getUserRepository();

        return new SyncWebUserData(gradesPage,
                mainPage,
                user.getPassword(),
                user.getRA(),
                userRepository);
    }

    public static SyncWebSubjectsData createSyncSubjectsData(SchoolYear schoolYear) {
        ISubjectRepository subjectRepository = RepositoryFactory.getSubjectRepository();
        ITeacherRepository teacherRepository = RepositoryFactory.getTeacherRepository();

        return new SyncWebSubjectsData(
                new CoursewarePage(schoolYear.toString()),
                subjectRepository,
                teacherRepository
        );
    }

    public static SyncWebGradesData createSyncGradesData(SchoolYear schoolYear) {
        IGradeRepository gradeRepository = RepositoryFactory.getGradeRepository();
        ISubjectRepository subjectRepository = RepositoryFactory.getSubjectRepository();

        return new SyncWebGradesData(
                new GradesPage(schoolYear.toString()),
                gradeRepository,
                subjectRepository
        );
    }

    public static SyncWebClassesData createSyncClassesData() {
        ISubjectRepository subjectRepository = RepositoryFactory.getSubjectRepository();
        IClassScheduleRepository classScheduleRepository = RepositoryFactory.getClassScheduleRepository();
        ITeacherRepository teacherRepository = RepositoryFactory.getTeacherRepository();

        return new SyncWebClassesData(
                new ClassesSchedulePage(),
                classScheduleRepository,
                subjectRepository,
                teacherRepository
        );
    }

    public static SyncWebCoursewareData createSyncCoursewareData(Subject subject) {
        return new SyncWebCoursewareData(
                new CoursewareSubjectPage(subject),
                RepositoryFactory.getCoursewareRepository()
        );
    }

    public static SyncWebCoursewareSemesterData createSyncCousewareSemesterData(SchoolYear schoolYear) {
        ISubjectRepository subjectRepository = RepositoryFactory.getSubjectRepository();
        return new SyncWebCoursewareSemesterData(subjectRepository, schoolYear);
    }

    public static SyncWebTestsData createSyncTestsData() {
        ISubjectRepository subjectRepository = RepositoryFactory.getSubjectRepository();
        ITestRepository testRepository = RepositoryFactory.getTestRepository();
        TestsPage testsPage = new TestsPage();

        return new SyncWebTestsData(testsPage, testRepository, subjectRepository);
    }
}
