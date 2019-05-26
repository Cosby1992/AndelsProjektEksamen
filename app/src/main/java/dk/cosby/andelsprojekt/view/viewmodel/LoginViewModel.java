package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import dk.cosby.andelsprojekt.model.User;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();
    private User user = new User();

    public LoginViewModel() {
        currentUserEmail.setValue(user.getEmailAdresse());
        currentUserPassword.setValue(user.getPassword());
    }

    public MutableLiveData<String> getCurrentUserEmail() {
        currentUserEmail.setValue(user.getEmailAdresse());
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String userEmail) {
        user.setEmailAdresse(userEmail);
        this.currentUserEmail.setValue(user.getEmailAdresse());
    }

    public MutableLiveData<String> getCurrentUserPassword() {
        currentUserPassword.setValue(user.getPassword());
        return currentUserPassword;
    }

    public void setCurrentUserPassword(String userPassword) {
        user.setPassword(userPassword);
        this.currentUserPassword.setValue(user.getPassword());
    }

}