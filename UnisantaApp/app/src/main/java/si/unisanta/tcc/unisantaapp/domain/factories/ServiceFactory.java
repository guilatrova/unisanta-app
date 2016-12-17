package si.unisanta.tcc.unisantaapp.domain.factories;

import android.content.Context;
import android.net.ConnectivityManager;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.services.ClearDataService;
import si.unisanta.tcc.unisantaapp.domain.services.ConnectionService;
import si.unisanta.tcc.unisantaapp.domain.services.IUserLoginService;
import si.unisanta.tcc.unisantaapp.domain.services.courseware.CoursewarePathStrategy;
import si.unisanta.tcc.unisantaapp.domain.services.update.IUpdateListener;
import si.unisanta.tcc.unisantaapp.domain.services.update.UnisantaAppUpdateService;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.LoginPage;
import si.unisanta.tcc.unisantaapp.infrastructure.services.WebPageLoginService;

public class ServiceFactory {
    public static IUserLoginService createUserLoginService() {
        return new WebPageLoginService(new LoginPage());
    }

    public static ConnectionService createConnectionService() {
        return new ConnectionService(
                (ConnectivityManager) UnisantaApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE)
        );
    }

    public static ClearDataService createClearDataService() {
        return new ClearDataService(
                RepositoryFactory.getUserRepository(),
                RepositoryFactory.getGradeRepository(),
                RepositoryFactory.getSubjectRepository(),
                RepositoryFactory.getTeacherRepository(),
                RepositoryFactory.getClassScheduleRepository(),
                RepositoryFactory.getCoursewareRepository(),
                RepositoryFactory.getTestRepository()
        );
    }

    public static CoursewarePathStrategy createCoursewarePathStrategy() {
        return new CoursewarePathStrategy();
    }

    public static UnisantaAppUpdateService createUpdateService(IUpdateListener updateListener) {
        return new UnisantaAppUpdateService(RepositoryFactory.getUserRepository(), updateListener);
    }
}
