package battleship.controller;

import battleship.model.player.Player;

public interface IGameController {
    void initializePlayers();
    boolean turn(Player player, boolean hasOpponentShotHit);
    void game();
    void start();
    void stop();
    boolean isEndGame();
    void displayWinner();
}
