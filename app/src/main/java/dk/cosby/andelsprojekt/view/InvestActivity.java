package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.Toast;

import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.InvestViewModel;

public class InvestActivity extends AppCompatActivity {

    private InvestViewModel viewModel;
    private Button invest;
    private TextInputEditText beloeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);

        viewModel = ViewModelProviders.of(this).get(InvestViewModel.class);

        invest = (Button) findViewById(R.id.btn_invester);
        beloeb = (TextInputEditText) findViewById(R.id.tiet_beloeb);

        beloeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    //Opdaterer ammount i viewmodel når tekst ændres
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
        Observer<Boolean> transactionStatus = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                // hvis transaktionen existerer og brugerens informationer succesfuldt er
                // blevet hentet fra databasen
                if (aBoolean) {
                    viewModel.investToDatabase();
                } else {
                    //Hvis der skete en fejl undervejs
                    Toast.makeText(InvestActivity.this, "Transaktionen fejlede.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //Obeerverer transactionsStatus fra viewmodel
        viewModel.getTransactionStatus().observe(this, transactionStatus);

        //Denne Observer obseverer på transaktionenen til firestore
        Observer<Boolean> fondStatus = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    Toast.makeText(InvestActivity.this, "Transaktionen var successfuld.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        viewModel.getFondDepositStatus().observe(this, fondStatus);


        //Laver en transaktion nårder trykke på knappe
        invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.makeTransaction();
            }
        });


    }


}
