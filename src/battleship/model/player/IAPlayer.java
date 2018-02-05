package battleship.model.player;

import battleship.model.board.*;
import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;

import java.util.Random;

public class IAPlayer extends Player {

    private PersonalBoard personalBoard;
    private TacticalBoard tacticalBoard;

    public IAPlayer() {
        this.personalBoard = new PersonalBoard();
        this.tacticalBoard = new TacticalBoard();
    }

    @Override
    public void placeShip(Ship ship, Coordinate targetCoordinate, boolean isVertical) throws Exception {
        this.personalBoard.placeShip(ship, targetCoordinate, isVertical);
    }

    /**
     * Fire on opponent board
     *
     * @param opponent   - Player to attack
     * @param targetCell - Target cell in opponent board
     * @return boolean - Return false if missed, true if hit
     */
    @Override
    public boolean fire(Player opponent, Coordinate targetCell) {
        if (!this.personalBoard.canFireAt(targetCell)) {
            return false;
        }

        Cell cell = opponent.getPersonalBoard().getCell(targetCell);
        if (cell.getShip() == null) {
            this.getTacticalBoard().missedFlag(targetCell);
            return false;
        }

        opponent.getPersonalBoard().hitShip(cell.getShip());
        this.getTacticalBoard().hitFlag(targetCell);

        return true;
    }

    @Override
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    @Override
    public TacticalBoard getTacticalBoard() {
        return this.tacticalBoard;
    }

    @Override
    public Coordinate requestToFire() {
        int y = (new Random()).nextInt(Board.HEIGHT);
        int x = (new Random()).nextInt(Board.WIDTH);
        return new Coordinate(x, y);
    }

    @Override
    public void requestToMoveShip() {
        return;
    }

    @Override
    public boolean hasFleet() {
        return this.getFleet().isEmpty();
    }

    @Override
    public Fleet getFleet() {
        return this.personalBoard.getFleet();
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