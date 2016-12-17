package tests.mocks.repositories;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Grade;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.IGradeRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class GradeMockRepository extends IRepositoryMock<Grade> implements IGradeRepository{
    @Override
    public List<Grade> findAll() {
        return new ArrayList<>(repositoryList);
    }

    @Override
    public List<Grade> findBySchoolYear(SchoolYear schoolYear) {
        List<Grade> returnList = new ArrayList<>();

        for (Grade grade : repositoryList) {
            if (grade.getSchoolYear().equals(schoolYear))
                returnList.add(grade);
        }

        return returnList;
    }

    @Override
    public List<Grade> findBySubject(Subject subject) {
        List<Grade> returnList = new ArrayList<>();

        for (Grade grade : repositoryList) {
            if (grade.getSubject().getId().equals(subject.getId()))
                returnList.add(grade);
        }

        return returnList;
    }

    @Override
    public Grade findBySubjectAndWeight(Subject subject, int weight) {
        for (Grade grade : repositoryList) {
            if (grade.getSubject().getId() == subject.getId()
                    && grade.getWeight() == weight)
                return grade;
        }

        return null;
    }

    @Override
    public long saveGrade(Grade grade) {
        grade.setId((long)repositoryList.size()+1);
        return save(grade);
    }
}
