package si.unisanta.tcc.unisantaapp.application.fragments.common;

import android.view.View;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;

public class HeaderViewHolder extends GenericViewHolder{
    private TextView tvHeader;

    public HeaderViewHolder(View itemView) {
        super(itemView);

        tvHeader = (TextView) itemView.findViewById(R.id.tvHeader);
    }

    @Override
    public void onBind(Object item) {
        tvHeader.setText((String)item);
    }
}
