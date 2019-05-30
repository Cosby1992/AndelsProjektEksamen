package dk.cosby.andelsprojekt.model;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class UserTest {

    @Test
    public void isEmailValid() {

        User user = new User();

        assertTrue(user.isEmailValid("andersbirkedahl@gmail.com"));
        assertTrue(user.isEmailValid("test.email.adresse@hotmail.com"));
        assertTrue(user.isEmailValid("emailadresse@yahoo.com"));
        assertTrue(user.isEmailValid("thisemailisok@ofir.dk"));
        assertFalse(user.isEmailValid("emailadresse.com"));
        assertFalse(user.isEmailValid("email adresse@gmail.com"));
        assertFalse(user.isEmailValid("emailadresse@gmail"));
        assertFalse(user.isEmailValid("Email@a.a"));

    }

    @Test
    public void isPasswordValid() {
    }
}