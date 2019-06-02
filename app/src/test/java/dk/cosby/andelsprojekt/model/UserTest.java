package dk.cosby.andelsprojekt.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    User user = new User();

    //////////////////////////////////////email tests///////////////////////////////////////////////
    @Test
    public void isEmailValid() {

            //Denne email er rigtig eftersom den indeholder 'navn@mail.com'
            String email1 = "nicklas.bring.nielsen@gmail.com";
            //Denne email er forkert eftersom der mangler '@'
            String email2 = "nicklas.bring.nielsengmail.com";
            //Denne email er forkert eftersom der mangler '.com'
            String email3 = "nicklas.bring.nielsen@gmail";

            //Tester emails
            assertTrue(user.isEmailValid(email1));
            assertFalse(user.isEmailValid(email2));
            assertFalse(user.isEmailValid(email3));
        }


    //////////////////////////////////////password tests////////////////////////////////////////////
    @Test
    public void isPasswordValid() {

        //Dette password er korrekt eftersom det indeholder mindst et lille og et stort bogstav, samt tal
        String password1 = "Ap123456";
        //Dette password er forkert eftersom det ikke indeholder et stort bogstav
        String password2 = "ap123456";
        //Dette password er forkert eftersom det ikke indeholder et stort og et lille bogstav
        String password3 = "123456";
        //Dette password er forkert eftersom det ikke indeholder tal
        String password4 = "Andelsprojekt";

        //Tester passwords
        assertTrue(user.isPasswordValid(password1));
        assertFalse(user.isPasswordValid(password2));
        assertFalse(user.isPasswordValid(password3));
        assertFalse(user.isPasswordValid(password4));
    }
}