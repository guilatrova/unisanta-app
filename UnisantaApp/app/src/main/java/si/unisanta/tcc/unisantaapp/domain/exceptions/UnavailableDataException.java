package si.unisanta.tcc.unisantaapp.domain.exceptions;

public class UnavailableDataException extends SyncFailedException {
    public UnavailableDataException(String url, String syncName) {
        super(url, syncName);
    }

    public UnavailableDataException(String detailMessage, String url, String syncName) {
        super(detailMessage, url, syncName);
    }

    public UnavailableDataException(String detailMessage, Throwable throwable, String url, String syncName) {
        super(detailMessage, throwable, url, syncName);
    }

    public UnavailableDataException(Throwable throwable, String url, String syncName) {
        super(throwable, url, syncName);
    }
}
