package dk.cosby.andelsprojekt.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {


    //Pga. Afrundingsfejl har vi valgt at sætte DELTA til 0.01
    private final double DELTA = 0.01;

    //////////////////////////////////////addFunds tests///////////////////////////////////////////////
    @Test
    public void addFunds() {
        Account account = new Account();
        
        account.setBalance(10000);
        account.addFunds(20000);
        assertEquals(30000,account.getBalance(), DELTA);

        account.setBalance(-50000);
        account.addFunds(40000);
        assertEquals(-10000,account.getBalance(), DELTA);

        account.setBalance(5000.41);
        account.addFunds(2000.40);
        assertEquals(7000.81,account.getBalance(),DELTA);

        account.setBalance(10000);
        account.addFunds(100000.10);
        assertEquals(110000.10,account.getBalance(),DELTA);

    }

    //////////////////////////////////////subtractFunds tests///////////////////////////////////////////////
    @Test
    public void subtractFunds() {
        Account account = new Account();

        account.setBalance(15000);
        account.subtractFunds(10000);
        assertEquals(5000,account.getBalance(),DELTA);

        account.setBalance(-10000);
        account.subtractFunds(5000);
        assertEquals(-15000,account.getBalance(),DELTA);

        account.setBalance(40000.50);
        account.subtractFunds(20000.40);
        assertEquals(20000.10,account.getBalance(),DELTA);

        account.setBalance(100000.50);
        account.subtractFunds(50000);
        assertEquals(50000.50,account.getBalance(),DELTA);
    }
}