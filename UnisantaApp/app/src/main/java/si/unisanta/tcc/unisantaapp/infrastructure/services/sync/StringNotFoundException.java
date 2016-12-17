package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

public class StringNotFoundException extends Exception {
    private String string;

    public StringNotFoundException(String string) {
        this.string = string;
    }

    public StringNotFoundException(String detailMessage, String string) {
        super(detailMessage);
        this.string = string;
    }

    public StringNotFoundException(String detailMessage, Throwable throwable, String string) {
        super(detailMessage, throwable);
        this.string = string;
    }

    public StringNotFoundException(Throwable throwable, String string) {
        super(throwable);
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
