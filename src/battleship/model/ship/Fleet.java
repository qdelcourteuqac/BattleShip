package battleship.model.ship;

import java.util.ArrayList;
import java.util.Observable;

public class Fleet {

    private ArrayList<Ship> ships;

    public Fleet() {
        this.ships = new ArrayList<>();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        this.ships.add(ship);
    }

    public boolean isEmpty() {
        return this.ships.isEmpty();
    }

    public boolean hit(Ship ship) {
        return (this.ships.contains(ship)) && this.ships.get(this.ships.indexOf(ship)).hit();
    }

    @Override
    public String toString() {
        StringBuilder fleetRepresentation = new StringBuilder();

        if (!this.isEmpty()) {
            fleetRepresentation.append("\n\nFleet:\n");
            for (Ship ship: this.getShips()) {
                fleetRepresentation.append(ship.getClass().getSimpleName() + " " + ship.getHited() + "/3, ");
            }
        }

        return fleetRepresentation.toString();
    }
}
