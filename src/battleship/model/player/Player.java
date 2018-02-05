package battleship.model.player;

import battleship.exception.CellNotEmptyException;
import battleship.exception.CoordinateOutOfBoardException;
import battleship.exception.ShipOutOfBoardException;
import battleship.model.board.Cell;
import battleship.model.board.Flag;
import battleship.model.board.PersonalBoard;
import battleship.model.board.TacticalBoard;
import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;
import battleship.utils.Coordinate;

public abstract class Player {

    protected String name;
    protected PersonalBoard personalBoard;
    protected TacticalBoard tacticalBoard;

    protected Player() {
        this.personalBoard = new PersonalBoard();
        this.tacticalBoard = new TacticalBoard();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public TacticalBoard getTacticalBoard() {
        return this.tacticalBoard;
    }

    /**
     * Implement logic to initialize your fleet (place your ships on personal board)
     */
    public abstract void initializeFleet();

    /**
     * Return Coordinate to shot on enemy's board
     *
     * @return Coordinate
     */
    public abstract Coordinate requestToFire();

    /**
     * Implement logic to move ship with user interaction
     */
    public abstract void requestToMoveShip();

    /**
     * Place the ship in the personal board's player
     *
     * @param ship             - Ship to place
     * @param targetCoordinate - Target Cell
     * @throws ShipOutOfBoardException, CellNotEmptyException, CoordinateOutOfBoardException
     */
    public void placeShip(Ship ship, Coordinate targetCoordinate) throws ShipOutOfBoardException, CellNotEmptyException, CoordinateOutOfBoardException {
        this.personalBoard.placeShip(ship, targetCoordinate);
    }


    /**
     * Return Fleet's player
     *
     * @return Fleet
     */
    public Fleet getFleet() {
        return this.getPersonalBoard().getFleet();
    }

    /**
     * Return false if all of his ships are destroyed
     *
     * @return boolean
     */
    public boolean hasFleet() {
        return this.getFleet().isEmpty();
    }

    /**
     * Fire on opponent board
     *
     * @param opponent   - Player to attack
     * @param targetCell - Target cell in opponent board
     * @return boolean - Return false if missed, true if hit
     */
    public boolean fire(Player opponent, Coordinate targetCell) {
        if (!this.getPersonalBoard().canFireAt(targetCell)) {
            return false;
        }

        Cell cell = opponent.getPersonalBoard().getCell(targetCell);
        if (cell.getShip() == null) {
            this.getTacticalBoard().getFlag(targetCell).setState(Flag.FlagType.MISSED);
            return false;
        }

        opponent.getPersonalBoard().hitShip(cell.getShip());
        this.getTacticalBoard().getFlag(targetCell).setState(Flag.FlagType.HIT);

        return true;
    }

    @Override
    public String toString() {
        return "\n\n========== Tactical Board ==========\n" +
                this.getTacticalBoard() +
                "\n\n========== Personal Board ==========\n" +
                this.getPersonalBoard() +
                "\n\n====================================\n";
    }
}
