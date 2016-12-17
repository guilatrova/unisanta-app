package si.unisanta.tcc.unisantaapp.domain.factories;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ICoursewareRepository;
import si.unisanta.tcc.unisantaapp.domain.model.IGradeRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITeacherRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITestRepository;
import si.unisanta.tcc.unisantaapp.domain.model.IUpdateDetailsRepository;
import si.unisanta.tcc.unisantaapp.domain.model.IUserRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.device.SharedPreferencesRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.device.UpdateDetailsStaticRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar.SugarClassScheduleRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar.SugarCoursewareRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar.SugarGradeRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar.SugarSubjectRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar.SugarTeacherRepository;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar.SugarTestRepository;

public class RepositoryFactory {
    private static IUserRepository userRepository;
    private static ISubjectRepository subjectRepository;
    private static ITeacherRepository teacherRepository;
    private static IGradeRepository gradeRepository;
    private static IClassScheduleRepository classScheduleRepository;
    private static ICoursewareRepository coursewareRepository;
    private static ITestRepository testRepository;
    private static IUpdateDetailsRepository updateDetailsRepository;

    public static IUserRepository getUserRepository() {
        if (userRepository == null)
            userRepository = new SharedPreferencesRepository(UnisantaApplication.getInstance().getApplicationContext());
        return userRepository;
    }

    public static ISubjectRepository getSubjectRepository() {
        if (subjectRepository == null)
            subjectRepository = new SugarSubjectRepository();
        return subjectRepository;
    }

    public static ITeacherRepository getTeacherRepository() {
        if (teacherRepository == null)
            teacherRepository = new SugarTeacherRepository();
        return teacherRepository;
    }

    public static IGradeRepository getGradeRepository() {
        if (gradeRepository == null)
            gradeRepository = new SugarGradeRepository();
        return gradeRepository;
    }

    public static IClassScheduleRepository getClassScheduleRepository() {
        if (classScheduleRepository == null)
            classScheduleRepository = new SugarClassScheduleRepository();
        return classScheduleRepository;
    }

    public static ICoursewareRepository getCoursewareRepository() {
        if (coursewareRepository == null)
            coursewareRepository = new SugarCoursewareRepository();
        return coursewareRepository;
    }

    public static ITestRepository getTestRepository() {
        if (testRepository == null)
            testRepository = new SugarTestRepository();
        return testRepository;
    }

    public static IUpdateDetailsRepository getUpdateDetailsRepository() {
        if (updateDetailsRepository == null)
            updateDetailsRepository = new UpdateDetailsStaticRepository();
        return updateDetailsRepository;
    }
}
