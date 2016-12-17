package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.entities.Teacher;
import si.unisanta.tcc.unisantaapp.domain.exceptions.MissingTagException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.factories.SubjectFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ITeacherRepository;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncBaseData;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.infrastructure.repository.website.pages.CoursewarePage;

public class SyncWebSubjectsData extends SyncBaseData {
    private CoursewarePage coursewarePage;
    private ISubjectRepository subjectRepository;
    private ITeacherRepository teacherRepository;

    public SyncWebSubjectsData(CoursewarePage coursewarePage, ISubjectRepository subjectRepository, ITeacherRepository teacherRepository) {
        this.coursewarePage = coursewarePage;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    public String getSemester() {
        return coursewarePage.getSemester();
    }

    @Override
    public void sync() throws SyncFailedException {
        try {
            String html = coursewarePage.getHtml();
            coursewarePage.checkDataAvailable(html);

            int pos = SyncDataHelper.getIndexAndGoToEnd("<ul id=\"disciplinasMaterialDidatico\"", html);
            int endPos = SyncDataHelper.getIndexOf("<ul>", html, pos);
            html = html.substring(pos, endPos);

            List<SubjectDataHolder> dataHolderList = new ArrayList<>();

            /* Transformando: "2015-2" em "2015/2|"
             * Motivo:               \/           \/                   \/
             * <a rel="1052N6B|947|2015/2|EMPREENDEDORISMO II|PAULO ROBERTO SALVADOR" class="disciplinasMaterialDidatico" ...
             */
            String semester = getSemester().replace("-", "/");
            final char SPECIAL_CHARACTER = (char) 124;

            endPos = 0;
            do {
                pos = SyncDataHelper.getIndexAndGoToEnd("a rel=\"", html, endPos);
                endPos = SyncDataHelper.getIndexOf("\"", html, pos);
                String unsplit = html.substring(pos, endPos);
                UnisantaApplication.Log_i("Linha: " + unsplit);

                SubjectDataHolder dataHolder = new SubjectDataHolder();
                int[] positions = getPositionsSpecialCharacter(unsplit, SPECIAL_CHARACTER);

                dataHolder.classGroup = unsplit.substring(0, positions[0]);
                dataHolder.id = unsplit.substring(positions[0]+1, positions[1]);
                dataHolder.subjectName = SyncDataHelper.fixSpecialCharacters(
                    unsplit.substring(positions[2]+1, positions[3])
                );
                dataHolder.teacherName = SyncDataHelper.fixSpecialCharacters(
                    unsplit.substring(positions[3]+1, unsplit.length())
                );
                dataHolderList.add(dataHolder);

                UnisantaApplication.Log_i("Matéria: " +
                        "\nid: " + dataHolder.id +
                        "\nnome: " + dataHolder.subjectName +
                        "\nturma: " + dataHolder.classGroup +
                        "\nprofesor: " + dataHolder.teacherName);
            }
            while (html.indexOf(semester, endPos) > -1);

            saveSubjects(dataHolderList);

        }
        catch (StringNotFoundException e) {
            throw new MissingTagException(coursewarePage.getUrl(), coursewarePage.getPageName(), e.getString());
        }
        catch (UnavailableDataException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SyncFailedException(e, coursewarePage.getUrl(), coursewarePage.getPageName());
        }
    }

    @Override
    public String getMessage() {
        return UnisantaApplication.getInstance().getString(R.string.subjects_of_semester, getSemester());
    }

    private int[] getPositionsSpecialCharacter(String line, char special) {
        int[] positions = new int[5];
        for (int i = 0, found = 0; i < line.length() && found < 5; i++) {
            if (line.charAt(i) == special) {
                positions[found] = i;
                found++;
            }
        }
        return positions;
    }

    private void saveSubjects(List<SubjectDataHolder> dataHolderList) {
        for (SubjectDataHolder dataHolder : dataHolderList) {
            UnisantaApplication.Log_i("\nSubject: " + dataHolder.subjectName +
                "\nTeacher: " + dataHolder.teacherName +
                "\nSemester: " + getSemester());
            
            Subject subject = subjectRepository.findByNameAndSchoolYear(
                    dataHolder.subjectName,
                    new SchoolYear(getSemester()));

            Teacher teacher = teacherRepository.findByName(dataHolder.teacherName);
            if (teacher == null) {
                teacher = new Teacher(dataHolder.teacherName);
            }

            //Matéria ainda não existe, preciso criar
            if (subject == null) {
                UnisantaApplication.Log_i("Matéria: " + dataHolder.subjectName + " não existe");

                subject = SubjectFactory.createSubject(
                        dataHolder.id,
                        dataHolder.subjectName,
                        getSemester(),
                        teacher,
                        dataHolder.classGroup,
                        subjectRepository);
            }
            else {
                UnisantaApplication.Log_i("Matéria " + dataHolder.subjectName + " já existe. Vou atualizar");

                subject.setTeacher(teacher);
                subject.setClassGroup(dataHolder.classGroup);
            }

            teacherRepository.saveTeacher(teacher);
            subject.save(subject);
        }
    }

    private final class SubjectDataHolder {
        public String id;
        public String classGroup;
        public String subjectName;
        public String teacherName;
    }
}
