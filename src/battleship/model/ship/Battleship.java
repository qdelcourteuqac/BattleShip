package battleship.model.ship;

/**
 * Torpilleur
 */
public class Battleship extends Ship {
    public final static int SIZE = 2;
    public final static int RANGE = 5;

    @Override
    public int getSize() {
        return SIZE;
    }

    @Override
    public int getRange() {
        return RANGE;
    }

    @Override
    public String toString() {
        return "B";
    }
}
