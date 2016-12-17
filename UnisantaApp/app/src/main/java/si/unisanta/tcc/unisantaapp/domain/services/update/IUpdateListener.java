package si.unisanta.tcc.unisantaapp.domain.services.update;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;

public interface IUpdateListener {
    void handleUpdate(AppVersion oldVersion, AppVersion newVersion);
}
