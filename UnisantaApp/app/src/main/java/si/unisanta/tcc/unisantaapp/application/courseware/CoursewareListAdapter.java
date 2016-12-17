package si.unisanta.tcc.unisantaapp.application.courseware;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.domain.entities.Courseware;
import si.unisanta.tcc.unisantaapp.domain.framework.CoursewareUploadComparator;

public class CoursewareListAdapter extends BaseAdapter {
    private Context context;
    private List<Courseware> coursewareList;

    public CoursewareListAdapter(Context context, List<Courseware> coursewareList) {
        this.context = context;
        Collections.sort(coursewareList, Collections.reverseOrder(new CoursewareUploadComparator()));
        this.coursewareList = coursewareList;
    }

    public void setList(List<Courseware> coursewareList) {
        Collections.sort(coursewareList, Collections.reverseOrder(new CoursewareUploadComparator()));
        this.coursewareList = coursewareList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return coursewareList.size();
    }

    @Override
    public Object getItem(int i) {
        return coursewareList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return coursewareList.get(0).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Courseware courseware = coursewareList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.courseware_list_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvCoursewareTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvCoursewareDescription);
            viewHolder.tvSize = (TextView) convertView.findViewById(R.id.tvSize);
            viewHolder.tvUpload = (TextView) convertView.findViewById(R.id.tvUpload);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(courseware.getTitle());
        viewHolder.tvDescription.setText(courseware.getDescription());
        viewHolder.tvSize.setText(courseware.getSize());
        viewHolder.tvUpload.setText(courseware.getUploadDate() + " " + courseware.getUploadTime());

        int visibility = courseware.getDescription().isEmpty() ? View.GONE : View.VISIBLE;
        viewHolder.tvDescription.setVisibility(visibility);

        return convertView;
    }

    private final class ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvUpload;
        private TextView tvSize;
    }
}
