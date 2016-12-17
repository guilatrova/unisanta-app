package si.unisanta.tcc.unisantaapp.domain.services.update.fixes;

import java.util.List;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class TestsDPFix {
    public void fix() {
        ISubjectRepository subjectRepository = RepositoryFactory.getSubjectRepository();
        List<Subject> subjectList = subjectRepository.findBySchoolYear(SchoolYear.getCurrent());

        //Risk 2.3
        for (Subject subject : subjectList) {
            if (subject.getNickName().contains("- DP")) {
                UnisantaApplication.Log_i("Matéria a ser corrigida: " + subject.getNickName());
                String fixedNickname = subject.getNickName().replace("- DP", "").trim();
                subject.setNickName(fixedNickname);
                subjectRepository.saveSubject(subject);
            }
        }
    }
}
