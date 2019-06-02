package dk.cosby.andelsprojekt.view.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;

import dk.cosby.andelsprojekt.model.User;
import dk.cosby.andelsprojekt.model.observermodel.Addict;
import dk.cosby.andelsprojekt.model.observermodel.Pusher;

/**
 * Dette er en ViewModel til CreateUserActivity.
 * CreateUserActivity opdatere CreateUserViewModel som opdatere User.
 *
 * @version 1.0
 * @author Cosby
 */
public class CreateUserViewModel extends ViewModel implements Pusher {

    private final String TAG = "CreateUserViewModel";

    private MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();
    private MutableLiveData<String> currentUserName = new MutableLiveData<>();
    private MutableLiveData<String> currentUserLastname = new MutableLiveData<>();

    private MutableLiveData<Boolean> currentIsEmailValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> currentIsPasswordValid = new MutableLiveData<>();

    private MutableLiveData<Boolean> everythingOk = new MutableLiveData<>();
    private boolean[] allGoodArray = new boolean[3];

    private User user = new User();
    private CreateUserFirebase database = new CreateUserFirebase();

    public CreateUserViewModel() {

        currentUserEmail.setValue(user.getEmailAdresse());
        currentUserPassword.setValue("");
        currentUserName.setValue(user.getNavn());
        currentUserLastname.setValue(user.getEfternavn());

        currentIsEmailValid.setValue(false);
        currentIsPasswordValid.setValue(false);

        everythingOk.setValue(false);

        setCurrentUserId("midlertidigt id");

    }

    public void createUserAuth(){
        database.createUserAuth(currentUserEmail.getValue().trim(), currentUserPassword.getValue())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        allGoodArray[0] = task.isSuccessful();
                        AuthResult result = task.getResult();
                        currentUserId.setValue(result.getUser().getUid());

                        pushBoolArrayToAddicts(allGoodArray);
                    }
                });
    }

    public void saveUserInFirestore(){

        database.saveUserInFirestore(user, currentUserId.getValue())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                allGoodArray[1] = task.isSuccessful();
                pushBoolArrayToAddicts(allGoodArray);
            }
        });

    }

    public void updateUserAuth(){

        database.updateUserDisplayNameAuth(currentUserName.getValue(), currentUserLastname.getValue())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        allGoodArray[2] = task.isSuccessful();
                        pushBoolArrayToAddicts(allGoodArray);
                    }
                });
    }

    private boolean isAllGood(){
        boolean result = true;
        for(Boolean bool : allGoodArray){
            if(!bool){
                result = false;
            }
        }
        return result;
    }

    public MutableLiveData<Boolean> getEverythingOk() {
        return everythingOk;
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
        currentUserEmail.setValue(user.getEmailAdresse());
        return currentUserEmail;
    }

    //Foretag ændring i modellen
    public void setCurrentUserEmail(String userEmail){
        user.setEmailAdresse(userEmail);
        currentIsEmailValid.setValue(user.isEmailValid(userEmail));
        currentUserEmail.setValue(user.getEmailAdresse());
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
        currentUserLastname.setValue(user.getEfternavn());
        return currentUserLastname;
    }

    //Foretag ændring i modellen
    public void setCurrentUserLastname(String userLastname){
        user.setEfternavn(userLastname);
        currentUserPassword.setValue(user.getEfternavn());
    }



    public MutableLiveData<String> getCurrentUserName() {
        currentUserName.setValue(user.getNavn());
        return currentUserName;
    }

    //Foretag ændring i modellen
    public void setCurrentUserName(String userName){
        user.setNavn(userName);
        currentUserName.setValue(user.getNavn());
    }



    public MutableLiveData<Boolean> isEmailValid() {
            return currentIsEmailValid;
    }

    public MutableLiveData<Boolean> isPasswordValid() {
        return currentIsPasswordValid;
    }

    private ArrayList<Addict> addicts = new ArrayList<>();


    @Override
    public void becomeAddict(Addict addict) {
        addicts.add(addict);
    }

    @Override
    public void goToRehab(Addict addict) {
        addicts.remove(addict);
    }

    @Override
    public void pushBoolArrayToAddicts(boolean[] dope) {
        for(Addict addict : addicts){
            addict.onBoolArrayDopeRecieved(allGoodArray);
        }
    }

    //    public void observeEmail(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserEmail.observe(lifeCycleOwner, stringObserver);
//////    }
//////
//////    public void observePassword(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserPassword.observe(lifeCycleOwner, stringObserver);
//////    }


}
