package dk.cosby.andelsprojekt.view.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import dk.cosby.andelsprojekt.model.Account;

public class MainActivityViewModel extends ViewModel {


    private MutableLiveData<Double> currentAccountBalance = new MutableLiveData<>();
    private Account account = new Account();

    public MainActivityViewModel() {

        currentAccountBalance.setValue(account.getBalance());

    }






}
