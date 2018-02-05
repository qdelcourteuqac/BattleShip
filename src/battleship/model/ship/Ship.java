package battleship.model.ship;

public abstract class Ship {

    public enum Orientation {
        VERTICAL, HORIZONTAL
    }

    public abstract int getSize();
    public abstract int getRange();

    protected Ship.Orientation orientation;
    protected boolean isAlive;
    protected int hit;

    public Ship(Orientation orientation) {
        this.orientation = orientation;
        this.isAlive = true;
        this.hit = 0;
    }

    public boolean isVertical() {
        return this.orientation == Orientation.VERTICAL;
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Return true if hit, false if dead
     *
     * @return boolean
     */
    public boolean hit() {
        if (!this.isAlive()) {
            return false;
        }

        this.hit++;
        if (this.hit >= 3) {
            this.isAlive = false;
        }

        return true;
    }

    /**
     * Get life
     *
     * @return int
     */
    public int getHit() {
        return this.hit;
    }
}
