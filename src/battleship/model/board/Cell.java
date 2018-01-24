package battleship.model.board;

import battleship.model.ship.Ship;

public class Cell {

    private Ship ship;

    private int x;
    private int y;

    public Cell(Ship ship, int x, int y) {
        this.ship = ship;
        this.x = x;
        this.y = y;
    }

    public Ship getShip() {
        return ship;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return this.ship == null ? " * " : " " + this.ship.toString() + " ";
    }
}
