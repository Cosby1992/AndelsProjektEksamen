package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.LoginViewModel;

/**
 * Login view der kontakter LoginViewModel når en EditText-værdi har ændret sig.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private LoginViewModel viewModel;
    private TextInputEditText email, password;
    private TextView sendToCreateUser;
    private Button login;
    private ProgressBar loginProgress;

    private long time = System.currentTimeMillis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ////////////////////////////// initialisere klassevariabler ///////////////////////////////
        //Initialisere ViewModel
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        /////////////////////////////// initialisering fra xml ////////////////////////////////////
        email = (TextInputEditText) findViewById(R.id.tiet_email_login);
        password = (TextInputEditText) findViewById(R.id.tiet_password_login);
        sendToCreateUser = (TextView) findViewById(R.id.tv_send_to_create_user);
        login = (Button) findViewById(R.id.btn_login);
        loginProgress = (ProgressBar) findViewById(R.id.pb_login_progress);

        email = findViewById(R.id.tiet_email_login);
        password = findViewById(R.id.tiet_password_login);
        sendToCreateUser = findViewById(R.id.tv_send_to_create_user);
        login = findViewById(R.id.btn_login);
        loginProgress = findViewById(R.id.pb_login_progress);


        //En Textwatcher som ændrer email værdien i viewModel når email teksten bliver ændret.
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    viewModel.setCurrentUserEmail(s.toString());
                } else {
                    viewModel.setCurrentUserEmail("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //En textwatcher som ændrer password værdien i viewModel når password teksten bliver ændret.
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setCurrentUserPassword(s != null ? s.toString() : "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //en observer der holder øje med om login fejler eller ej og opdatere UI derefter.
        Observer<Boolean> loginObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    //login var en succes
                    Log.d(TAG, "signInWithEmail:success");
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                } else {
                    //login fejlede
                    showLoginButton();
                    Log.w(TAG, "signInWithEmail:failure");
                    Toast.makeText(LoginActivity.this, "Login fejlede",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        viewModel.getLoginBool().observe(this, loginObserver);

    }

    @Override
    public void onBackPressed() {

        if(time + 3000L < System.currentTimeMillis()) {
            Toast.makeText(LoginActivity.this, "Du kan ikke gå tilbage herfra", Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        }

    }

    //Laver en knap til create user
    public void createNewUserButton(View view) {
        Intent createUserActivity = new Intent(getApplicationContext(), CreateUserActivity.class);
        startActivity(createUserActivity);
    }

    //Når login knappen trykkes
    public void loginOnClick(View view) {

        //Når der er blevet klikket login så kommer progress cirklen op, og login knappen fjernes.
        showProgressBar();

        //Tjekker om emailen er null, og om email feltet er tomt.
        if(viewModel.getCurrentUserEmail().getValue() != null && viewModel.getCurrentUserPassword().getValue() != null) {
            if (!viewModel.getCurrentUserEmail().getValue().isEmpty() && !viewModel.getCurrentUserPassword().getValue().isEmpty()) {

                //prøver at kontakte firebase og logge brugeren ind med de indtastede værdier
                //for email og password der er gemt i viewmodellen
                viewModel.login();

            } else {
                showLoginButton();
                Toast.makeText(LoginActivity.this, "Du har ikke udfyldt email eller password feltet.", Toast.LENGTH_SHORT).show();
            }
        } else {
            showLoginButton();
            Toast.makeText(LoginActivity.this, "Du har ikke udfyldt email eller password feltet.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.detach();
    }

    private void showProgressBar(){
        loginProgress.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        sendToCreateUser.setVisibility(View.GONE);
    }

    private void showLoginButton(){
        loginProgress.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
        sendToCreateUser.setVisibility(View.VISIBLE);
    }

}