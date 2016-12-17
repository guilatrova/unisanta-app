package si.unisanta.tcc.unisantaapp.domain.factories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.entities.Test;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;

public class TestFactory {
    public static Test createTest(String title, String time, Subject subject, String classroom) throws ParseException {
        String strWeight = title.substring(
                title.indexOf(' ')+2,
                title.lastIndexOf('-')
        ).trim();

        String date = title.substring(title.lastIndexOf(':') + 2).trim();

        int blockStart = classroom.indexOf("Bloco");
        String room = classroom.substring(0, blockStart).replace("Sala", "").trim();
        String block = classroom.substring(blockStart + 6, blockStart + 7);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        cal.setTime(sdf.parse(date + " " + time));

        DateTime dateTime = new DateTime(
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE)
        );
        int weight = getWeight(strWeight);
        Classroom testClassroom = new Classroom(block.charAt(0), room);

        return new Test(testClassroom, dateTime, subject, weight);
    }

    private static int getWeight(String strWeight) {
        switch (strWeight) {
            case "P1":
                return Test.P1;

            case "P2":
                return Test.P2;

            default:
                return Test.P3;
        }
    }
}
