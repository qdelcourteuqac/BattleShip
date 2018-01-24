package battleship.model.player;

import battleship.model.board.PersonalBoard;
import battleship.model.board.TacticalBoard;

public class HumanPlayer extends Player {

    private PersonalBoard personalBoard;
    private TacticalBoard tacticalBoard;

    public HumanPlayer() {
        this.personalBoard = new PersonalBoard();
        this.tacticalBoard = new TacticalBoard();
    }

    @Override
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    @Override
    public TacticalBoard getTacticalBoard() {
        return this.tacticalBoard;
    }
}
