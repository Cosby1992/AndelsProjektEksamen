package dk.cosby.andelsprojekt.view.viewmodel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import dk.cosby.andelsprojekt.model.User;

public class InvestFirebase {

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String userid;


    public InvestFirebase() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userid = auth.getCurrentUser().getUid();
    }


    public User getUserData(){
        return firestore.collection("users").document(userid)
    }


}
