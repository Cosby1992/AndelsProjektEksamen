package dk.cosby.andelsprojekt.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;
import java.util.function.IntBinaryOperator;
import java.util.regex.Pattern;

/**
 * Denne klasse indeholder al brugerinformation samt en constructor til at oprette brugere.
 *
 * @version 1.0
 * @author Cosby
 */

public class User implements EmailAndPasswordVerification {


    private static final String TAG = "User";

    private String user_id;
    private String navn;
    private String efternavn;
    private String emailAdresse;


    //no-arg constructor
    public User() {
        navn = "";
        efternavn = "";
        emailAdresse = "";

        Log.i(TAG, "Et nyt User object blev skabt ved brug af no-arg constructoren");
    }

    //Constructor (brugt under udvikling)
    public User(String emailAdresse) {
        this.emailAdresse = emailAdresse;
        Log.i(TAG, "Et nyt User object blev skabt");
    }

    /**
     //     * Bruger en regular expression til at finde ud af om en email overholder den
     //     * normale opbygning for en email.
     //     *
     //     * @param email email der skal tjekkes
     //     * @return true hvis emailen overholder RFC-5322
     //     */
    @Override
    public boolean isEmailValid(String email) {
        return Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", email);
    }

    /**
     //     * Bruger en regular expression til at finde ud af om et password overholder følgene regler:
     //     *  1. passwordet er mindst 8 karakterer langt
     //     *  2. passwordet indeholder mindst 1 stort bogstav
     //     *  3. passwordet indeholder mindst 1 lille bogstav
     //     *  4. passwordet indeholder mindst 1 tal/tegn
     //     *
     //     * @param password password der skal tjekkes
     //     * @return true hvis passwordet overholder ovenstående regler
     //     */
    @Override
    public boolean isPasswordValid(String password) {
        return Pattern.matches("(?-i)(?=^.{8,}$)((?!.*\\s)(?=.*[A-Z])(?=.*[a-z]))((?=(.*\\d){1,})|(?=(.*\\W){1,}))^.*$", password);
    }

    /////////////////////////////// getters and setters ////////////////////////////////////



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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
}
