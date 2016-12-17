package si.unisanta.tcc.unisantaapp.domain.model;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public interface ICoursewareRepository extends IDeletable {

    List<Courseware> findBySubject(Subject subject);
    Courseware findByTitleAndUploadDate(String title, String date, Time time);

    long saveCourseware(Courseware courseware);
}
