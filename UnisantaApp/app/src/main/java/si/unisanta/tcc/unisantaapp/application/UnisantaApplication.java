package si.unisanta.tcc.unisantaapp.application;

import android.content.pm.PackageManager;
import android.util.Log;

import com.orm.SugarApp;

public class UnisantaApplication extends SugarApp {
    public static final String APP_TAG = "UNISANTA_APP";
    private static UnisantaApplication instance;

    public static final boolean DEBUG_MODE = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static UnisantaApplication getInstance() {
        return instance;
    }

    public static void Log_i(String message) {
        if (DEBUG_MODE)
            Log.i(APP_TAG, message);
    }

    public static void Log_e(String message, Exception e) {
        if (DEBUG_MODE) {
            if (message != null)
                Log.e(APP_TAG, message);
            if (e != null)
                e.printStackTrace();
        }
    }

    public static void Log_e(String message) {
        Log_e(message, null);
    }

    public static void Log_d(String message) {
        if (DEBUG_MODE)
            Log.d(APP_TAG, message);
    }

    public static String getVersionName() {
        try {
            return
                getInstance().getPackageManager().getPackageInfo(
                    getInstance().getPackageName(), 0
                ).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log_e("Não consegui obter a versão");
            return "0.0";
        }
    }

    public static int getResourceId(String pVariableName, String pResourcename) {
        try {
            return getInstance().
                    getResources().
                    getIdentifier(pVariableName, pResourcename, getInstance().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getStringId(String name) {
        return getResourceId(name, "string");
    }
}
