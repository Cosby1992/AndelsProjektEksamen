package dk.cosby.andelsprojekt.model;

public class Fond {

    private double balance;

    public Fond() {
    }

    public Fond(double balance) {
        this.balance = balance;
    }


    public void add(double amount){
        balance += amount;
    }

    public void subtract(double amount){
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
