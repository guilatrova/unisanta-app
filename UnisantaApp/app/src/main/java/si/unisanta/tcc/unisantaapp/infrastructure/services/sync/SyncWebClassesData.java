package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingTagException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.factories.ClassScheduleFactory;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITeacherRepository;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncBaseData;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.ClassesSchedulePage;

public class SyncWebClassesData extends SyncBaseData {
    private ClassesSchedulePage classesPage;
    private IClassScheduleRepository classesRepository;
    private ISubjectRepository subjectRepository;
    private ITeacherRepository teacherRepository;
    private SchoolYear actualSchoolYear;

    public SyncWebClassesData(ClassesSchedulePage classesPage, IClassScheduleRepository classesRepository, ISubjectRepository subjectRepository, ITeacherRepository teacherRepository) {
        this.classesPage = classesPage;
        this.classesRepository = classesRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    public void sync() throws SyncFailedException {
        try {
            String html = classesPage.getHtml();
            classesPage.checkDataAvailable(html);

            int pos = SyncDataHelper.getIndexAndGoToEnd("Letivo:", html);
            int endPos = SyncDataHelper.getIndexOf("<", html, pos);

            actualSchoolYear = new SchoolYear(html.substring(pos, endPos).trim());
            if (actualSchoolYear.isBefore(SchoolYear.getCurrent())) {
                throw new UnavailableDataException("Você não tem aulas no semestre corrente", classesPage.getUrl(), classesPage.getPageName());
            }

            pos = SyncDataHelper.getIndexAndGoToEnd("table id=\"horarios\"", html);
            endPos = SyncDataHelper.getIndexOf("</table>", html);

            html = html.substring(pos, endPos);

            List<ClassScheduleDataHolder> dataHolderList = new ArrayList<>();

            endPos = 0;
            do {
                ClassScheduleDataHolder dataHolder = new ClassScheduleDataHolder();

                //Id
                pos = SyncDataHelper.getIndexAndGoToEnd("data-id=\"", html, endPos);
                endPos = SyncDataHelper.getIndexOf("\"", html, pos);
                dataHolder.id = html.substring(pos, endPos);
                UnisantaApplication.Log_i("ID: " + dataHolder.id);

                //Dia da semana
                pos = SyncDataHelper.getIndexAndGoToEnd("strong>", html, endPos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.weekDay = html.substring(pos,endPos);
                UnisantaApplication.Log_i("Dia da semana encontrado: " + dataHolder.weekDay + " | na posição " + pos + " até " + endPos);

                //Horário
                pos = SyncDataHelper.getIndexAndGoToEnd("<td class=\"colunaDuracaHorario", html, endPos);
                pos = SyncDataHelper.getIndexOf(">", html, pos);
                endPos = SyncDataHelper.getIndexOf("<", html, pos);

                String horario = html.substring(pos, endPos);
                int aux = horario.indexOf("-");
                dataHolder.startTime = horario.substring(1, aux).trim();
                dataHolder.endTime = horario.substring(aux + 1, horario.length()).trim();
                UnisantaApplication.Log_i("A aula começa às: " + dataHolder.startTime + " e acaba às " + dataHolder.endTime);

                //Matéria
                pos = SyncDataHelper.getIndexAndGoToEnd("alinhamentoEsquerda", html, endPos);
                pos = SyncDataHelper.getIndexOf(">", html, pos)+3;
                endPos = SyncDataHelper.getIndexOf("<", html, pos);

                dataHolder.subjectName = SyncDataHelper.fixSpecialCharacters(html.substring(pos, endPos).trim());
                dataHolder.isDP = dataHolder.subjectName.contains("- DP");
                UnisantaApplication.Log_i("Matéria encontrada: " + dataHolder.subjectName);

                //Professor
                pos = SyncDataHelper.getIndexAndGoToEnd("alinhamentoEsquerda", html, endPos);
                pos = SyncDataHelper.getIndexOf(">", html, pos)+1;
                endPos = SyncDataHelper.getIndexOf("<", html, pos);

                dataHolder.teacherName = SyncDataHelper.fixSpecialCharacters(html.substring(pos, endPos));
                UnisantaApplication.Log_i("Professor encontrado: " + dataHolder.teacherName);

                //Sala
                pos = SyncDataHelper.getIndexAndGoToEnd("colunaSalaHorario", html, endPos);
                pos = SyncDataHelper.getIndexOf(">", html, pos)+1;
                endPos = SyncDataHelper.getIndexOf("<", html, pos);
                dataHolder.classroom = html.substring(pos,endPos);
                UnisantaApplication.Log_i("Sala de aula encontrada: " + dataHolder.classroom);

                dataHolderList.add(dataHolder);
            } while (html.indexOf("<tr", endPos) > -1);

            saveClasses(dataHolderList);

        } catch (StringNotFoundException e) {
            throw new MissingTagException(classesPage.getUrl(), classesPage.getPageName(), e.getString());
        }
        catch (UnavailableDataException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SyncFailedException(e, classesPage.getUrl(), classesPage.getPageName());
        }
    }

    @Override
    public String getMessage() {
        return UnisantaApplication.getInstance().getString(R.string.class_schedule);
    }

    private void saveClasses(List<ClassScheduleDataHolder> dataHolderList) {
        for (ClassScheduleDataHolder dataHolder : dataHolderList) {
            UnisantaApplication.Log_i("Verificando horário de: " + dataHolder.subjectName);

            Subject subject = subjectRepository.findByOriginalIdAndSchoolYear(
                    Long.parseLong(dataHolder.id), actualSchoolYear);
            
            if (subject != null) {
                subject.setNickName(dataHolder.subjectName.replace("- DP", "").trim());
                subjectRepository.saveSubject(subject);

                Time start = new Time(dataHolder.startTime);
                Time end = new Time(dataHolder.endTime);
                int weekDay = getWeekDay(dataHolder.weekDay);

                ClassSchedule classSchedule =
                        classesRepository.findBySubjectAndDateTime(subject, weekDay, start, end);

                //Não existe, preciso criar e salvar
                if (classSchedule == null) {
                    classSchedule = ClassScheduleFactory.createClassSchedule(
                            weekDay,
                            dataHolder.classroom,
                            subject,
                            start,
                            end,
                            actualSchoolYear
                    );

                    subject.setDP(dataHolder.isDP);

                    subjectRepository.saveSubject(subject);
                    classesRepository.saveClassSchedule(classSchedule);
                }
            }
            else {
                UnisantaApplication.Log_e("Não encontrei a matéria de: " + dataHolder.subjectName);
            }
        }
    }

    private int getWeekDay(String weekName) {
        switch (weekName) {
            case "Dom.":
                return 1;
            case "Seg.":
                return 2;
            case "Ter.":
                return 3;
            case "Qua.":
                return 4;
            case "Qui.":
                return 5;
            case "Sex.":
                return 6;
            default:
                return 7;
        }
    }

    private final class ClassScheduleDataHolder {
        public String id;
        public String weekDay;
        public String startTime;
        public String endTime;
        public String subjectName;
        public String teacherName;
        public String classroom;
        public boolean isDP;

        @Override
        public String toString() {
            return subjectName + " - " + teacherName;
        }
    }
}
