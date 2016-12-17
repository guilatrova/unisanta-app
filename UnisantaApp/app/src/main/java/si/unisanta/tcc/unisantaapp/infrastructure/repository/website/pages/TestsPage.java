package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

public class TestsPage extends RegularWebPage {
    public static final String TESTS_URL = "http://portalaluno.unisanta.br/Academico/HorariosDeProvas?aluno=S";


    public TestsPage() {
        super(TESTS_URL, "fora do ar", "O horário de provas ainda não foi divulgado.");
    }

    @Override
    public String getPageName() {
        return "horário de provas";
    }
}
