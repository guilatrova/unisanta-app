package si.unisanta.tcc.unisantaapp.domain.factories;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class CoursewareFactory {

    public static Courseware createCourseware(Subject subject, String title, String description, String uploadDateTime, String size, String downloadLink) {

        String uploadDate = uploadDateTime.substring(0, uploadDateTime.indexOf(","));
        String uploadTime = uploadDateTime.substring(uploadDateTime.length()-5, uploadDateTime.length());

        UnisantaApplication.Log_d("Date: " + uploadDate);
        UnisantaApplication.Log_d("Time: " + uploadTime);

        return new Courseware(
            subject,
            title,
            description,
            uploadDate, new Time(uploadTime),
            size,
            downloadLink
        );
    }
}
