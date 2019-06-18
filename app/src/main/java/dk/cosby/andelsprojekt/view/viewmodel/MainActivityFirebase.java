package dk.cosby.andelsprojekt.view.viewmodel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import dk.cosby.andelsprojekt.model.Fond;

public class MainActivityFirebase {

    private final String TAG = "MainActivityFirebase";

    private FirebaseFirestore firestore;
    private DocumentReference docRef;

    private Fond fond;

    public MainActivityFirebase() {
        firestore = FirebaseFirestore.getInstance();
        docRef = firestore.collection("fond").document("psuedoFond");
    }

    Boolean isLoggedIn(){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }


    //TODO: gør det muligt at mainActivityViewModel kan lytte på om funktionerne er
    // succesfulde ved hjælp af Addict og Pusher interfaces. denne klasse gøres til pusher og
    // viewmodel til addict

    public void makeInvestment(double investmentAmount){

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()){
                    fond = document.toObject(Fond.class);
                    fond.add(investmentAmount);
                    docRef.set(fond, SetOptions.merge()).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "makeInvestment: 'failure' investement failed" + e.getMessage());
                        }
                    });
                } else {
                    Log.d(TAG, "makeInvestment: 'failed' dokumentet existerede ikke i firebase");
                }
            }
        });

    }

    public void makeLoan(double loanAmount){
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()){
                    fond = document.toObject(Fond.class);
                    fond.subtract(loanAmount);
                    docRef.set(fond, SetOptions.merge()).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "makeLoan: 'failure' loan failed");
                        }
                    });
                } else {
                    Log.d(TAG, "makeLoan: 'failed' dokumentet existerede ikke i firebase");
                }
            }
        });
    }

}
