package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import dk.cosby.andelsprojekt.model.User;
import dk.cosby.andelsprojekt.model.observermodel.Addict;


/**
 * Denne klasse benytter sig af android.arch.lifecycle.ViewModel som gør det muligt
 * at holde en klasse der er initialiseret i et view at bliver holdt i live over
 * Android lifecycle changes. Denne klasse bliver benyttet i LoginActivity.java
 */
public class LoginViewModel extends ViewModel implements Addict {

    //MutableLiveData initialiseres
    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();

    private MutableLiveData<Boolean> isPasswordOkay = new MutableLiveData<>();
    private MutableLiveData<Boolean> isEmailOkay = new MutableLiveData<>();

    //Denne variabel bliver observeveret i LoginActivity
    private MutableLiveData<Boolean> loginBool = new MutableLiveData<>();

    //Initialisere et User- og LoginFirebase Objekt.
    private User user = new User();
    private LoginFirebase firebase = new LoginFirebase();


    /**
     * Constructor, bliver kaldt hvergang en instans af denne viewmodel bliver skabt.
     * sætter email og password værdier til at være ulig null.
     */
    public LoginViewModel() {
        //sørger for at email og password ikke er null selvom der ikke er indtastet data i view.
        currentUserEmail.setValue("");
        currentUserPassword.setValue("");

        //bliver addict (observer) af LoginFirebase boolean værdi.
        firebase.becomeAddict(this);
    }

    //når login knappen trykkes i viewet
    public void login(){
        firebase.login(currentUserEmail.getValue(), currentUserPassword.getValue());
    }


    public MutableLiveData<String> getCurrentUserEmail() {
        currentUserEmail.setValue(user.getEmailAddress());
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String email) {
        user.setEmailAddress(email);
        isEmailOkay.setValue(user.isEmailValid(email));
        currentUserPassword.setValue(user.getEmailAddress());
    }

    public MutableLiveData<Boolean> getLoginBool() {
        return loginBool;
    }

    public MutableLiveData<String> getCurrentUserPassword() {
        return currentUserPassword;
    }

    public void setCurrentUserPassword(String password) {
        isPasswordOkay.setValue(user.isPasswordValid(password));
        currentUserPassword.setValue(password);
    }

    ////////////////////////////// Obeserver/Observable methods ///////////////////////////////////
    @Override
    public void shootBool(boolean dope) {
        loginBool.setValue(dope);
    }

    public void detach(){
        firebase.goToRehab(this);
    }


}