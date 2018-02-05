package battleship.utils;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Translate coordinate with offsets
     *
     * @param offsetCoordinates - Offsets
     * @return Coordinate
     */
    public Coordinate translate(Coordinate offsetCoordinates) {
        this.x += offsetCoordinates.getX();
        this.y += offsetCoordinates.getY();
        return this;
    }

    /**
     * Return length of Coordinate by simple operation
     *
     * @return int
     */
    public int getLength() {
        return this.x + this.y;
    }

    @Override
    public String toString() {
        return String.format("[X : %d, Y : %d]", this.getX(), this.getY());
    }
}
