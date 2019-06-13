package dk.cosby.andelsprojekt.view.viewmodel;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import dk.cosby.andelsprojekt.model.observermodel.Addict;
import dk.cosby.andelsprojekt.model.observermodel.Pusher;

class LoginFirebase implements Pusher {

    private static final String TAG = "FIREBASE_REPOSITORY";

    //ArrayList der indeholder de observes der har knyttet sig til denne klasse
    private ArrayList<Addict> addictList = new ArrayList<>();

    //En deklaration af et FirebaseAuth Object
    private FirebaseAuth auth;

    //no-arg constructor
    LoginFirebase() {
        //initialisere auth
        auth = FirebaseAuth.getInstance();
    }

    //login metode, giver observers besked ved completion
    void login(String email, String password){

        //sørger for at der ikke er mellemrum før eller efter emailens værdi
        email = email.trim();

        //kontakter firebase med brugerens oplysninger og forsøger at logge brugeren ind.
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Hvis brugeren blev succesfuldt logget ind gives besked til observers
                    //om ændringen
                    pushBoolToAddicts(true);
                } else {
                    //Hvis brugeren ikke blev logget ind gives besked til observers
                    //om ændringen
                    pushBoolToAddicts(false);
                }
            }
        });

    }


    /////////////////////////////////// Observer/Observable methods ///////////////////////////////

    //bliv observer
    @Override
    public void becomeAddict(Addict addict) {
        addictList.add(addict);
    }

    //fjern observer
    @Override
    public void goToRehab(Addict addict) {
        if(addictList.contains(addict)){
            addictList.remove(addict);
        }
    }

    //notify (giv observers besked)
    @Override
    public void pushBoolToAddicts(boolean dope) {
        for (Addict addict : addictList) {
            addict.shootBool(dope);
        }
    }
}
