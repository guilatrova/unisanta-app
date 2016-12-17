package si.unisanta.tcc.unisantaapp.domain.services.sync;

public interface ISyncProgressListener<T> {
    void onStarting();
    void onMessageChange(String msg);
    void onProgressChange(int progress);
    void onSuccess(T result);
    void onFailure(Exception e);
}
