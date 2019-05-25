package dk.cosby.andelsprojekt.model;

import android.util.Log;
import java.util.Date;

/**
 * Denne klasse indeholder al brugerinformation samt en constructor til at oprette brugere.
 *
 * @version 1.0
 * @author Cosby
 */

public class User {


    private static final String TAG = "User";

    private int user_id;
    private String navn;
    private String efternavn;
    private String emailAdresse;
    private String password;


    //no-arg constructor
    public User() {
        navn = "";
        efternavn = "";
        emailAdresse = "";
        password = "";

        Log.i(TAG, "Et nyt User object blev skabt ved brug af no-arg constructoren");
    }

    //Constructor (brugt under udvikling)
    public User(String emailAdresse, String password) {
        this.emailAdresse = emailAdresse;
        this.password = password;

        Log.i(TAG, "Et nyt User object blev skabt");
    }

    /////////////////////////////// getters and setters ////////////////////////////////////


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public String getEmailAdresse() {
        return emailAdresse;
    }

    public void setEmailAdresse(String emailAdresse) {
        this.emailAdresse = emailAdresse;
        Log.i(TAG, "User email blev sat til: " + emailAdresse);

    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
