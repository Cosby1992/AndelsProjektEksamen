package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import dk.cosby.andelsprojekt.model.User;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();
    private User user = new User();

    public LoginViewModel() {

    }
}