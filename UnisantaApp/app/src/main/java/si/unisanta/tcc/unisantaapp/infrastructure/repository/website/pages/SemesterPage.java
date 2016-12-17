package si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages;

public class SemesterPage extends RegularWebPage {
    protected String semester;

    public String getSemester() {
        return semester;
    }

    public SemesterPage(String url, String unavailableText, String errorMessageOnUnavaialable, String semester) {
        super(url, unavailableText, errorMessageOnUnavaialable);
        this.semester = semester;
    }

    @Override
    public String getUrl() {
        return url + semester;
    }
}
