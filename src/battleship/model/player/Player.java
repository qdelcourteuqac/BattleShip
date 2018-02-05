package battleship.model.player;

import battleship.utils.Coordinate;
import battleship.model.board.PersonalBoard;
import battleship.model.board.TacticalBoard;
import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;

public abstract class Player {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void placeShip(Ship ship, Coordinate targetCell, boolean isVertical) throws Exception;

    /**
     * Return false if all of his ships are destroyed
     *
     * @return boolean
     */
    public abstract boolean hasFleet();

    public abstract Fleet getFleet();

    public abstract boolean fire(Player opponent, Coordinate targetCell);


    public abstract PersonalBoard getPersonalBoard();

    public abstract TacticalBoard getTacticalBoard();

    public abstract Coordinate requestToFire();

    public abstract void requestToMoveShip() throws Exception;
}
