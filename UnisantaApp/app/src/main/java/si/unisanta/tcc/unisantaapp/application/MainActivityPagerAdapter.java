package si.unisanta.tcc.unisantaapp.application;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.fragments.IRefreshFragment;
import si.unisanta.tcc.unisantaapp.application.fragments.classes.ClassesFragment;
import si.unisanta.tcc.unisantaapp.application.fragments.grade.GradesFragment;
import si.unisanta.tcc.unisantaapp.application.fragments.tests.TestsFragment;

public class MainActivityPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] tabs;
    private String semester;
    private Map<Integer, Fragment> pageReferenceMap = new HashMap<>();

    private static final int GRADES_INDEX = 2;

    public MainActivityPagerAdapter(FragmentManager fm, Context context, String semester) {
        super(fm);
        this.context = context;
        this.semester = semester;
        tabs = context.getResources().getStringArray(R.array.main_toolbar_tabs);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ClassesFragment.newInstance();

            case 1:
                return TestsFragment.newInstance();

            case 2:
                return GradesFragment.newInstance(semester);
        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container, position);
        pageReferenceMap.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        pageReferenceMap.remove(position);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    public void setSemester(String newSemester) {
        this.semester = newSemester;
        Fragment gradesFragment = pageReferenceMap.get(GRADES_INDEX);
        if (gradesFragment != null) {
            ((GradesFragment)gradesFragment).changeSemester(newSemester);
        }
    }

    public void refreshFragments() {
        for (int i = 0; i < tabs.length; i++) {
            if (pageReferenceMap.get(i) != null)
                ((IRefreshFragment)pageReferenceMap.get(i)).refreshData(context);
        }
    }

    public void refreshDone() {
        for (int i = 0; i < tabs.length; i++) {
            if (pageReferenceMap.get(i) != null)
                ((IRefreshFragment)pageReferenceMap.get(i)).refreshDone();
        }
    }
}
