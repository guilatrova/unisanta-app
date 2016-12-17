package si.unisanta.tcc.unisantaapp.application.config;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.AppCompatActivityToolbarBase;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.model.IUserRepository;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class EditProfileActivity extends AppCompatActivityToolbarBase {
    private IUserRepository userRepository;
    private TextView tvName;
    private TextView tvCourse;
    private EditText txtRA;
    private EditText txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        super.setupToolbar();

        tvName = (TextView) findViewById(R.id.tvStudentName);
        tvCourse = (TextView) findViewById(R.id.tvCourse);
        txtRA = (EditText) findViewById(R.id.txtRA);
        txtPassword = (EditText) findViewById(R.id.txtPass);
        userRepository = RepositoryFactory.getUserRepository();
        setupButton();
    }

    @Override
    protected void onStart() {
        super.onStart();

        userRepository.retrieve();
        User user = User.getInstance();

        tvName.setText(getString(R.string.student_name, user.getName()));
        tvCourse.setText(getString(R.string.course_name, user.getCourse()));
        txtRA.setText(user.getRA());
        txtPassword.setText(user.getPassword());
    }

    private void setupButton() {
        Button btnSave = (Button) findViewById(R.id.btnAction);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getInstance();
                user.setPassword(txtPassword.getText().toString());
                userRepository.save(user);
                finish();
            }
        });
    }
}
