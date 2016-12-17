package si.unisanta.tcc.unisantaapp.application.services;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;

public class IntentHelper {
    private static final String FACEBOOK_URL = "https://www.facebook.com/unisantaapp";
    private static final String FACEBOOK_PAGE_ID = "1739443639633267";

    public static Intent createIntentForURL(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    public static Intent createIntentForFacebookPage() {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String uri = getFacebookPageUri();
        facebookIntent.setData(Uri.parse(uri));
        return facebookIntent;
    }

    private static String getFacebookPageUri() {
        Context context = UnisantaApplication.getInstance();
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}
