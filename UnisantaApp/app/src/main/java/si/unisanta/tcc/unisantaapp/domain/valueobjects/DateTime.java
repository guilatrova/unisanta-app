package si.unisanta.tcc.unisantaapp.domain.valueobjects;

import java.util.Calendar;
import java.util.TimeZone;

public class DateTime extends Time {
    private int day;
    private int month;
    private int year;

    public DateTime(int day, int month, int year, int hour, int minute) {
        super(hour, minute);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public DateTime(long unix) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(unix * 1000);
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }

    public DateTime(Calendar cal) {
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }

    public Time getTime() {
        return new Time(hour, minute);
    }

    public long toUnixTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, 0);
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        return cal.getTimeInMillis() / 1000;
    }

    public Calendar toCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, minute, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        return cal;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DateTime dateTime = (DateTime) o;

        if (day != dateTime.day) return false;
        if (month != dateTime.month) return false;
        return year == dateTime.year;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%d às %s", day, month, year, super.toString());
    }
}
