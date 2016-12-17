package si.unisanta.tcc.unisantaapp.domain.dto;

import java.util.TreeSet;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SyncDTO implements Comparable<SyncDTO>{
    private int what;
    private SchoolYear extraSchoolYear;
    private Subject extraSubject;

    private static TreeSet<SchoolYear> schoolYears = new TreeSet<>();

    public static SchoolYear[] getSchoolYears() {
        return schoolYears.toArray(new SchoolYear[schoolYears.size()]);
    }

    public static void clearCache() {
        schoolYears.clear();
    }

    public SyncDTO(int what, SchoolYear schoolYear) {
        this.what = what;
        this.extraSchoolYear = schoolYear;

        if (!schoolYears.contains(schoolYear))
            schoolYears.add(schoolYear);
    }

    public SyncDTO(int what) {
        this(what, SchoolYear.getCurrent());
    }

    public SyncDTO(int what, Subject subject) {
        this(what, subject.getSchoolYear());

        extraSubject = subject;
    }

    public int getWhat() {
        return what;
    }

    public SchoolYear getExtraSchoolYear() {
        return extraSchoolYear;
    }

    public Subject getExtraSubject() {
        return extraSubject;
    }

    @Override
    public int compareTo(SyncDTO syncDTO) {
        if (extraSchoolYear.equals(syncDTO.extraSchoolYear)) {
            if (what == syncDTO.what) {
                return 0;
            }
            else if (what < syncDTO.what) {
                return -1;
            }

            return 1;
        }

        if (getExtraSubject() != null) {
            if (!getExtraSubject().equals(syncDTO.getExtraSubject())) {
                return 1;
            }
        }
        else if(syncDTO.getExtraSubject() != null) {
            return -1;
        }

        return extraSchoolYear.compareTo(syncDTO.extraSchoolYear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SyncDTO syncDTO = (SyncDTO) o;

        if (what != syncDTO.what) return false;
        if (!extraSchoolYear.equals(syncDTO.extraSchoolYear)) return false;
        return extraSubject != null ? extraSubject.equals(syncDTO.extraSubject) : syncDTO.extraSubject == null;

    }

    @Override
    public int hashCode() {
        int result = what;
        result = 31 * result + extraSchoolYear.hashCode();
        result = 31 * result + (extraSubject != null ? extraSubject.hashCode() : 0);
        return result;
    }
}
