package battleship.model.board;

import battleship.model.ship.Ship;

public class Cell {

    private Ship ship;

    public Cell(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public String toString() {
        return this.ship == null ? " * " : " " + this.ship.toString() + " ";
    }
}
