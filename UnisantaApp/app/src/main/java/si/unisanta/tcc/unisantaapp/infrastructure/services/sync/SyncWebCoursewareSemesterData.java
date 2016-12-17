package si.unisanta.tcc.unisantaapp.infrastructure.services.sync;

import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.exceptions.SyncFailedException;
import si.unisanta.tcc.unisantaapp.domain.exceptions.UnavailableDataException;
import si.unisanta.tcc.unisantaapp.domain.factories.SyncServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncBaseData;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SyncWebCoursewareSemesterData extends SyncBaseData{
    private ISubjectRepository subjectRepository;
    private SchoolYear schoolYear;

    public SyncWebCoursewareSemesterData(ISubjectRepository subjectRepository, SchoolYear schoolYear) {
        this.subjectRepository = subjectRepository;
        this.schoolYear = schoolYear;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    @Override
    public void sync() throws SyncFailedException {
        List<Subject> subjectList = subjectRepository.findBySchoolYear(schoolYear);
        for (Subject subject : subjectList) {
            try {
                SyncServiceFactory.createSyncCoursewareData(subject).sync();
            }
            catch (UnavailableDataException e) {
                continue;
            }
        }
    }

    @Override
    public String getMessage() {
        return UnisantaApplication.getInstance().getString(R.string.courseware_full_semester, getSchoolYear().toString());
    }
}
