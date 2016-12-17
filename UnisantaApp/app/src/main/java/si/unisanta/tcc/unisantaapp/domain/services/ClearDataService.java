package si.unisanta.tcc.unisantaapp.domain.services;

import com.orm.SugarRecord;

import si.unisanta.tcc.unisantaapp.domain.model.IDeletable;

public class ClearDataService {
    private IDeletable[] toDeleteArray;

    public ClearDataService(IDeletable... toDeleteArray) {
        this.toDeleteArray = toDeleteArray;
    }

    public void clear() {
        for (IDeletable deletable : toDeleteArray) {
            deletable.deleteAll();
        }
        SugarRecord.executeQuery("VACUUM");
    }
}
