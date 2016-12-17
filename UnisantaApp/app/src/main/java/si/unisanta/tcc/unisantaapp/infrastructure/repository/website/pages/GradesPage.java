package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

public class GradesPage extends SemesterPage{
    public static final String GRADES_URL = "http://portalaluno.unisanta.br/Academico/NotasPorPeriodoLetivo?PeriodoLetivo=";

    public GradesPage(String semester) {
        super(GRADES_URL, "sem notas", "Aluno sem notas cadastradas para este período letivo.", semester);
    }

    @Override
    public String getPageName() {
        return "notas";
    }
}
