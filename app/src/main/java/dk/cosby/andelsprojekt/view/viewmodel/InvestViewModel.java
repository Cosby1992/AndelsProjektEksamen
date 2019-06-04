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

public class InvestViewModel extends ViewModel {

    //MutableLiveData objekter
    private MutableLiveData<Double> currentAmount = new MutableLiveData<>();
    private MutableLiveData<Boolean> userDataObtained = new MutableLiveData<>();
    private MutableLiveData<Boolean> fondDepositStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> transactionsStatus = new MutableLiveData<>();

    //Firebase og model objekter
    private User user;
    private Transaction transaction;
    private TransactionFirebase firebase;
    private Fond fond;


    /**
     * Constructor der initialiserer modelen og firbase objekterne
     */
    public InvestViewModel() {
        user = new User();
        firebase = new TransactionFirebase();
        userDataObtained.setValue(false);
        transaction = new Transaction(user,0);

        //Bruger user informationerne fra firebase til at opdatere objektet i klassen
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
     * Denne metode gemmer transaktionerne sammen med bruger objektet i firestore
     * Den ændrer også transktionsstatusen alt efter om persisteringen lykkedes
     * Den sætter også transaktionens beløb til currentAmount
     */
    public void makeTransaction(){

        //Opdaterer transaktionsobjekterne
        if (!user.getUser_id().isEmpty()){
            transaction.setAmount(Double.valueOf(currentAmount.toString()));
            transaction.setUser(user);

            //Kalder metode i TransactionFirebase
            firebase.saveTransaktion(transaction).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()){
                        //TransactionsStatus bliver ture hvis den bliver persisteret korrekt
                        transactionsStatus.setValue(true);
                    }
                    else {
                        /*
                        Ellers sættes værdien til false hvis den ikke bliver
                        persisteret korrekt
                        */
                        transactionsStatus.setValue(false);
                    }
                }
            });
        }
    }

    /**
     * Metode der smider penge ind på vores fond i firebasen når folk investerer
     */
    public void investToDatabase(){

        //kalder metode i TransactionFirebase som returnere et Task<DocumentSnapshot> objekt
        //der lyttes efter hvornår dokumentet bliver returneret
        firebase.getFondRef().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && currentAmount.getValue() != null){

                    //Værdi ændres i firebase
                    fond = document.toObject(Fond.class);
                    fond.add(currentAmount.getValue());
                    firebase.getFondRef().set(fond, SetOptions.merge()).addOnFailureListener(new OnFailureListener() {

                        //Når der sker en fejl under investering
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            fondDepositStatus.setValue(false);
                            Log.d(TAG, "Invest: 'failed' ");
                        }

                        //Når investeringen bliver godkendt
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Værdien i MutableLiveData bliver ændret og giver besked til observers
                            fondDepositStatus.setValue(true);
                        }
                    });

                } else {
                    Log.d(TAG, "Invest: 'failed' dokumentet existerede ikke i firebase");
                }
            }
        });

    }

     ////////////////////////////Getters and Setters/////////////////////////////////////////////////

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

    public MutableLiveData<Boolean> getTransactionStatus() {
        return transactionsStatus;
    }

    public MutableLiveData<Boolean> getFondDepositStatus() {
        return fondDepositStatus;
    }
}


