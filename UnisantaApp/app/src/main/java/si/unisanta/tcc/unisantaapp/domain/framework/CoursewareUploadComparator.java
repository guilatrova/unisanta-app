package si.unisanta.tcc.unisantaapp.domain.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;

public class CoursewareUploadComparator implements Comparator<Courseware> {
    @Override
    public int compare(Courseware courseware, Courseware t1) {
        String uploadDate2 = t1.getUploadDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = dateFormat.parse(courseware.getUploadDate() + " " + courseware.getUploadTime());
            dt2 = dateFormat.parse(t1.getUploadDate() + " " + t1.getUploadTime());
        }
        catch (ParseException e) {
            UnisantaApplication.Log_e(e.getMessage(), e);
            return 0;
        }

        return dt1.compareTo(dt2);
    }
}
