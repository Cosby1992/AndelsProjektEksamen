package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.CreateUserViewModel;
import dk.cosby.andelsprojekt.view.viewmodel.LoginViewModel;

/**
 * Login view der kontakter LoginViewModel når en EditText-værdi har ændret sig.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    private LoginViewModel viewModel;
    private TextInputEditText email, password;
    private TextView sendToCreateUser;
    private Button login;
    private ProgressBar loginProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ////////////////////////////// initialisere klassevariabler ///////////////////////////////
        //Initialisere ViewModel
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        mAuth = FirebaseAuth.getInstance();

        /////////////////////////////// initialisering fra xml ////////////////////////////////////
        email = (TextInputEditText) findViewById(R.id.tiet_email_login);
        password = (TextInputEditText) findViewById(R.id.tiet_password_login);
        sendToCreateUser = (TextView) findViewById(R.id.tv_send_to_create_user);
        login = (Button) findViewById(R.id.btn_login);
        loginProgress = (ProgressBar) findViewById(R.id.pb_login_progress);


        //En Textwatcher som ændrer email værdien i viewModel når email teksten bliver ændret.
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null){
                    viewModel.setCurrentUserEmail(s.toString());
                } else viewModel.setCurrentUserEmail("");
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
                if (s != null) {
                    viewModel.setCurrentUserPassword(s.toString());
                } else viewModel.setCurrentUserPassword("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //Laver en knap til create user
    public void createNewUserButton(View view) {
        Intent createUserActivity = new Intent(getApplicationContext(), CreateUserActivity.class);
        startActivity(createUserActivity);
    }

    //Når login knappen trykkes
    public void loginOnClick(View view) {

        //Når der er blevet klikket login så kommer progress cirklen op, og login knappen fjernes.
        loginProgress.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        sendToCreateUser.setVisibility(View.GONE);

        //Tjekker om emailen er null, og om email feltet er tomt.
        if(viewModel.getCurrentUserEmail().getValue() != null && viewModel.getCurrentUserPassword().getValue() != null) {
            if (!viewModel.getCurrentUserEmail().getValue().isEmpty() && !viewModel.getCurrentUserPassword().getValue().isEmpty()) {

                //Forsøger at logge ind med de tastede værdier, email og password gennem Firebasen.
                //Hvis den er succesfuld logger den ind og viser mainActivity.
                //Hvis den fejler kommer der en fejl meddelelse
                mAuth.signInWithEmailAndPassword(viewModel.getCurrentUserEmail().getValue().trim(), viewModel.getCurrentUserPassword().getValue())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                loginProgress.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                sendToCreateUser.setVisibility(View.VISIBLE);
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");

                                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainActivity);

                                //updateUI(user);
                            } else {
                                loginProgress.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                sendToCreateUser.setVisibility(View.VISIBLE);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Login fejlede.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        });
            } else {
                loginProgress.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                sendToCreateUser.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Du har ikke udfyldt email eller password feltet.", Toast.LENGTH_SHORT).show();
            }
        } else {
            loginProgress.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            sendToCreateUser.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, "Du har ikke udfyldt email eller password feltet.", Toast.LENGTH_SHORT).show();
        }


    }
}