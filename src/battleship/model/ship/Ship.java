package battleship.model.ship;

public abstract class Ship {


    public abstract int getSize();
    public abstract int getRange();

    public boolean isAlive;

    protected int hited;

    public Ship() {
        this.hited = 0;
        this.isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void hit() {
        this.hited++;
        if (this.hited >= 3) {
            this.isAlive = false;
        }
    }

    public int getHited() {
        return this.hited;
    }
}
