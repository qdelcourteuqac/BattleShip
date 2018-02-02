package battleship.model.board;

public class TacticalBoard extends Board {

    public TacticalBoard() {
        this.cells = new Cell[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.cells[x][y] = new Cell(new Flag(), new Coordinate(x, y));
            }
        }
    }

    public void hitFlag(String targetCell) {
        this.getCell(targetCell).getFlag().setState(Flag.HIT);
    }

    public void missedFlag(String targetCell) {
        this.getCell(targetCell).getFlag().setState(Flag.MISSED);
    }

    @Override
    public String toString() {
        StringBuilder tacticalBoardRepresentation = new StringBuilder();

        tacticalBoardRepresentation.append(super.toString());

        return tacticalBoardRepresentation.toString();
    }
}
