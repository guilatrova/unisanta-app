package tests.mocks.repositories;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class ClassScheduleMockRepository extends IRepositoryMock<ClassSchedule> implements IClassScheduleRepository{
    @Override
    public List<ClassSchedule> findAll() {
        return new ArrayList<>(repositoryList);
    }

    @Override
    public List<ClassSchedule> findBySchoolYear(SchoolYear schoolYear) {
        List<ClassSchedule> returnList = new ArrayList<>();

        for (ClassSchedule schedule : repositoryList) {
            if (schedule.getSubject().getSchoolYear().equals(schoolYear))
                returnList.add(schedule);
        }

        return returnList;
    }

    @Override
    public List<ClassSchedule> findClassesOfDay(int weekDay) {
        List<ClassSchedule> returnList = new ArrayList<>();

        for (ClassSchedule schedule : repositoryList) {
            if (schedule.getWeekDay() == weekDay)
                returnList.add(schedule);
        }

        return returnList;
    }

    @Override
    public List<ClassSchedule> findNextClasses() {
        return null;
    }

    @Override
    public boolean anyClassThisSchoolYear(SchoolYear schoolYear) {
        return false;
    }

    @Override
    public ClassSchedule findBySubjectAndDateTime(Subject subject, int weekDay, Time start, Time end) {
        for (ClassSchedule schedule : repositoryList) {
            if (schedule.getSubject().getId() == subject.getId() &&
                    schedule.getWeekDay() == weekDay &&
                    schedule.getStartTime().equals(start) &&
                    schedule.getEndTime().equals(end)) {
                return schedule;
            }
        }

        return null;
    }

    @Override
    public long saveClassSchedule(ClassSchedule classSchedule) {
        classSchedule.setId((long) repositoryList.size()+1);
        return save(classSchedule);
    }
}
