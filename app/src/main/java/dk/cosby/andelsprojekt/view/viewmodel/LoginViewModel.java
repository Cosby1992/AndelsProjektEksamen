package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import dk.cosby.andelsprojekt.model.User;
import dk.cosby.andelsprojekt.model.observermodel.Addict;

public class LoginViewModel extends ViewModel implements Addict {

    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();

    private MutableLiveData<Boolean> isPasswordOkay = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEmailOkay = new MutableLiveData<>();

    private MutableLiveData<Boolean> loginBool = new MutableLiveData<>();

    private User user = new User();
    private LoginFirebase firebase = new LoginFirebase();

    public LoginViewModel() {
        currentUserEmail.setValue("");
        currentUserPassword.setValue("");

        firebase.becomeAddict(this);
    }

    public void login(){
        firebase.login(currentUserEmail.getValue(), currentUserPassword.getValue());
    }

    public MutableLiveData<String> getCurrentUserEmail() {
        currentUserEmail.setValue(user.getEmailAddress());
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String email) {
        user.setEmailAddress(email);
        currentUserPassword.setValue(user.getEmailAddress());
    }

    public MutableLiveData<Boolean> getLoginBool() {
        return loginBool;
    }

    public MutableLiveData<String> getCurrentUserPassword() {
        return currentUserPassword;
    }

    public void setCurrentUserPassword(String password) {
        currentUserPassword.setValue(password);
    }

    ////////////////////////////// Obeserver/Observable methods ///////////////////////////////////
    @Override
    public void onBoolDopeReceived(boolean dope) {
        loginBool.setValue(dope);
    }

    public void detach(){
        firebase.goToRehab(this);
    }


}