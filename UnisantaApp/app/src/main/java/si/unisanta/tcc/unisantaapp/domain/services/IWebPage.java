package si.unisanta.tcc.unisantaapp.domain.services;

import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;

public interface IWebPage {
    String getHtml() throws PageRequestFailedException;
    String getUrl();
    String getPageName();
}
