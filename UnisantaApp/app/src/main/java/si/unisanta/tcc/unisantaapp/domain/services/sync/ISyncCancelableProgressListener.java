package si.unisanta.tcc.unisantaapp.domain.services.sync;

public interface ISyncCancelableProgressListener<T> extends ISyncProgressListener<T> {
    void onCancel();
}
