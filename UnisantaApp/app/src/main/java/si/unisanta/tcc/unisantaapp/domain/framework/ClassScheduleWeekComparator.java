package si.unisanta.tcc.unisantaapp.domain.framework;

import java.util.Comparator;

import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;

public class ClassScheduleWeekComparator implements Comparator<ClassSchedule> {
    @Override
    public int compare(ClassSchedule classSchedule, ClassSchedule t1) {
        if (classSchedule.getWeekDay() == t1.getWeekDay()) {
            return classSchedule.getStartTime().getHour() - t1.getStartTime().getHour();
        }
        return classSchedule.getWeekDay() - t1.getWeekDay();
    }
}
