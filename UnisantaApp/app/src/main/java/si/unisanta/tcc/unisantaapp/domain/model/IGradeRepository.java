package si.unisanta.tcc.unisantaapp.domain.model;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Grade;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public interface IGradeRepository extends IDeletable {
    List<Grade> findAll();
    List<Grade> findBySchoolYear(SchoolYear schoolYear);
    List<Grade> findBySubject(Subject subject);
    Grade findBySubjectAndWeight(Subject subject, int weight);
    long saveGrade(Grade grade);
}
