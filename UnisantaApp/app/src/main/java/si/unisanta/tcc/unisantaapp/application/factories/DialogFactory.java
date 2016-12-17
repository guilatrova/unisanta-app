package si.unisanta.tcc.unisantaapp.application.factories;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.SnackDialogAdapter;
import si.unisanta.tcc.unisantaapp.application.config.EditProfileActivity;
import si.unisanta.tcc.unisantaapp.application.services.ColoredSnackbar;
import si.unisanta.tcc.unisantaapp.application.services.UserErrorMessageHelper;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UserChangedPasswordException;

public class DialogFactory {
    public Context context;
    private static final int SNACK_MAX_LINES = 5;

    public DialogFactory(Context context) {
        this.context = context;
    }

    public Dialog createDialogFromException(Exception e, Activity activity) {
        if (hasAction(e)) {
            Snackbar snackbar = ColoredSnackbar.alert(
                    Snackbar.make(activity.findViewById(android.R.id.content),
                        UserErrorMessageHelper.getMessage(e),
                        Snackbar.LENGTH_LONG)
            );

            snackbar.setActionTextColor(context.getColor(R.color.white));
            setMaxLines(snackbar);
            setAction(snackbar, e);
            return new SnackDialogAdapter(activity, snackbar);
        }

        return new AlertDialog.Builder(activity)
            .setTitle(R.string.error_title_dialog)
            .setMessage(UserErrorMessageHelper.getMessage(e))
            .setNeutralButton("OK", null).create();
    }

    private boolean hasAction(Exception e) {
        if (e instanceof UserChangedPasswordException)
            return true;
        return false;
    }

    private void setMaxLines(Snackbar snackbar) {
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
    }

    private void setAction(Snackbar snackbar, Exception e) {
        if (e instanceof UserChangedPasswordException) {
            snackbar.setAction(R.string.update, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditProfileActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
