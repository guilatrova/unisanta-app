package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

public class ClassesSchedulePage extends RegularWebPage {

    private static final String CLASSES_SCHEDULE_URL = "http://portalaluno.unisanta.br/Academico/Horarios";

    public ClassesSchedulePage() {
        super(CLASSES_SCHEDULE_URL, "cadastrado", "Horário não cadastrado");
    }

    @Override
    public String getPageName() {
        return "aulas";
    }
}
