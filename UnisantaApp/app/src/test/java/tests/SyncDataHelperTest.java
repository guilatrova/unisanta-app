package tests;

import junit.framework.Assert;

import org.junit.Test;

import si.unisanta.tcc.unisantaapp.infrastructure.services.sync.SyncDataHelper;

public class SyncDataHelperTest {
    @Test
    public void testDecoding() throws Exception {
        Assert.assertEquals(
            "GESTÃO DA INFORMÇÃO",
            SyncDataHelper.fixSpecialCharacters("GEST&#195;O DA INFORM&#199;&#195;O"));

        Assert.assertEquals(
            "MARESSA D' PAULA GONÇALVES ROSA NOGUEIRA",
            SyncDataHelper.fixSpecialCharacters("MARESSA D&#39; PAULA GON&#199;ALVES ROSA NOGUEIRA"));
    }
}
