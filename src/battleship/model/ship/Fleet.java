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
        this.ships.add(ship);
    }

    public boolean isEmpty() {
        return !this.ships.isEmpty();
    }

    public boolean hit(Ship ship) {
        // TODO: si le bateau n'a plus de vie alors le supprimer de la flotte
        boolean isHitted = this.ships.contains(ship) && this.ships.get(this.ships.indexOf(ship)).hit();
        if (!ship.isAlive) {
            this.ships.remove(ship);
        }
        return isHitted;
    }

    @Override
    public String toString() {
        StringBuilder fleetRepresentation = new StringBuilder();

        if (this.isEmpty()) {
            fleetRepresentation.append("\n\nFleet:\n");
            for (Ship ship : this.getShips()) {
                fleetRepresentation.append(ship.getClass().getSimpleName() + " " + ship.getHited() + "/3, ");
            }
        }

        return fleetRepresentation.toString();
    }
}
