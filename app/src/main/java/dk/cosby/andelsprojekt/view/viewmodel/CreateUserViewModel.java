package dk.cosby.andelsprojekt.view.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import dk.cosby.andelsprojekt.model.User;
import dk.cosby.andelsprojekt.model.observermodel.Addict;

/**
 * Dette er en ViewModel til CreateUserActivity.
 * CreateUserActivity opdaterer CreateUserViewModel som opdaterer User.
 *
 * @version 1.0
 * @author Cosby
 */
public class CreateUserViewModel extends ViewModel implements Addict {

    private final String TAG = "CreateUserViewModel";

    private MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();
    private MutableLiveData<String> currentUserName = new MutableLiveData<>();
    private MutableLiveData<String> currentUserLastname = new MutableLiveData<>();
    private MutableLiveData<String> currentUserUsername = new MutableLiveData<>();

    private MutableLiveData<Boolean> currentIsEmailValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> currentIsPasswordValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> status = new MutableLiveData<>();
    private MutableLiveData<Integer> statusInt = new MutableLiveData<>();

    private User user = new User();
    private CreateUserFirebase database = new CreateUserFirebase();

    public CreateUserViewModel() {

        currentUserEmail.setValue(user.getEmailAddress());
        currentUserPassword.setValue("");
        currentUserName.setValue(user.getName());
        currentUserLastname.setValue(user.getLastname());

        currentIsEmailValid.setValue(false);
        currentIsPasswordValid.setValue(false);

        setCurrentUserId("midlertidigt id");

        database.becomeAddict(this);
        statusInt.setValue(0);

    }

    public void createUserAuth(){
        database.createUserAuth(currentUserEmail.getValue().trim(), currentUserPassword.getValue(), currentUserUsername.getValue().trim(), user);
    }

    public MutableLiveData<String> getCurrentUserId() {
        currentUserId.setValue(user.getUser_id());
        return currentUserId;
    }

    public void setCurrentUserId(String userId) {
        if(userId != null && !userId.isEmpty()) {
            user.setUser_id(userId);
            currentUserId.setValue(user.getUser_id());
        }
    }

    public MutableLiveData<String> getCurrentUserEmail() {
        currentUserEmail.setValue(user.getEmailAddress());
        return currentUserEmail;
    }

    //Foretag ændring i modellen
    public void setCurrentUserEmail(String userEmail){
        user.setEmailAddress(userEmail);
        currentIsEmailValid.setValue(user.isEmailValid(userEmail));
        currentUserEmail.setValue(user.getEmailAddress());
    }

    public MutableLiveData<String> getCurrentUserPassword() {
        return currentUserPassword;
    }

    //Foretag ændring i modellen
    public void setCurrentUserPassword(String userPassword){
        currentIsPasswordValid.setValue(user.isPasswordValid(userPassword));
        currentUserPassword.setValue(userPassword);
    }

    public MutableLiveData<String> getCurrentUserLastname() {
        currentUserLastname.setValue(user.getLastname());
        return currentUserLastname;
    }

    //Foretag ændring i modellen
    public void setCurrentUserLastname(String userLastname){
        user.setLastname(userLastname);
        currentUserPassword.setValue(user.getLastname());
    }

    public MutableLiveData<String> getCurrentUserName() {
        currentUserName.setValue(user.getName());
        return currentUserName;
    }

    //Foretag ændring i modellen
    public void setCurrentUserName(String userName){
        user.setName(userName);
        currentUserName.setValue(user.getName());
    }

    public MutableLiveData<String> getCurrentUserUsername() {
        return currentUserUsername;
    }

    public void setCurrentUserUsername(String userUsername) {
        user.setUsername(userUsername);
        currentUserUsername.setValue(user.getUsername());
    }

    public MutableLiveData<Boolean> isEmailValid() {
            return currentIsEmailValid;
    }

    public MutableLiveData<Boolean> isPasswordValid() {
        return currentIsPasswordValid;
    }

    public MutableLiveData<Boolean> getStatus() {
        return status;
    }

    public void setStatus(MutableLiveData<Boolean> status) {
        this.status = status;
    }

    public MutableLiveData<Integer> getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(int statusInt) {
        this.statusInt.setValue(statusInt);
    }

    public void detach(){
        database.goToRehab(this);
    }


//////////////////////////////////// overskriver Addict metoder ///////////////////////////////////


    @Override
    public void shootString(String dope) {
        setCurrentUserId(dope);
    }

    @Override
    public void shootBool(boolean dope) {
        status.setValue(dope);
    }

    @Override
    public void shootInt(int dope) {
        statusInt.setValue(dope);
    }

    //////////////////////////////// bliver ikke længere benyttet /////////////////////////////////

    //    public void observeEmail(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserEmail.observe(lifeCycleOwner, stringObserver);
//////    }
//////
//////    public void observePassword(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserPassword.observe(lifeCycleOwner, stringObserver);
//////    }


}
