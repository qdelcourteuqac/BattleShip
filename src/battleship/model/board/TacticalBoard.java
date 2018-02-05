package battleship.model.board;

import battleship.utils.Coordinate;

public class TacticalBoard extends Board {

    public TacticalBoard() {
        this.cells = new Cell[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.cells[x][y] = new Cell(new Flag(), new Coordinate(x, y));
            }
        }
    }

    public Flag getFlag(Coordinate targetCoordinate) {
        return this.getCell(targetCoordinate).getFlag();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
