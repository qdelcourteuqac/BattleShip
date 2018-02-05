package battleship.model.board;

import battleship.utils.Coordinate;

public abstract class Board {

    public static int HEIGHT = 10;
    public static int WIDTH = 10;

    protected Cell[][] cells;

    public Board() {
        this.cells = new Cell[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                this.clearCell(coordinate);
            }
        }
    }

    /**
     * Input format : from A1 to J10
     * where 10 is the max value in x
     *
     * @param key - Cell key
     */
    public static Coordinate getCoordinate(String key) {
        if (!key.matches("^[A-J]([1-9]|10)$")) {
            throw new IllegalArgumentException(String.format("The matching key \"%s\" doesn't exists", key));
        }

        // Get x and y for cell array
        int x = Integer.parseInt(key.substring(1, key.length())) - 1;
        int y = WIDTH - (Character.getNumericValue(key.charAt(0)) - Character.getNumericValue('A')) - 1;

        return new Coordinate(x, y);
    }

    public Cell getCell(Coordinate coordinate) {
        return this.cells[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Clear cell by initialize a new Cell in it
     *
     * @param coordinate - Coordinate of cell to clear
     */
    protected void clearCell(Coordinate coordinate) {
        this.cells[coordinate.getX()][coordinate.getY()] = new Cell(null, coordinate);
    }

    /**
     * Add cell
     *
     * @param cell - Cell
     * @throws Exception
     */
    protected void addCell(Cell cell) throws Exception {
        if (this.cells[cell.getX()][cell.getY()].getObject() != null) {
            throw new Exception("This cell contains an object ! ");
        }

        this.cells[cell.getX()][cell.getY()] = cell;
    }

    /**
     * Move cell to X, Y position on the board
     *
     * @param cell - Cell to move
     * @param toX  - To X
     * @param toY  - To Y
     * @throws Exception
     */
    protected void moveCell(Cell cell, int toX, int toY) throws Exception {
        if (this.cells[toX][toY].getObject() != null) {
            throw new Exception("This cell contains an object ! ");
        }

        int fromX = cell.getX();
        int fromY = cell.getY();

        this.cells[cell.getX()][cell.getY()] = new Cell(cell.getObject(), new Coordinate(toX, toY));

        System.out.printf("Moving from %s:%s, to %s:%s.\n", fromX, fromY, toX, toY);
        Coordinate coordinate = new Coordinate(fromX, fromY);
        this.clearCell(coordinate);
    }

    @Override
    public String toString() {
        StringBuilder boardRepresentation = new StringBuilder();

        boardRepresentation.append("\n  |");
        for (int x = 0; x < WIDTH; x++) {
            boardRepresentation.append(String.format(" %d ", x + 1));
        }
        boardRepresentation.append("|\n  |");
        for (int x = 0; x < WIDTH; x++) {
            boardRepresentation.append("---");
        }
        boardRepresentation.append("|");

        char startLetter = 'A';
        for (int y = HEIGHT - 1; y >= 0; y--) {
            boardRepresentation.append("\n");
            boardRepresentation.append(String.format("%s |", startLetter++));
            for (int x = 0; x < WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Cell cell = this.getCell(coordinate);

                boardRepresentation.append(cell);
            }
            boardRepresentation.append("|");
        }

        boardRepresentation.append("\n  |");
        for (int x = 0; x < WIDTH; x++) {
            boardRepresentation.append("---");
        }
        boardRepresentation.append("|");

        return boardRepresentation.toString();
    }
}
