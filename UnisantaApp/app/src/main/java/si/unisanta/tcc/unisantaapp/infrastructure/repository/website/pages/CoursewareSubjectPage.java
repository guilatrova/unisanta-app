package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpRequest;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpResponse;

public class CoursewareSubjectPage extends RegularWebPage {
    private Subject subject;
    public static final String COURSEWARE_SUBJECT_URL = "http://portalaluno.unisanta.br";
    public static final String[] SEGMENT_URL = new String[] {"Academico", "Disciplina"};

    public CoursewareSubjectPage(Subject subject) {
        super(COURSEWARE_SUBJECT_URL, "possui nenhum", "Esta disciplina não possui nenhum arquivo publicado");
        this.subject = subject;
    }

    @Override
    public String getPageName() {
        return String.format("Material didático da matéria %s/%s",
                subject.getName(),
                subject.getSchoolYear().toString());
    }

    @Override
    public String getHtml() throws PageRequestFailedException {
        try {
            HttpResponse response = HttpRequest.getInstance()
                    .requestPageWithGet(
                            getUrl().replace("http://", ""),
                            SEGMENT_URL,
                            "CodTur", subject.getClassGroup(),
                            "CodMat", ""+subject.getOriginalId(),
                            "PeriodoLetivo", subject.getSchoolYear().toString('/'),
                            "Disciplina", subject.getName(),
                            "Professor", subject.getTeacher().getName()
                    );

            if (response.getResult() == HttpResponse.RESULT_OK) {
                return response.getHtml();
            }

            throw new PageRequestFailedException(response.getException(), getUrl());
        }
        catch (Exception e) {
            throw new PageRequestFailedException(e, getUrl());
        }
    }

    public Subject getSubject() {
        return subject;
    }
}
