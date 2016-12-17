package si.unisanta.tcc.unisantaapp.application.fragments.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterRecyclerAdapter;
import si.unisanta.tcc.unisantaapp.application.fragments.common.GenericViewHolder;
import si.unisanta.tcc.unisantaapp.application.fragments.common.HeaderViewHolder;
import si.unisanta.tcc.unisantaapp.application.fragments.common.IGenericListItem;
import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;
import si.unisanta.tcc.unisantaapp.domain.framework.ClassScheduleWeekComparator;

public class ClassRecyclerAdapter extends FilterRecyclerAdapter<ClassSchedule> {

    public ClassRecyclerAdapter(Context context, List<ClassSchedule> fullList) {
        super(context, fullList);
    }

    @Override
    protected void beforeFilter() {
        Collections.sort(fullList, new ClassScheduleWeekComparator());
    }

    @Override
    protected boolean mayAdd(ClassSchedule classSchedule) {
        if (displayFilter == SHOW_ALL)
            return true;

        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        if (displayFilter == SHOW_TODAY) {
            return (classSchedule.getWeekDay() == today);
        }
        else { //NEXT
            int next = (today == 7) ? 1 : today+1;

            while (!isThereAnyClassThisDay(next)) {
                next++;
                if (next > 7) next = 1;

                if (next == today)
                    return false;
            }

            return (classSchedule.getWeekDay() == next);
        }
    }

    private boolean isThereAnyClassThisDay(int day) {
        for (ClassSchedule classSchedule : fullList) {
            if (classSchedule.getWeekDay() == day) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean shouldCreateHeader(ClassSchedule item, IGenericListItem last) {
        if (last != null) {
            ClassScheduleItemAdapter lastClass = (ClassScheduleItemAdapter)last;
            return  (lastClass.getClassSchedule().getWeekDay() != item.getWeekDay());
        }
        return true;
    }

    @Override
    protected IGenericListItem createHeader(ClassSchedule item) {
        return new WeekHeaderItem(getWeekTitle(item.getWeekDay()));
    }

    @Override
    protected IGenericListItem createItem(ClassSchedule item) {
        return new ClassScheduleItemAdapter(item);
    }

    private String getWeekTitle(int weekDay) {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        String divider = " - ";
        String suffix;
        if (today == weekDay)
            suffix = "HOJE";
        else if (today-1 == weekDay)
            suffix = "ONTEM";
        else if (today+1 == weekDay)
            suffix = "AMANHÃ";
        else {
            int diff = weekDay - today;
            calendar.add(Calendar.DAY_OF_MONTH, diff);

            suffix = String.format("%02d/%02d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)+1);
        }

        weekDay--;
        String[] days = context.getResources().getStringArray(R.array.week_days);

        return
                days[weekDay].toUpperCase() + divider + suffix;
    }

    @Override
    public int getItemViewType(int position) {
        if (filterList.isEmpty())
            return RowType.EMPTY.ordinal();

        if (filterList.get(position) instanceof ClassScheduleItemAdapter)
            return RowType.ITEM.ordinal();
        return RowType.HEADER.ordinal();
    }

    @Override
    protected GenericViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view;

        if (viewType == RowType.ITEM.ordinal()) {
            view = inflater.inflate(R.layout.class_list_item, parent, false);
            return new ClassScheduleViewHolder(view);
        }

        view = inflater.inflate(R.layout.header_list_item, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    protected Object getBindObject(GenericViewHolder holder, int position) {
        if (holder instanceof ClassScheduleViewHolder) {
            ClassScheduleItemAdapter classSchedule = (ClassScheduleItemAdapter) filterList.get(position);
            return classSchedule.getClassSchedule();
        }

        WeekHeaderItem weekHeader = (WeekHeaderItem) filterList.get(position);
        return weekHeader.getTitle();
    }

    @Override
    protected String getEmptyString() {
        if (fullList.isEmpty() || displayFilter == SHOW_NEXT || displayFilter == SHOW_ALL) {
            return context.getString(R.string.no_classes_message);
        }

        return context.getString(R.string.no_classes_today_message);
    }

    final class ClassScheduleViewHolder extends GenericViewHolder {
        TextView dpView;
        TextView subjectNameView;
        TextView teacherView;
        TextView roomView;
        TextView startView;
        TextView endView;

        public ClassScheduleViewHolder(View viewItem) {
            super(viewItem);

            subjectNameView = (TextView) viewItem.findViewById(R.id.tvSubjectName);
            teacherView = (TextView) viewItem.findViewById(R.id.tvTeacherName);
            dpView = (TextView) viewItem.findViewById(R.id.tvDP);
            roomView = (TextView) viewItem.findViewById(R.id.tvClassroom);
            startView = (TextView) viewItem.findViewById(R.id.tvStart);
            endView = (TextView) viewItem.findViewById(R.id.tvEnd);
        }

        public void onBind(Object param) {
            ClassSchedule classSchedule = (ClassSchedule) param;

            subjectNameView.setText(classSchedule.getSubject().getName());
            teacherView.setText(classSchedule.getSubject().getTeacher().getDisplayName());
            dpView.setVisibility(classSchedule.getSubject().isDP() ? View.VISIBLE : View.GONE);
            roomView.setText(classSchedule.getClassroom().toString());
            startView.setText(classSchedule.getStartTime().toString());
            endView.setText(classSchedule.getEndTime().toString());
        }
    }
}
