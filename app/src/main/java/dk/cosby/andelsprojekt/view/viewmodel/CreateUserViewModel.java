package dk.cosby.andelsprojekt.view.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import dk.cosby.andelsprojekt.model.User;

/**
 * Dette er en ViewModel til CreateUserActivity.
 * CreateUserActivity opdatere CreateUserViewModel som opdatere User.
 *
 * @version 1.0
 * @author Cosby
 */
public class CreateUserViewModel extends ViewModel {

    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();
    private MutableLiveData<String> currentUserName = new MutableLiveData<>();
    private MutableLiveData<String> currentUserLastname = new MutableLiveData<>();
    private User user = new User();

    public CreateUserViewModel() {
        currentUserEmail.setValue(user.getEmailAdresse());
        currentUserPassword.setValue(user.getPassword());
        currentUserName.setValue(user.getNavn());
        currentUserLastname.setValue(user.getEfternavn());
    }

    //Foretag ændring i modellen
    public void setCurrentUserEmail(String userEmail){
        user.setEmailAdresse(userEmail);
        currentUserEmail.setValue(user.getEmailAdresse());
    }

    //Foretag ændring i modellen
    public void setCurrentUserPassword(String userPassword){
        user.setPassword(userPassword);
        currentUserPassword.setValue(user.getPassword());
    }

    //Foretag ændring i modellen
    public void setCurrentUserName(String userName){
        user.setNavn(userName);
        currentUserName.setValue(user.getNavn());
    }

    //Foretag ændring i modellen
    public void setCurrentUserLastname(String userLastname){
        user.setEfternavn(userLastname);
        currentUserPassword.setValue(user.getEfternavn());
    }

    public MutableLiveData<String> getCurrentUserEmail() {
        currentUserEmail.setValue(user.getEmailAdresse());
        return currentUserEmail;
    }

    public MutableLiveData<String> getCurrentUserPassword() {
        currentUserPassword.setValue(user.getPassword());
        return currentUserPassword;
    }

//    public void observeEmail(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserEmail.observe(lifeCycleOwner, stringObserver);
//////    }
//////
//////    public void observePassword(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserPassword.observe(lifeCycleOwner, stringObserver);
//////    }


}
