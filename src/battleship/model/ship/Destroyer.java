package battleship.model.ship;

/**
 * Contre torpilleur
 */
public class Destroyer extends Ship {
    public final static int SIZE = 4;
    public final static int RANGE = 2;

    public Destroyer(Orientation orientation) {
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
        return "D";
    }
}
