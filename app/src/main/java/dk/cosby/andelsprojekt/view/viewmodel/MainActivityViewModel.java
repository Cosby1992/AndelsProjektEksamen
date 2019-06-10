package dk.cosby.andelsprojekt.view.viewmodel;


import android.arch.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {

    MainActivityFirebase firebase;

    public MainActivityViewModel() {

        firebase = new MainActivityFirebase();


    }

    public Boolean isUserLoggedIn(){
        return firebase.isLoggedIn();
    }

    public void makeInvestment(double amount){
        firebase.makeInvestment(amount);
    }

    public void makeLoan(double amount){
        firebase.makeLoan(amount);
    }






}
