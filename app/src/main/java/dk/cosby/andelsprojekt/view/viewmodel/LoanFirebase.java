package dk.cosby.andelsprojekt.view.viewmodel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Firebase klasse der bliver benyttet i LoanViewModel
 * Holder kontakt med firebase separeret i en klasse for sig
 *
 * @author Mathias
 * @version 1.0
 */
public class LoanFirebase {


    // no-arg constructor
    LoanFirebase(){

    }

    //TODO: ret således at viewmodel lytter på ændringer i firebase og retter UI accordingly


    /**
     * @return en Task der returnere en reference til et bruger dokument i firebase
     */
    public Task<DocumentSnapshot> getUserData(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference userDocRef = firestore.collection("users").document(auth.getCurrentUser().getUid());

        return userDocRef.get();
    }

    /**
     * @param o objekt parameter der skal gemmes i firestore
     * @return en Task der returnere en reference til dokumentet hvor objektet bliver gemt
     */
    public Task<DocumentReference> saveTransaktion(Object o){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        //en reference til transaktions collectionen i firestore
        CollectionReference userColRef = firestore.collection("users")
                                                    .document(auth.getCurrentUser().getUid())
                                                    .collection("transactions");

        return userColRef.add(o);
    }


    /**
     * @return en reference til fonddokumentet i firestore
     */
    public DocumentReference getFondRef(){

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        return firestore.collection("fond").document("psuedoFond");
    }


}
