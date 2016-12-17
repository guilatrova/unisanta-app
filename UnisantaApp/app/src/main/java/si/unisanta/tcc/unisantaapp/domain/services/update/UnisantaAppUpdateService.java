package si.unisanta.tcc.unisantaapp.domain.services.update;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.model.IUserRepository;
import si.unisanta.tcc.unisantaapp.domain.services.update.fixes.TestsDPFix;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class UnisantaAppUpdateService {
    private IUserRepository userRepository;
    private IUpdateListener updateListener;

    public UnisantaAppUpdateService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UnisantaAppUpdateService(IUserRepository userRepository, IUpdateListener updateListener) {
        this.userRepository = userRepository;
        this.updateListener = updateListener;
    }

    public void execute() {
        userRepository.retrieve();

        User user = User.getInstance();
        AppVersion lastVersion = user.getAppVersion();

        if (lastVersion.isBefore(getCurrentVersion())) {
            handleUpdate(lastVersion);

            if (updateListener != null) {
                updateListener.handleUpdate(lastVersion, getCurrentVersion());
            }

            user.setAppVersion(getCurrentVersion());
            RepositoryFactory.getUserRepository().save(user);
        }
    }

    private AppVersion getCurrentVersion() {
        return new AppVersion(UnisantaApplication.getVersionName());
    }

    private void handleUpdate(AppVersion old) {
        if (old.equals(new AppVersion("<3"))) {
            new TestsDPFix().fix();
        }
    }
}
