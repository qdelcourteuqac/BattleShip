package battleship.model.player;

import battleship.model.board.Board;

public class HumanPlayer extends Player {

    private Board personalBoard;
    private Board tacticalBoard;

    public HumanPlayer() {
        this.personalBoard = new Board();
        this.tacticalBoard = new Board();
    }

    @Override
    public Board getPersonalBoard() {
        return this.personalBoard;
    }

    @Override
    public Board getTacticalBoard() {
        return this.tacticalBoard;
    }
}
