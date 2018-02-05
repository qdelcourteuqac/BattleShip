package battleship.model.player;

import battleship.model.board.*;
import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

    private PersonalBoard personalBoard;
    private TacticalBoard tacticalBoard;

    public HumanPlayer() {
        this.personalBoard = new PersonalBoard();
        this.tacticalBoard = new TacticalBoard();
    }

    @Override
    public void placeShip(Ship ship, Coordinate targetCoordinate, boolean isVertical) throws Exception {
        this.personalBoard.placeShip(ship, targetCoordinate, isVertical);
    }

    /**
     * Fire on opponent board
     *
     * @param opponent   - Player to attack
     * @param targetCell - Target cell in opponent board
     * @return boolean - Return false if missed, true if hit
     */
    @Override
    public boolean fire(Player opponent, Coordinate targetCell) {
        if (!this.personalBoard.canFireAt(targetCell)) {
            return false;
        }

        Cell cell = opponent.getPersonalBoard().getCell(targetCell);
        if (cell.getShip() == null) {
            this.getTacticalBoard().missedFlag(targetCell);
            return false;
        }

        opponent.getPersonalBoard().hitShip(cell.getShip());
        this.getTacticalBoard().hitFlag(targetCell);

        return true;
    }

    @Override
    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    @Override
    public TacticalBoard getTacticalBoard() {
        return this.tacticalBoard;
    }

    @Override
    public Coordinate requestToFire() {
        System.out.print("\nType the target cell to fire (i.e : I3) : ");
        String targetCell = (new Scanner(System.in)).nextLine();
        return Board.getCoordinate(targetCell);
    }

    @Override
    public void requestToMoveShip() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your opponent have missed his shot.");
        System.out.print("Do you want move one of your ships ? (y/n) : ");

        String response = scanner.nextLine();

        if (!response.equals("y")) {
            return;
        }

        System.out.println("Your fleet : ");

        ArrayList<Ship> ships = this.getFleet().getShips();
        for (int i = 0; i < ships.size(); i++) {
            System.out.printf("%d) %s\n", i, ships.get(i).toString());
        }

        int index;
        do {
            System.out.printf("Which one do you want to move ? [0;%d]", ships.size() - 1);
            index = Integer.parseInt(scanner.nextLine());
        } while (index < 0 || index >= ships.size());

        Ship shipToMove = ships.get(index);

        System.out.println("Enter offset (i.e : 1 1) : ");
        String offsetsResponse = scanner.nextLine();
        System.out.println(offsetsResponse);

        String[] offsets = offsetsResponse.split(" ");
        int x = Integer.parseInt(offsets[0]);
        int y = Integer.parseInt(offsets[1]);
        Coordinate offsetCoordinate = new Coordinate(x, y);

        this.getPersonalBoard().translateShip(shipToMove, offsetCoordinate);

    }

    @Override
    public boolean hasFleet() {
        return this.getFleet().isEmpty();
    }

    @Override
    public Fleet getFleet() {
        return this.personalBoard.getFleet();
    }

    @Override
    public String toString() {
        return "\n\n========== Tactical Board ==========\n" +
                this.getTacticalBoard() +
                "\n\n====================================\n" +
                "\n\n========== Personal Board ==========\n" +
                this.getPersonalBoard() +
                "\n\n====================================\n";
    }

}