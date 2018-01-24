package battleship.model.ship;

/**
 * Porte avion
 */
public class Carrier extends Ship {
    public final static int SIZE = 5;
    public final static int RANGE = 2;

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
        return "C";
    }
}
