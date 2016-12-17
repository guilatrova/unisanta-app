package si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar;

import com.google.common.collect.Lists;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SugarSubjectRepository implements ISubjectRepository {
    @Override
    public Subject findById(long id) {
        return Subject.findById(Subject.class, id);
    }

    @Override
    public List<Subject> findByOriginalId(long id) {
        return Lists.newArrayList(Subject.find(
                Subject.class,
                "original_id = ?",
                Long.toString(id)
        ));
    }

    @Override
    public List<Subject> findByName(String name) {
        return Subject.find(
                Subject.class,
                "name = ?",
                name
        );
    }

    @Override
    public List<Subject> findBySchoolYear(SchoolYear schoolYear) {
        return Subject.find(
                Subject.class,
                "semester = ?",
                schoolYear.toString()
        );
    }

    @Override
    public List<Subject> findByTeacherAndSchoolYear(Teacher teacher, SchoolYear schoolYear) {
        return Subject.findWithQuery(
                Subject.class,
                "Select Subject.* from Subject " +
                        "join Teacher on Teacher.id = Subject.teacher " +
                        "where semester = ? and Teacher.id = ?",
                schoolYear.toString(),
                Long.toString(teacher.getId())
        );
    }

    @Override
    public Subject findByNameAndSchoolYear(String name, SchoolYear schoolYear) {
        List<Subject> subjectList = Subject.find(
                Subject.class,
                "semester = ? and name = ?",
                schoolYear.toString(),
                name
        );

        if (subjectList.size() > 0)
            return subjectList.get(0);
        return null;
    }

    @Override
    public Subject findByNicknameAndSchoolYear(String nickname, SchoolYear schoolYear) {
        List<Subject> subjectList = Subject.find(
                Subject.class,
                "semester = ? and nick_name = ?",
                schoolYear.toString(),
                nickname
        );

        if (subjectList.size() > 0)
            return subjectList.get(0);
        return null;
    }

    @Override
    public Subject findByOriginalIdAndSchoolYear(long id, SchoolYear schoolYear) {
        List<Subject> subjectList = Subject.find(
                Subject.class,
                "semester = ? and original_id = ?",
                schoolYear.toString(),
                Long.toString(id)
        );

        if (subjectList.size() > 0)
            return subjectList.get(0);
        return null;
    }

    @Override
    public Subject findByNameLikeAndSchoolYear(String nameLike, SchoolYear schoolYear) {
        List<Subject> subjectList = Subject.findWithQuery(
                Subject.class,
                "Select * from Subject where name like ? and semester = ? limit 1",
                nameLike,
                schoolYear.toString()
        );

        /*String query = "Select * from Subject where name like '" + nameLike + "' and semester = '" + schoolYear.toString() + "' limit 1";
        UnisantaApplication.Log_d(query);
        List<Subject> subjectList = Subject.findWithQuery(Subject.class, query);*/

        if (subjectList.size() > 0)
            return subjectList.get(0);
        return null;
    }

    @Override
    public boolean isDP(long originalId, SchoolYear schoolYear) {
        List<Subject> subjectList = findByOriginalId(originalId);
        for (Subject checkDP : subjectList) {
            if (schoolYear.isAfter(checkDP.getSchoolYear())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long saveSubject(Subject subject) {
        return subject.save();
    }

    @Override
    public void deleteAll() {
        Subject.deleteAll(Subject.class);
        Subject.executeQuery("delete from sqlite_sequence where name='SUBJECT';");
    }
}
