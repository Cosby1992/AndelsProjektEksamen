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
import android.widget.Toast;

import java.util.regex.Pattern;
import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.CreateUserViewModel;

/**
 * Opret bruger view der kontakter CreateUserViewModel når en EditText-værdi har ændret sig.
 * Klassen benyttes til at håndtere brugeroprettelse.
 */
public class CreateUserActivity extends AppCompatActivity {

    private static final String TAG = "CreateUserActivity";

    private CreateUserViewModel viewModel;
    private TextInputEditText email, password, reapeatPassword, name, lastname, username;
    private ProgressBar opretBrugerProgressbar;
    private Button createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        ////////////////////////////// initialisere klassevariabler ///////////////////////////////
        //Initialisere ViewModel
        viewModel = ViewModelProviders.of(this).get(CreateUserViewModel.class);

        /////////////////////////////// initialisering fra xml ////////////////////////////////////
        email = (TextInputEditText) findViewById(R.id.tiet_email);
        password = (TextInputEditText) findViewById(R.id.tiet_password);
        reapeatPassword = (TextInputEditText) findViewById(R.id.tiet_reapeat_password);

        name = (TextInputEditText) findViewById(R.id.tiet_name);
        lastname = (TextInputEditText) findViewById(R.id.tiet_lastname);
        username = (TextInputEditText) findViewById(R.id.tiet_username);

        createUserButton = (Button) findViewById(R.id.btn_create_user);
        opretBrugerProgressbar = (ProgressBar) findViewById(R.id.pb_opret_bruger_progressbar);


        //Ændre værdien i viewModel hver gang email teksten ændres
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
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
                if (s != null) {
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
                if (s != null) {
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
                if (s != null) {
                    viewModel.setCurrentUserLastname(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    viewModel.setCurrentUserUsername(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Observer der kigger på status boolean fra viewmodel. fortæller om authentication og
        //persistering gik godt.
        Observer<Boolean> statusObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    //Hvis det gik godt, send til mainActivity
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                } else {

                    switch (viewModel.getStatusInt().getValue()){
                        case 0: errorCreatingUser("Der skete en fejl under oprettelsen " +
                                "af brugeren. E-mailen findes evt. allerede i databasen. Prøv igen", "Fejl ved persistering ||| " +
                                "status code 0 ||| brugeren blev ikke gemt i Firebase Auth");
                            showButtonHideProgress();
                            break;
                        case 1: errorCreatingUser("Der skete en fejl under oprettelsen " +
                                "af brugeren, prøv igen", "Fejl ved persistering ||| " +
                                "status code 1 ||| brugeren blev gemt i Firebase Auth, men kunne " +
                                "ikke opdaterer username i Firebase Auth");
                            showButtonHideProgress();
                            break;
                        case 2: errorCreatingUser("Der skete en fejl under oprettelsen " +
                                "af brugeren, prøv igen", "Fejl ved persistering ||| " +
                                "status code 2 ||| brugeren blev ikke gemt i Firebase Firestore");
                            showButtonHideProgress();
                            break;
                    }

                }
            }
        };
        //aktivering af observeren
        viewModel.getStatus().observe(this, statusObserver);

    }


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


    public void createUserOnClick(View view) {
        hideButtonShowProgress();

        //Info udskrift i loggen
        Log.i(TAG, "onClick: \nUser Value = " + viewModel.getCurrentUserEmail().getValue() + "\n" +
                "isEmailValid = " + viewModel.isEmailValid().getValue() + "\n" +
                "isPasswordValid = " + viewModel.isPasswordValid().getValue());

        // tjekker om E-mail og password overholder de givne regler samt om password og
        // repeat password er ens og name og lastname fra viewmodel ikke er tomme.
        if(viewModel.isEmailValid().getValue()
                && viewModel.isPasswordValid().getValue()
                && viewModel.getCurrentUserPassword().getValue().equals(reapeatPassword.getText().toString())
                && !viewModel.getCurrentUserName().getValue().isEmpty()
                && !viewModel.getCurrentUserLastname().getValue().isEmpty()) {

            //kontakter databasen og forsøger at oprette en bruger.
            viewModel.createUserAuth();

            //else if sætninger der præsentere en specifik fejl for brugeren. (oplysninger findes
            // ud for hver else if sætning.
        } else if(viewModel.getCurrentUserName().getValue().isEmpty()) { //hvis navn er tomt
            errorCreatingUser("Du har ikke angivet et navn", "navn blev ikke angivet");
        } else if(viewModel.getCurrentUserLastname().getValue().isEmpty()){ //hvis efternavn er tomt
            errorCreatingUser("Du har ikke angivet et efternavn", "efternavn blev ikke angivet");
        } else if (!viewModel.isEmailValid().getValue()){ // hvis emailen ikke overholder regex
            errorCreatingUser("Der er noget galt med din E-mail adresse", "E-mailen overholder ikke regex i isEmailValid metoden");
        } else if (!viewModel.getCurrentUserPassword().getValue().equals(reapeatPassword.getText().toString())){ // hvis passwords ikke er ens
            errorCreatingUser("Passwords er ikke ens!", "Password og repeat password er ikke ens.");
        } else if (viewModel.getCurrentUserPassword().getValue().length() < 8){ //hvis password er mindre end 8 karakterer langt
            errorCreatingUser("Password skal være minimum 8 karaktere langt", "Password er mindre end 8 karaktere langt");
        } else if (!Pattern.matches(".*[A-Z].*", viewModel.getCurrentUserPassword().getValue())){ //hvis password ikke indeholder et stort bogstav
            errorCreatingUser("Password skal indeholde et stort bogstav", "Password indeholder ikke et stort bogstav");
        } else if (!Pattern.matches(".*[a-z].*", viewModel.getCurrentUserPassword().getValue())){ //hvis password ikke indeholder et lille bogstav
            errorCreatingUser("Password skal indeholde et lille bogstav", "Password indeholder ikke et lille bogstav");
        } else if (!viewModel.isPasswordValid().getValue()){ //hvis password ikke indeholder et tegn eller tal
            errorCreatingUser("Password skal indeholde et tegn eller tal", "Password indeholder ikke et tegn eller tal");
        } else { //i alle andre tilfælde
            errorCreatingUser("Dine indtastninger blev ikke godkendt af en ukendt årsag. Prøv venligst igen", "indtasningerne blev ikke godkendt af en ukendt årsag");
        }

    }


    /**
     * metode der bliver benyttet til at præsentere end fejl ved brugeroprettelse for brugeren.
     * Den benytter sig også af metoden showButtonHideProgress();
     * @param toastText den text der skal præsenteret for brugeren
     * @param errorMessage den fejl der skal skrives i loggen
     */
    private void errorCreatingUser(String toastText, String errorMessage){
        showButtonHideProgress();
        Toast.makeText(CreateUserActivity.this, toastText, Toast.LENGTH_SHORT).show();
        Log.w(TAG, "onClick: " + errorMessage);
    }


    //viser opret bruger knappen og skjuler progressbaren i UI
    private void showButtonHideProgress(){
        opretBrugerProgressbar.setVisibility(View.GONE);
        createUserButton.setVisibility(View.VISIBLE);

    }


    //viser progressbaren og skjuler opretbruger knappen i UI
    private void hideButtonShowProgress(){
        opretBrugerProgressbar.setVisibility(View.VISIBLE);
        createUserButton.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        viewModel.detach();
        super.onDestroy();
    }

}
