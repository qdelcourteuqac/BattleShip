package battleship.model.ship;

/**
 * Sous-marin
 */
public class Submarine extends Ship {
    public final static int SIZE = 3;
    public final static int RANGE = 4;

    public Submarine(Orientation orientation) {
        super(orientation);
    }

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
        return "S";
    }
}
