package si.unisanta.tcc.unisantaapp.application.fragments.common;

public class HeaderItem implements IGenericListItem {
    private String title;

    public HeaderItem(String title) {
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
