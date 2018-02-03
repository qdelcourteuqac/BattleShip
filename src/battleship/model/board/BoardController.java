package battleship.model.board;

import battleship.model.player.Player;
import battleship.model.ship.*;

public class BoardController {

    private PersonalBoard personalBoard;
    private TacticalBoard tacticalBoard;

    public BoardController() {
        this.personalBoard = new PersonalBoard();
        this.tacticalBoard = new TacticalBoard();
    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public TacticalBoard getTacticalBoard() {
        return this.tacticalBoard;
    }

    public Fleet getFleet() {
        return this.getPersonalBoard().getFleet();
    }

    /**
     * Fire on opponent board
     *
     * @param opponent - Player to attack
     * @param targetCell - Target cell in opponent board
     * @return boolean - Return false if missed, true if hit
     */
    public boolean fire(Player opponent, String targetCell) {
        if (!this.getPersonalBoard().canFireAt(targetCell)) {
            return false;
        }

        Cell cell = opponent.getBoardController().getPersonalBoard().getCell(targetCell);
        if (cell.getShip() == null) {
            this.getTacticalBoard().missedFlag(targetCell);
            return false;
        }

        opponent.getBoardController().getPersonalBoard().hitShip(cell.getShip());
        this.getTacticalBoard().hitFlag(targetCell);

        return true;
    }

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
