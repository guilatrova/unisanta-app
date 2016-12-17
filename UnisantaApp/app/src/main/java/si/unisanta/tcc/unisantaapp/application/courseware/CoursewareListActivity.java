package si.unisanta.tcc.unisantaapp.application.courseware;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.AppCompatActivityToolbarBase;
import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.application.factories.DialogFactory;
import si.unisanta.tcc.unisantaapp.application.services.ColoredSnackbar;
import si.unisanta.tcc.unisantaapp.application.services.CoursewareHelper;
import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.ServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ICoursewareRepository;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;
import si.unisanta.tcc.unisantaapp.domain.services.courseware.DownloadCoursewareService;
import si.unisanta.tcc.unisantaapp.domain.services.sync.ISyncCancelableProgressListener;
import si.unisanta.tcc.unisantaapp.domain.services.sync.ISyncProgressListener;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncTaskService;
import si.unisanta.tcc.unisantaapp.infrastructure.web.http.HttpRequest;

public class CoursewareListActivity extends AppCompatActivityToolbarBase implements AdapterView.OnItemClickListener, ISyncCancelableProgressListener<Courseware>, SwipeRefreshLayout.OnRefreshListener {
    public static final String KEY_SUBJECT_ID = "SUBJECT_ID";
    public static final int PERMISSION_SAVE_DOWNLOAD_REQUEST = 1;

    private BaseAdapter adapter;
    private ISubjectRepository subjectRepository;
    private ICoursewareRepository coursewareRepository;

    private Subject subject;
    private ProgressDialog progressDialog;
    protected SwipeRefreshLayout swipeContainer;
    private DownloadCoursewareService downloadService;
    private Courseware lastCoursewareClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_content_refresh);

        super.setupToolbar();

        if (!getIntent().hasExtra(KEY_SUBJECT_ID))
            throw new IllegalArgumentException("É necessário receber o id da matéria");
        long id = getIntent().getLongExtra(KEY_SUBJECT_ID, -1);

        subjectRepository = RepositoryFactory.getSubjectRepository();
        coursewareRepository = RepositoryFactory.getCoursewareRepository();
        subject = subjectRepository.findById(id);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        setupRefreshLayout(swipeContainer);
        setupListView(subject);

        super.setToolbarTitle(subject.getNickName());
        TextView tvNoData = (TextView) findViewById(android.R.id.empty);
        tvNoData.setText(R.string.no_courseware_for_this_subject);
    }

    private void createDownloadService() {
        downloadService = new DownloadCoursewareService(
                ServiceFactory.createUserLoginService(),
                ServiceFactory.createConnectionService(),
                HttpRequest.getInstance(),
                ServiceFactory.createCoursewarePathStrategy());
        downloadService.setListener(this);
    }

    private void setupRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
    }

    private void setupListView(Subject subject) {
        final ListView listView = (ListView) findViewById(android.R.id.list);
        adapter = new CoursewareListAdapter(
                this,
                coursewareRepository.findBySubject(subject)
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(findViewById(android.R.id.empty));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (listView == null || listView.getChildCount() == 0) ?
                                0 : listView.getChildAt(0).getTop();
                swipeContainer.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });
    }

    @Override
    public void onRefresh() {
        new SyncTaskService.Builder()
            .add(subject)
            .build()
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
                    ((CoursewareListAdapter)adapter).setList(
                            coursewareRepository.findBySubject(subject)
                    );

                    if (swipeContainer.isRefreshing())
                        swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(Exception e) {
                    if (swipeContainer.isRefreshing())
                        swipeContainer.setRefreshing(false);

                    new DialogFactory(CoursewareListActivity.this).createDialogFromException(e, CoursewareListActivity.this).show();
                }
            })
            .execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Courseware courseware = (Courseware) adapter.getItem(i);
        lastCoursewareClicked = courseware;

        if (CoursewareHelper.alreadyExists(courseware, ServiceFactory.createCoursewarePathStrategy())) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.download_title, courseware.getFileName()))
                    .setMessage(R.string.courseware_already_exists)
                    .setPositiveButton(R.string.replace_courseware, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            downloadCourseware(courseware);
                        }
                    })
                    .setNegativeButton(R.string.open, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openFile(courseware);
                        }
                    })
                    .setNeutralButton(R.string.cancel, null)
                    .show();
        }
        else {
            downloadCourseware(courseware);
        }
    }

    private void downloadCourseware(Courseware courseware) {
        if (hasPermissionToDownload()) {
            createDownloadService();
            downloadService.execute(courseware);
        }
        else {
            explainOrAskPermission();
        }
    }

    private boolean hasPermissionToDownload() {
        int permissionResult = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void explainOrAskPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                .setTitle(R.string.download_permission_explain_title)
                .setMessage(getString(R.string.download_permission_explain_message))
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        askPermission();
                    }
                })
                .show();
        }
        else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_SAVE_DOWNLOAD_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_SAVE_DOWNLOAD_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadCourseware(lastCoursewareClicked);
            }
            else {
                new AlertDialog.Builder(this)
                    .setTitle(R.string.download_permission_denied_title)
                    .setMessage(R.string.download_permission_denied_message)
                    .setNeutralButton(R.string.ok, null)
                    .show();
            }
        }
    }

    @Override
    public void onStarting() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Download");
        progressDialog.setMessage("Iniciando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //true if the thread executing this task should be interrupted;
                //otherwise, in-progress tasks are allowed to complete.
                downloadService.cancel(false);
            }
        });
        progressDialog.setMax(100);
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
    public void onSuccess(final Courseware result) {
        progressDialog.dismiss();

        ColoredSnackbar.info(
        Snackbar.make(findViewById(android.R.id.content), R.string.download_successful, Snackbar.LENGTH_LONG)
            .setAction(R.string.open, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFile(result);
                }
            }))
            .show();
    }

    private void openFile(Courseware courseware) {
        Intent intent = CoursewareHelper.createIntent(courseware, ServiceFactory.createCoursewarePathStrategy());

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            ColoredSnackbar.alert(Snackbar.make(findViewById(android.R.id.content),
                    UnisantaApplication.getInstance().getString(
                            R.string.cant_open_file, courseware.getExtension().toUpperCase()),
                    Snackbar.LENGTH_LONG))
                    .show();
        }
    }

    @Override
    public void onFailure(Exception e) {
        progressDialog.dismiss();
        DialogFactory dialogFactory = new DialogFactory(this);

        Dialog dialog = dialogFactory.createDialogFromException(e, this);
        dialog.show();
    }

    @Override
    public void onCancel() {
        ColoredSnackbar.info(
            Snackbar.make(findViewById(android.R.id.content), R.string.download_canceled, Snackbar.LENGTH_SHORT))
            .show();
    }
}
