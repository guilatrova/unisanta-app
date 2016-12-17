package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

public class MainPage extends RegularWebPage {
    public static final String MAIN_URL = "http://portalaluno.unisanta.br/Academico";

    public MainPage() {
        super(MAIN_URL);
    }

    @Override
    public String getPageName() {
        return "página inicial";
    }
}
