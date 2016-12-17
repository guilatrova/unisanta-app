package si.unisanta.tcc.unisantaapp.application.fragments.tests;

import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterRecyclerAdapter;
import si.unisanta.tcc.unisantaapp.application.fragments.common.IGenericListItem;

public class TestHeaderItem implements IGenericListItem {
    private String title;

    public TestHeaderItem(String title) {
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
