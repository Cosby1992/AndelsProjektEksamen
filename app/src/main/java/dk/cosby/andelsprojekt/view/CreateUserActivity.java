package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.CreateUserViewModel;

/**
 * Opret bruger view der kontakter CreateUserViewModel når en EditText-værdi har ændret sig.
 */
public class CreateUserActivity extends AppCompatActivity {

    private static final String TAG = "CreateUserActivity";

    private FirebaseAuth mAuth;

    private CreateUserViewModel viewModel;
    private TextView emailErrorText, passwordErrorText;
    private EditText email, password;
    private Button create_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //Initialisere ViewModel i Viewet
        viewModel = ViewModelProviders.of(this).get(CreateUserViewModel.class);

        mAuth = FirebaseAuth.getInstance();

        emailErrorText = (TextView) findViewById(R.id.tv_email_error_text);
        passwordErrorText = (TextView) findViewById(R.id.tv_password_error_text);
        email = (EditText) findViewById(R.id.et_email_create_user);
        password = (EditText) findViewById(R.id.et_password_create_user);
        create_user = (Button) findViewById(R.id.btn_create_user);



        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null) {
                    viewModel.setCurrentUserEmail(s.toString());
                    if(isEmailValid(s.toString())) {
                        emailErrorText.setVisibility(View.GONE);
                    } else {
                        emailErrorText.setVisibility(View.VISIBLE);
                    }
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
                if(s != null) {
                    viewModel.setCurrentUserPassword(s.toString());
                    if(isPasswordValid(s.toString())) {
                        passwordErrorText.setVisibility(View.GONE);
                    } else {
                        passwordErrorText.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: \nUser Value = " + viewModel.getCurrentUserEmail().getValue() + "\n" +
                        "isEmailValid = " + isEmailValid(viewModel.getCurrentUserEmail().getValue()) + "\n" +
                        "isPasswordValid = " + isPasswordValid(viewModel.getCurrentUserPassword().getValue()));


                if(isEmailValid(viewModel.getCurrentUserEmail().getValue()) && isPasswordValid(viewModel.getCurrentUserPassword().getValue())) {

                    mAuth.createUserWithEmailAndPassword(viewModel.getCurrentUserEmail().getValue(),
                            viewModel.getCurrentUserPassword().getValue())
                            .addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "addUserToFirebase: 'success' Brugeren blev tilføjet til firebase");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(goToMainActivity);
//                                updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "addUserToFirebase: 'failure' brugeren blev ikke tilføjet til Firebase", task.getException());
                                        Toast.makeText(CreateUserActivity.this, "Oprettelse Fejlede - tjek dine informationer og prøv igen.",
                                                Toast.LENGTH_LONG).show();
//                                updateUI(null);
                                    }

                                    // ...
                                }
                            });
                } else {
                    Toast.makeText(CreateUserActivity.this, "Din email og password skal være godkendt først", Toast.LENGTH_SHORT).show();
                }
            }
        });




//
//        final Observer<String> emailStringObserver = new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable final String a) {
//                // Update the UI, in this case, a TextView.
//                email.setText(a);
//            }
//        };
//
//        viewModel.observeEmail(this, emailStringObserver);
//
//        final Observer<String> passwordStringObserver = new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable final String a) {
//                // Update the UI, in this case, a TextView.
//                email.setText(a);
//            }
//        };
//
//        viewModel.observePassword(this, passwordStringObserver);

    }

    /**
     * Bruger en regular expression til at finde ud af om en email overholder den
     * normale opbygning for en email.
     *
     * @param email email der skal tjekkes
     * @return true hvis emailen overholder RFC-5322
     */
    public static boolean isEmailValid(String email) {
        return Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", email);
    }

    /**
     * Bruger en regular expression til at finde ud af om et password overholder følgene regler:
     *  1. passwordet er mindst 8 karakterer langt
     *  2. passwordet indeholder mindst 1 stort bogstav
     *  3. passwordet indeholder mindst 1 lille bogstav
     *  4. passwordet indeholder mindst 1 tal/tegn
     *
     * @param password password der skal tjekkes
     * @return true hvis passwordet overholder ovenstående regler
     */
    public static boolean isPasswordValid(String password) {
        return Pattern.matches("(?-i)(?=^.{8,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$", password);
    }

}
