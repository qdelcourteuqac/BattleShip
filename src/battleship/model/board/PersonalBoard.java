package battleship.model.board;

import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;

public class PersonalBoard extends Board {

    private Fleet fleet;

    public PersonalBoard() {
        this.fleet = new Fleet();
    }

    public void hitShip(Ship ship) {
        this.fleet.hit(ship);
    }

    @Override
    public void placeShip(Ship ship, Coordinate coordinate, boolean isVertical) {
        super.placeShip(ship, coordinate, isVertical);
        this.fleet.addShip(ship);
    }

    @Override
    public String toString() {
        return super.toString() + this.fleet;
    }
}
