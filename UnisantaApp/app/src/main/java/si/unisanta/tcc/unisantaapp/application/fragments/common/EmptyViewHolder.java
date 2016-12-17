package si.unisanta.tcc.unisantaapp.application.fragments.common;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;

public class EmptyViewHolder extends GenericViewHolder {
    private TextView tvEmpty;

    public EmptyViewHolder(View itemView) {
        super(itemView);

        tvEmpty = (TextView) itemView.findViewById(R.id.tvEmpty);
    }

    @Override
    public void onBind(Object item) {
        tvEmpty.setText(Html.fromHtml((String)item));
    }
}
