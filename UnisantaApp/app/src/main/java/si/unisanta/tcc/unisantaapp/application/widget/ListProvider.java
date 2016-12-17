package si.unisanta.tcc.unisantaapp.application.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.framework.ClassScheduleWeekComparator;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<ClassSchedule> classesList;
    private Context context = null;

    public ListProvider(Context context, Intent intent) {
        int appWidgetId;

        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        IClassScheduleRepository repository = RepositoryFactory.getClassScheduleRepository();
        classesList = repository.findNextClasses();
        Collections.sort(classesList, new ClassScheduleWeekComparator());
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (classesList.size() > 0)
            return classesList.size()+1;//+1 do header
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView;
        if (position == 0) {
            remoteView = new RemoteViews(context.getPackageName(), R.layout.header_list_item);

            if (classesList.size() > 0) {
                String title = getTitle(classesList.get(0).getWeekDay());
                remoteView.setTextViewText(R.id.tvHeader, title);
            }
        }
        else {
            position--;
            ClassSchedule classSchedule = classesList.get(position);

            remoteView = new RemoteViews(context.getPackageName(), R.layout.class_list_item);

            remoteView.setViewVisibility(R.id.tvDP, (classSchedule.getSubject().isDP()) ? View.VISIBLE : View.GONE);
            remoteView.setTextViewText(R.id.tvSubjectName, classSchedule.getSubject().getName());
            remoteView.setTextViewText(R.id.tvTeacherName, classSchedule.getSubject().getTeacher().getDisplayName());
            remoteView.setTextViewText(R.id.tvClassroom, classSchedule.getClassroom().toString());
            remoteView.setTextViewText(R.id.tvStart, classSchedule.getStartTime().toString());
            remoteView.setTextViewText(R.id.tvEnd, classSchedule.getEndTime().toString());
        }

        return remoteView;
    }

    private String getTitle(int weekDay) {
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);

        if (today != weekDay) {
            int diff = weekDay - today;
            cal.add(Calendar.DAY_OF_MONTH, diff);
        }

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        String[] days = context.getResources().getStringArray(R.array.week_days);
        weekDay--;
        month++;

        return String.format("%s (%02d/%02d)",
                days[weekDay].toUpperCase(),
                day,
                month);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }
}