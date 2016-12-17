package tests;

import junit.framework.Assert;

import org.junit.Test;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SchoolYearTest {

    @Test
    public void testIsAfter() throws Exception {
        SchoolYear ref = new SchoolYear(2016, 1);

        Assert.assertTrue(ref.isAfter(new SchoolYear("2014/1")));
        Assert.assertTrue(ref.isAfter(new SchoolYear("2014/2")));
        Assert.assertTrue(ref.isAfter(new SchoolYear("2015/1")));
        Assert.assertTrue(ref.isAfter(new SchoolYear("2015/2")));

        Assert.assertFalse(ref.isAfter(new SchoolYear("2016/1")));
        Assert.assertFalse(ref.isAfter(new SchoolYear("2016/2")));
        Assert.assertFalse(ref.isAfter(new SchoolYear("2017/1")));
        Assert.assertFalse(ref.isAfter(new SchoolYear("2017/2")));
    }

    @Test
    public void testIsBefore() throws Exception {
        SchoolYear ref = new SchoolYear(2014, 1);

        Assert.assertFalse(ref.isBefore(new SchoolYear("2012/2")));
        Assert.assertFalse(ref.isBefore(new SchoolYear("2013/1")));
        Assert.assertFalse(ref.isBefore(new SchoolYear("2013/2")));
        Assert.assertFalse(ref.isBefore(new SchoolYear("2014/1")));

        Assert.assertTrue(ref.isBefore(new SchoolYear("2014/2")));
        Assert.assertTrue(ref.isBefore(new SchoolYear("2015/1")));
        Assert.assertTrue(ref.isBefore(new SchoolYear("2016/1")));
        Assert.assertTrue(ref.isBefore(new SchoolYear("2016/2")));
    }
}
