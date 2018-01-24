package battleship.model.player;

import battleship.model.board.PersonalBoard;
import battleship.model.board.TacticalBoard;

public class IAPlayer extends Player {
    @Override
    public PersonalBoard getPersonalBoard() {
        return null;
    }

    @Override
    public TacticalBoard getTacticalBoard() {
        return null;
    }
}
