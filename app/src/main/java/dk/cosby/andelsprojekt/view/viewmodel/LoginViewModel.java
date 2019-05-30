package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import dk.cosby.andelsprojekt.model.User;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();
    private User user = new User();
    private LoginFirebaseRepository firebaseRepository = new LoginFirebaseRepository();

    public LoginViewModel() {
        currentUserEmail.setValue("");
        currentUserPassword.setValue("");
    }

    public Task<AuthResult> login(){
        return firebaseRepository.login(currentUserEmail.getValue(), currentUserPassword.getValue());
    }

    public Boolean isUserLoggedIn(){
        return firebaseRepository.isLoggedIn();
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
        return currentUserPassword;
    }

    public void setCurrentUserPassword(String userPassword) {
        this.currentUserPassword.setValue(userPassword);
    }

}