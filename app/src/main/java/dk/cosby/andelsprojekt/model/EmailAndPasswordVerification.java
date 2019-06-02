package dk.cosby.andelsprojekt.model;

/**
 * En interfase som indeholder to metoder der kan validerer email og password
 * bliver brugt i modellen i user.
 */
public interface EmailAndPasswordVerification {

    boolean isEmailValid(String email);
    boolean isPasswordValid(String password);

}
