package si.unisanta.tcc.unisantaapp.infrastructure.web.http;

import com.squareup.okhttp.Response;

import java.io.IOException;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;

public class HttpResponse {
    public static final int RESULT_OK = 1;
    public static final int RESULT_FAIL = 0;

    private Response response;
    private Exception exceptionThrown;
    private int result;
    private String html;
    private boolean done;

    public HttpResponse() {
        done = false;
        exceptionThrown = null;
    }

    public String getHtml() {
        return html;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) throws IOException {
        this.response = response;
        this.result = RESULT_FAIL;

        if (response.isSuccessful()) {
            html = response.body().string();
            result = RESULT_OK;
        }
        else {
            result = RESULT_FAIL;
            UnisantaApplication.Log_e("Falha ao recuperar página", null);
        }

        done = true;
    }

    public void setResponse(Exception exceptionThrown) {
        this.exceptionThrown = exceptionThrown;
        done = true;
    }

    public Exception getException() {
        return exceptionThrown;
    }

    public int getResult() {
        return result;
    }

    public boolean isDone() {
        return done;
    }
}
