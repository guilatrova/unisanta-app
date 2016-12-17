package si.unisanta.tcc.unisantaapp.application.fragments.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;

public abstract class FilterRecyclerAdapter<T> extends RecyclerView.Adapter<GenericViewHolder> {

    protected List<T> fullList;
    protected List<IGenericListItem> filterList;
    protected Context context;
    protected int displayFilter = SHOW_TODAY;

    //FILTER OPTIONS
    public static final int SHOW_ALL = 0;
    public static final int SHOW_TODAY = 1;
    public static final int SHOW_NEXT = 2;

    public enum RowType {
        ITEM,
        HEADER,
        EMPTY
    }

    public FilterRecyclerAdapter(Context context, List<T> fullList) {
        this.context = context;
        this.fullList = fullList;
        filterList = new ArrayList<>();
        filter();
    }

    public void setDisplayFilter(int display) {
        if (displayFilter != display) {
            displayFilter = display;
            filter();
        }
    }

    public int getDisplayFilter() {
        return displayFilter;
    }

    protected abstract void beforeFilter();

    private void filter() {
        beforeFilter();
        filterList.clear();
        IGenericListItem last = null;
        for (T item : fullList) {

            if (mayAdd(item)) {
                if (shouldCreateHeader(item, last)) {
                    IGenericListItem header = createHeader(item);
                    filterList.add(header);
                }

                last = createItem(item);
                filterList.add(last);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public abstract int getItemViewType(int position);

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (filterList.isEmpty()) {
            View view = inflater.inflate(R.layout.empty_item, parent, false);
            return new EmptyViewHolder(view);
        }

        return createViewHolder(inflater, parent, viewType);
    }

    protected abstract GenericViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        Object toBind = filterList.isEmpty() ?
                getEmptyString() : getBindObject(holder, position);

        holder.onBind(toBind);
    }

    protected abstract Object getBindObject(GenericViewHolder holder, int position);

    protected abstract String getEmptyString();

    @Override
    public int getItemCount() {
        if (filterList.isEmpty())
            return 1;
        return filterList.size();
    }

    protected IGenericListItem getListItem(int position) {
        return filterList.get(position);
    }

    protected abstract boolean mayAdd(T item);

    protected abstract boolean shouldCreateHeader(T item, IGenericListItem last);

    protected abstract IGenericListItem createHeader(T item);

    protected abstract IGenericListItem createItem(T item);
}
