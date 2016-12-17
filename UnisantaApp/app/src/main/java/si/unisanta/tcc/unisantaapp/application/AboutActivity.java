package si.unisanta.tcc.unisantaapp.application;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;

public class AboutActivity extends AppCompatActivityToolbarBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        super.setupToolbar();

        TextView tvAbout;
        TextView tvAcknowledgments;
        TextView tvTeam;

        tvAbout = (TextView) findViewById(R.id.tvAboutDescription);
        tvAcknowledgments = (TextView) findViewById(R.id.tvAcknowledgments);
        tvTeam = (TextView)  findViewById(R.id.tvTeam);

        tvAbout.setText(Html.fromHtml(getString(R.string.about_description, UnisantaApplication.getVersionName())));
        tvTeam.setText(Html.fromHtml(getString(R.string.team)));
        tvTeam.setMovementMethod(LinkMovementMethod.getInstance());
        tvAcknowledgments.setText(Html.fromHtml(getText(R.string.acknowledgments).toString()));
    }
}
