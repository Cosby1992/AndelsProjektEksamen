package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import dk.cosby.andelsprojekt.model.Transaction;
import dk.cosby.andelsprojekt.model.User;

public class InvestViewModel extends ViewModel {

    //Mutable
    private MutableLiveData<String> currentAmount = new MutableLiveData<>();
    private User user = new User();
    private Transaction transaction;
    private TransactionFirebase firebase;


    public InvestViewModel() {
        transaction = new Transaction(user,0);
        firebase = new TransactionFirebase();

    }


}


