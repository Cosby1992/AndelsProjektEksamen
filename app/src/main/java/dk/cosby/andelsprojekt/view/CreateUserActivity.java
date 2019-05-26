package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.CreateUserViewModel;

/**
 * Opret bruger view der kontakter CreateUserViewModel når en EditText-værdi har ændret sig.
 * Klassen benyttes til at håndtere brugeroprettelse.
 */
public class CreateUserActivity extends AppCompatActivity {

    private static final String TAG = "CreateUserActivity";

    private FirebaseAuth mAuth;

    private CreateUserViewModel viewModel;
    private TextInputEditText email, password, reapeatPassword, name, lastname;
    private Button createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ////////////////////////////// initialisere klassevariabler ///////////////////////////////
        //Initialisere ViewModel
        viewModel = ViewModelProviders.of(this).get(CreateUserViewModel.class);

        mAuth = FirebaseAuth.getInstance();

        /////////////////////////////// initialisering fra xml ////////////////////////////////////
        email = (TextInputEditText) findViewById(R.id.tiet_email);
        password = (TextInputEditText) findViewById(R.id.tiet_password);
        reapeatPassword = (TextInputEditText) findViewById(R.id.tiet_reapeat_password);

        name = (TextInputEditText) findViewById(R.id.tiet_name);
        lastname = (TextInputEditText) findViewById(R.id.tiet_lastname);

        createUserButton = (Button) findViewById(R.id.btn_create_user);



        //Ændre værdien i viewModel hver gang email teksten ændres
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null) {
                    viewModel.setCurrentUserEmail(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Ændre værdien i viewModel hver gang password teksten ændres
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null) {
                    viewModel.setCurrentUserPassword(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Ændre værdien i viewModel hver gang navn teksten ændres
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null){
                    viewModel.setCurrentUserName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Ændre værdien i viewModel hver gang efternavn teksten ændres
        lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null){
                    viewModel.setCurrentUserLastname(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Når opret bruger knappes klikkes
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Info udskrift i loggen
                Log.i(TAG, "onClick: \nUser Value = " + viewModel.getCurrentUserEmail().getValue() + "\n" +
                        "isEmailValid = " + isEmailValid(viewModel.getCurrentUserEmail().getValue()) + "\n" +
                        "isPasswordValid = " + isPasswordValid(viewModel.getCurrentUserPassword().getValue()));

                // tjekker om E-mail og password overholder de givne regler samt om password og
                // repeat password er ens
                if(isEmailValid(viewModel.getCurrentUserEmail().getValue())
                        && isPasswordValid(viewModel.getCurrentUserPassword().getValue())
                        && viewModel.getCurrentUserPassword().getValue().equals(reapeatPassword.getText().toString())) {

                    // Forsøger at tilføje bruger til database med email og password gennem FirebaseAuth instans.
                    // Når dette er forsøgt undersøges det om forsøget var succesfuld eller ikke.
                    mAuth.createUserWithEmailAndPassword(viewModel.getCurrentUserEmail().getValue().trim(),
                            viewModel.getCurrentUserPassword().getValue())
                            .addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "addUserToFirebase: 'success' Brugeren blev tilføjet til firebase");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        String displayName = viewModel.getCurrentUserName().getValue().trim() + " " + viewModel.getCurrentUserLastname().getValue().trim();

                                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(displayName)
                                                .build();

                                        user.updateProfile(profileUpdate);

                                        Log.i(TAG, "FIREBASE USER: \n" +
                                                "display name: " + user.getDisplayName() + "\n" +
                                                "number: " + user.getPhoneNumber() + "\n" +
                                                "id: " + user.getUid());

                                        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(mainActivity);
//                                updateUI(user);
                                    } else { // Hvis oprettelsen ikke var successfuld
                                        Log.w(TAG, "addUserToFirebase: 'failure' brugeren blev ikke tilføjet til Firebase", task.getException());
                                        Toast.makeText(CreateUserActivity.this, "Oprettelse Fejlede - tjek dine informationer og prøv igen.",
                                                Toast.LENGTH_LONG).show();
//                                updateUI(null);
                                    }



                                    // ...
                                }
                            });
                } else if (!viewModel.getCurrentUserPassword().getValue().equals(reapeatPassword.getText().toString())){
                    Toast.makeText(CreateUserActivity.this, "Passwords er ikke ens!", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password og repeat password er ikke ens.");
                } else if (!isEmailValid(viewModel.getCurrentUserEmail().getValue())){
                    Toast.makeText(CreateUserActivity.this, "Der er noget galt med din E-mail adresse", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: E-mailen overholder ikke regex i isEmailValid metoden");
                } else if (viewModel.getCurrentUserPassword().getValue().length() < 8){
                    Toast.makeText(CreateUserActivity.this, "Password skal være minimum 8 karaktere langt", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password er mindre end 8 karaktere langt");
                } else if (!Pattern.matches(".*[A-Z].*", viewModel.getCurrentUserPassword().getValue())){
                    Toast.makeText(CreateUserActivity.this, "Password skal indeholde et stort bogstav", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password indeholder ikke et stort bogstav");
                } else if (!Pattern.matches(".*[a-z].*", viewModel.getCurrentUserPassword().getValue())){
                    Toast.makeText(CreateUserActivity.this, "Password skal indeholde et lille bogstav", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password indeholder ikke et lille bogstav");
                } else if (!isPasswordValid(viewModel.getCurrentUserPassword().getValue())){
                    Toast.makeText(CreateUserActivity.this, "Password skal indeholde et tegn eller tal", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password indeholder ikke et tegn eller tal");
                } else {
                    Toast.makeText(CreateUserActivity.this, "Dine indtastninger blev ikke godkendt af en ukendt årsag. Prøv venligst igen.", Toast.LENGTH_SHORT).show();
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
