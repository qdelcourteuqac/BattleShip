package battleship;

import battleship.model.GameController;
import battleship.model.player.Player;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();

        Player ramzi = gameController.getPlayer1();

        System.out.println(ramzi);

        System.out.println(ramzi.getBoardController().fire(ramzi, "I2"));

        System.out.println(ramzi);

        System.out.println(ramzi.getBoardController().fire(ramzi, "J1"));

        System.out.println(ramzi);
    }
}
