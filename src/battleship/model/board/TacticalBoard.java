package battleship.model.board;

public class TacticalBoard extends Board {

    @Override
    public String toString() {
        StringBuilder tacticalBoardRepresentation = new StringBuilder();

        tacticalBoardRepresentation.append(super.toString());

        return tacticalBoardRepresentation.toString();
    }
}
