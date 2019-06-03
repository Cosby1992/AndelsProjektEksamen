package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        

    }




}
