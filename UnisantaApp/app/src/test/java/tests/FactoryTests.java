package tests;

import junit.framework.Assert;

import org.junit.Test;

import si.unisanta.tcc.unisantaapp.domain.factories.TestFactory;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;

public class FactoryTests {
    @Test
    public void testCreateTest() throws Exception {
        si.unisanta.tcc.unisantaapp.domain.entities.Test t
                = TestFactory.createTest("Segunda-Feira - P1 - Prova: 04/04/2016",
                "21:00", null,
                "Sala 34 Bloco D 3º Andar");

        DateTime dateTime = t.getDatetime();
        Classroom classroom = t.getClassroom();

        Assert.assertEquals(4, dateTime.getDay());
        Assert.assertEquals(4, dateTime.getMonth());
        Assert.assertEquals(2016, dateTime.getYear());
        Assert.assertEquals(21, dateTime.getHour());
        Assert.assertEquals(0, dateTime.getMinute());
        Assert.assertEquals(1, t.getWeight());
        Assert.assertEquals('D', classroom.getBlock());
        Assert.assertEquals("34", classroom.getRoom());
        Assert.assertEquals("D34", classroom.toString());
    }
}
