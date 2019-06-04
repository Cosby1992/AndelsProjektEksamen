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


/**
 * ViewModel objekt benyttet i LoanActivity.
 *
 * @author Mathias
 * @version 1.0
 */
public class LoanViewModel extends ViewModel {

    //MutableLiveData objecter
    private MutableLiveData<Boolean> userDataObtained = new MutableLiveData<>();
    private MutableLiveData<Boolean> transaktionStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> fondWithdrawStatus = new MutableLiveData<>();
    private MutableLiveData<Double> currentAmount = new MutableLiveData<>();

    //model og firebase objekter
    private User user;
    private Fond fond;
    private TransactionFirebase firebase;
    private Transaction transaction;


    /**
     * ViewModel constructor der initialisere model og firebase objekter
     */
    public LoanViewModel(){
        user = new User();
        firebase = new TransactionFirebase();
        userDataObtained.setValue(false);
        transaction = new Transaction(user, 0);

        //Obdatere user objektet med information fra firebase
        firebase.getUserData().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                if (snapshot.exists()){
                    user = snapshot.toObject(User.class);
                }
            }
        });

        //sætter værdien af currentAmount
        currentAmount.setValue(0.0);

    }


    /**
     * Metode der er ansvarlig for at gemme en transaktion sammen med brugerobjektet i firestore
     * retter værdien af transaktionStatus til true ved succes.
     *
     * benytter værdien i currentAmount
     */
    public void makeTransaktion(){
        // opdatere transaktionsopjekter
        if(!user.getUser_id().isEmpty()){
            transaction.setAmount(Double.valueOf(currentAmount.getValue().toString()));
            transaction.setUser(user);

            //Kalder metode i TransactionFirebase
            firebase.saveTransaktion(transaction).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if(task.isSuccessful()){
                        // opdaterer boolean til true hvis transaktionen succesfuldt bliver
                        // gemt i firestore
                        transaktionStatus.setValue(true);
                    } else {
                        // opdatere boolean til false hvis transaktionen fejler
                        transaktionStatus.setValue(false);
                    }
                }
            });
        }
    }


    /**
     * Metode der ændre en værdi i vores psuedofond i firestore
     */
    public void makePsuedoFondTransaction(){

        //kalder metode i TransactionFirebase der returnere en Task<DocumentSnapshot> objekt
        //der lyttes efter hvornår dokumentet bliver returneret
        firebase.getFondRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && currentAmount.getValue() != null){
                    //værdien ændres i firebase
                    fond = document.toObject(Fond.class);
                    fond.subtract(currentAmount.getValue());
                    firebase.getFondRef().set(fond, SetOptions.merge()).addOnFailureListener(new OnFailureListener() {
                        //ved fejl
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            fondWithdrawStatus.setValue(false);
                            Log.d(TAG, "makeLoan: 'failure' loan failed");
                        }
                        //ved succes
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //ændre værdien af MutablaLiveData og giver besked til observers
                            fondWithdrawStatus.setValue(true);
                        }
                    });
                } else {
                    Log.d(TAG, "makeLoan: 'failed' dokumentet existerede ikke i firebase");
                }
            }
        });
    }

    //////////////////////////// LIVEDATA GETTERS AND SETTERS ////////////////////////////////////
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
