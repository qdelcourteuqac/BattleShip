package battleship.model.player;

import battleship.model.board.Board;

public abstract class Player {

    public abstract Board getPersonalBoard();
    public abstract Board getTacticalBoard();

    @Override
    public String toString() {
        return "\n\n========== Tactical Board ==========\n" +
                this.getTacticalBoard() +
                "\n\n====================================\n" +
                "\n\n========== Personal Board ==========\n" +
                this.getPersonalBoard() +
                "\n\n====================================\n";
    }
}
