package dk.cosby.andelsprojekt.model;

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
    private String name;
    private String lastname;
    private String username;
    private String emailAddress;


    //no-arg constructor
    public User() {
        name = "";
        lastname = "";
        emailAddress = "";
        username = "";
    }

    //Constructor (brugt under udvikling)
    public User(String emailAddress) {
        this.emailAddress = emailAddress;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
