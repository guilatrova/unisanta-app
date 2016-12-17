package si.unisanta.tcc.unisantaapp.domain.valueobjects;

import java.util.Calendar;

public class SchoolYear implements Comparable<SchoolYear>{
    private int semester;
    private int year;

    public SchoolYear(String schoolYear) {
        year = Integer.parseInt(schoolYear.substring(0, 4));
        semester = Integer.parseInt(schoolYear.substring(5));
    }

    public SchoolYear(int year, int semester) {
        this.year = year;
        this.semester = semester;
    }

    public static SchoolYear getCurrent() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; //0 = January && 11 = December

        SchoolYear schoolYear;
        if (month <= 6) {
            schoolYear  = new SchoolYear(year, 1);
        } else {
            schoolYear = new SchoolYear(year, 2);
        }

        return schoolYear;
    }

    public int getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    /*
    public int countDistance(SchoolYear schoolYear) {
        if (schoolYear.isBefore(this))
            return -1;
        int count = 1;
        while (schoolYear.isAfter(this)) {
            schoolYear = schoolYear.prev();
            count++;
        }

        return count;
    }*/

    public int countDistanceFrom(SchoolYear schoolYear) {
        if (schoolYear.isAfter(this))
            return -1;

        int count = 1;
        while (schoolYear.isBefore(this)) {
            schoolYear = schoolYear.next();
            count++;
        }

        return count;
    }

    public boolean isAfter(SchoolYear schoolYear) {
        if (this.year > schoolYear.year)
            return true;
        if (this.year == schoolYear.year)
            return this.semester > schoolYear.semester;
        return false;
    }

    public boolean isBefore(SchoolYear schoolYear) {
        if (this.year < schoolYear.year)
            return true;
        if (this.year == schoolYear.year)
            return this.semester < schoolYear.semester;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SchoolYear that = (SchoolYear) o;

        if (semester != that.semester) return false;
        return year == that.year;

    }

    @Override
    public int hashCode() {
        int result = semester;
        result = 31 * result + year;
        return result;
    }

    public SchoolYear next() {
        if (semester == 2) {
            return new SchoolYear(year+1, 1);
        }
        return new SchoolYear(year, 2);
    }

    public SchoolYear prev() {
        if (semester == 1) {
            return new SchoolYear(year-1, 2);
        }
        return new SchoolYear(year, 1);
    }

    @Override
    public String toString() {
        return year + "-" + semester;
    }

    public String toString(char separator) {
        return year + "" + separator + semester;
    }

    @Override
    public int compareTo(SchoolYear schoolYear) {
        if (isBefore(schoolYear))
            return -1;

        if (this.isAfter(schoolYear))
            return 1;

        return 0;
    }
}
