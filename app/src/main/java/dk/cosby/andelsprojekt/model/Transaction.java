package dk.cosby.andelsprojekt.model;

import android.util.Log;

/**
 * Denne klasse kan tage varer om en transaktion mellem to Users.
 *
 * @version 1.0
 * @author Cosby
 */

public class Transaction {

    private static final String TAG = "Transaktion";

    private User user;
    private double amount;

    //Constructor
    public Transaction(User user, double amount) {
        this.user = user;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "user=" + user +
                ", amount=" + amount +
                '}';
    }

    ////////////////////////// getters and setters ///////////////////////////////

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
