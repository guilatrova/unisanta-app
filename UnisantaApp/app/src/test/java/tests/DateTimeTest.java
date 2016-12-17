package tests;

import junit.framework.Assert;

import org.junit.Test;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;

public class DateTimeTest {

    @Test
    public void testDateTimeToUnixConversion() throws Exception {
        DateTime datetime = new DateTime(4, 4, 2016, 21, 0);

        Assert.assertEquals(datetime.getDay(), 4);
        Assert.assertEquals(datetime.getMonth(), 4);
        Assert.assertEquals(datetime.getYear(), 2016);
        Assert.assertEquals(datetime.getHour(), 21);
        Assert.assertEquals(datetime.getMinute(), 0);
        Assert.assertEquals(1459803600, datetime.toUnixTime());
    }

    @Test
    public void testUnitToDateTimeConversion() throws Exception {
        long unix = 1459803600L;

        DateTime datetime = new DateTime(unix);
        Assert.assertEquals(4, datetime.getDay());
        Assert.assertEquals(4, datetime.getMonth()); //0 based index
        Assert.assertEquals(2016, datetime.getYear());
        Assert.assertEquals(21, datetime.getHour());
        Assert.assertEquals(0, datetime.getMinute());
    }
}
