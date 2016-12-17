package si.unisanta.tcc.unisantaapp.application.services;

import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.framework.ClassScheduleWeekComparator;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;

public class ShareHelper {
    public static final int ALL = 0;
    public static final int NEXT = 1;
    public static final int TODAY = 2;

    private Context context;
    private IClassScheduleRepository classScheduleRepository;

    public ShareHelper(Context context, IClassScheduleRepository classScheduleRepository) {
        this.context = context;
        this.classScheduleRepository = classScheduleRepository;
    }

    public Intent createIntentToShareClasses(int option) {
        List<ClassSchedule> classScheduleList;
        if (option == TODAY) {
            classScheduleList = classScheduleRepository.findClassesOfDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        }
        else if(option == NEXT) {
            classScheduleList = classScheduleRepository.findNextClasses();
        }
        else {
            classScheduleList = classScheduleRepository.findAll();
        }

        Collections.sort(classScheduleList, new ClassScheduleWeekComparator());
        String text = "";
        String line = "\n\n-----";
        String[] weekdays = context.getResources().getStringArray(R.array.week_days);
        int lastWeek = -1;

        text += context.getString(R.string.share_classes_header);

        for (ClassSchedule classSchedule : classScheduleList) {
            if (classSchedule.getWeekDay() != lastWeek) {
                lastWeek = classSchedule.getWeekDay();
                text += line + weekdays[lastWeek-1];
            }

            text += String.format("\n%s na %s às %s",
                    classSchedule.getSubject().getNickName(),
                    classSchedule.getClassroom().toString(),
                    classSchedule.getStartTime().toString());
        }

        text += "\n\n" + context.getString(R.string.share_signature);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        return Intent.createChooser(shareIntent, context.getString(R.string.share_classes_chooser));
    }

    public Intent createIntentToShareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_app));
        shareIntent.setType("text/plain");
        return Intent.createChooser(shareIntent, context.getString(R.string.share_classes_chooser));
    }
}
