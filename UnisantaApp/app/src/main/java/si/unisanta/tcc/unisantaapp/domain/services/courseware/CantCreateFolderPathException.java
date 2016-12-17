package si.unisanta.tcc.unisantaapp.domain.services.courseware;

public class CantCreateFolderPathException extends Exception {
    private String path;

    public CantCreateFolderPathException(String path) {
        this.path = path;
    }

    public CantCreateFolderPathException(String detailMessage, String path) {
        super(detailMessage);
        this.path = path;
    }

    public CantCreateFolderPathException(String detailMessage, Throwable throwable, String path) {
        super(detailMessage, throwable);
        this.path = path;
    }

    public CantCreateFolderPathException(Throwable throwable, String path) {
        super(throwable);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
