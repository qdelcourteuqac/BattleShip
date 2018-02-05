package battleship.model.board;

import battleship.model.ship.Ship;
import battleship.utils.Coordinate;

public class Cell {

    private Object object;
    private Coordinate coordinate;

    public Cell(Object object, Coordinate coordinate) {
        this.object = object;
        this.coordinate = coordinate;
    }

    public Object getObject() {
        return object;
    }

    public Ship getShip() {
        return (this.object instanceof Ship)? (Ship) this.object : null;
    }

    public Flag getFlag() {
        return (this.object instanceof Flag)? (Flag) this.object : null;
    }

    public int getX() {
        return this.coordinate.getX();
    }

    public int getY() {
        return this.coordinate.getY();
    }

    @Override
    public String toString() {
        return this.object == null ? " * " : " " + this.object.toString() + " ";
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }
}
