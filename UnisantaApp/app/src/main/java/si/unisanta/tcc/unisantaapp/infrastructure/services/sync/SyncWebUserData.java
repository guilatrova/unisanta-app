package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingTagException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.PageRequestFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.model.IUserRepository;
import si.unisanta.tcc.unisantaapp.domain.services.IWebPage;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncBaseData;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.GradesPage;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.MainPage;

public class SyncWebUserData extends SyncBaseData {
    private GradesPage gradesPage;
    private MainPage mainPage;
    private String ra;
    private String pass;

    private IUserRepository repository;

    public SyncWebUserData(GradesPage gradesPage, MainPage mainPage, String pass, String ra, IUserRepository repository) {
        this.gradesPage = gradesPage;
        this.mainPage = mainPage;
        this.pass = pass;
        this.ra = ra;
        this.repository = repository;
    }

    @Override
    public void sync() throws SyncFailedException {
        IWebPage curPage = gradesPage;
        UserDataHolder dataHolder = new UserDataHolder();

        try {
            dataHolder.firstSemester = getFirstSemester();

            curPage = mainPage;
            setNameAndCourse(dataHolder);
            saveUserData(dataHolder);

        }
        catch (MissingTagException e) { //Just rethrow
            throw e;
        }
        catch (Exception e) {
            throw new SyncFailedException(e, curPage.getUrl(), "dados do usuário");
        }
    }

    @Override
    public String getMessage() {
        return "dados do aluno";
    }

    private String getFirstSemester() throws PageRequestFailedException, MissingTagException {
        String htmlGrades = gradesPage.getHtml();

        try {
            int pos = SyncDataHelper.getIndexAndGoToEnd("<select id=\"dlPeriodoLetivo\">", htmlGrades);
            int endPos = SyncDataHelper.getIndexOf("</select>", htmlGrades, pos);

            htmlGrades = htmlGrades.substring(pos, endPos);

            int lastSelectItem = htmlGrades.lastIndexOf("</option>");
            return htmlGrades.substring(lastSelectItem - 6, lastSelectItem);
        }
        catch (StringNotFoundException e) {
            throw new MissingTagException(gradesPage.getUrl(), "dados do usuário", e.getString());
        }
    }

    private void setNameAndCourse(UserDataHolder userDataHolder) throws PageRequestFailedException, MissingTagException {
        String htmlMain = mainPage.getHtml();

        try {
            //Pegar curso
            int pos = SyncDataHelper.getIndexAndGoToEnd("<div class=\"onde_estou\">", htmlMain);
            pos = SyncDataHelper.getIndexOf(">", htmlMain, pos) + 1;
            int endPos = SyncDataHelper.getIndexOf("-", htmlMain, pos);
            userDataHolder.course = SyncDataHelper.fixSpecialCharacters(
                    htmlMain.substring(pos, endPos).trim());

            //Pegar nome do aluno
            pos = endPos;
            endPos = SyncDataHelper.getIndexOf("<span class=\"raAluno\">", htmlMain);
            htmlMain = htmlMain.substring(pos, endPos);

            int lastSpan = 0;
            do {
                lastSpan = SyncDataHelper.getIndexOf("</span>", htmlMain, lastSpan +1);
            } while (htmlMain.indexOf("</span>", lastSpan+1) > -1);
            pos = lastSpan - 1;
            while (htmlMain.charAt(pos - 1) != '>') {
                pos--;
            }
            userDataHolder.name = SyncDataHelper.fixSpecialCharacters(htmlMain.substring(pos, lastSpan));
        }
        catch (StringNotFoundException e) {
            throw new MissingTagException(mainPage.getUrl(), "dados do usuário", e.getString());
        }
    }

    private void saveUserData(UserDataHolder dataHolder) {
        AppVersion appVersion = User.getInstance().getAppVersion();
        User.init(
                new SchoolYear(dataHolder.firstSemester),
                dataHolder.name,
                dataHolder.course,
                ra,
                pass
            );

        User.getInstance().setAppVersion(appVersion);
        repository.save(User.getInstance());
        UnisantaApplication.Log_i(
                ".\nName: " + dataHolder.name +
                ".\nCurso: " +  dataHolder.course +
                ".\nSemestre: " + dataHolder.firstSemester
        );
    }

    private final class UserDataHolder {
        public String firstSemester;
        public String name;
        public String course;
    }
}
