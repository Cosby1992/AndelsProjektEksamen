package dk.cosby.andelsprojekt.model;

public class Account {

    private double balance;

    public Account() {
        balance = 0;
    }

    public void addFunds(double amount){
        balance += amount;
    }

    public void subtractFunds(double amount){
        balance -= amount;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
