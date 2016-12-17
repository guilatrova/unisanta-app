package si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.model.ICoursewareRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class SugarCoursewareRepository implements ICoursewareRepository{
    @Override
    public void deleteAll() {
        Courseware.deleteAll(Courseware.class);
        Courseware.executeQuery("delete from sqlite_sequence where name='COURSEWARE';");
    }

    @Override
    public List<Courseware> findBySubject(Subject subject) {
        return
            Courseware.find(
                Courseware.class,
                "subject = ? ",
                Long.toString(subject.getId())
        );
    }

    @Override
    public Courseware findByTitleAndUploadDate(String title, String date, Time time) {
        List<Courseware> coursewareList = Courseware.find(
                Courseware.class,
                "title = ? and upload_date = ? and upload_time = ?",
                title, date, time.toString()
            );

        if (coursewareList.size() > 0)
            return coursewareList.get(0);
        return  null;
    }

    @Override
    public long saveCourseware(Courseware courseware) {
        return courseware.save();
    }
}