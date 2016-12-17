package si.unisanta.tcc.unisantaapp.application;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;

public class SnackDialogAdapter extends Dialog {
    private Snackbar snackbar;

    public SnackDialogAdapter(Context context, Snackbar snackbar) {
        super(context);
        this.snackbar = snackbar;
    }

    @Override
    public void show() {
        snackbar.show();
    }
}
