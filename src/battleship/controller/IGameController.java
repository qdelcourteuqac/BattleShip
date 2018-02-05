package battleship.controller;

public interface IGameController {
    void game() throws Exception;
    void start() throws Exception;
    void stop();
    boolean isFinished();
}
