package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Grade;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingDataException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingTagException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.factories.GradeFactory;
import si.unisanta.tcc.unisantaapp.domain.model.IGradeRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncBaseData;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.GradesPage;

public class SyncWebGradesData extends SyncBaseData {
    private GradesPage gradesPage;
    private IGradeRepository gradeRepository;
    private ISubjectRepository subjectRepository;

    public SyncWebGradesData(GradesPage gradesPage, IGradeRepository gradeRepository, ISubjectRepository subjectRepository) {
        this.gradesPage = gradesPage;
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
    }

    public String getSemester() {
        return gradesPage.getSemester();
    }

    @Override
    public void sync() throws SyncFailedException {
        final String TAG_SUBJECT = "celulaNomeDisciplina\">";
        final String TAG_GRADE = "\"nota\">";
        final String TAG_STATUS = "statusDisciplina\">";

        try {
            String html = gradesPage.getHtml();
            gradesPage.checkDataAvailable(html);

            int pos = SyncDataHelper.getIndexAndGoToEnd("<tbody>", html);
            int endPos = SyncDataHelper.getIndexOf("</tbody>", html);
            html = html.substring(pos, endPos);

            endPos = 0;
            List<GradeDataHolder> holderList = new ArrayList<>();
            do {
                GradeDataHolder dataHolder = new GradeDataHolder();

                pos = SyncDataHelper.getIndexAndGoToEnd(TAG_SUBJECT, html, endPos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.subjectName = SyncDataHelper.fixSpecialCharacters(html.substring(pos, endPos));

                //P1
                pos = SyncDataHelper.getIndexAndGoToEnd(TAG_GRADE, html, endPos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.p1 = html.substring(pos, endPos);

                //P2
                pos = SyncDataHelper.getNextPosition(TAG_GRADE, html, endPos, 2);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.p2 = html.substring(pos, endPos);

                //P3
                pos = SyncDataHelper.getNextPosition(TAG_GRADE, html, endPos, 4);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.p3 = html.substring(pos, endPos);

                //Média
                pos = SyncDataHelper.getIndexAndGoToEnd(TAG_GRADE, html, endPos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.finalScore = html.substring(pos, endPos);

                //Status
                pos = SyncDataHelper.getIndexAndGoToEnd(TAG_STATUS, html, endPos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.status = html.substring(pos, endPos);

                holderList.add(dataHolder);
            } while (html.indexOf("celulaNomeDisciplina\">", endPos) > -1);

            saveGrades(holderList);

        } catch (StringNotFoundException e) {
            throw new MissingTagException(gradesPage.getUrl(), gradesPage.getPageName(), e.getString());
        }
        catch (UnavailableDataException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SyncFailedException(e, GradesPage.GRADES_URL, gradesPage.getPageName());
        }
    }

    @Override
    public String getMessage() {
        return "notas de " + getSemester();
    }

    private void saveGrades(List<GradeDataHolder> holderList) throws MissingDataException {
        for (GradeDataHolder dataHolder : holderList) {

            Subject subject = subjectRepository.findByNameAndSchoolYear(
                    dataHolder.subjectName,
                    new SchoolYear(getSemester()));
            if (subject == null) {
                UnisantaApplication.Log_i(dataHolder.subjectName + " do semestre " + getSemester() + " É NULO!!");
                throw new MissingDataException(gradesPage.getUrl(), "notas", dataHolder.subjectName + " do semestre " + getSemester());
            }

            saveGradeByWeight(dataHolder.p1, subject, Grade.P1);
            saveGradeByWeight(dataHolder.p2, subject, Grade.P2);
            saveGradeByWeight(dataHolder.p3, subject, Grade.P3);
            if (saveGradeByWeight(dataHolder.finalScore, subject, Grade.FINAL)) {
                //Se salvou a média, então preciso atualizar a matéria com a situação
                UnisantaApplication.Log_i(dataHolder.subjectName + " = " + dataHolder.status);
                if (dataHolder.status.trim().equals("Aprovado")) {
                    subject.setStatus(Subject.APPROVED);
                    UnisantaApplication.Log_d("[APROVADO] - STATUS DA MATÉRIA: " + Subject.APPROVED);
                }
                else {
                    subject.setStatus(Subject.DISAPPROVED);
                    UnisantaApplication.Log_d("[REPROVADO] - STATUS DA MATÉRIA: " + Subject.DISAPPROVED);
                }
                subjectRepository.saveSubject(subject);
            }
        }
    }

    private boolean saveGradeByWeight(String score, Subject subject, int weight) {
        if (!score.equals("")) {
            Grade grade = gradeRepository.findBySubjectAndWeight(subject, weight); //Passando a matéria, automaticamente eu filtro o semestre correto
            //A nota ainda não existe
            if (grade == null) {
                grade = GradeFactory.createGrade(score, getSemester(), subject, weight);
                gradeRepository.saveGrade(grade);
                return true;
            }
            else {
                grade.setScore(Float.parseFloat(score.replace(",", ".")));
                gradeRepository.saveGrade(grade);
            }
        }

        return false;
    }

    private final class GradeDataHolder {
        public String subjectName;
        public String p1;
        public String p2;
        public String p3;
        public String finalScore;
        public String status;
    }
}
