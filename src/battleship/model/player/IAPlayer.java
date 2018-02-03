package battleship.model.player;

import battleship.model.board.BoardController;

public class IAPlayer extends Player {
    private BoardController boardController;

    public IAPlayer() {
        this.boardController = new BoardController();
    }

    @Override
    public BoardController getBoardController() {
        return this.boardController;
    }

    @Override
    public boolean hasFleet() {
        return this.boardController.getFleet().isEmpty();
    }
}
