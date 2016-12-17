package si.unisanta.tcc.unisantaapp.domain.factories;

import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class ClassScheduleFactory {
    public static ClassSchedule createClassSchedule(int weekDay, String classroom, Subject subject, Time start, Time end, SchoolYear schoolYear) {
        return new ClassSchedule(
                weekDay,
                new Classroom(classroom),
                subject,
                start,
                end,
                schoolYear
        );
    }
}