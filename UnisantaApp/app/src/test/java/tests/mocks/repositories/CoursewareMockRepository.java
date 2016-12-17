package tests.mocks.repositories;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.ICoursewareRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class CoursewareMockRepository extends IRepositoryMock<Courseware> implements ICoursewareRepository{

    @Override
    public List<Courseware> findBySubject(Subject subject) {
        List<Courseware> returnList = new ArrayList<>();
        for (Courseware courseware : repositoryList) {
            if (courseware.getSubject().getOriginalId() == subject.getOriginalId())
                returnList.add(courseware);
        }

        return returnList;
    }

    @Override
    public Courseware findByTitleAndUploadDate(String title, String date, Time time) {
        return null;
    }

    public int getCount() {
        return repositoryList.size();
    }

    @Override
    public long saveCourseware(Courseware courseware) {
        courseware.setId((long) repositoryList.size() + 1);
        repositoryList.add(courseware);
        return save(courseware);
    }
}
