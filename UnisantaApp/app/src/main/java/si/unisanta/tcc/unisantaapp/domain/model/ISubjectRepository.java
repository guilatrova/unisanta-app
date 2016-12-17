package si.unisanta.tcc.unisantaapp.domain.model;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public interface ISubjectRepository extends IDeletable {
    Subject findById(long id);
    List<Subject> findByOriginalId(long id);
    List<Subject> findByName(String name);
    List<Subject> findBySchoolYear(SchoolYear schoolYear);
    List<Subject> findByTeacherAndSchoolYear(Teacher teacher, SchoolYear schoolYear);
    Subject findByNameAndSchoolYear(String name, SchoolYear schoolYear);
    Subject findByNicknameAndSchoolYear(String nickname, SchoolYear schoolYear);
    Subject findByOriginalIdAndSchoolYear(long id, SchoolYear schoolYear);
    Subject findByNameLikeAndSchoolYear(String nameLike, SchoolYear schoolYear);
    boolean isDP(long originalId, SchoolYear schoolYear);
    long saveSubject(Subject subject);
}
