package si.unisanta.tcc.unisantaapp.application.factories;

import android.content.Context;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.services.AppRater;

public class AppRaterFactory {
    public static AppRater createAppRater(Context context) {
        AppRater appRater = new AppRater(context);

        appRater.setDaysBeforePrompt(5);
        appRater.setLaunchesBeforePrompt(6);
        appRater.setPhrases(R.string.rate_title, R.string.rate_message, R.string.rate_now, R.string.rate_later, R.string.rate_never);

        return appRater;
    }
}
