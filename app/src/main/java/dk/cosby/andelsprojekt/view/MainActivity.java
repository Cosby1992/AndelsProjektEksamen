package dk.cosby.andelsprojekt.view;


import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dk.cosby.andelsprojekt.R;
import dk.cosby.andelsprojekt.view.viewmodel.MainActivityViewModel;

/**
 * Denne activity er applicationens "forside" eller "startskærm"
 * Det vil være her man bliver taget til når brugeren er authenticated af Firebase
 *
 * Viewet vil indeholde kontobalance, seneste transaktioner samt mulighed for at gå
 * videre til en låne eller betalings activity
 *
 * @version 1.0
 * @author Cosby
 *
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView statusText;

    private Button loan, invest;

    private MainActivityViewModel viewModel;


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Log Ud")
                .setMessage("Er du sikker på at du vil logge ud?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginActivity);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.tv_status_text);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();

        loan = (Button) findViewById(R.id.btn_loan_main);
        invest = (Button) findViewById(R.id.btn_invest_main);

        if(firebaseAuth.getCurrentUser() != null) {
            statusText.setText(firebaseAuth.getCurrentUser().getDisplayName());
        } else {
            statusText.setText("No user logged in");
        }

//        final Observer<Double> balanceDoubleObserver = new Observer<Double>() {
//            @Override
//            public void onChanged(@Nullable Double aDouble) {
//                if (aDouble != null) {
//                    String showText = aDouble + " AC";
//                    statusText.setText(showText);
//                } else statusText.setText("Kunne ikke hente balance informationer.");
//            }
//        };
//
//        viewModel.observeCurrentAccountBalance(this, balanceDoubleObserver);


    }


    public void investOnClick(View view) {

        Intent investActicity = new Intent(getApplicationContext(), InvestActivity.class);
        startActivity(investActicity);


    /*    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Investér");

// Set up the input
        final EditText input = new EditText(getBaseContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Beløb til overførsel");
        input.setTextColor(Color.BLACK);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!input.getText().toString().isEmpty()) {
                    viewModel.makeInvestment(Double.valueOf(input.getText().toString()));
                    Toast.makeText(getApplicationContext(), "Overførsel " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                } else dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        */
    }

    public void loanOnClick(View view) {

        Intent loanActivity = new Intent(getApplicationContext(),LoanActivity.class);
        startActivity(loanActivity);



//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Lån");
//
//// Set up the input
//        final EditText input = new EditText(getBaseContext());
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        input.setInputType(InputType.TYPE_CLASS_NUMBER);
//        input.setTextColor(Color.BLACK);
//        builder.setView(input);
//
//// Set up the buttons
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(!input.getText().toString().isEmpty()) {
//                    viewModel.makeLoan(Double.valueOf(input.getText().toString()));
//                    Toast.makeText(getApplicationContext(), "Du lånte " + input.getText().toString(), Toast.LENGTH_SHORT).show();
//                } else dialog.cancel();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

}

