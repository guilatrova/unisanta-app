package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.services.IWebPage;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpRequest;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpResponse;

public class RegularWebPage implements IWebPage {
    protected String url;
    protected String unavailableText;
    protected String errorOnUnavailableStr;

    public RegularWebPage(String url) {
        this.url = url;
        this.unavailableText = null;
    }

    public RegularWebPage(String url, String unavailableText, String errorOnUnavailableStr) {
        this.url = url;
        this.unavailableText = unavailableText;
        this.errorOnUnavailableStr = errorOnUnavailableStr;
    }

    @Override
    public String getHtml() throws PageRequestFailedException {
        HttpResponse response = HttpRequest.getInstance().requestPage(getUrl());

        if (response.getResult() == HttpResponse.RESULT_OK) {
            return response.getHtml();
        }

        throw new PageRequestFailedException(response.getException(), getUrl());
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getPageName() {
        return null;
    }


    public void checkDataAvailable(final String source) throws UnavailableDataException {
        if (unavailableText != null && source.contains(unavailableText)) {
            throw new UnavailableDataException(errorOnUnavailableStr, getUrl(), getPageName());
        }
    }
}
