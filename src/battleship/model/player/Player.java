package battleship.model.player;

import battleship.model.board.PersonalBoard;
import battleship.model.board.TacticalBoard;

public abstract class Player {

    public abstract PersonalBoard getPersonalBoard();
    public abstract TacticalBoard getTacticalBoard();

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
