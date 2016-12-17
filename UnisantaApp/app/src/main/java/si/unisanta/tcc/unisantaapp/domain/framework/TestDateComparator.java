package si.unisanta.tcc.unisantaapp.domain.framework;

import java.util.Calendar;
import java.util.Comparator;

import si.unisanta.tcc.unisantaapp.domain.entities.Test;

public class TestDateComparator implements Comparator<Test> {
    @Override
    public int compare(Test test, Test t1) {
        Calendar dt1 = test.getDatetime().toCalendar();
        Calendar dt2 = t1.getDatetime().toCalendar();

        if (dt1.getTimeInMillis() < dt2.getTimeInMillis())
            return -1;
        if (dt1.getTimeInMillis() > dt2.getTimeInMillis())
            return 1;
        return 0;
    }
}
