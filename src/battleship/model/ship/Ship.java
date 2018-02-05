package battleship.model.ship;

public abstract class Ship {

    public abstract int getSize();
    public abstract int getRange();

    protected boolean isVertical;
    public boolean isAlive;

    protected int hited;

    public Ship() {
        this.hited = 0;
        this.isAlive = true;
        this.isVertical = false;
    }

    public void setOrientation(boolean isVertical) {
        this.isVertical = isVertical;
    }

    public boolean isVertical() {
        return this.isVertical;
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

        this.hited++;
        if (this.hited >= 3) {
            this.isAlive = false;
        }

        return true;
    }

    public int getHited() {
        return this.hited;
    }
}
