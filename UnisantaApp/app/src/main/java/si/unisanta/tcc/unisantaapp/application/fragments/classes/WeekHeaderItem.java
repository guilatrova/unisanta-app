package si.unisanta.tcc.unisantaapp.application.fragments.classes;

import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterRecyclerAdapter;
import si.unisanta.tcc.unisantaapp.application.fragments.common.IGenericListItem;

public class WeekHeaderItem implements IGenericListItem {
    private String title;

    public WeekHeaderItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getViewType() {
        return FilterRecyclerAdapter.RowType.HEADER.ordinal();
    }
}
