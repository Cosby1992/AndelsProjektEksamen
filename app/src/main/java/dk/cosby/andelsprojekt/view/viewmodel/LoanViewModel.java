package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import dk.cosby.andelsprojekt.model.Transaction;
import dk.cosby.andelsprojekt.model.User;

public class LoanViewModel extends ViewModel {

    private MutableLiveData<Boolean> userDataObtained = new MutableLiveData<>();
    private MutableLiveData<Boolean> transaktionStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> fondWithdrawStatus = new MutableLiveData<>();
    private MutableLiveData<Double> currentAmount = new MutableLiveData<>();

    private User user = new User();
    private LoanFirebase firebase;
    private Transaction transaction;


    public LoanViewModel(){
        firebase = new LoanFirebase();
        userDataObtained.setValue(false);
        transaktionStatus.setValue(false);
        fondWithdrawStatus.setValue(false);
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
            transaction.setAmount(currentAmount.getValue());
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

        firebase.withdrawFromFond(Double.valueOf(currentAmount.getValue())).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    fondWithdrawStatus.setValue(true);
                } else {
                    fondWithdrawStatus.setValue(false);
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
