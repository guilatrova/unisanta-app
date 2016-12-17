package si.unisanta.tcc.unisantaapp.application.courseware;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class SubjectListAdapter extends BaseAdapter {
    private List<Subject> subjectList;
    private Context context;
    private SchoolYear schoolYear;

    public SubjectListAdapter(Context context, List<Subject> subjectList, SchoolYear schoolYear) {
        this.context = context;
        this.subjectList = subjectList;
        this.schoolYear = schoolYear;
    }

    @Override
    public int getCount() {
        return subjectList.size();
    }

    @Override
    public Object getItem(int i) {
        return subjectList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return subjectList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Subject subject = subjectList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.subject_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.subjectNameView = (TextView) convertView.findViewById(R.id.tvSubjectName);
            viewHolder.dpView = convertView.findViewById(R.id.tvDP);
            viewHolder.teacherNameView = (TextView) convertView.findViewById(R.id.tvTeacherName);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int dpVisibility = subject.isDP() ? View.VISIBLE : View.GONE;

        viewHolder.subjectNameView.setText(subject.getName());
        viewHolder.dpView.setVisibility(dpVisibility);
        viewHolder.teacherNameView.setText(subject.getTeacher().getName());

        return convertView;
    }

    private final class ViewHolder {
        private View dpView;
        private TextView subjectNameView;
        private TextView teacherNameView;
    }
}
