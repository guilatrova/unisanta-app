package si.unisanta.tcc.unisantaapp.domain.services.sync;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;

public abstract class SyncBaseData {
    private List<SyncBaseData> dependentsList = new ArrayList<>();

    public abstract void sync() throws SyncFailedException;
    public abstract String getMessage();

    public SyncBaseData[] getDependents() {
        return dependentsList.toArray(new SyncBaseData[dependentsList.size()]);
    }

    public void addDependent(SyncBaseData syncBaseData) {
        if (syncBaseData != null)
            dependentsList.add(syncBaseData);
    }
}