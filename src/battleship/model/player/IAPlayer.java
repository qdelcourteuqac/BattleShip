package battleship.model.player;

import battleship.model.board.Board;

public class IAPlayer extends Player {
    @Override
    public Board getPersonalBoard() {
        return null;
    }

    @Override
    public Board getTacticalBoard() {
        return null;
    }
}
