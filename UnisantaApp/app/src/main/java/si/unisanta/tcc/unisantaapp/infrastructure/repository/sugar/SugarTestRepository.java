package si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar;

import com.google.common.collect.Lists;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Test;
import si.unisanta.tcc.unisantaapp.domain.model.ITestRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Classroom;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SugarTestRepository implements ITestRepository {
    @Override
    public List<Test> findByWeight(int weight) {
        return Lists.newArrayList(Test.find(
                Test.class,
                "weight = ?",
                Integer.toString(weight)
            )
        );
    }

    @Override
    public List<Test> findBetweenDates(DateTime begin, DateTime end) {
        return Lists.newArrayList(Test.find(
                Test.class,
                "datetime >= " + begin.toUnixTime()  +
                    " AND datetime <= " + end.toUnixTime()
            )
        );
    }

    @Override
    public List<Test> findFrom(DateTime from) {
        return Lists.newArrayList(Test.find(
                Test.class,
                "datetime >= " + from.toUnixTime()
            )
        );
    }

    @Override
    public Test findByDateTimeAndClassroom(DateTime dateTime, Classroom classroom) {
        List<Test> testList = Test.find(
            Test.class,
            "datetime = ? and classroom = ?",
            Long.toString(dateTime.toUnixTime()),
            classroom.toString()
        );

        if (testList.size() > 0)
            return testList.get(0);
        return null;
    }

    @Override
    public boolean anyTestThisSchoolYear(SchoolYear schoolYear) {
        DateTime begin, end;
        if (schoolYear.getSemester() == 1) {
            begin = new DateTime(1, 1, schoolYear.getYear(), 0, 0);
            end = new DateTime(1, 7, schoolYear.getYear(), 0, 0);
        }
        else {
            begin = new DateTime(1, 7, schoolYear.getYear(), 0, 0);
            end = new DateTime(1, 1, schoolYear.getYear()+1, 0, 0);
        }

        return Test.count(Test.class,
                "datetime >= " + begin.toUnixTime()  +
                " AND datetime <= " + end.toUnixTime(), new String[]{}) > 0;
    }

    @Override
    public long save(Test test) {
        return test.save();
    }

    @Override
    public void deleteAll() {
        Test.deleteAll(Test.class);
        Test.executeQuery("delete from sqlite_sequence where name='SUBJECT';");
    }
}
