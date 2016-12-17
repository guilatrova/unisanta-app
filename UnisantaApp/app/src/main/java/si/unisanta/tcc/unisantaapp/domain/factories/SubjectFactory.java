package si.unisanta.tcc.unisantaapp.domain.factories;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SubjectFactory {
    public static Subject createSubject(String id, String name, String semester, Teacher teacher, String classGroup, ISubjectRepository subjectRepository) {
        SchoolYear schoolYear = new SchoolYear(semester);

        return new Subject(
                Long.parseLong(id),
                name,
                subjectRepository.isDP(Long.parseLong(id), schoolYear),
                classGroup,
                new SchoolYear(semester),
                teacher
        );
    }
}
