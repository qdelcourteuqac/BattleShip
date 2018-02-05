package battleship.model;

public interface IGameController {
    public void start() throws Exception;
    public void stop();
    public boolean isFinished();
}
