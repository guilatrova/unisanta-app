package tests.mocks.repositories;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SubjectMockRepository extends IRepositoryMock<Subject> implements ISubjectRepository{

    @Override
    public Subject findById(long id) {
        return null;
    }

    @Override
    public List<Subject> findByOriginalId(long id) {
        return null;
    }

    @Override
    public List<Subject> findByName(String name) {
        List<Subject> returnList = new ArrayList<>();

        for (Subject subject : repositoryList) {
            if (subject.getName().equals(name)) {
                returnList.add(subject);
            }
        }
        return returnList;
    }

    @Override
    public List<Subject> findBySchoolYear(SchoolYear schoolYear) {
        List<Subject> returnList = new ArrayList<>();

        for (Subject subject : repositoryList) {
            if (subject.getSchoolYear().equals(schoolYear)) {
                returnList.add(subject);
            }
        }
        return returnList;
    }

    @Override
    public List<Subject> findByTeacherAndSchoolYear(Teacher teacher, SchoolYear schoolYear) {
        List<Subject> returnList = new ArrayList<>();

        for (Subject subject : repositoryList) {
            if (subject.getSchoolYear().equals(schoolYear) &&
                    subject.getTeacher().getId() == teacher.getId()) {
                returnList.add(subject);
            }
        }
        return returnList;
    }

    @Override
    public Subject findByNameAndSchoolYear(String name, SchoolYear schoolYear) {
        for (Subject subject : repositoryList) {
            if (subject.getSchoolYear().equals(schoolYear) &&
                    subject.getName().equals(name)) {
                return subject;
            }
        }
        return null;
    }

    @Override
    public Subject findByNicknameAndSchoolYear(String nickname, SchoolYear schoolYear) {
        for (Subject subject : repositoryList) {
            if (subject.getSchoolYear().equals(schoolYear) &&
                    subject.getNickName().equals(nickname)) {
                return subject;
            }
        }
        return null;
    }

    @Override
    public Subject findByOriginalIdAndSchoolYear(long id, SchoolYear schoolYear) {
        return null;
    }

    @Override
    public Subject findByNameLikeAndSchoolYear(String nameLike, SchoolYear schoolYear) {
        for (Subject subject : repositoryList) {
            if (subject.getSchoolYear().equals(schoolYear) &&
                    subject.getName().matches(nameLike.replace("%", "(.*)"))) {
                return subject;
            }
        }
        return null;
    }

    @Override
    public boolean isDP(long originalId, SchoolYear schoolYear) {
        return false;
    }

    @Override
    public long saveSubject(Subject subject) {
        subject.setId((long)repositoryList.size()+1);
        return save(subject);
    }
}
