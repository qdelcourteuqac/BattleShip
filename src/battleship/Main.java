package battleship;

import battleship.controller.GameController;
import battleship.model.player.HumanPlayer;
import battleship.model.player.Player;

public class Main {
    public static void main(String[] args) {
        Player player1 = new HumanPlayer();
        Player player2 = new HumanPlayer();
        player1.setName("Ramzi");
        player2.setName("Quentin");

        (new GameController(player1, player2)).start();
    }
}
