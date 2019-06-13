package dk.cosby.andelsprojekt.view.viewmodel;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


import dk.cosby.andelsprojekt.model.User;
import dk.cosby.andelsprojekt.model.observermodel.Addict;
import dk.cosby.andelsprojekt.model.observermodel.Pusher;

/**
 * Denne klasse håndtere databaseoperationer der vedrører oprettelse af en bruger
 *
 * @version 1.0
 * @author Cosby
 */
public class CreateUserFirebase implements Pusher {

    private final String TAG = "FIREBASE_REPOSITORY";

    private ArrayList<Addict> addicts = new ArrayList<>();
    private boolean status;
    private int statusInt;

    //no-arg constructor
    public CreateUserFirebase() {
        statusInt = 0;
    }


//    public Task<Void> writeObjectToFirestore(Object object){
//        return firestoreDB.collection("users").document(auth.getCurrentUser().getUid()).set(object, SetOptions.merge());
//    }

//TODO: Ret javadoc til den nye kode
    /**
     * Metode der forsøger at oprette en bruger i firebase
     *
     * @param email brugerens email
     * @param password brugerens password
     * @return om brugeren blev oprettet succesfuldt
     */
    public void createUserAuth(String email, String password, String username, User user){

        if (statusInt == 0) {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        statusInt = 1;
                        pushIntToAddicts(statusInt);
                        updateUserDisplayNameAuth(username, user);
                    } else {
                        statusInt = 0;
                        status = false;
                        pushIntToAddicts(statusInt);
                        pushBoolToAddicts(status);
                    }
                }
            });
        } else {
            updateUserDisplayNameAuth(username, user);
        }

    }


//TODO: ret javadoc til ny kode
    /**
     * Metode der forsøger at opdatere en Firebase bruger profil
     *
     * @param username det displaynavn brugeren har angivet
     */
    public void updateUserDisplayNameAuth(String username, User user){

        if(statusInt == 1) {
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build();

            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        statusInt = 2;
                        pushIntToAddicts(statusInt);
                        saveUserInFirestore(user);
                    } else {
                        statusInt = 1;
                        status = false;
                        pushIntToAddicts(statusInt);
                        pushBoolToAddicts(status);
                    }
                }
            });

        } else {
            saveUserInFirestore(user);
        }

    }

    //TODO: ret javadoc til ny kode
    /**
     * Metode der gemmer et User objekt i Firestore
     *
     * @param user bruger der skal gemmes
     */
    public void saveUserInFirestore(User user) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("users").document(firebaseUser.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    statusInt = 3;
                    status = true;
                    pushIntToAddicts(statusInt);
                    pushBoolToAddicts(status);
                } else {
                    statusInt = 2;
                    status = false;
                    pushIntToAddicts(statusInt);
                    pushBoolToAddicts(status);
                }
            }
        });

    }


    ///////////////////////// overskriver metoder Pusher interfacet ///////////////////////
    @Override
    public void becomeAddict(Addict addict) {
        addicts.add(addict);
    }

    @Override
    public void goToRehab(Addict addict) {
        addicts.remove(addict);
    }

    @Override
    public void pushIntToAddicts(int dope) {
        for (Addict addict : addicts) {
            addict.shootInt(dope);
        }
    }

    @Override
    public void pushBoolToAddicts(boolean dope) {
        for (Addict addict : addicts) {
            addict.shootBool(dope);
        }
    }

}
