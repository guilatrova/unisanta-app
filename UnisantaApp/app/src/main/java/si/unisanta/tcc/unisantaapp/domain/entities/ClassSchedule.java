package si.unisanta.tcc.unisantaapp.domain.entities;

import com.orm.SugarRecord;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class ClassSchedule extends SugarRecord{
    private int weekDay;
    private String classroom;
    private Subject subject;

    //startTime
    private int startHour;
    private int startMinute;

    //endTime
    private int endHour;
    private int endMinute;

    private String semester;

    public ClassSchedule(int weekDay, Classroom classroom, Subject subject, Time startTime, Time endTime, SchoolYear schoolYear) {
        this.weekDay = weekDay;
        this.classroom = classroom.toString();
        this.subject = subject;
        this.startHour = startTime.getHour();
        this.startMinute = startTime.getMinute();
        this.endHour = endTime.getHour();
        this.endMinute = endTime.getMinute();
        this.semester = schoolYear.toString();
    }

    public ClassSchedule() {
    }

    public int getWeekDay() {
        return weekDay;
    }

    public Classroom getClassroom() {
        return new Classroom(classroom);
    }

    public Subject getSubject() {
        return subject;
    }

    public Time getStartTime() {
        return new Time(startHour, startMinute);
    }

    public Time getEndTime() {
        return new Time(endHour, endMinute);
    }

    public String getSchedule() {
        return String.format("%02d:%02d - %02d:%02d", startHour, startMinute, endHour, endMinute);
    }

    public SchoolYear getSemester() {
        return new SchoolYear(semester);
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom.toString();
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setStartTime(Time time) {
        this.startHour = time.getHour();
        this.startMinute = time.getMinute();
    }

    public void setEndHour(Time time) {
        this.endHour = time.getHour();
        this.endMinute = time.getMinute();
    }

    public void setSemester(SchoolYear schoolYear) {
        this.semester = schoolYear.toString();
    }
}
