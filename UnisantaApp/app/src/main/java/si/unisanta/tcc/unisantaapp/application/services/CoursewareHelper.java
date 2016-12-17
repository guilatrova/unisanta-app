package si.unisanta.tcc.unisantaapp.application.services;

import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.io.File;

import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.services.courseware.CoursewarePathStrategy;

public class CoursewareHelper {

    public static boolean alreadyExists(Courseware courseware, CoursewarePathStrategy pathStrategy) {
        File file = new File(pathStrategy.getAbsolutePath(courseware));
        return file.exists();
    }

    public static Intent createIntent(Courseware courseware, CoursewarePathStrategy pathStrategy) {
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String fullPath = pathStrategy.getAbsolutePath(courseware);

        String ext = fullPath.substring(fullPath.lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(fullPath)), type);

        return intent;
    }
}
