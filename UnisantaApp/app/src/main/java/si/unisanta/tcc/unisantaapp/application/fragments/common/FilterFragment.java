package si.unisanta.tcc.unisantaapp.application.fragments.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.fragments.IRefreshFragment;

public abstract class FilterFragment extends Fragment implements IRefreshFragment {
    protected Context context;
    protected RecyclerView recyclerView;
    protected FilterRecyclerAdapter adapter;
    protected SwipeRefreshLayout refreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = createAdapter(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment, container, false);

        setupRefreshLayout(view);
        setupRadioGroup(view);
        setupRecyclerView(view);

        return view;
    }

    private void setupRadioGroup(View view) {
        RadioButton rbAll, rbToday, rbNext;

        rbAll = (RadioButton) view.findViewById(R.id.rbAllOption);
        rbNext = (RadioButton) view.findViewById(R.id.rbNextOption);
        rbToday = (RadioButton) view.findViewById(R.id.rbTodayOption);

        rbAll.setTag(FilterRecyclerAdapter.SHOW_ALL);
        rbNext.setTag(FilterRecyclerAdapter.SHOW_NEXT);
        rbToday.setTag(FilterRecyclerAdapter.SHOW_TODAY);

        OnChangeFilter changeFilter = new OnChangeFilter();
        rbAll.setOnCheckedChangeListener(changeFilter);
        rbNext.setOnCheckedChangeListener(changeFilter);
        rbToday.setOnCheckedChangeListener(changeFilter);
    }

    private void setupRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        refreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)getActivity());
        refreshLayout.setColorSchemeColors(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
    }

    private void setupRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    protected abstract FilterRecyclerAdapter createAdapter(Context context);

    public void onRefresh() {}

    public void refreshData(Context context) {
        onRefresh();
        int display = adapter.getDisplayFilter();
        adapter = createAdapter(context);
        adapter.setDisplayFilter(display);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshDone() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    protected final class OnChangeFilter implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int color;
            if (b) {
                color = ContextCompat.getColor(getContext(), R.color.white);

                int selected = (int) compoundButton.getTag();
                adapter.setDisplayFilter(selected);
            }
            else {
                color = ContextCompat.getColor(getContext(), R.color.dark_red);
            }

            compoundButton.setTextColor(color);
        }
    }
}
