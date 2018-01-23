package battleship.model.player;

import battleship.model.board.Board;

public abstract class Player {

    public abstract Board getPersonalBoard();
    public abstract Board getTacticalBoard();
}
