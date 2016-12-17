package si.unisanta.tcc.unisantaapp.application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class AppCompatActivityToolbarBase extends AppCompatActivity{
    protected Toolbar toolbar;
    protected TextView tvSemester;
    protected TextView tvTitle;
    public static final String SEMESTER_KEY = "SELECTED_SEMESTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (tvSemester != null)
            tvSemester.setText(savedInstanceState.getString(SEMESTER_KEY));
    }

    protected void setupToolbar() {
        tvTitle = (TextView) findViewById(R.id.toolbar_title);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false); Não necessário
    }

    protected void setupSemesterSelector() {
        tvSemester = (TextView) findViewById(R.id.semester_selector);
        tvSemester.setVisibility(View.VISIBLE);
        tvSemester.setText(SchoolYear.getCurrent().toString('/'));

        if (User.getInstance() == null) {
            RepositoryFactory.getUserRepository().retrieve();
        }

        SchoolYear start = User.getInstance().getFirstSchoolYear();
        final String[] semesters = new String[SchoolYear.getCurrent().countDistanceFrom(start)];
        int i = 0;
        for (SchoolYear sy = start;
             sy.isBefore(SchoolYear.getCurrent()) || sy.equals(SchoolYear.getCurrent()); sy = sy.next()) {
            semesters[i] = sy.toString('/');
            i++;
        }

        tvSemester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AppCompatActivityToolbarBase.this)
                    .setTitle(R.string.pick_semester)
                    .setItems(semesters, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onChangeSemester(tvSemester.getText().toString(), semesters[i]);
                        }
                    })
                    .show();
            }
        });
    }

    protected void onChangeSemester(String oldSemester, String newSemester) {
        tvSemester.setText(newSemester);
    }

    protected void setToolbarTitle(String title) {
        tvTitle.setText(title);
    }

    protected void setToolbarTitle(int resId) {
        tvTitle.setText(resId);
    }

    protected SchoolYear getSelectedSchoolYear() {
        return new SchoolYear(tvSemester.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tvSemester != null)
            outState.putString(SEMESTER_KEY, tvSemester.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
