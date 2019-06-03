package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import dk.cosby.andelsprojekt.model.Fond;
import dk.cosby.andelsprojekt.model.Transaction;
import dk.cosby.andelsprojekt.model.User;

import static android.support.constraint.Constraints.TAG;

public class LoanViewModel extends ViewModel {

    private MutableLiveData<Boolean> userDataObtained = new MutableLiveData<>();
    private MutableLiveData<Boolean> transaktionStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> fondWithdrawStatus = new MutableLiveData<>();
    private MutableLiveData<Double> currentAmount = new MutableLiveData<>();

    private User user = new User();
    private Fond fond;
    private LoanFirebase firebase;
    private Transaction transaction;


    public LoanViewModel(){
        firebase = new LoanFirebase();
        userDataObtained.setValue(false);
        transaction = new Transaction(user, 0);

        firebase.getUserData().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                if (snapshot.exists()){
                    user = snapshot.toObject(User.class);
                }
            }
        });

        currentAmount.setValue(0.0);


    }

    public void makeTransaktion(){
        if(!user.getUser_id().isEmpty()){
            transaction.setAmount(Double.valueOf(currentAmount.getValue().toString()));
            transaction.setUser(user);

            firebase.saveTransaktion(transaction).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if(task.isSuccessful()){
                        transaktionStatus.setValue(true);
                    } else {
                        transaktionStatus.setValue(false);
                    }
                }
            });
        }
    }


    public void makePsuedoFondTransaction(){

        firebase.getFondRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && currentAmount.getValue() != null){
                    fond = document.toObject(Fond.class);
                    fond.subtract(currentAmount.getValue());
                    firebase.getFondRef().set(fond, SetOptions.merge()).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            fondWithdrawStatus.setValue(false);
                            Log.d(TAG, "makeLoan: 'failure' loan failed");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            fondWithdrawStatus.setValue(true);
                        }
                    });
                } else {
                    Log.d(TAG, "makeLoan: 'failed' dokumentet existerede ikke i firebase");
                }
            }
        });
    }

    public MutableLiveData<Double> getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double amount) {
        transaction.setAmount(amount);
        currentAmount.setValue(transaction.getAmount());
    }

    public MutableLiveData<Boolean> getUserDataObtained() {
        return userDataObtained;
    }

    public MutableLiveData<Boolean> getTransaktionStatus() {
        return transaktionStatus;
    }

    public MutableLiveData<Boolean> getFondWithdrawStatus() {
        return fondWithdrawStatus;
    }
}
