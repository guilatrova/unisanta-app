package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import si.unisanta.tcc.unisantaapp.domain.framework.CalendarDayComparator;
import si.unisanta.tcc.unisantaapp.domain.framework.TestDateComparator;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;

public class ComparatorTests {

    private CalendarDayComparator dayComparator;
    private TestDateComparator testComparator;

    @Before
    public void setUp() throws Exception {
        dayComparator = new CalendarDayComparator();
        testComparator = new TestDateComparator();
    }

    @Test
    public void dayComparatorDeveCalcularDiferencaDeDias() {
        DateTime dateToday = new DateTime(1, 7, 2016, 0, 0);
        DateTime dateUntil = new DateTime(15, 7, 2016, 0, 0);

        Assert.assertEquals(-14, dayComparator.compare(dateToday.toCalendar(), dateUntil.toCalendar()));
    }

    @Test
    public void dayComparatorDeveCalcularDiferencaDeDiasComHorasPassadas() throws Exception {
        DateTime dateToday = new DateTime(1, 7, 2016, 18, 0);
        DateTime dateUntil = new DateTime(15, 7, 2016, 14, 0);

        Assert.assertEquals(-14, dayComparator.compare(dateToday.toCalendar(), dateUntil.toCalendar()));
    }

    @Test
    public void dayComparatorDeveCalcularDiferencaDeDiasComHorasFuturas() throws Exception {
        DateTime dateToday = new DateTime(1, 7, 2016, 9, 0);
        DateTime dateUntil = new DateTime(15, 7, 2016, 18, 0);

        Assert.assertEquals(-14, dayComparator.compare(dateToday.toCalendar(), dateUntil.toCalendar()));
    }

    @Test
    public void testComparatorDeveCalcularDiferencaDeData() throws Exception {
        si.unisanta.tcc.unisantaapp.domain.entities.Test tRef = new si.unisanta.tcc.unisantaapp.domain.entities.Test();
        si.unisanta.tcc.unisantaapp.domain.entities.Test tFuturo = new si.unisanta.tcc.unisantaapp.domain.entities.Test();
        si.unisanta.tcc.unisantaapp.domain.entities.Test tPassado = new si.unisanta.tcc.unisantaapp.domain.entities.Test();

        tRef.setDatetime(new DateTime(7, 7, 2016, 0, 0));
        tFuturo.setDatetime(new DateTime(12, 7, 2016, 0, 0));
        tPassado.setDatetime(new DateTime(5, 7, 2016, 0, 0));

        Assert.assertEquals(0, testComparator.compare(tRef, tRef));
        Assert.assertEquals(-1, testComparator.compare(tRef, tFuturo));
        Assert.assertEquals(1, testComparator.compare(tRef, tPassado));
    }

    @Test
    public void testComparatorDeveCalcularDiferencaHoras() throws Exception {
        si.unisanta.tcc.unisantaapp.domain.entities.Test tRef = new si.unisanta.tcc.unisantaapp.domain.entities.Test();
        si.unisanta.tcc.unisantaapp.domain.entities.Test tFuturo = new si.unisanta.tcc.unisantaapp.domain.entities.Test();
        si.unisanta.tcc.unisantaapp.domain.entities.Test tPassado = new si.unisanta.tcc.unisantaapp.domain.entities.Test();

        tRef.setDatetime(new DateTime(7, 7, 2016, 14, 0));
        tFuturo.setDatetime(new DateTime(7, 7, 2016, 19, 0));
        tPassado.setDatetime(new DateTime(7, 7, 2016, 8, 0));

        Assert.assertEquals(0, testComparator.compare(tRef, tRef));
        Assert.assertEquals(-1, testComparator.compare(tRef, tFuturo));
        Assert.assertEquals(1, testComparator.compare(tRef, tPassado));
    }
}
