package battleship.model.board;

import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;

public class PersonalBoard extends Board {

    private Fleet fleet;

    public PersonalBoard() {
        this.fleet = new Fleet();
    }

    public Fleet getFleet() {
        return this.fleet;
    }

    public boolean hitShip(Ship ship) {
        return this.fleet.hit(ship);
    }

    @Override
    public void placeShip(Ship ship, Coordinate targetCoordinate, boolean isVertical) throws Exception {
        super.placeShip(ship, targetCoordinate, isVertical);
        this.fleet.addShip(ship);
    }

    @Override
    public String toString() {
        return super.toString() + this.fleet;
    }
}
