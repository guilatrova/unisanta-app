package tests.mocks.repositories;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Test;
import si.unisanta.tcc.unisantaapp.domain.model.ITestRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class TestMockRepository extends IRepositoryMock<Test> implements ITestRepository {
    @Override
    public List<Test> findByWeight(int weight) {
        List<Test> returnList = new ArrayList<>();
        for (Test test : repositoryList) {
            if (test.getWeight() == weight) {
                returnList.add(test);
            }
        }

        return returnList;
    }

    @Override
    public List<Test> findBetweenDates(DateTime begin, DateTime end) {
        return null;
    }

    @Override
    public List<Test> findFrom(DateTime from) {
        return null;
    }

    @Override
    public Test findByDateTimeAndClassroom(DateTime dateTime, Classroom classroom) {
        return null;
    }

    @Override
    public boolean anyTestThisSchoolYear(SchoolYear schoolYear) {
        return false;
    }

    @Override
    public long save(Test test) {
        test.setId((long)repositoryList.size()+1);
        return (long)super.save(test);
    }
}
