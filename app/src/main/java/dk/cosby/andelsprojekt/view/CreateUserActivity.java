package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import java.util.EmptyStackException;
import java.util.regex.Pattern;
import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.model.observermodel.Addict;
import dk.cosby.andelsprojekt.view.viewmodel.CreateUserViewModel;

/**
 * Opret bruger view der kontakter CreateUserViewModel når en EditText-værdi har ændret sig.
 * Klassen benyttes til at håndtere brugeroprettelse.
 */
public class CreateUserActivity extends AppCompatActivity implements Addict {

    private static final String TAG = "CreateUserActivity";

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

        viewModel.becomeAddict(this);

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
        // repeat password er ens
        if(viewModel.isEmailValid().getValue()
                && viewModel.isPasswordValid().getValue()
                && viewModel.getCurrentUserPassword().getValue().equals(reapeatPassword.getText().toString())
                && !name.getText().toString().isEmpty()
                && !lastname.getText().toString().isEmpty()) {

            viewModel.createUserAuth();



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

    @Override
            public void onBoolArrayDopeReceived(boolean[] dope) {
                if(dope[0] && !dope[1] && !dope[2]){
                    viewModel.saveUserInFirestore();
                } else if (dope[0] && dope[1] && !dope[2]) {
                    viewModel.updateUserAuth();
                } else if (dope[0] && dope[1] && dope[2]) {
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                } else {
                    showButtonHideProgress();
        }
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
