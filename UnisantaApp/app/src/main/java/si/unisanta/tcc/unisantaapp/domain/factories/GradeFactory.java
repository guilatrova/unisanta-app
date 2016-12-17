package si.unisanta.tcc.unisantaapp.domain.factories;

import si.unisanta.tcc.unisantaapp.domain.entities.Grade;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class GradeFactory {
    public static Grade createGrade(String score, String semester, Subject subject, int weight) {
        return new Grade(Float.parseFloat(score.replace(",", ".")), new SchoolYear(semester), subject, weight);
    }
}
