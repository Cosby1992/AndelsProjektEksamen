package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private FirebaseFirestore database;

    private CreateUserViewModel viewModel;
    private TextInputEditText email, password, reapeatPassword, name, lastname;
    private ProgressBar opretBrugerProgressbar;
    private Button createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ////////////////////////////// initialisere klassevariabler ///////////////////////////////
        //Initialisere ViewModel
        viewModel = ViewModelProviders.of(this).get(CreateUserViewModel.class);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        /////////////////////////////// initialisering fra xml ////////////////////////////////////
        email = findViewById(R.id.tiet_email);
        password = findViewById(R.id.tiet_password);
        reapeatPassword = findViewById(R.id.tiet_reapeat_password);

        name = findViewById(R.id.tiet_name);
        lastname = findViewById(R.id.tiet_lastname);

        createUserButton = findViewById(R.id.btn_create_user);
        opretBrugerProgressbar = findViewById(R.id.pb_opret_bruger_progressbar);



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

                hideButtonShowProgress();

                //Info udskrift i loggen
                Log.i(TAG, "onClick: \nUser Value = " + viewModel.getCurrentUserEmail().getValue() + "\n" +
                        "isEmailValid = " + viewModel.isEmailValid().getValue() + "\n" +
                        "isPasswordValid = " + viewModel.isPasswordValid().getValue());

                // tjekker om E-mail og password overholder de givne regler samt om password og
                // repeat password er ens
                if(viewModel.isEmailValid().getValue()
                        && viewModel.isPasswordValid().getValue()
                        && viewModel.getCurrentUserPassword().getValue().equals(reapeatPassword.getText().toString())) {


                    // Forsøger at tilføje bruger til database med email og password gennem FirebaseAuth instans.
                    // Når dette er forsøgt undersøges det om forsøget var succesfuld eller ikke.
                    viewModel.createUserAuth()
                            .addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        viewModel.setCurrentUserId(user.getUid());

                                        viewModel.updateUserAuth().addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(mainActivity);
                                                } else {
                                                    showButtonHideProgress();
                                                    Toast.makeText(CreateUserActivity.this, "Brugeren blev oprettet, men navn og efternavn blev ikke gemt i databasen.",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });



                                    } else { // Hvis oprettelsen ikke var successfuld
                                        showButtonHideProgress();
                                        Toast.makeText(CreateUserActivity.this, "Oprettelse Fejlede - tjek dine informationer eller din internetforbindelse og prøv igen.",
                                                Toast.LENGTH_LONG).show();
                                    }



                                    // ...
                                }
                            });
                } else if (!viewModel.getCurrentUserPassword().getValue().equals(reapeatPassword.getText().toString())){
                    showButtonHideProgress();
                    Toast.makeText(CreateUserActivity.this, "Passwords er ikke ens!", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password og repeat password er ikke ens.");
                } else if (!viewModel.isEmailValid().getValue()){
                    showButtonHideProgress();
                    Toast.makeText(CreateUserActivity.this, "Der er noget galt med din E-mail adresse", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: E-mailen overholder ikke regex i isEmailValid metoden");
                } else if (viewModel.getCurrentUserPassword().getValue().length() < 8){
                    showButtonHideProgress();
                    Toast.makeText(CreateUserActivity.this, "Password skal være minimum 8 karaktere langt", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password er mindre end 8 karaktere langt");
                } else if (!Pattern.matches(".*[A-Z].*", viewModel.getCurrentUserPassword().getValue())){
                    showButtonHideProgress();
                    Toast.makeText(CreateUserActivity.this, "Password skal indeholde et stort bogstav", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password indeholder ikke et stort bogstav");
                } else if (!Pattern.matches(".*[a-z].*", viewModel.getCurrentUserPassword().getValue())){
                    showButtonHideProgress();
                    Toast.makeText(CreateUserActivity.this, "Password skal indeholde et lille bogstav", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password indeholder ikke et lille bogstav");
                } else if (!viewModel.isPasswordValid().getValue()){
                    showButtonHideProgress();
                    Toast.makeText(CreateUserActivity.this, "Password skal indeholde et tegn eller tal", Toast.LENGTH_SHORT).show();
                    Log.w(TAG, "onClick: Password indeholder ikke et tegn eller tal");
                } else {
                    showButtonHideProgress();
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


    private void showButtonHideProgress(){
        opretBrugerProgressbar.setVisibility(View.GONE);
        createUserButton.setVisibility(View.VISIBLE);
    }

    private void hideButtonShowProgress(){
        opretBrugerProgressbar.setVisibility(View.VISIBLE);
        createUserButton.setVisibility(View.GONE);
    }

}
