package dk.cosby.andelsprojekt.view.viewmodel;


import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.Executor;

import dk.cosby.andelsprojekt.model.User;

/**
 * Dette er en ViewModel til CreateUserActivity.
 * CreateUserActivity opdatere CreateUserViewModel som opdatere User.
 *
 * @version 1.0
 * @author Cosby
 */
public class CreateUserViewModel extends ViewModel {

    private final String TAG = "CreateUserViewModel";

    private MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private MutableLiveData<String> currentUserEmail = new MutableLiveData<>();
    private MutableLiveData<String> currentUserPassword = new MutableLiveData<>();
    private MutableLiveData<String> currentUserName = new MutableLiveData<>();
    private MutableLiveData<String> currentUserLastname = new MutableLiveData<>();

    private MutableLiveData<Boolean> currentIsEmailValid = new MutableLiveData<>();
    private MutableLiveData<Boolean> currentIsPasswordValid = new MutableLiveData<>();

    private User user = new User();
    private CreateUserFirebaseRepository database = new CreateUserFirebaseRepository();

    public CreateUserViewModel() {
        currentUserEmail.setValue(user.getEmailAdresse());
        currentUserPassword.setValue("");
        currentUserName.setValue(user.getNavn());
        currentUserLastname.setValue(user.getEfternavn());

        currentIsEmailValid.setValue(false);
        currentIsPasswordValid.setValue(false);

    }

    public Task<AuthResult> createUserAuth(){

        Task<AuthResult> task = database.createUserAuth(currentUserEmail.getValue().trim(), currentUserPassword.getValue());

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "saveUserAuth(String, String): 'Brugeren blev ikke gemt'");
            }
        });

        return task;
    }

    public Task<Void> saveUserInFirestore(){

        Task<Void> task = database.saveUserInFirestore(user);
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "saveUserInFirestore(): 'Brugeren blev ikke gemt'");
            }
        });

        return task;

    }

    public Task<Void> updateUserAuth(){

        Task<Void> task = database.updateUserAuth(currentUserName.getValue(), currentUserLastname.getValue());
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "saveUserInFirestore(): 'Brugeren blev ikke gemt'");
            }
        });

        return task;

    }


    public void setCurrentUserId(String userId) {
        if(userId != null && !userId.isEmpty()) {
            user.setUser_id(userId);
            currentUserId.setValue(user.getUser_id());
        }
    }

    //Foretag ændring i modellen
    public void setCurrentUserEmail(String userEmail){
        user.setEmailAdresse(userEmail);
        currentIsEmailValid.setValue(user.isEmailValid(userEmail));
        currentUserEmail.setValue(user.getEmailAdresse());
    }

    //Foretag ændring i modellen
    public void setCurrentUserPassword(String userPassword){
        currentIsPasswordValid.setValue(user.isPasswordValid(userPassword));
        currentUserPassword.setValue(userPassword);
    }

    //Foretag ændring i modellen
    public void setCurrentUserLastname(String userLastname){
        user.setEfternavn(userLastname);
        currentUserPassword.setValue(user.getEfternavn());
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

    public MutableLiveData<String> getCurrentUserId() {
        currentUserId.setValue(user.getUser_id());
        return currentUserId;
    }

    public MutableLiveData<String> getCurrentUserEmail() {
        currentUserEmail.setValue(user.getEmailAdresse());
        return currentUserEmail;
    }

    public MutableLiveData<String> getCurrentUserPassword() {
        return currentUserPassword;
    }

    public MutableLiveData<String> getCurrentUserName() {
        currentUserName.setValue(user.getNavn());
        return currentUserName;
    }

    public MutableLiveData<String> getCurrentUserLastname() {
        currentUserLastname.setValue(user.getEfternavn());
        return currentUserLastname;
    }

//    public void observeEmail(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserEmail.observe(lifeCycleOwner, stringObserver);
//////    }
//////
//////    public void observePassword(LifecycleOwner lifeCycleOwner, Observer<String> stringObserver) {
//////        currentUserPassword.observe(lifeCycleOwner, stringObserver);
//////    }


}
