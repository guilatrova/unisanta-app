package tests;

import junit.framework.Assert;

import org.junit.Test;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;

public class VersionTest {

    @Test
    public void testConstructor() throws Exception {
        AppVersion appVersion = new AppVersion("<3");

        Assert.assertEquals(3, appVersion.getPrimaryVersion());
        Assert.assertEquals(0, appVersion.getSecondaryVersion());

        appVersion = new AppVersion("<4.2");

        Assert.assertEquals(4, appVersion.getPrimaryVersion());
        Assert.assertEquals(2, appVersion.getSecondaryVersion());

        appVersion = new AppVersion("<52.12");

        Assert.assertEquals(52, appVersion.getPrimaryVersion());
        Assert.assertEquals(12, appVersion.getSecondaryVersion());

        appVersion = new AppVersion("22.12");

        Assert.assertEquals(22, appVersion.getPrimaryVersion());
        Assert.assertEquals(12, appVersion.getSecondaryVersion());
    }

    @Test
    public void testIsBefore() throws Exception {
        AppVersion ref = new AppVersion("12");

        Assert.assertEquals(false, ref.isBefore(new AppVersion("11.9")));
        Assert.assertEquals(false, ref.isBefore(new AppVersion("12.0")));
        Assert.assertEquals(false, ref.isBefore(new AppVersion("<12.0")));

        ref = new AppVersion("<12");
        
        Assert.assertEquals(true, ref.isBefore(new AppVersion("12")));
        Assert.assertEquals(true, ref.isBefore(new AppVersion("12.0")));
        Assert.assertEquals(true, ref.isBefore(new AppVersion("13")));

        ref = new AppVersion("<3");

        Assert.assertEquals(true, ref.isBefore(new AppVersion("3")));
    }

    @Test
    public void testIsAfter() throws Exception {
        AppVersion ref = new AppVersion("22.7");

        Assert.assertEquals(false, ref.isAfter(new AppVersion("22.7")));
        Assert.assertEquals(false, ref.isAfter(new AppVersion("22.8")));
        Assert.assertEquals(false, ref.isAfter(new AppVersion("23")));

        ref = new AppVersion("<22");

        Assert.assertEquals(true, ref.isAfter(new AppVersion("12")));
        Assert.assertEquals(true, ref.isAfter(new AppVersion("21")));
        Assert.assertEquals(true, ref.isAfter(new AppVersion("20")));
    }

    @Test
    public void testEquals() throws Exception {
        AppVersion ref = new AppVersion("22.0");

        Assert.assertEquals(true, ref.equals(new AppVersion("22.0")));
        Assert.assertEquals(true, ref.equals(new AppVersion("22")));
        Assert.assertEquals(false, ref.equals(new AppVersion("22.1")));

        AppVersion lessThan = new AppVersion("<7");

        Assert.assertEquals(false, lessThan.equals(new AppVersion("7")));
        Assert.assertEquals(false, lessThan.equals(new AppVersion("7.0")));
    }
}
