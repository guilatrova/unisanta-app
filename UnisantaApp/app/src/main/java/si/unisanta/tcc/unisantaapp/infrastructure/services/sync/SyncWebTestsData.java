package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.entities.Test;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingDataException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingTagException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.factories.TestFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITestRepository;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncBaseData;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.TestsPage;

public class SyncWebTestsData extends SyncBaseData {
    private TestsPage testsPage;
    private ITestRepository testRepository;
    private ISubjectRepository subjectRepository;

    public SyncWebTestsData(TestsPage testsPage, ITestRepository testRepository, ISubjectRepository subjectRepository) {
        this.testsPage = testsPage;
        this.testRepository = testRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void sync() throws SyncFailedException {
        try {
            String html = testsPage.getHtml();
            testsPage.checkDataAvailable(html);

            int pos = SyncDataHelper.getIndexAndGoToEnd("tbody>", html);
            int endPos = SyncDataHelper.getIndexAndGoToEnd("</table>", html, pos);
            html = html.substring(pos, endPos);

            if (!html.contains("<td")) {
                throw new UnavailableDataException("As datas das provas ainda não foram publicadas", testsPage.getUrl(), testsPage.getPageName());
            }

            List<TestDataHolder> dataHolderList = new ArrayList<>();
            endPos = 0;
            do {
                pos = SyncDataHelper.getIndexAndGoToEnd("<td colspan=\"7\"", html, endPos);
                pos = SyncDataHelper.getIndexAndGoToEnd(">", html, pos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);

                String title = html.substring(pos, endPos);

                boolean hasNextTestThisDay;
                do {
                    hasNextTestThisDay = false;
                    TestDataHolder dataHolder = new TestDataHolder();
                    dataHolder.dateWeight = title;

                    pos = SyncDataHelper.getNextPosition("<td>", html, endPos, 2);
                    endPos = SyncDataHelper.getIndexOf("</", html, pos);

                    dataHolder.time = html.substring(pos, endPos);

                    pos = SyncDataHelper.getIndexAndGoToEnd("<td>", html, endPos);
                    endPos = SyncDataHelper.getIndexOf("</", html, pos);

                    dataHolder.subjectNickname = SyncDataHelper.fixSpecialCharacters(html.substring(pos, endPos).trim());

                    pos = SyncDataHelper.getNextPosition("<td>", html, endPos, 4);
                    endPos = SyncDataHelper.getIndexOf("</", html, pos);

                    dataHolder.classroom = html.substring(pos, endPos);
                    dataHolderList.add(dataHolder);
                    UnisantaApplication.Log_i("PROVA " +
                            "\ndata: " + dataHolder.dateWeight +
                            "\nsala: " + dataHolder.classroom +
                            "\nhora: " + dataHolder.time +
                            "\nmatéria:" + dataHolder.subjectNickname);

                    //Has next this day?
                    int aux = html.indexOf("<td", endPos);
                    if (aux > -1 && !html.substring(aux, aux + 20).contains("colspan")) {
                        hasNextTestThisDay = true;
                    }
                }
                while (hasNextTestThisDay);

            } while (html.indexOf("<td colspan=\"7\"", endPos) > -1);
            saveTests(dataHolderList);
        } catch (StringNotFoundException e) {
            throw new MissingTagException(testsPage.getUrl(), testsPage.getPageName(), e.getString());
        }
        catch (UnavailableDataException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SyncFailedException(e, testsPage.getUrl(), testsPage.getPageName());
        }
    }

    private void saveTests(List<TestDataHolder> dataHolderList) throws MissingDataException, ParseException {
        for (TestDataHolder dataHolder : dataHolderList) {
            dataHolder.subjectNickname = dataHolder.subjectNickname.substring(0, dataHolder.subjectNickname.lastIndexOf('-')).trim();

            Subject subject = subjectRepository.findByNicknameAndSchoolYear(dataHolder.subjectNickname, SchoolYear.getCurrent());
            if (subject == null) {
                UnisantaApplication.Log_i(dataHolder.subjectNickname + " do semestre " +  SchoolYear.getCurrent().toString() + " É NULO!!");
                throw new MissingDataException(testsPage.getUrl(), "horário de provas", dataHolder.subjectNickname + " do semestre " + SchoolYear.getCurrent().toString());
            }

            Test test = TestFactory.createTest(dataHolder.dateWeight, dataHolder.time, subject, dataHolder.classroom);
            if (testRepository.findByDateTimeAndClassroom(test.getDatetime(), test.getClassroom()) == null) {
                testRepository.save(test);
            }
            else {
                UnisantaApplication.Log_i("Prova de " + subject.getNickName() + " já existe");
            }
        }
    }

    @Override
    public String getMessage() {
        return UnisantaApplication.getInstance().getString(R.string.tests);
    }

    private final class TestDataHolder {
        public String classroom;
        public String dateWeight;
        public String time;
        public String subjectNickname;
    }
}
