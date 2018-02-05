package battleship.model.ship;

/**
 * Croiseur
 */
public class Cruiser extends Ship {
    public final static int SIZE = 4;
    public final static int RANGE = 2;

    public Cruiser(Orientation orientation) {
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
        return "C";
    }
}
