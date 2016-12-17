package si.unisanta.tcc.unisantaapp.domain.framework;

import java.util.Calendar;
import java.util.Comparator;

public class CalendarDayComparator implements Comparator<Calendar> {
    @Override
    public int compare(Calendar dt1, Calendar dt2) {
        resetTime(dt1);
        resetTime(dt2);

        long diff = dt1.getTimeInMillis() - dt2.getTimeInMillis();
        return (int)(diff / (24 * 60 * 60 * 1000)); //Diff in days
    }

    private void resetTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}