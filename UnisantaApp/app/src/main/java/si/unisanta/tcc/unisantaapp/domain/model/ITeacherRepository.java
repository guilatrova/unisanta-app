package si.unisanta.tcc.unisantaapp.domain.model;

import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;

public interface ITeacherRepository extends IDeletable {
    Teacher findByName(String name);
    long saveTeacher(Teacher teacher);
}
