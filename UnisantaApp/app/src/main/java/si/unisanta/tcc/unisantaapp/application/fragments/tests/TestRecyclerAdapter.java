package si.unisanta.tcc.unisantaapp.application.fragments.tests;

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
import si.unisanta.tcc.unisantaapp.domain.entities.Test;
import si.unisanta.tcc.unisantaapp.domain.framework.CalendarDayComparator;
import si.unisanta.tcc.unisantaapp.domain.framework.TestDateComparator;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;

public class TestRecyclerAdapter extends FilterRecyclerAdapter<Test> {

    public TestRecyclerAdapter(Context context, List<Test> fullList) {
        super(context, fullList);
    }

    @Override
    protected void beforeFilter() {
        Collections.sort(fullList, new TestDateComparator());
    }

    @Override
    protected boolean mayAdd(Test test) {
        if (displayFilter == SHOW_ALL)
            return true;

        Calendar today = Calendar.getInstance();
        DateTime testDatetime = test.getDatetime();

        if (displayFilter == SHOW_TODAY) {
            return (today.get(Calendar.DAY_OF_MONTH) == testDatetime.getDay() &&
                    today.get(Calendar.MONTH) +1 == testDatetime.getMonth() &&
                    today.get(Calendar.YEAR) == testDatetime.getYear());
        }
        else { //NEXT
            return (today.get(Calendar.DAY_OF_MONTH) < testDatetime.getDay() &&
                    today.get(Calendar.MONTH) +1 <= testDatetime.getMonth() &&
                    today.get(Calendar.YEAR) <= testDatetime.getYear());
        }
    }

    @Override
    protected boolean shouldCreateHeader(Test item, IGenericListItem last) {
        if (last != null) {
            TestScheduleItemAdapter lastTest = (TestScheduleItemAdapter) last;
            return lastTest.getTest().getDatetime().getDay() != item.getDatetime().getDay();
        }
        return true;
    }

    @Override
    protected IGenericListItem createHeader(Test item) {
        return new TestHeaderItem(getHeaderTitle(item.getDatetime()));
    }

    @Override
    protected IGenericListItem createItem(Test item) {
        return new TestScheduleItemAdapter(item);
    }

    private String getHeaderTitle(DateTime dateTime) {
        CalendarDayComparator dayComparator = new CalendarDayComparator();
        int dayDiff = dayComparator.compare(dateTime.toCalendar(), Calendar.getInstance());
        int weekDay = dateTime.toCalendar().get(Calendar.DAY_OF_WEEK) - 1;
        String[] week = context.getResources().getStringArray(R.array.week_days);
        String endText;

        switch (dayDiff) {
            case -1:
                endText = "ONTEM";
                break;

            case 0:
                endText = "HOJE";
                break;

            case 1:
                endText = "AMANHÃ";
                break;

            case 2:
                endText = "EM 2 DIAS";
                break;

            case 3:
                endText = "EM 3 DIAS";
                break;

            default:
                endText = String.format("%02d/%02d/%02d", dateTime.getDay(), dateTime.getMonth(), dateTime.getYear());
                break;
        }

        return String.format("%s - %s", week[weekDay].toUpperCase(), endText);
    }

    @Override
    public int getItemViewType(int position) {
        if (filterList.isEmpty())
            return RowType.EMPTY.ordinal();

        if (filterList.get(position) instanceof TestScheduleItemAdapter)
            return RowType.ITEM.ordinal();
        return RowType.HEADER.ordinal();
    }

    @Override
    protected GenericViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view;

        if (viewType == RowType.ITEM.ordinal()) {
            view = inflater.inflate(R.layout.test_list_item, parent, false);
            return new TestViewHolder(view);
        }

        view = inflater.inflate(R.layout.header_list_item, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    protected Object getBindObject(GenericViewHolder holder, int position) {
        if (holder instanceof TestViewHolder) {
            TestScheduleItemAdapter testSchedule = (TestScheduleItemAdapter) filterList.get(position);
            return testSchedule.getTest();
        }

        TestHeaderItem dateHeader = (TestHeaderItem) filterList.get(position);
        return dateHeader.getTitle();
    }

    @Override
    protected String getEmptyString() {
        if (fullList.isEmpty() || super.displayFilter == SHOW_NEXT || super.displayFilter == SHOW_ALL) {
            return context.getString(R.string.no_tests_message);
        }

        return context.getString(R.string.no_tests_today_message);
    }

    final class TestViewHolder extends GenericViewHolder {
        TextView headerView;
        View dpView;
        TextView subjectNameView;
        TextView tvClassroom;
        TextView tvWeight;
        TextView tvTime;

        public TestViewHolder(View itemView) {
            super(itemView);

            headerView = (TextView) itemView.findViewById(R.id.tvHeader);
            dpView = itemView.findViewById(R.id.tvDP);
            subjectNameView = (TextView) itemView.findViewById(R.id.tvSubjectName);
            tvWeight = (TextView) itemView.findViewById(R.id.tvWeight);
            tvClassroom = (TextView) itemView.findViewById(R.id.tvClassroom);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }

        public void onBind(Object param) {
            Test test = (Test) param;

            subjectNameView.setText(test.getSubject().getName());
            tvWeight.setText(getWeight(test.getWeight()));
            dpView.setVisibility(test.getSubject().isDP() ? View.VISIBLE : View.GONE);
            tvClassroom.setText(test.getClassroom().toString());
            tvTime.setText(test.getDatetime().getTime().toString());
        }

        private String getWeight(int weight) {
            if (weight == Test.P1)
                return "P1";
            if (weight == Test.P2)
                return "P2";
            return "P3";
        }
    }
}
