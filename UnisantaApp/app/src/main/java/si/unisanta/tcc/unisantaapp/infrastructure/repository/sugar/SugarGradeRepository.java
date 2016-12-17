package si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar;

import com.google.common.collect.Lists;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Grade;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.IGradeRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SugarGradeRepository implements IGradeRepository {
    @Override
    public List<Grade> findAll() {
        return Lists.newArrayList(Grade.findAll(Grade.class));
    }

    @Override
    public List<Grade> findBySchoolYear(SchoolYear schoolYear) {
        return Lists.newArrayList(Grade.find(
                Grade.class,
                "semester = ?",
                schoolYear.toString()
        ));
    }

    @Override
    public List<Grade> findBySubject(Subject subject) {
        return Lists.newArrayList(Grade.find(
                Grade.class,
                "subject = ?",
                Long.toString(subject.getId())
            )
        );
    }

    @Override
    public Grade findBySubjectAndWeight(Subject subject, int weight) {
        List<Grade> gradeList = Lists.newArrayList(Grade.find(
                Grade.class,
                "subject = ? and weight = ?",
                Long.toString(subject.getId()),
                Integer.toString(weight)
        ));

        if (gradeList.size() > 0)
            return gradeList.get(0);

        return null;
    }

    @Override
    public long saveGrade(Grade grade) {
        return grade.save();
    }

    @Override
    public void deleteAll() {
        Grade.deleteAll(Grade.class);
        Grade.executeQuery("delete from sqlite_sequence where name='GRADE';");
    }
}
