package si.unisanta.tcc.unisantaapp.domain.services;

import com.orm.SugarRecord;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;

public class SqliteHelper {
    public static void vacuum() {
        SugarRecord.executeQuery("VACUUM");
        UnisantaApplication.Log_i("Feito vacuum no banco");
    }
}
