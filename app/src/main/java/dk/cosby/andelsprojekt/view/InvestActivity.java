package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;

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

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void investOnClick(View view){

    }


}
