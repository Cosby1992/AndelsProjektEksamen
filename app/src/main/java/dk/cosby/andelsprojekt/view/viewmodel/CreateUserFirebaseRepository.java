package dk.cosby.andelsprojekt.view.viewmodel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import dk.cosby.andelsprojekt.model.User;

/**
 * Denne klasse håndtere databaseoperationer der vedrører oprettelse af en bruger
 *
 * @version 1.0
 * @author Cosby
 */
public class CreateUserFirebaseRepository {



    private final String TAG = "FIREBASE_REPOSITORY";

    private FirebaseFirestore firestoreDB;
    private FirebaseAuth auth;

    //no-arg constructor
    public CreateUserFirebaseRepository() {
        firestoreDB = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    /**
     * Metode der gemmer et User objekt i Firestore
     *
     * @param user bruger der skal gemmes
     * @return en Task der gør det muligt at reagere på svar fra firestore databasen
     */
    public Task<Void> saveUserInFirestore(User user) {
        return firestoreDB.collection("users").document(auth.getCurrentUser().getUid()).set(user, SetOptions.merge());
    }
//    public Task<Void> writeObjectToFirestore(Object object){
//        return firestoreDB.collection("users").document(auth.getCurrentUser().getUid()).set(object, SetOptions.merge());
//    }


    /**
     * Metode der forsøger at oprette en bruger i firebase
     *
     * @param email brugerens email
     * @param password brugerens password
     * @return om brugeren blev oprettet succesfuldt
     */
    public Task<AuthResult> createUserAuth(String email, String password){
        return auth.createUserWithEmailAndPassword(email,password);
    }

    /**
     * Metode der forsøger at opdatere en Firebase bruger profil
     *
     * @param name bruger navn
     * @param lastname bruger efternavn
     * @return en Task der gør det reagere på databasens svar
     */
    public Task<Void> updateUserAuth(String name, String lastname){

        String displayName = name.trim() + " " + lastname.trim();

        Log.d(TAG, "UpdateUserAuth: display name is set to " + displayName);

        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(displayName)
                                                .build();

        Task<Void> task = auth.getCurrentUser().updateProfile(profileUpdate);

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "UpdateUserAuth(String, String): 'Brugeren profil blev ikke opdateret'");
            }
        });

        return task;
    }

}
