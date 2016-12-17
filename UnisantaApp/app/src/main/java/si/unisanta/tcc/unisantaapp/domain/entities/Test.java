package si.unisanta.tcc.unisantaapp.domain.entities;

import com.orm.SugarRecord;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;

public class Test extends SugarRecord{
    public static final int P1 = 1;
    public static final int P2 = 2;
    public static final int P3 = 3;

    private String classroom;
    private long datetime;
    private Subject subject;
    private int weight;

    public Test() {}

    public Test(Classroom classroom, DateTime datetime, Subject subject, int weight) {
        this.classroom = classroom.toString();
        this.datetime = datetime.toUnixTime();
        this.subject = subject;
        this.weight = weight;
    }

    public Classroom getClassroom() {
        return new Classroom(classroom);
    }

    public DateTime getDatetime() {
        return new DateTime(datetime);
    }

    public Subject getSubject() {
        return subject;
    }

    public int getWeight() {
        return weight;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom.toString();
    }

    public void setDatetime(DateTime datetime) {
        this.datetime = datetime.toUnixTime();
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
