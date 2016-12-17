package si.unisanta.tcc.unisantaapp;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import si.unisanta.tcc.unisantaapp.domain.factories.ServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.services.IUserLoginService;

public class SyncLoginTestBase extends ApplicationTestCase<Application> {
    public static final int WAIT_DELAY = 3000;
    private static final String RA = "ALGUM RA AQUI";
    private static final String SENHA = "SENHA AQUI";
    public static final String LOG_TAG = "UNISANTA_APP/TESTE";

    protected IUserLoginService loginService;

    public SyncLoginTestBase() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        loginService = ServiceFactory.createUserLoginService();

        Log.d(LOG_TAG, "Esperando pra iniciar...");
        Thread.sleep(WAIT_DELAY);
        Log.d(LOG_TAG, "Terminei de esperar. Vou efetuar login no RA:" + RA);

        loginService.doLogin(RA, SENHA);
    }
}
