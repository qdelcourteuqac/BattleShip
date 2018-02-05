package battleship.model.ship;

import java.util.ArrayList;

public class Fleet {

    private ArrayList<Ship> ships;

    public Fleet() {
        this.ships = new ArrayList<>();
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        if (!this.ships.contains(ship)) {
            this.ships.add(ship);
        }
    }

    /**
     * Return true if there is no more ships, false otherwise
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return !this.ships.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder fleetRepresentation = new StringBuilder();

        if (this.isEmpty()) {
            fleetRepresentation.append("\n\nFleet:\n");
            for (Ship ship : this.getShips()) {
                fleetRepresentation.append(ship.getClass().getSimpleName() + " " + ship.getHit() + "/3, ");
            }
        }

        return fleetRepresentation.toString();
    }
}
