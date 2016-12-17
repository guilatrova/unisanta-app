package si.unisanta.tcc.unisantaapp.application.fragments.classes;

import si.unisanta.tcc.unisantaapp.application.fragments.common.FilterRecyclerAdapter;
import si.unisanta.tcc.unisantaapp.application.fragments.common.IGenericListItem;
import si.unisanta.tcc.unisantaapp.domain.entities.ClassSchedule;

public class ClassScheduleItemAdapter implements IGenericListItem {
    private ClassSchedule classSchedule;

    public ClassScheduleItemAdapter(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    @Override
    public int getViewType() {
        return FilterRecyclerAdapter.RowType.ITEM.ordinal();
    }
}
