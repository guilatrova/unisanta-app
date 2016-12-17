package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

public class CoursewarePage extends SemesterPage{
    public static final String COURSEWARE_URL = "http://portalaluno.unisanta.br/Academico/MaterialDidatico?id=";

    public CoursewarePage(String semester) {
        super(COURSEWARE_URL, "Nenhuma", "Nenhuma disciplina disponível", semester);
    }

    @Override
    public String getPageName() {
        return "material didático";
    }
}
