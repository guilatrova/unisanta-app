package si.unisanta.tcc.unisantaapp.domain.exceptions;

public class PageRequestFailedException extends Exception {
    private String url;

    public PageRequestFailedException(String url) {
        this.url = url;
    }

    public PageRequestFailedException(String detailMessage, String url) {
        super(detailMessage);
        this.url = url;
    }

    public PageRequestFailedException(String detailMessage, Throwable throwable, String url) {
        super(detailMessage, throwable);
        this.url = url;
    }

    public PageRequestFailedException(Throwable throwable, String url) {
        super(throwable);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
