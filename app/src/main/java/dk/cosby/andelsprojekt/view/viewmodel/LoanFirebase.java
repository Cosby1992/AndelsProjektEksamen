package dk.cosby.andelsprojekt.view.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

public class LoanFirebase {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DocumentReference userDoc, fondDoc;
    private String userID;

    LoanFirebase(){
        auth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        userID = auth.getCurrentUser().getUid();

        userDoc = firestore.collection("users").document(userID);

        fondDoc = firestore.collection("fond").document("psuedoFond");



    }

    public Task<DocumentSnapshot> getUserData(){
        return userDoc.get();
    }

    public Task<DocumentReference> saveTransaktion(Object o){
        return userDoc.collection("transactions").add(o);
    }

    public Task<DocumentSnapshot> withdrawFromFond(double amount){
        return fondDoc.get();
    }





}
