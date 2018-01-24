package battleship.model.board;

import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;

public class PersonalBoard extends Board {

    private Fleet fleet;

    public PersonalBoard() {
        this.fleet = new Fleet();
    }

    @Override
    public void placeShip(Ship ship, int x, int y, boolean isVertical) {
        super.placeShip(ship, x, y, isVertical);
        this.fleet.addShip(ship);
    }

    @Override
    public String toString() {
        return super.toString() + this.fleet;
    }
}