package battleship.model.board;

public class Flag {

    public final static String NONE = "none";
    public final static String MISSED = "missed";
    public final static String HIT = "hit";

    private String state;

    public Flag() {
        this.state = NONE;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        switch (this.state) {
            case NONE:
                return "*";
            case MISSED:
                return "M";
            case HIT:
                return "H";
        }
        return "0";
    }
}
