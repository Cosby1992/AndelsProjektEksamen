package dk.cosby.andelsprojekt.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    User user = new User();

    //////////////////////////////////////email tests///////////////////////////////////////////////
        @Test
    //Denne email er korrekt eftersom den indeholder 'navn@mail.com'
    public void isEmailValid() {
        String email = "nicklas.bring.nielsen@gmail.com";
        assertTrue(user.isEmailValid(email));
    }

    @Test
    //Denne email er forkert eftersom den ikke indeholder et '@'
    public void isEmailValid2() {
        String email = "nicklas.bring.nielsengmail.com";
        assertTrue(user.isEmailValid(email));
    }

    @Test
    //Denne email er forkert eftersom den ikke indeholder '.com'
    public void isEmailValid3() {
        String email = "nicklas.bring.nielsen@gmail";
        assertTrue(user.isEmailValid(email));
    }


    //////////////////////////////////////password tests////////////////////////////////////////////
    @Test
    //Dette password er korrekt eftersom det indeholder mindst et lille og et stort bogstav, samt tal
    public void isPasswordValid() {
        String password = "Ap123456";
        assertTrue(user.isPasswordValid(password));
    }

    @Test
    //Dette password er forkert eftersom det ikke indeholder et stort bogstav
    public void isPasswordValid2() {
        String password = "ap123456";
        assertTrue(user.isPasswordValid(password));
    }

    @Test
    //Dette password er forkert eftersom det ikke indeholder et stort og et lille bogstav
    public void isPasswordValid3() {
        String password = "123456";
        assertTrue(user.isPasswordValid(password));
    }

    @Test
    //Dette password er forkert eftersom det ikke indeholder tal
    public void isPasswordValid4() {
        String password = "Andelsprojekt";
        assertTrue(user.isPasswordValid(password));
    }
}