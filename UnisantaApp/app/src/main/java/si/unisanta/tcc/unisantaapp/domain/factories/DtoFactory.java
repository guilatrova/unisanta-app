package si.unisanta.tcc.unisantaapp.domain.factories;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.dto.SubjectGradeDto;
import si.unisanta.tcc.unisantaapp.domain.entities.Grade;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;

/**
 * Created by Guilherme on 03/02/2016.
 */
public class DtoFactory {
    public static SubjectGradeDto createSubjectGradeDto(Subject subject, List<Grade> grades) {
        String subjectName = subject.getName();
        int status = subject.getStatus();
        boolean dp = subject.isDP();

        float p1 = -1;
        float p2 = -1;
        float p3 = -1;
        float finalScore = -1;

        for (Grade grade : grades) {
            switch (grade.getWeight()) {
                case Grade.P1:
                    p1 = grade.getScore();
                    break;
                case Grade.P2:
                    p2 = grade.getScore();
                    break;
                case Grade.P3:
                    p3 = grade.getScore();
                    break;
                default:
                    finalScore = grade.getScore();
                    break;
            }
        }

        return new SubjectGradeDto(subjectName, status, dp, p1, p2, p3, finalScore);
    }
}
