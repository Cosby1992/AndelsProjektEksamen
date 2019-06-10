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


    private ArrayList<Addict> addictList = new ArrayList<>();
    private static final String TAG = "FIREBASE_REPOSITORY";

    private FirebaseAuth auth;

    //no-arg constructor
    LoginFirebase() {
        auth = FirebaseAuth.getInstance();
    }

    void login(String email, String password){

        email = email.trim();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    pushBoolToAddicts(true);
                } else {
                    pushBoolToAddicts(false);
                }
            }
        });

    }


    /////////////////////////////////// Observer/Observable methods ///////////////////////////////

    //become observer
    @Override
    public void becomeAddict(Addict addict) {
        addictList.add(addict);
    }

    //remove observer
    @Override
    public void goToRehab(Addict addict) {
        if(addictList.contains(addict)){
            addictList.remove(addict);
        }
    }

    //notify
    @Override
    public void pushBoolToAddicts(boolean dope) {
        for (Addict addict : addictList) {
            addict.onBoolDopeReceived(dope);
        }
    }
}
