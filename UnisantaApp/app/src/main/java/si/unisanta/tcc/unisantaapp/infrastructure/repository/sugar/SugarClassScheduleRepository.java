package si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar;

import com.google.common.collect.Lists;

import java.util.Calendar;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class SugarClassScheduleRepository implements IClassScheduleRepository {
    @Override
    public void deleteAll() {
        ClassSchedule.deleteAll(ClassSchedule.class);
        ClassSchedule.executeQuery("delete from sqlite_sequence where name='CLASS_SCHEDULE';");
    }

    @Override
    public List<ClassSchedule> findAll() {
        return Lists.newArrayList(ClassSchedule.findAll(ClassSchedule.class));
    }

    @Override
    public List<ClassSchedule> findBySchoolYear(SchoolYear schoolYear) {
        return Lists.newArrayList(ClassSchedule.find(
                ClassSchedule.class,
                "semester = ?",
                schoolYear.toString()));
    }

    @Override
    public List<ClassSchedule> findClassesOfDay(int weekDay) {
        return Lists.newArrayList(ClassSchedule.find(
                ClassSchedule.class,
                "week_day = ?",
                Integer.toString(weekDay)
        ));
    }

    @Override
    public List<ClassSchedule> findNextClasses() {
        List<ClassSchedule> lstAux;
        Calendar cal = Calendar.getInstance();
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String query = "week_day = " +
                "(SELECT MIN(week_day) FROM CLASS_SCHEDULE WHERE " +
                "(week_day == ? AND end_hour > ?) OR " +
                "(week_day > ?))";

        /*
         * Pegando depois do dia atual
         * ex: Hoje é quarta, vou procurar a partir de quinta
         */
        lstAux =
            Lists.newArrayList(ClassSchedule.find(
                ClassSchedule.class, query,
                    Integer.toString(weekDay),
                    Integer.toString(hour),
                    Integer.toString(weekDay)
        ));

        /*
         * Se não encontrei, quer dizer que depois daquele dia não há mais aulas na semana
         * Por tanto, vou procurar de novo, mas apartir do dia semana
         * Ex: Hoje é sexta, não tenho aula sábado, vou procurar a partir de domingo
         */
        if (lstAux.size() == 0) {
            lstAux = Lists.newArrayList(ClassSchedule.find(
                    ClassSchedule.class, query,
                    Integer.toString(1),
                    Integer.toString(hour),
                    Integer.toString(1)
            ));
        }

        return lstAux;
    }

    @Override
    public boolean anyClassThisSchoolYear(SchoolYear schoolYear) {
        return ClassSchedule.count(ClassSchedule.class,
                "semester = ?",
                new String[] {schoolYear.toString()}) > 0;
    }

    @Override
    public ClassSchedule findBySubjectAndDateTime(Subject subject, int weekDay, Time start, Time end) {
        List<ClassSchedule> classes = ClassSchedule.find(
                ClassSchedule.class,
                "subject = ? and week_day = ? " +
                        "and start_hour = ? and start_minute = ? " +
                        "and end_hour = ? and end_minute = ?",
                Long.toString(subject.getId()),
                Integer.toString(weekDay),
                Integer.toString(start.getHour()),
                Integer.toString(start.getMinute()),
                Integer.toString(end.getHour()),
                Integer.toString(end.getMinute())
        );

        if (classes.size() > 0)
            return classes.get(0);

        return null;
    }

    @Override
    public long saveClassSchedule(ClassSchedule classSchedule) {
        return classSchedule.save();
    }
}
