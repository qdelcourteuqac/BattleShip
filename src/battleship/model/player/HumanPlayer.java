package battleship.model.player;

import battleship.model.board.BoardController;

public class HumanPlayer extends Player {

    private BoardController boardController;

    public HumanPlayer() {
        this.boardController = new BoardController();
    }

    @Override
    public BoardController getBoardController() {
        return this.boardController;
    }
}
