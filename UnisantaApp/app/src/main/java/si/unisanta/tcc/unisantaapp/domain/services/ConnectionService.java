package si.unisanta.tcc.unisantaapp.domain.services;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Guilherme on 26/01/2016.
 */
public class ConnectionService {
    private ConnectivityManager connectivityManager;

    public ConnectionService(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    public boolean isOnline() {
        //ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
