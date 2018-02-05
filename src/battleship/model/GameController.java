package battleship.model;

import battleship.model.board.Board;
import battleship.model.board.Coordinate;
import battleship.model.player.HumanPlayer;
import battleship.model.player.IAPlayer;
import battleship.model.player.Player;
import battleship.model.ship.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameController implements IGameController {

    private Scanner scanner;

    private boolean finish;

    private Player player1;
    private Player player2;


    public GameController() {
        this.scanner = new Scanner(System.in);
        this.finish = false;

        this.player1 = new IAPlayer();
        this.player2 = new HumanPlayer();

        this.player1.setName("Ramzi");
        this.player2.setName("Quentin");
    }

    /**
     * Game Logic loop
     */
    private void game() throws Exception {
//        this.initPlayers();

//        this.initFleet();
        this.initFleetMock();

        boolean shotHit = true;

        // Tant que la partie n'est pas finie
        while (!this.isFinished()) {
            shotHit = this.turn(this.player1, shotHit);
            shotHit = this.turn(this.player2, shotHit);
        }
    }

    /**
     * Init Phase : set player's name
     */
    private void initPlayers() {
        System.out.print("\nPlayer 1 name : ");
        this.player1.setName(this.scanner.nextLine());
        System.out.print("Player 2 name : ");
        this.player2.setName(this.scanner.nextLine());
    }

    private void initFleetMock() throws Exception {
        Player[] players = new Player[]{this.player1, this.player2};

        for (Player player : players) {

            Battleship battleship = Battleship.class.newInstance();
            Carrier carrier = Carrier.class.newInstance();
            Destroyer destroyer = Destroyer.class.newInstance();
            Cruiser cruiser = Cruiser.class.newInstance();

            // TODO Is a valid targetCell ?
            // TODO ArrayOfBoundException throwed
            player.placeShip(battleship, new Coordinate(0, 0), true);
            player.placeShip(carrier, new Coordinate(3, 3), true);
            player.placeShip(destroyer, new Coordinate(5, 5), false);
            player.placeShip(cruiser, new Coordinate(4, 8), false);
        }
    }


    /**
     * Init Phase : place fleet on player's board
     */
    private void initFleet() throws Exception {
        System.out.println("\nWelcome in ship placement system !");

        Player[] players = new Player[]{this.player1, this.player2};

        Class[] ships = new Class[]{Battleship.class, Carrier.class, Destroyer.class, Cruiser.class};

        for (Player player : players) {
            System.out.printf("%s it's to you to choose !\n", player.getName());
            for (Class shipClass : ships) {
                System.out.println(player);
                Ship ship = (Ship) shipClass.newInstance();

                System.out.printf("Place a %s(%d) in (i.e: J2) : ", shipClass.getSimpleName(), ship.getSize());

                Coordinate targetCoordinate = Board.getCoordinate(this.scanner.nextLine());

                System.out.println("In vertical (y/n) : ");
                String response = this.scanner.nextLine();

                boolean isVertical = false;
                if (response.equals("y")) {
                    isVertical = true;
                }
                // TODO Is a valid targetCell ?
                // TODO ArrayOfBoundException throwed
                player.placeShip(ship, targetCoordinate, isVertical);
            }
        }
    }

    /**
     * Turn for one player
     */
    private boolean turn(Player player, boolean hasOpponentShotHit) throws Exception {
        System.out.println("========================== New Turn ==========================");

        System.out.printf("Turn of player : %s\n", player.getName());
        Player opponent = this.getOpponent(player);

        // Display player board
        System.out.println(player);

        // The current player have the possibility to move one of his ships
        if (!hasOpponentShotHit) {
            player.requestToMoveShip();
        }

        // TODO: revoir (peut etre) les messages dans la console

        Coordinate targetCoordinate = player.requestToFire();

        System.out.println("Fire on this piece of shit !!!");

        boolean hit;

//        Thread.sleep(100);
        hit = player.fire(opponent, targetCoordinate);
        if (hit) {
            System.out.println("You hit one of his ships ! Nice shot !");
        } else {
            System.out.println("You missed ! You'll do better next time ! ...Unless if u're not dead before.. Ahahah !!!");
        }
//        Thread.sleep(400);

        // Refresh player board
        System.out.println(player);

        // Does the opponent lose the game ?
        if (!opponent.hasFleet()) {
            this.stop();
        }
        return hit;
    }

    private Player getOpponent(Player player) {
        return (player == this.player1) ? this.player2 : this.player1;
    }

    @Override
    public void start() throws Exception {
        this.finish = false;
        this.game();
    }

    @Override
    public void stop() {
        this.finish = true;
    }

    @Override
    public boolean isFinished() {
        return this.finish;
    }
}
