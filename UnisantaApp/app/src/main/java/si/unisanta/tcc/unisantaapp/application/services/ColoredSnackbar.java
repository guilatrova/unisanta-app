package si.unisanta.tcc.unisantaapp.application.services;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;

public class ColoredSnackbar {

    private static final int red = 0xfff44336;
    private static final int green = 0xff4caf50;
    private static final int blue = 0xff2195f3;
    private static final int orange = 0xffffc107;

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, int colorId) {
        View snackBarView = getSnackBarLayout(snackbar);
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
        }

        snackbar.setActionTextColor(ContextCompat.getColor(UnisantaApplication.getInstance(), R.color.white));
        return snackbar;
    }

    public static Snackbar info(Snackbar snackbar) {
        return colorSnackBar(snackbar, blue);
    }

    public static Snackbar warning(Snackbar snackbar) {
        return colorSnackBar(snackbar, orange);
    }

    public static Snackbar alert(Snackbar snackbar) {
        return colorSnackBar(snackbar, red);
    }

    public static Snackbar confirm(Snackbar snackbar) {
        return colorSnackBar(snackbar, green);
    }
}