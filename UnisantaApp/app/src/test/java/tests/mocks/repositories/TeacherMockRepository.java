package tests.mocks.repositories;

import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;
import si.unisanta.tcc.unisantaapp.domain.model.ITeacherRepository;

public class TeacherMockRepository extends IRepositoryMock<Teacher> implements ITeacherRepository
{
    @Override
    public void deleteAll() {
        repositoryList.clear();
    }

    @Override
    public Teacher findByName(String name) {
        for (Teacher t : repositoryList) {
            if (t.getName().equals(name))
                return t;
        }

        return null;
    }

    @Override
    public long saveTeacher(Teacher teacher) {
        teacher.setId((long)repositoryList.size()+1);
        return save(teacher);
    }
}
