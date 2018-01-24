package battleship.model.board;

import battleship.model.ship.Ship;

import java.util.ArrayList;

public class PersonalBoard extends Board {

    private ArrayList<Ship> ships;

    public PersonalBoard() {
        this.ships = new ArrayList<>();
    }

    @Override
    public void placeShip(Ship ship, int x, int y, boolean isVertical) {
        super.placeShip(ship, x, y, isVertical);
        this.ships.add(ship);
    }

    @Override
    public String toString() {
        StringBuilder personalBoardRepresentation = new StringBuilder();

        personalBoardRepresentation.append(super.toString());
        if (!this.ships.isEmpty()) {
            personalBoardRepresentation.append("\n\nFleet:\n");
            for (Ship ship: this.ships) {
                personalBoardRepresentation.append(ship.getClass().getSimpleName() + " " + ship.getHited() + "/3, ");
            }
        }

        return personalBoardRepresentation.toString();
    }
}
