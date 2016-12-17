package si.unisanta.tcc.unisantaapp.application.courseware;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.AppCompatActivityToolbarBase;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.model.ISubjectRepository;

public class SubjectListActivity extends AppCompatActivityToolbarBase implements AdapterView.OnItemClickListener{
    private BaseAdapter adapter;
    private ISubjectRepository subjectRepository;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_content_item);

        subjectRepository = RepositoryFactory.getSubjectRepository();

        super.setupToolbar();
        super.setToolbarTitle(R.string.choose_subject);
        super.setupSemesterSelector();

        if (savedInstanceState != null) {
            tvSemester.setText(savedInstanceState.getString(SEMESTER_KEY));
        }
        setupListView();

        TextView tvNoData = (TextView) findViewById(android.R.id.empty);
        tvNoData.setText(R.string.no_subject_to_choose);
    }

    private void setupListView() {
        listView = (ListView) findViewById(android.R.id.list);
        setListAdapter();
        listView.setOnItemClickListener(this);
    }

    private void setListAdapter() {
        adapter = new SubjectListAdapter(
                this,
                subjectRepository.findBySchoolYear(
                        getSelectedSchoolYear()
                ),
                getSelectedSchoolYear()
        );

        listView.setAdapter(adapter);
    }

    @Override
    protected void onChangeSemester(String oldSemester, String newSemester) {
        super.onChangeSemester(oldSemester, newSemester);
        setListAdapter();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View empty = findViewById(android.R.id.empty);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setEmptyView(empty);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        long id = adapter.getItemId(i);
        Intent intent = new Intent(SubjectListActivity.this, CoursewareListActivity.class);
        intent.putExtra(CoursewareListActivity.KEY_SUBJECT_ID, id);
        startActivity(intent);
    }
}
