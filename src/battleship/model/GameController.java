package battleship.model;

import battleship.model.player.HumanPlayer;
import battleship.model.player.Player;
import battleship.model.ship.*;

import java.util.ArrayList;
import java.util.Scanner;

public class GameController implements IGameController {

    private Scanner scanner = new Scanner(System.in);

    private boolean finish;

    private Player player1;
    private Player player2;


    public GameController() {
        this.finish = false;

        this.player1 = new HumanPlayer();
        this.player2 = new HumanPlayer();

        this.player1.setName("Player 1");
        this.player2.setName("Player 2");
    }

    /**
     * Game Logic loop
     */
    private void game() {
        //Pas obligatoire
        //this.initPlayers();

        this.initFleet();

        boolean shotHit = true;

        // Tant que la partie n'est pas finie
        while(!this.isFinished()) {
            shotHit = this.turn(this.player1, shotHit);
            shotHit = this.turn(this.player2, shotHit);
            this.stop();
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

    /**
     * Init Phase : place fleet on player's board
     */
    private void initFleet() {
        System.out.println("\nWelcome in ship placement system !");

        Player[] players = new Player[]{this.player1, this.player2};

        Class[] ships = new Class[]{Battleship.class, Carrier.class, Destroyer.class, Cruiser.class};

        for (Player player : players) {
            System.out.printf("%s it's to you to choose !\n", player.getName());
            for (Class shipClass : ships) {
                System.out.println(player);
                try {
                    Ship ship = (Ship) shipClass.newInstance();

                    System.out.printf("Place a %s(%d) in (i.e: J2) : ", shipClass.getSimpleName(), ship.getSize());
                    String targetCell = this.scanner.nextLine();
                    System.out.println("In vertical (y/n) : ");
                    String response = this.scanner.nextLine();
                    boolean isVertical = false;
                    if (response.equals("y")) {
                        isVertical = true;
                    }
                    player.getBoardController().getPersonalBoard().placeShip(ship, targetCell, isVertical);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Turn for one player
     */
    private boolean turn(Player player, boolean hasOpponentShotHit) {
        System.out.printf("Turn of player : %s\n", player.getName());
        Player opponent = this.getOpponent(player);

        // Display player board
        System.out.println(player);

        if (!hasOpponentShotHit) {
            // The current player have the possibility to move one of his ships
            // TODO: terminer cette étape
            System.out.println("Your opponent have missed his shot.");
            System.out.print("Do you want move one of your ships ? (y/n) : ");
            String response = this.scanner.nextLine();
            if (response.equals("y")) {
                System.out.println("Your fleet : ");
                ArrayList<Ship> ships = player.getBoardController().getFleet().getShips();
                for (int i = 0; i < ships.size(); i++) {
                    System.out.printf("%d) %s\n", i, ships.get(i).toString());
                }
                System.out.println("Which one do you want to move ? : ");
                int index = this.scanner.nextInt();
                if (ships.get(index) != null) {
                    System.out.print("You choose : " + ships.get(index).toString() + ". Are you sure ? (y/n)");
                    String responseShip = this.scanner.nextLine();
                    if (responseShip.equals("y")) {
                        System.out.println("Enter offset (i.e : 1 1) : ");
                        String offsetsResponse = this.scanner.nextLine();
                        System.out.println(offsetsResponse);
                        String[] offsets = offsetsResponse.split(" ");
                        try {
                            player.getBoardController().getPersonalBoard().translateShip(ships.get(index), Integer.parseInt(offsets[0]), Integer.parseInt(offsets[1]));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // TODO: revoir (peut etre) les messages dans la console
        System.out.print("\nType the target cell to fire (i.e : I3) : ");
        String targetCell = this.scanner.nextLine();
        System.out.println("Fire on this piece of shit !!!");

        boolean hit = false;
        try {
            Thread.sleep(2000);
            hit = player.getBoardController().fire(opponent, targetCell);
            if (hit) {
                System.out.println("You hit one of his ships ! Nice shot !");
            } else {
                System.out.println("You missed ! You'll do better next time ! ...Unless if u're not dead before.. Ahahah !!!");
            }
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Refresh player board
        System.out.println(player);

        if (!opponent.hasFleet()) {
            this.stop();
            //Le joueur en cours a alors gagné la partie
        }

        return hit;
    }

    private Player getOpponent(Player player) {
        return (player == this.player1)? this.player2 : this.player1;
    }

    @Override
    public void start() {
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
