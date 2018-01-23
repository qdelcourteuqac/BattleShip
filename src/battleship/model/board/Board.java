package battleship.model.board;

public class Board {

    private static int HEIGHT = 10;
    private static int WIDTH = 10;


    private Cell[][] cells;

    public Board() {
        this.cells = new Cell[WIDTH][HEIGHT];

    }

    @Override
    public String toString() {
        StringBuilder boardRepresentation = new StringBuilder();

        for (int width = 0; width < WIDTH; width++) {
            for (int height = 0; height < HEIGHT; height++) {
                Cell cell = this.getCell(width, height);
                boardRepresentation.append(cell);
            }
        }
        return boardRepresentation.toString();
    }


    public Cell getCell(int widht, int height) {
        return this.cells[widht][height];
    }
}
