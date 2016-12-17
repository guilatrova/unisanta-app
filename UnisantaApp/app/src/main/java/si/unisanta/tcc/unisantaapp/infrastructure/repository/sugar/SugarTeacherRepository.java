package si.unisanta.tcc.unisantaapp.infrastructure.repository.sugar;

import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;
import si.unisanta.tcc.unisantaapp.domain.model.ITeacherRepository;

public class SugarTeacherRepository implements ITeacherRepository {
    @Override
    public Teacher findByName(String name) {
        List<Teacher> teacherList = Teacher.find(
                Teacher.class,
                "name = ?",
                name);

        if (teacherList.size() > 0)
            return teacherList.get(0);

        return null;
    }

    @Override
    public long saveTeacher(Teacher teacher) {
        return teacher.save();
    }

    @Override
    public void deleteAll() {
        Teacher.deleteAll(Teacher.class);
        Teacher.executeQuery("delete from sqlite_sequence where name='TEACHER';");
    }
}
