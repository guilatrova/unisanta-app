package si.unisanta.tcc.unisantaapp.domain.exceptions;

public class MissingDataException extends SyncFailedException{
    private String what;

    public String getWhatsMissing() {
        return what;
    }

    public MissingDataException(String url, String which, String what) {
        super(url, which);
        this.what = what;
    }

    public MissingDataException(String detailMessage, Throwable throwable, String url, String which, String what) {
        super(detailMessage, throwable, url, which);
        this.what = what;
    }

    public MissingDataException(Throwable throwable, String url, String which, String what) {
        super(throwable, url, which);
        this.what = what;
    }
}
