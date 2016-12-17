package tests;

import junit.framework.Assert;

import org.junit.Test;

import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class CoursewareTest {
    @Test
    public void testCalcSize() throws Exception {
        Courseware coursewareKB = createCourseware(null, "453,52 KB");
        long sizeBytes = coursewareKB.calcSizeInBytes();

        Assert.assertEquals(453520, sizeBytes);

        Courseware coursewareMB = createCourseware(null, "1,39 MB");
        sizeBytes = coursewareMB.calcSizeInBytes();

        Assert.assertEquals(1390000, sizeBytes);
    }

    @Test
    public void testFileName() throws Exception {
        Courseware pdf = createCourseware("NBC TA 315 (PDF)", null);

        Assert.assertEquals("NBC_TA_315.pdf", pdf.getFileName());

        Courseware zip = createCourseware("BIM02SEMANA12 (ZIP)", null);

        Assert.assertEquals("BIM02SEMANA12.zip", zip.getFileName());
    }

    public Courseware createCourseware(String titulo, String size)
    {
        return new Courseware(
                null,
                titulo,
                "qualquer",
                "10/10/2012",
                new Time(10, 52),
                size,
                "any"
        );
    }
}
