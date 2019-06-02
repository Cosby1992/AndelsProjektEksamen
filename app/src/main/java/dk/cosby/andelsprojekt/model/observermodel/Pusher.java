package dk.cosby.andelsprojekt.model.observermodel;


/**
 * Pusher er en del af vores egne observer klasser og kan sammenlignes med java.util.Observable.
 * Udarbejdet pga. java.util.Observable er blevet erklæret forældet (deprecated) og
 * MutableLiveData observers kræver en Activity Context for at blive implementeret.
 *
 */
public interface Pusher {
    void becomeAddict(Addict addict);
    void goToRehab(Addict addict);

    default void pushToAddicts(String dope){

    }

    default void pushBoolToAddicts(boolean dope){

    }

    default void pushBoolArrayToAddicts(boolean[] dope){

    }
}
