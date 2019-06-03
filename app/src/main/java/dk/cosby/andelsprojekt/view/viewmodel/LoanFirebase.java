package dk.cosby.andelsprojekt.view.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

public class LoanFirebase {


    LoanFirebase(){

    }

    public Task<DocumentSnapshot> getUserData(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference userDocRef = firestore.collection("users").document(auth.getCurrentUser().getUid());

        return userDocRef.get();
    }

    public Task<DocumentReference> saveTransaktion(Object o){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        CollectionReference userColRef = firestore.collection("users")
                                                    .document(auth.getCurrentUser().getUid())
                                                    .collection("transactions");

        return userColRef.add(o);
    }


    public DocumentReference getFondRef(){

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        return firestore.collection("fond").document("psuedoFond");

    }





}
