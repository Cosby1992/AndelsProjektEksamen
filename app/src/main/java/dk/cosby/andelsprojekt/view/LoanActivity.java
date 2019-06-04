package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.LoanViewModel;


/**
 * Denne klasse tager sig af lån activity. Den foretager ændringer i brugerfladen for at låne
 * i fonden.
 *
 * @author Mathias
 * @version 1.0
 */
public class LoanActivity extends AppCompatActivity {

    // klasse variable i dette tilfælde er det viewmodellen til aktiviteten og
    // de ting brugeren kan interagere med.
    private LoanViewModel viewModel;

    private TextInputEditText loanText;
    private Button loanButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        // initialisere viewmodel
        viewModel = ViewModelProviders.of(this).get(LoanViewModel.class);

        // forbinder xml elementer med view (activity)
        loanText = (TextInputEditText) findViewById(R.id.tiet_loan);
        loanButton = (Button) findViewById(R.id.btn_loan);

        // lytter på ændringer i EditText feltet
        loanText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null){
                    // opdaterer viewModellen amount værdi ved textændringer
                    viewModel.setCurrentAmount(Double.valueOf(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        // Skaber observere til at kalde metoder når MutableLiveData booleans fra viewmodel ændre værdi
        // de benyttes for at der ikke bliver foretaget flere kald til databasen samtidig.
        // samt dele processerne op således at der kan reageres på forskellige metoders succes eller
        // fejl.
        Observer<Boolean> transactionStatus = new Observer<Boolean>(){
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                // hvis transaktionen existerer og brugerens informationer succesfuldt er
                // blevet hentet fra databasen
                if(aBoolean){
                    viewModel.makePsuedoFondTransaction();
                } else {
                    //Hvis der skete en fejl undervejs
                    Toast.makeText(LoanActivity.this, "Transaktionen fejlede.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Gør observeren observer a MutableLiveData<Boolean> transaktionsStatus fra viewmodellen
        viewModel.getTransaktionStatus().observe(this, transactionStatus);


        // Det samme som ovenfor men for transaktionen med psuedofonden i firestore db
        Observer<Boolean> pseudoFondStatus = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(LoanActivity.this, "Transaktionen var successfuld.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        viewModel.getFondWithdrawStatus().observe(this, pseudoFondStatus);


        loanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start en transaktion ved tryk på lån-knappen på brugerfladen
                viewModel.makeTransaktion();
            }
        });

    }

}
