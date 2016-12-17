package si.unisanta.tcc.unisantaapp.application.fragments.grade;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.fragments.IRefreshFragment;
import si.unisanta.tcc.unisantaapp.domain.dto.SubjectGradeDto;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.factories.DtoFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class GradesFragment extends Fragment implements IRefreshFragment {
    private static final String ARG_SEMESTER = "semester";

    private String semester;
    private GradeRecyclerAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    public static GradesFragment newInstance(String semester) {
        Bundle args = new Bundle();
        args.putString(ARG_SEMESTER, semester);

        GradesFragment fragment = new GradesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            semester = getArguments().getString(ARG_SEMESTER);
        }

        adapter = createAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.grades_fragment, container, false);

        setupRefresherLayout(root);
        setupRecyclerView(root);

        return root;
    }

    private void setupRefresherLayout(View root) {
        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
        if (getActivity() != null) {
            refreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)getActivity());
            refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
            refreshLayout.setColorSchemeColors(
                    R.color.refresh_progress_1,
                    R.color.refresh_progress_2,
                    R.color.refresh_progress_3);
        }
    }

    private void setupRecyclerView(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    private GradeRecyclerAdapter createAdapter() {
        return new GradeRecyclerAdapter(getContext(), createDataSet());
    }

    protected List<SubjectGradeDto> createDataSet() {
        List<SubjectGradeDto> returnList = new ArrayList<>();
        List<Subject> subjectList = RepositoryFactory.getSubjectRepository().findBySchoolYear(new SchoolYear(semester));

        for (Subject subject : subjectList) {
            SubjectGradeDto dto =
                    DtoFactory.createSubjectGradeDto(subject, RepositoryFactory.getGradeRepository().findBySubject(subject));
            returnList.add(dto);
        }

        return returnList;
    }

    public void changeSemester(String newSemester) {
        if (!semester.equals(newSemester)) {
            this.semester = newSemester;
            adapter.changeDataSet(createDataSet());
        }
    }

    @Override
    public void refreshData(Context context) {
        adapter.changeDataSet(createDataSet());
    }

    @Override
    public void refreshDone() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
