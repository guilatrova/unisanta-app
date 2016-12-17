package si.unisanta.tcc.unisantaapp.domain.exceptions;

public class SyncFailedException extends Exception{
    private String url;
    private String syncName;

    public SyncFailedException(String url, String syncName) {
        this.url = url;
        this.syncName = syncName;
    }

    public SyncFailedException(String detailMessage, String url, String syncName) {
        super(detailMessage);
        this.url = url;
        this.syncName = syncName;
    }

    public SyncFailedException(String detailMessage, Throwable throwable, String url, String syncName) {
        super(detailMessage, throwable);
        this.url = url;
        this.syncName = syncName;
    }

    public SyncFailedException(Throwable throwable, String url, String syncName) {
        super(throwable);
        this.url = url;
        this.syncName = syncName;
    }

    public String getUrl() {
        return url;
    }

    public String getSyncName() {
        return syncName;
    }
}
