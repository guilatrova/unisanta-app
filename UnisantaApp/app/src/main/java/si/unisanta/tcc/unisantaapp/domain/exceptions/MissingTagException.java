package si.unisanta.tcc.unisantaapp.domain.exceptions;

public class MissingTagException extends SyncFailedException {
    private String tag;

    public String getTag() {
        return tag;
    }

    public MissingTagException(String detailMessage, Throwable throwable, String url, String which, String tag) {
        super(detailMessage, throwable, url, which);
        this.tag = tag;
    }

    public MissingTagException(Throwable throwable, String url, String which, String tag) {
        super(throwable, url, which);
        this.tag = tag;
    }

    public MissingTagException(String url, String which, String tag) {
        super(url, which);
        this.tag = tag;
    }
}
