package battleship.model.board;

import battleship.model.player.Player;
import battleship.model.ship.*;

public class BoardController {

    private PersonalBoard personalBoard;
    private TacticalBoard tacticalBoard;

    public BoardController() {
        this.personalBoard = new PersonalBoard();
        this.tacticalBoard = new TacticalBoard();

        Battleship battleship = new Battleship();
        Carrier carrier = new Carrier();
        Destroyer destroyer = new Destroyer();
        Cruiser cruiser = new Cruiser();

        this.personalBoard.placeShip(battleship, new Coordinate(0, 9), false);
        this.personalBoard.placeShip(carrier, new Coordinate(0, 0), true);
        this.personalBoard.placeShip(destroyer, new Coordinate(2, 0), false);
        this.personalBoard.placeShip(cruiser, new Coordinate(3, 1), true);

        try {
            this.personalBoard.translateShip(carrier, 1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public TacticalBoard getTacticalBoard() {
        return this.tacticalBoard;
    }

    public Fleet getFleet() {
        return this.getPersonalBoard().getFleet();
    }

    /**
     * Fire on opponent board
     *
     * @param opponent - Player to attack
     * @param targetCell - Target cell in opponent board
     * @return boolean - Return false if missed, true if hit
     */
    public boolean fire(Player opponent, String targetCell) {
        if (!this.getPersonalBoard().canFireAt(targetCell)) {
            return false;
        }

        Cell cell = opponent.getBoardController().getPersonalBoard().getCell(targetCell);
        if (cell.getShip() == null) {
            this.getTacticalBoard().missedFlag(targetCell);
            return false;
        }

        opponent.getBoardController().getPersonalBoard().hitShip(cell.getShip());
        this.getTacticalBoard().hitFlag(targetCell);

        /**
         * TODO:Lors d'un hit d'un bateau vérifier si le bateau est encore vivant, si oui alors tranquille
         * (on lui enleve 1 pt de vie), si non (il est mort) bah on ne peut plus tirer dessus et son range d'action
         * n'est plus prit en compte lors d'un tir
         */
        return true;
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
