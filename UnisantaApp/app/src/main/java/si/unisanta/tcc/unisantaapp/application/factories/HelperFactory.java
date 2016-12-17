package si.unisanta.tcc.unisantaapp.application.factories;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.application.services.ShareHelper;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;

public class HelperFactory {

    public static ShareHelper createShareHelper() {
        return new ShareHelper(
                UnisantaApplication.getInstance().getApplicationContext(),
                RepositoryFactory.getClassScheduleRepository()
        );
    }
}
