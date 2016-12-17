package si.unisanta.tcc.unisantaapp.application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.courseware.SubjectListActivity;
import si.unisanta.tcc.unisantaapp.application.factories.AppRaterFactory;
import si.unisanta.tcc.unisantaapp.application.factories.DialogFactory;
import si.unisanta.tcc.unisantaapp.application.factories.HelperFactory;
import si.unisanta.tcc.unisantaapp.application.services.IntentHelper;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.ServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.services.sync.ISyncProgressListener;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncTaskService;
import si.unisanta.tcc.unisantaapp.domain.services.update.IUpdateListener;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class MainActivity extends AppCompatActivityToolbarBase
        implements ISyncProgressListener, SwipeRefreshLayout.OnRefreshListener, IUpdateListener, NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private MainActivityPagerAdapter pagerAdapter;
    private ProgressDialog progressDialog;
    private String selectedTabText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.selectedTabText = getString(R.string.classes_tab_main);

        super.setupToolbar();
        setupSemesterSelector();

        setupTabsAndViewPager();
        setupNavigationDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (User.getInstance() == null) {
            RepositoryFactory.getUserRepository().retrieve();
        }

        ServiceFactory.createUpdateService(this).execute();
        AppRaterFactory.createAppRater(this).show();
    }

    @Override
    public void handleUpdate(AppVersion oldVersion, AppVersion newVersion) {
        String news = RepositoryFactory.getUpdateDetailsRepository().findAfter(oldVersion);
        UnisantaApplication.Log_i(String.format("Atualizado de %s para %s.\nNovidades: %s",
                oldVersion.toString(),
                newVersion.toString(),
                news));

        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.news_title, newVersion.toString()))
            .setMessage(news)
            .setPositiveButton(R.string.ok, null)
            .show();
    }

    @Override
    protected void setupSemesterSelector() {
        super.setupSemesterSelector();
        tvSemester.setVisibility(View.GONE);
    }

    private void setupNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupTabsAndViewPager() {
        TabLayout tabLayout;
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new MainActivityPagerAdapter(
                getSupportFragmentManager(),
                this,
                super.getSelectedSchoolYear().toString()
        );

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabText = tab.getText().toString();
                if (tab.getText().equals(getResources().getString(R.string.grades_tab_main))) {
                    tvSemester.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getText().equals(getResources().getString(R.string.grades_tab_main))) {
                    tvSemester.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle != null && drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onChangeSemester(String oldSemester, String newSemester) {
        super.onChangeSemester(oldSemester, newSemester);
        if (!oldSemester.equals(newSemester)) {
            pagerAdapter.setSemester(newSemester);
        }
    }

    private void syncDataMenu() {
        final List<Integer> mSelectedItems = new ArrayList<>();

        new AlertDialog.Builder(this)
                .setTitle(R.string.sync)
                .setMultiChoiceItems(new String[]{"Todos os semestres", "Aulas", "Notas", "Material didático", "Provas"}, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SyncTaskService.Builder builder = new SyncTaskService.Builder();

                        if (mSelectedItems.contains(0))
                            builder = builder.setSyncAllSemesters(true);

                        builder = builder.add(SyncTaskService.Builder.SYNC_SUBJECTS);

                        if (mSelectedItems.contains(1))
                            builder = builder.add(SyncTaskService.Builder.SYNC_CLASSES);

                        if (mSelectedItems.contains(2))
                            builder = builder.add(SyncTaskService.Builder.SYNC_GRADES);

                        if (mSelectedItems.contains(3))
                            builder = builder.add(SyncTaskService.Builder.SYNC_COURSEWARE);

                        if (mSelectedItems.contains(4))
                            builder = builder.add(SyncTaskService.Builder.SYNC_TESTS);

                        SyncTaskService syncTaskService = builder.build();
                        syncTaskService.setListener(MainActivity.this);
                        syncTaskService.execute();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    //Sincronização dos dados
    @Override
    public void onStarting() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onMessageChange(String msg) {
        progressDialog.setMessage(msg);
    }

    @Override
    public void onProgressChange(int progress) {
        progressDialog.setProgress(progress);
    }

    @Override
    public void onSuccess(Object result) {
        if (progressDialog != null)
            progressDialog.dismiss();
        pagerAdapter.refreshFragments();
    }

    @Override
    public void onFailure(Exception e) {
        if (progressDialog != null)
            progressDialog.dismiss();
        new DialogFactory(this).createDialogFromException(e, this).show();
    }

    @Override
    public void onRefresh() {
        SyncTaskService.Builder builder = new SyncTaskService.Builder();
        SchoolYear schoolYear = SchoolYear.getCurrent();

        if (selectedTabText.equals(getString(R.string.classes_tab_main))) {
            builder.add(SyncTaskService.Builder.SYNC_CLASSES);
        }
        else if (selectedTabText.equals(getString(R.string.grades_tab_main))) {

            //TODO create specification AnySubjectExistsThisSchoolYear.And(LastSyncWas())
            if (!RepositoryFactory.getClassScheduleRepository().anyClassThisSchoolYear(getSelectedSchoolYear())) {
                builder.add(SyncTaskService.Builder.SYNC_SUBJECTS, getSelectedSchoolYear());
            }
            builder.add(SyncTaskService.Builder.SYNC_GRADES, getSelectedSchoolYear());
        }
        else {
            builder.add(SyncTaskService.Builder.SYNC_TESTS);
        }

        builder.build()
                .setListener(new ISyncProgressListener() {
                    @Override
                    public void onStarting() {
                    }

                    @Override
                    public void onMessageChange(String msg) {
                    }

                    @Override
                    public void onProgressChange(int progress) {
                    }

                    @Override
                    public void onSuccess(Object result) {
                        pagerAdapter.refreshDone();
                        MainActivity.this.onSuccess(result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        pagerAdapter.refreshDone();
                        MainActivity.this.onFailure(e);
                    }
                })
                .execute();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.menu_item_courseware:
                intent = new Intent(MainActivity.this, SubjectListActivity.class);
                startActivity(intent);
                break;

            case R.id.sync:
                syncDataMenu();
                break;

            case R.id.share_app:
                intent = HelperFactory.createShareHelper().createIntentToShareApp();
                startActivity(intent);
                break;

            case R.id.facebook:
                intent = IntentHelper.createIntentForFacebookPage();
                startActivity(intent);
                break;

            case R.id.delete_all:
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(R.string.clear_student_data_dialog)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                UnisantaApplication.Log_i("deletando");
                                ServiceFactory.createClearDataService().clear();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();
                break;

            case R.id.about_app:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}
