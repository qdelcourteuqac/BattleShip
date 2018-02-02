package battleship.model;

import battleship.model.player.HumanPlayer;
import battleship.model.player.IAPlayer;
import battleship.model.player.Player;

public class GameController {

    private Player player1;
    private Player player2;

    public GameController() {
        this.player1 = new HumanPlayer();
        this.player2 = new IAPlayer();
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }
}
