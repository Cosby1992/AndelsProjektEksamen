package dk.cosby.andelsprojekt.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import dk.cosby.andelsprojekt.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}
