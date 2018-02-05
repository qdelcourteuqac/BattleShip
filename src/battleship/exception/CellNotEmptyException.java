package battleship.exception;

public class CellNotEmptyException extends Exception {

    public CellNotEmptyException() {
        super("This cell contains an object !");
    }
}
