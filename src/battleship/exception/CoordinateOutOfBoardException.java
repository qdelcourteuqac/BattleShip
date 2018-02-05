package battleship.exception;

import battleship.model.board.Board;

public class CoordinateOutOfBoardException extends Exception {

    public CoordinateOutOfBoardException() {
        super("Your coordinate is out of board, must be in [0, "+Board.WIDTH+"[ !");
    }
}
