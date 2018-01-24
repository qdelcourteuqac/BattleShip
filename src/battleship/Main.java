package battleship;

import battleship.model.board.PersonalBoard;
import battleship.model.player.HumanPlayer;
import battleship.model.player.Player;
import battleship.model.ship.Battleship;
import battleship.model.ship.Carrier;
import battleship.model.ship.Cruiser;
import battleship.model.ship.Destroyer;

public class Main {
    public static void main(String[] args) {

        Player ramzi = new HumanPlayer();

        PersonalBoard board = ramzi.getPersonalBoard();

        Battleship battleship = new Battleship();
        Carrier carrier = new Carrier();
        Destroyer destroyer = new Destroyer();
        Cruiser cruiser = new Cruiser();

        board.placeShip(battleship, 0, 9, false);
        board.placeShip(carrier, 0, 0, true);
        board.placeShip(destroyer, 2, 0, false);
        board.placeShip(cruiser, 3, 1, true);

        board.translateShip(carrier, 1, 1);


        System.out.println(ramzi);

        System.out.println(ramzi.fire(ramzi, "I2"));

        System.out.println(ramzi);
    }
}
