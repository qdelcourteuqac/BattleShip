package battleship.exception;

public class LimitTranslationException extends Exception {

    public LimitTranslationException() {
        super("You can only perform maximum two cell moving !");
    }
}
