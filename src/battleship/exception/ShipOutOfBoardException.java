package battleship.exception;

public class ShipOutOfBoardException extends Exception {

    public ShipOutOfBoardException() {
        super("Your ship is out of board !");
    }
}
