package dk.cosby.andelsprojekt.model;

import android.util.Log;
import java.util.Date;

/**
 * Denne klasse indeholder al brugerinformation samt en constructor til at oprette brugere.
 *
 * @version 1.0
 * @author Cosby
 */

public class User  {

    private static final String TAG = "User";

    private int user_id;
    private String emailAdresse;
    private String password;
    private String timestamp;
    private UserType type;

    //no-arg constructor
    public User() {
        emailAdresse = "";
        password = "";
        type = UserType.TEST;

        Log.i(TAG, "Et nyt User object blev skabt ved brug af no-arg constructoren");
    }

    //Constructor (brugt under udvikling)
    public User(String emailAdresse, String password, UserType type) {
        this.emailAdresse = emailAdresse;
        this.password = password;
        this.type = type;
        timestamp = new Date().toString();

        Log.i(TAG, "Et nyt User object blev skabt");
    }


    /////////////////////////////// getters and setters ////////////////////////////////////


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

}
