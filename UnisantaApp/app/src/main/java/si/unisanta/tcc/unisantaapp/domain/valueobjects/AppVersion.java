package si.unisanta.tcc.unisantaapp.domain.valueobjects;

public class AppVersion implements Comparable<AppVersion>{
    private int primaryVersion;
    private int secondaryVersion;
    private String versionName;

    public AppVersion(String version) {
        versionName = version;
        int start = 0;
        if (versionName.contains("<")) {
            start = 1;
        }

        int end = version.length();
        if (versionName.contains(".")) {
            end = version.indexOf(".");
            secondaryVersion = Integer.parseInt(versionName.substring(end + 1));
        }
        else {
            secondaryVersion = 0;
        }

        primaryVersion = Integer.parseInt(
                version.substring(start, end));
    }

    public int getPrimaryVersion() {
        return primaryVersion;
    }

    public int getSecondaryVersion() {
        return secondaryVersion;
    }

    public boolean isBefore(AppVersion that) {
        if (primaryVersion == that.primaryVersion) {
            if (versionName.contains("<")) {
                if (!that.versionName.contains("<"))
                    return true;
            }
            else if(that.versionName.contains("<")) {
                return false;
            }

            return (secondaryVersion < that.secondaryVersion);
        }

        return (primaryVersion < that.primaryVersion);
    }

    public boolean isAfter(AppVersion that) {
        if (primaryVersion == that.primaryVersion) {
            if (versionName.contains("<")) {
                if (!that.versionName.contains("<"))
                    return false;
            }
            else if(that.versionName.contains("<")) {
                return true;
            }

            return (secondaryVersion > that.secondaryVersion);
        }

        return (primaryVersion > that.primaryVersion);
    }

    @Override
    public int compareTo(AppVersion appVersion) {
        if (isBefore(appVersion))
            return -1;

        if (isAfter(appVersion))
            return 1;

        return 0;
    }

    @Override
    public boolean equals(Object o) { //TODO adicionar tratativa para string
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppVersion that = (AppVersion) o;

        if (primaryVersion != that.primaryVersion) return false;
        if (secondaryVersion != that.secondaryVersion) return false;

        if (versionName.contains("<")) {
            return that.versionName.contains("<");
        }
        return !that.versionName.contains("<");
    }

    @Override
    public int hashCode() {
        int result = primaryVersion;
        result = 31 * result + secondaryVersion;
        return result;
    }

    @Override
    public String toString() {
        return versionName;
    }
}
