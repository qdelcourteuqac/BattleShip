package battleship.model.player;

import battleship.model.board.BoardController;

public abstract class Player {

    public abstract BoardController getBoardController();

    @Override
    public String toString() {
        return this.getBoardController().toString();
    }
}
