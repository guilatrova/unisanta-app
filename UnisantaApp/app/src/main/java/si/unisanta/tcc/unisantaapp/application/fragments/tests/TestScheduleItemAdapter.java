package si.unisanta.tcc.unisantaapp.application.fragments.tests;

import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterRecyclerAdapter;
import si.unisanta.tcc.unisantaapp.application.fragments.common.IGenericListItem;
import si.unisanta.tcc.unisantaapp.domain.entities.Test;

public class TestScheduleItemAdapter implements IGenericListItem{
    private Test test;

    public TestScheduleItemAdapter(Test test) {
        this.test = test;
    }

    public Test getTest() {
        return test;
    }

    @Override
    public int getViewType() {
        return FilterRecyclerAdapter.RowType.ITEM.ordinal();
    }
}
