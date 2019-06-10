package dk.cosby.andelsprojekt.view.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

class LoginFirebase {


    private static final String TAG = "FIREBASE_REPOSITORY";

    private FirebaseAuth auth;

    //no-arg constructor
    LoginFirebase() {
        auth = FirebaseAuth.getInstance();
    }

    Task<AuthResult> login(String email, String password){
        email = email.trim();
        return auth.signInWithEmailAndPassword(email, password);
    }

}
