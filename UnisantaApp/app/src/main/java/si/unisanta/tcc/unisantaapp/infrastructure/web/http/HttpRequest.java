package si.unisanta.tcc.unisantaapp.infrastructure.web.http;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.InvalidParameterException;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;

public class HttpRequest {
    private OkHttpClient client;
    private static HttpRequest instance;
    private static final int WAIT_DELAY = 200;

    private HttpRequest() {
        client = new OkHttpClient();
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);
    }

    public OkHttpClient getClient() {
        return client;
    }

    public static HttpRequest getInstance() {
        if (instance == null)
            instance = new HttpRequest();
        return instance;
    }

    public InputStream getFileFrom(String url) throws IOException {
        Request request = new Request.Builder()
            .url(url)
            .build();

        Response response = client.newCall(request).execute();
        return response.body().byteStream();
    }

    public HttpResponse postAndRequestPage(RequestBody post, String url) throws InterruptedException {
        Request request = new Request.Builder()
                .url(url)
                .post(post)
                .build();

        return requestPage(request);
    }

    public HttpResponse requestPageWithGet(String url, String[] segments, String... keyvalues) throws InterruptedException, InvalidParameterException {
        if (keyvalues.length % 2 != 0)
            throw new InvalidParameterException("Expected a pair key-value. Found: " + keyvalues.length);

        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme("http")
                .host(url);

        for (String segment : segments)
            builder = builder.addPathSegment(segment);

        for (int i = 0; i < keyvalues.length; i += 2) {
            builder = builder.setQueryParameter(keyvalues[i], keyvalues[i+1]);
        }

        HttpUrl urlGet = builder.build();

        Request request = new Request.Builder()
                .url(urlGet)
                .build();

        return requestPage(request);
    }

    public HttpResponse requestPage(String url)  {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return requestPage(request);
    }

    private HttpResponse requestPage(Request request) {
        final HttpResponse mResponse = new HttpResponse();

        try {
            mResponse.setResponse(
                client.newCall(request).execute()
            );
        } catch (IOException e) {
            UnisantaApplication.Log_e("HTTP Request - On Failure: " + e.getMessage(), e);
            mResponse.setResponse(e);
        } catch (Exception e) {
            UnisantaApplication.Log_e("HTTP Request - On Response: " + e.getMessage(), e);
            mResponse.setResponse(e);
        }

        return mResponse;
    }
}
