package si.unisanta.tcc.unisantaapp.infrastructure.services;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.services.IUserLoginService;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.LoginPage;

public class WebPageLoginService implements IUserLoginService {
    private LoginPage loginPage;

    public WebPageLoginService(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    @Override
    public boolean doLogin(String ra, String password) {
        String html = null;
        try {
            html = loginPage.postCredentialsAndGetHtml(ra, password);
        } catch (Exception e) {
            UnisantaApplication.Log_e("Erro ao tentar logar: " + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return !html.contains("incorret");

    }
}
