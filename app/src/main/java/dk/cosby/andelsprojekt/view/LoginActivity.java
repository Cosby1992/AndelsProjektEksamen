package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText email, password;
    private Button login;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.tiet_email_login);
        password = (EditText) findViewById(R.id.tiet_password_login);
        login = (Button) findViewById(R.id.btn_login);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null){
                    viewModel.setCurrentUserEmail(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setCurrentUserPassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.setOnClickListener((v)-> mAuth.signInWithEmailAndPassword(viewModel.getCurrentUserEmail().getValue(), viewModel.getCurrentUserPassword().getValue())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");

                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainActivity);

                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "",
                                Toast.LENGTH_SHORT).show();

                    }

                    // ...
                }));
    }

    public void createNewUserButton(View view) {
        Intent createUserActivity = new Intent(getApplicationContext(), CreateUserActivity.class);
        startActivity(createUserActivity);
    }
}