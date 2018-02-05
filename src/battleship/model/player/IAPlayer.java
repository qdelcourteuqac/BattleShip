package battleship.model.player;

import battleship.model.board.Board;
import battleship.model.ship.*;
import battleship.utils.Coordinate;

import java.util.Random;

public class IAPlayer extends Player {

    @Override
    public void initializeFleet() throws Exception {
        Battleship battleship = new Battleship(Ship.Orientation.VERTICAL);
        Carrier carrier = new Carrier(Ship.Orientation.VERTICAL);
        Destroyer destroyer = new Destroyer(Ship.Orientation.HORIZONTAL);
        Cruiser cruiser = new Cruiser(Ship.Orientation.HORIZONTAL);
        Submarine submarine = new Submarine(Ship.Orientation.HORIZONTAL);

        this.placeShip(battleship, new Coordinate(0, 0));
        this.placeShip(carrier, new Coordinate(3, 3));
        this.placeShip(destroyer, new Coordinate(5, 5));
        this.placeShip(cruiser, new Coordinate(4, 8));
        this.placeShip(submarine, new Coordinate(0, 8));
    }

    @Override
    public Coordinate requestToFire() {
        int y = (new Random()).nextInt(Board.HEIGHT);
        int x = (new Random()).nextInt(Board.WIDTH);
        return new Coordinate(x, y);
    }

    @Override
    public void requestToMoveShip() {
        return;
    }
}