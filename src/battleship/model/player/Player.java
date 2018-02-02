package battleship.model.player;

import battleship.model.board.Cell;
import battleship.model.board.PersonalBoard;
import battleship.model.board.TacticalBoard;

public abstract class Player {

    public abstract PersonalBoard getPersonalBoard();
    public abstract TacticalBoard getTacticalBoard();


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

        Cell cell = opponent.getPersonalBoard().getCell(targetCell);
        if (cell.getShip() == null) {
            this.getTacticalBoard().missedFlag(targetCell);
            return false;
        }

        opponent.getPersonalBoard().hitShip(cell.getShip());
        opponent.getTacticalBoard().hitFlag(targetCell);
        System.out.println("Fire !!!");
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
