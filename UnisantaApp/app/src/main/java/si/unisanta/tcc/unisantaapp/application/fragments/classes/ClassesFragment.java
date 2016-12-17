package si.unisanta.tcc.unisantaapp.application.fragments.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.factories.HelperFactory;
import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterFragment;
import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterRecyclerAdapter;
import si.unisanta.tcc.unisantaapp.application.services.ShareHelper;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.model.IClassScheduleRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class ClassesFragment extends FilterFragment {

    public static ClassesFragment newInstance() {
        return new ClassesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_classes, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_action:
                ShareHelper shareHelper = HelperFactory.createShareHelper();
                Intent shareClasses = shareHelper.createIntentToShareClasses(getClassShareOption());
                startActivity(shareClasses);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getClassShareOption() {
        switch (getAdapter().getDisplayFilter()) {
            case FilterRecyclerAdapter.SHOW_ALL:
                return ShareHelper.ALL;

            case FilterRecyclerAdapter.SHOW_TODAY:
                return ShareHelper.TODAY;

            case FilterRecyclerAdapter.SHOW_NEXT:
                return ShareHelper.NEXT;
        }
        return -1;
    }

    @Override
    protected FilterRecyclerAdapter createAdapter(Context context) {
        IClassScheduleRepository classScheduleRepository = RepositoryFactory.getClassScheduleRepository();

        return new ClassRecyclerAdapter(context,
                classScheduleRepository.findBySchoolYear(SchoolYear.getCurrent()));
    }

    protected ClassRecyclerAdapter getAdapter() {
        return (ClassRecyclerAdapter) super.adapter;
    }
}
