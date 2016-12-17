package si.unisanta.tcc.unisantaapp.domain.entities;

import com.orm.SugarRecord;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class Grade extends SugarRecord {
    public static final int P1 = 1;
    public static final int P2 = 2;
    public static final int P3 = 3;
    public static final int FINAL = 4;

    private float score;
    private String semester;
    private Subject subject;
    private int weight;

    public Grade() {
    }

    public Grade(float score, SchoolYear schoolYear, Subject subject, int weight) {
        this.score = score;
        this.semester = schoolYear.toString();
        this.subject = subject;
        this.weight = weight;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public SchoolYear getSchoolYear() {
        return new SchoolYear(semester);
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        semester = schoolYear.toString();
    }
}
