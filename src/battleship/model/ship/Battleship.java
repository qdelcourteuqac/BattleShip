package battleship.model.ship;

import java.util.Random;

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
}
