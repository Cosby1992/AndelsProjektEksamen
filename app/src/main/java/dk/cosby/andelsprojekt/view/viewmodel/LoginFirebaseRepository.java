package dk.cosby.andelsprojekt.view.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFirebaseRepository {


    private static final String TAG = "FIREBASE_REPOSITORY";

    private FirebaseFirestore firestoreDB;
    private FirebaseAuth auth;

    //no-arg constructor
    public LoginFirebaseRepository() {
        firestoreDB = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> login(String email, String password){
        email = email.trim();
        return auth.signInWithEmailAndPassword(email, password);
    }
}
