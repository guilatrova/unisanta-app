package si.unisanta.tcc.unisantaapp.domain.model;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Test;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public interface ITestRepository extends IDeletable{
    List<Test> findByWeight(int weight);
    List<Test> findBetweenDates(DateTime begin, DateTime end);
    List<Test> findFrom(DateTime from);
    Test findByDateTimeAndClassroom(DateTime dateTime, Classroom classroom);
    boolean anyTestThisSchoolYear(SchoolYear schoolYear);
    long save(Test test);
}
