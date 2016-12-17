package si.unisanta.tcc.unisantaapp.application.fragments.tests;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterFragment;
import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterRecyclerAdapter;
import si.unisanta.tcc.unisantaapp.domain.entities.Test;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.DateTime;

public class TestsFragment extends FilterFragment {
    private static final int LAST_X_DAYS = 15;

    public static TestsFragment newInstance() {
        return new TestsFragment();
    }

    public TestsFragment() {
    }

    @Override
    protected FilterRecyclerAdapter createAdapter(Context context) {
        return new TestRecyclerAdapter(context, createList());
    }

    private List<Test> createList() {
        Calendar begin = Calendar.getInstance();
        begin.add(Calendar.DAY_OF_MONTH, -LAST_X_DAYS);
        DateTime dtBegin = new DateTime(begin);

        return RepositoryFactory.getTestRepository().findFrom(dtBegin);
    }
}
