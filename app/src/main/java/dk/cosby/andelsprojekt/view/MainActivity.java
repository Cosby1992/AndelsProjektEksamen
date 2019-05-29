package dk.cosby.andelsprojekt.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        statusText = findViewById(R.id.tv_status_text);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        if(user != null) {
            statusText.setText(user.getDisplayName());
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
}
