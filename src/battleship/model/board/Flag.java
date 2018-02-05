package battleship.model.board;

public class Flag {

    public enum FlagType {
        NONE,
        MISSED,
        HIT
    }

    private FlagType state;

    Flag() {
        this.state = FlagType.NONE;
    }

    /**
     * Change FlagType of Flag
     *
     * @param state - New state flag
     */
    public void setState(FlagType state) {
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
        throw new IllegalStateException("Illegal type, this should never append !");
    }

}
