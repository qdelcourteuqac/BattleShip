package battleship.controller;

import battleship.model.player.Player;
import battleship.utils.Coordinate;
import java.util.Scanner;

public class GameController implements IGameController {

    private Scanner scanner;

    private boolean endGame;

    private Player player1;
    private Player player2;


    public GameController(Player player1, Player player2) {
        this.scanner = new Scanner(System.in);
        this.endGame = false;

        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Game Logic loop
     */
    @Override
    public void game() {
        // TODO FIX !
//        this.initializePlayers();

        // Init player's fleet
        this.player1.initializeFleet();
        this.player2.initializeFleet();

        // Previous player has been hit
        boolean isHit = true;

        while (!this.isEndGame()) {
            isHit = this.turn(this.player1, isHit);
            isHit = this.turn(this.player2, isHit);
        }

        this.displayWinner();
    }

    /**
     * Init Phase : set player's name
     */
    @Override
    public void initializePlayers() {
        System.out.print("\nPlayer 1 name : ");
        this.player1.setName(this.scanner.nextLine());
        System.out.print("Player 2 name : ");
        this.player2.setName(this.scanner.nextLine());
    }

    /**
     * Turn for one player
     */
    @Override
    public boolean turn(Player player, boolean hasOpponentShotHit) {
        // If there is a winner
        if (this.getWinner() != null) {
            return true;
        }

        System.out.println("========================== New Turn ==========================");

        System.out.printf("Turn of player : %s\n", player.getName());
        Player opponent = this.getOpponent(player);

        // Display player board
        System.out.println(player);

        // The current player have the possibility to move one of his ships
        if (!hasOpponentShotHit) {
            player.requestToMoveShip();
        }

        Coordinate targetCoordinate = player.requestToFire();
        System.out.println("Fire !!!");
        boolean hit;

        hit = player.fire(opponent, targetCoordinate);
        if (hit) {
            System.out.println("You hit one of his ships ! Nice shot !");
        } else {
            System.out.println("You have missed ! You'll do better next time ! ...Unless if u're not dead before.. Ahahah !!!");
        }

        // Refresh player board
        System.out.println(player);

        return hit;
    }

    /**
     * Return the opponent's player
     *
     * @param player - Player who want to know its opponent
     *
     * @return Player
     */
    private Player getOpponent(Player player) {
        return (player == this.player1) ? this.player2 : this.player1;
    }

    /**
     * Return player who win the game
     *
     * @return Player|null
     */
    private Player getWinner() {
        if (!this.player1.hasFleet()) {
            this.stop();
            return this.player2;
        }
        if (!this.player2.hasFleet()) {
            this.stop();
            return this.player1;
        }

        return null;
    }

    /**
     * Display winner of the game
     */
    @Override
    public void displayWinner() {
        Player winner = this.getWinner();
        if (winner != null) {
            System.out.println("====================================");
            System.out.println("======== We have a winner !!!========\n");
            System.out.println("           " + winner.getName() + " !!!");
        }
    }

    @Override
    public void start() {
        this.endGame = false;
        this.game();
    }

    @Override
    public void stop() {
        this.endGame = true;
    }

    @Override
    public boolean isEndGame() {
        return this.endGame;
    }
}
