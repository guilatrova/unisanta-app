package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpRequest;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpResponse;

public class LoginPage extends RegularWebPage {
    public static final String LOGIN_URL = "http://portalaluno.unisanta.br/Acesso/Login";
    private static final String RA_FIELD = "RA";
    private static final String PASSWORD_FIELD = "Senha";

    public LoginPage() {
        super(LOGIN_URL);
    }

    public String postCredentialsAndGetHtml(String ra, String password) throws PageRequestFailedException {
        RequestBody formBody = new FormEncodingBuilder()
                .add(RA_FIELD, ra)
                .add(PASSWORD_FIELD, password)
                .build();

        try {
            HttpResponse response = HttpRequest.getInstance().postAndRequestPage(formBody, LOGIN_URL);

            if (response.getResult() == HttpResponse.RESULT_OK) {
                return response.getHtml();
            }

            throw new PageRequestFailedException(response.getException(), getUrl());
        }
        catch (InterruptedException e) {
            throw new PageRequestFailedException(e, getUrl());
        }
    }

    @Override
    public String getPageName() {
        return "login";
    }
}
