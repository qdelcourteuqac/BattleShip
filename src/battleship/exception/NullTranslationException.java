package battleship.exception;

public class NullTranslationException extends Exception {

    public NullTranslationException() {
        super("You cannot move ship with 0 0 translation !");
    }
}
