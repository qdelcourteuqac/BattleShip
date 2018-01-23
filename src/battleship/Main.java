package battleship;

import battleship.model.board.Board;
import battleship.model.player.HumanPlayer;
import battleship.model.player.Player;

public class Main {
    public static void main(String[] args) {

        Player ramzi = new HumanPlayer();

        Board board = ramzi.getPersonalBoard();

        System.out.println(board);
    }
}
