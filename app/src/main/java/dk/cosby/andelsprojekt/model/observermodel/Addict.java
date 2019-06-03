package dk.cosby.andelsprojekt.model.observermodel;


/**
 * Addicts er en del af vores egne observer klasser og kan sammenlignes med java.util.Observer.
 * Udarbejdet pga. java.util.Observer er blevet erklæret forældet (deprecated) og
 * MutableLiveData observers kræver en Activity Context for at blive implementeret.
 */
public interface Addict {

    default void shoot(){

    }

    default void onStringDopeRecieved(String dope){

    }

    default void onBoolDopeRecieved(){

    }

    default void onBoolArrayDopeRecieved(boolean[] dope){

    }


}
