package si.unisanta.tcc.unisantaapp.application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import si.unisanta.tcc.unisantaapp.R;
import si.unisanta.tcc.unisantaapp.application.services.UserErrorMessageHelper;
import si.unisanta.tcc.unisantaapp.domain.factories.RepositoryFactory;
import si.unisanta.tcc.unisantaapp.domain.factories.SyncServiceFactory;
import si.unisanta.tcc.unisantaapp.domain.services.sync.ISyncProgressListener;
import si.unisanta.tcc.unisantaapp.domain.services.sync.SyncTaskService;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.AppVersion;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.User;

public class LoginActivity extends AppCompatActivity implements ISyncProgressListener<Void> {

    private ProgressDialog progressDialog;
    private EditText txtLogin;
    private EditText txtPass;
    private boolean redirected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (isFirstLogin()) {
            setupViews();
        }
        else { //Usuário já logou alguma vez
            User user = User.getInstance();
            Toast.makeText(getApplicationContext(), getString(R.string.welcome_back, user.getName()), Toast.LENGTH_LONG).show();
            redirect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //O usuário tinha sido direcionado para a tela principal, mas deletou todos os dados
        if (redirected) {
            redirected = false;
            setupViews();
        }
    }

    private void setupViews() {
        Button btnLogin;
        TextView tvAboutApp;

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtLogin = (EditText) findViewById(R.id.txtRA);
        txtPass = (EditText) findViewById(R.id.txtPass);
        tvAboutApp = (TextView) findViewById(R.id.tvLoginFooter);

        txtLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6)
                    txtPass.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPass.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                return true;
            }
        });

        tvAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ra = txtLogin.getText().toString();
                String pass = txtPass.getText().toString();
                User.init(null, null, null, ra, pass);
                User.getInstance().setAppVersion(new AppVersion(UnisantaApplication.getVersionName()));

                SyncTaskService firstSync = SyncServiceFactory.createSyncService(SyncServiceFactory.FIRST_LOGIN);

                firstSync.setListener(LoginActivity.this);
                firstSync.execute();
            }
        });
    }

    private boolean isFirstLogin() {
        try {
            if (RepositoryFactory.getUserRepository().retrieve()) {
                return false;
            }
        } catch (Exception e) {
            UnisantaApplication.Log_e("Dados corrompidos: " + e.getMessage(), e);
            Toast.makeText(getApplicationContext(), "Dados corrompidos", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void redirect() {
        redirected = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStarting() {
        progressDialog = new ProgressDialog(this, R.style.WhiteDialogTheme);
        progressDialog.setTitle(null);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void onMessageChange(String msg) {
        progressDialog.setMessage(msg);
    }

    @Override
    public void onProgressChange(int progress) {
        progressDialog.setProgress(progress);
    }

    @Override
    public void onSuccess(Void result) {
        progressDialog.dismiss();

        User user = User.getInstance();
        Toast.makeText(LoginActivity.this, getString(R.string.welcome) + user.getName(), Toast.LENGTH_LONG).show();

        redirect();
    }

    @Override
    public void onFailure(Exception ex) {
        progressDialog.dismiss();
        RepositoryFactory.getUserRepository().deleteAll();
        showError(UserErrorMessageHelper.getMessage(ex));
    }

    private void showError(String msg) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle(R.string.error_title_dialog)
                .setMessage(msg)
                .setNeutralButton("OK", null)
                .show();
    }
}
