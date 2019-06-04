package dk.cosby.andelsprojekt.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testklasse der tester funktionaliteten af udvalgte metoder fra klassen Fond der kan findes
 * i dk.cosby.andelsprojekt.model
 */
public class FondTest {

    private final double DELTA = 0.01;

    //Tester add metoden i fond klassen
    @Test
    public void add() {
        Fond fond = new Fond();
        fond.setBalance(100);
        fond.add(200);

        Fond fond2 = new Fond();
        fond2.setBalance(100);
        fond2.add(1000000.5);

        Fond fond3 = new Fond();
        fond3.setBalance(100);
        fond3.add(-10);

        Fond fond4 = new Fond();
        fond4.setBalance(100);
        fond4.add(0);

        assertEquals(300.0, fond.getBalance(), DELTA);
        assertEquals(1000100.5, fond2.getBalance(), DELTA);
        assertEquals(90.0, fond3.getBalance(), DELTA);
        assertEquals(100.0, fond4.getBalance(), DELTA);

    }

    //Tester subtract metoden i fond klassen
    @Test
    public void subtract() {
        Fond fond = new Fond();
        fond.setBalance(10000);
        fond.subtract(5000);

        Fond fond2 = new Fond();
        fond2.setBalance(100);
        fond2.subtract(0.01);

        Fond fond3 = new Fond();
        fond3.setBalance(100);
        fond3.subtract(-10);

        Fond fond4 = new Fond();
        fond4.setBalance(100);
        fond4.subtract(0);

        assertEquals(5000.0, fond.getBalance(), DELTA);
        assertEquals(99.99, fond2.getBalance(), DELTA);
        assertEquals(110.0, fond3.getBalance(), DELTA);
        assertEquals(100.0, fond4.getBalance(), DELTA);
    }

    //tester setbalance metoden i fond klassen
    @Test
    public void setBalance() {

        Fond fond = new Fond();
        fond.setBalance(1000);

        Fond fond2 = new Fond();
        fond2.setBalance(-1000);

        Fond fond3 = new Fond();
        fond3.setBalance(0);

        Fond fond4 = new Fond();
        fond4.setBalance(123456789.123456789);

        assertEquals(1000.0, fond.getBalance(), DELTA);
        assertEquals(-1000.0, fond2.getBalance(), DELTA);
        assertEquals(0, fond3.getBalance(), DELTA);
        assertEquals(123456789.123456789, fond4.getBalance(), DELTA);

    }
}