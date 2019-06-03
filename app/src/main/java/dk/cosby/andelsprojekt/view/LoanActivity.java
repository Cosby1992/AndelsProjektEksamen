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

public class LoanActivity extends AppCompatActivity {

    private LoanViewModel viewModel;

    private TextInputEditText loanText;
    private Button loanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        viewModel = ViewModelProviders.of(this).get(LoanViewModel.class);

        loanText = (TextInputEditText) findViewById(R.id.tiet_loan);
        loanButton = (Button) findViewById(R.id.btn_loan);

        loanText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null){
                    viewModel.setCurrentAmount(Double.valueOf(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        Observer<Boolean> transactionStatus = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    viewModel.makePsuedoFondTransaction();
                } else {
                    Toast.makeText(LoanActivity.this, "Transaktionen med pseudofonden fejlede.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        viewModel.getTransaktionStatus().observe(this, transactionStatus);

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
                viewModel.makeTransaktion();
            }
        });



    }




}
