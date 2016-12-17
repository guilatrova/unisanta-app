package si.unisanta.tcc.unisantaapp.application.fragments.grade;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.fragments.common.EmptyViewHolder;
import si.unisanta.tcc.unisantaapp.application.fragments.common.GenericViewHolder;
import si.unisanta.tcc.unisantaapp.domain.dto.SubjectGradeDto;
import si.unisanta.tcc.unisantaapp.domain.entities.Subject;

public class GradeRecyclerAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private Context context;
    private List<SubjectGradeDto> dataSet;

    private static final int EMPTY = -1;
    private static final int ITEM = 1;

    public GradeRecyclerAdapter(Context context, List<SubjectGradeDto> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    public void changeDataSet(List<SubjectGradeDto> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.isEmpty())
            return EMPTY;
        return ITEM;
    }

    @Override
    public int getItemCount() {
        if (dataSet.isEmpty())
            return 1;
        return dataSet.size();
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (viewType == EMPTY) {
            view = inflater.inflate(R.layout.empty_item, parent, false);
            return new EmptyViewHolder(view);
        }

        view = inflater.inflate(R.layout.grade_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        Object toBind;

        if (dataSet.isEmpty()) {
            toBind = context.getString(R.string.no_grades_message);
        }
        else {
            toBind = dataSet.get(position);
        }

        holder.onBind(toBind);
    }

    private void setGrade(TextView textView, float score) {
        if (score > -1) {
            textView.setText(Float.toString(score));
        }
        else {
            textView.setText("-");
        }
    }

    final class ViewHolder extends GenericViewHolder {
        TextView subjectNameView;
        TextView dpView;
        TextView p1View;
        TextView p2View;
        TextView p3View;
        TextView statusView;
        TextView finalGradeView;


        public ViewHolder(View itemView) {
            super(itemView);

            subjectNameView = (TextView) itemView.findViewById(R.id.tvSubjectName);
            dpView = (TextView) itemView.findViewById(R.id.tvDP);
            p1View = (TextView) itemView.findViewById(R.id.tvP1);
            p2View = (TextView) itemView.findViewById(R.id.tvP2);
            p3View = (TextView) itemView.findViewById(R.id.tvP3);
            finalGradeView = (TextView) itemView.findViewById(R.id.tvFinal);
            statusView = (TextView) itemView.findViewById(R.id.tvStatus);
        }

        @Override
        public void onBind(Object item) {
            SubjectGradeDto subjectGrade = (SubjectGradeDto) item;

            subjectNameView.setText(subjectGrade.getSubject());
            setGrade(p1View, subjectGrade.getP1());
            setGrade(p2View, subjectGrade.getP2());
            setGrade(p3View, subjectGrade.getP3());
            setGrade(finalGradeView, subjectGrade.getFinalScore());

            dpView.setVisibility(subjectGrade.isDP() ? View.VISIBLE : View.GONE);

            if (subjectGrade.getStatus() == Subject.UNKNOWN) {
                statusView.setVisibility(View.INVISIBLE);
                return;
            }

            int status, color;
            if (subjectGrade.getStatus() == Subject.APPROVED) {
                status =  R.string.approved;
                color = ContextCompat.getColor(context, R.color.green);
            }
            else {
                status =  R.string.disapproved;
                color = ContextCompat.getColor(context, R.color.red);
            }

            statusView.setVisibility(View.VISIBLE);
            statusView.setTextColor(color);
            statusView.setText(status);
        }
    }
}
