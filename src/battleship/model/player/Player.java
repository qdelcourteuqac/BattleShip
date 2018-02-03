package battleship.model.player;

import battleship.model.board.BoardController;

public abstract class Player {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract BoardController getBoardController();

    /**
     * Return false if all of his ships are destroyed
     *
     * @return boolean
     */
    public abstract boolean hasFleet();

    @Override
    public String toString() {
        return this.getBoardController().toString();
    }
}
