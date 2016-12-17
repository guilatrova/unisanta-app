package si.unisanta.tcc.unisantaapp.domain.valueobjects;

public class User {
    private static User instance;

    private String name;
    private String password;
    private String RA;
    private SchoolYear firstSchoolYear;
    private String course;
    private String appVersion;

    private User(SchoolYear firstSchoolYear, String name, String course, String RA, String password) {
        this.firstSchoolYear = firstSchoolYear;
        this.name = name;
        this.password = password;
        this.RA = RA;
        this.course = course;
    }

    public static void init(SchoolYear firstSchoolYear, String name, String course, String RA, String password) {
        instance = new User(firstSchoolYear, name, course, RA, password);
    }

    public static User getInstance() {
        return instance;
    }

    public void setAppVersion(AppVersion appVersion) {
        this.appVersion = appVersion.toString();
    }

    public AppVersion getAppVersion() {
        if (appVersion.isEmpty())
            return null;
        return new AppVersion(appVersion);
    }

    public SchoolYear getFirstSchoolYear() {
        return firstSchoolYear;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRA() {
        return RA;
    }

    public String getCourse() {
        return course;
    }
}
