package si.unisanta.tcc.unisantaapp.domain.model;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public interface IClassScheduleRepository extends IDeletable {
    List<ClassSchedule> findAll();
    List<ClassSchedule> findBySchoolYear(SchoolYear schoolYear);
    List<ClassSchedule> findClassesOfDay(int weekDay);
    List<ClassSchedule> findNextClasses();
    boolean anyClassThisSchoolYear(SchoolYear schoolYear);
    ClassSchedule findBySubjectAndDateTime(Subject subject, int weekDay, Time start, Time end);
    long saveClassSchedule(ClassSchedule classSchedule);
}
