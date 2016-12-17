package si.unisanta.tcc.unisantaapp.infrastructure.repository.device;

import android.content.Context;
import android.content.SharedPreferences;

import si.unisanta.tcc.unisantaapp.domain.model.IUserRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class SharedPreferencesRepository implements IUserRepository {
    private static final String APP_FILE_NAME = "si.unisanta.tcc.unisantaapp.app";

    private static final String FIRST_YEAR_KEY = "si.unisanta.tcc.unisantaapp.first_year";
    private static final String NAME_KEY = "si.unisanta.tcc.unisantaapp.student";
    private static final String PASS_KEY = "si.unisanta.tcc.unisantaapp.pass";
    private static final String RA_KEY = "si.unisanta.tcc.unisantaapp.ra";
    private static final String COURSE_KEY = "si.unisanta.tcc.unisantaapp.course";
    private static final String LAST_VERSION = "si.unisanta.tcc.unisantaapp.last_version";

    private Context context;

    public SharedPreferencesRepository(Context context) {
        this.context = context;
    }

    @Override
    public boolean retrieve() {
        SharedPreferences prefs = context.getSharedPreferences(APP_FILE_NAME, Context.MODE_PRIVATE);

        String name = prefs.getString(NAME_KEY, "");
        if (name.length() > 0) {
            String ra = prefs.getString(RA_KEY, "");
            String password = prefs.getString(PASS_KEY, "");
            String semester = prefs.getString(FIRST_YEAR_KEY, "");
            String course = prefs.getString(COURSE_KEY, "");

            User.init(
                    new SchoolYear(decrypt(semester)),
                    (name),
                    (course),
                    decrypt(ra),
                    decrypt(password)
            );

            String lastVersion = prefs.getString(LAST_VERSION, "<3");
            User.getInstance().setAppVersion(new AppVersion(lastVersion));

            return true;
        }

        return false;
    }

    @Override
    public void save(User user) {
        SharedPreferences settings = context.getSharedPreferences(APP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(NAME_KEY, user.getName());
        editor.putString(PASS_KEY, encrypt(user.getPassword()));
        editor.putString(COURSE_KEY, user.getCourse());
        editor.putString(RA_KEY, encrypt(user.getRA()));
        editor.putString(FIRST_YEAR_KEY, encrypt(user.getFirstSchoolYear().toString()));
        editor.putString(LAST_VERSION, user.getAppVersion().toString());

        editor.commit();
    }

    private String encrypt(String data) {
        String crypt = "";

        int f1 = 1;
        int f2 = 0;
        for (int i = 0; i < data.length(); i++) {
            int next = f1 + f2;
            f1 = f2;
            f2 = next;
            crypt += (char)(data.charAt(i) + next);
        }
        return crypt;
    }

    private String decrypt(String encrypted) {
        String decrypt = "";

        int f1 = 1;
        int f2 = 0;
        for (int i = 0; i < encrypted.length(); i++) {
            int next = f1 + f2;
            f1 = f2;
            f2 = next;
            decrypt += (char)(encrypted.charAt(i) - next);
        }
        return decrypt;
    }

    @Override
    public void deleteAll() {
        SharedPreferences settings = context.getSharedPreferences(APP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        
        editor.remove(NAME_KEY);
        editor.remove(PASS_KEY);
        editor.remove(COURSE_KEY);
        editor.remove(RA_KEY);
        editor.remove(FIRST_YEAR_KEY);
        
        editor.commit();
    }
}
