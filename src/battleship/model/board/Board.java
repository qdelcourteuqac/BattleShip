package battleship.model.board;

import battleship.model.ship.Ship;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public abstract class Board {

    private static int HEIGHT = 10;
    private static int WIDTH = 10;

    private Cell[][] cells;

    public Board() {
        this.cells = new Cell[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.clearCell(x, y);
            }
        }
    }

    private Cell getCell(int x, int y) {
        return this.cells[x][y];
    }

    /**
     * TODO: trouver un meilleur nom de variable
     *
     * Format d'entrée : A1 jusqu'à J10
     * où 10 est la valeur maximale en x
     */
    private Cell getCell(String key) {
        if (!key.matches("^[A-J]([1-9]|10)$")) {
            throw new IllegalArgumentException(String.format("The matching key \"%s\" doesn't exists", key));
        }

        // Get x and y for cell array
        int x = Integer.parseInt(key.substring(1, key.length())) - 1;
        int y = WIDTH - (Character.getNumericValue(key.charAt(0)) - Character.getNumericValue('A')) - 1;

        return this.getCell(x, y);
    }

    private void clearCell(int x, int y) {
        this.cells[x][y] = new Cell(null, x, y);
    }

    private void addCell(Cell cell, int x, int y) throws Exception {
        if (this.cells[x][y].getShip() != null) {
            throw new Exception("This cell contains a ship ! ");
        }

        this.cells[x][y] = cell;
    }

    private void moveCell(Cell cell, int toX, int toY) throws Exception {
        int fromX = cell.getX();
        int fromY = cell.getY();

        this.addCell(cell, toX, toY);
        System.out.printf("Moving from %s:%s, to %s:%s.\n", fromX, fromY, toX, toY);
        this.clearCell(fromX, fromY);
    }

    public void placeShip(Ship ship, int x, int y, boolean isVertical) {
        for (int part = 0; part < ship.getSize(); part++) {
            try {
                int xCell = isVertical ? x : x + part;
                int yCell = isVertical ? y + part : y;
                Cell cell = new Cell(ship, xCell, yCell);
                this.addCell(cell, xCell, yCell);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void translateShip(Ship ship, int xOffset, int yOffset) {
        if (xOffset + yOffset > 2) {
            throw new IllegalArgumentException("You can only perform maximum two cell moving !");
        }

        if (this.canTranslateShip(ship, xOffset, yOffset)) {
            Cell[] cellsToTranslate = this.getCellsToTranslateForShip(ship);
            try {
                for(Cell cellToTranslate: cellsToTranslate) {
                    this.moveCell(cellToTranslate, cellToTranslate.getX() + xOffset, cellToTranslate.getY() + yOffset);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(String.format("Impossible to move ship %s with offset : %d:%d", ship.getClass().getSimpleName(), xOffset, yOffset));
        }
    }

    private boolean canTranslateShip(Ship ship, int xOffset, int yOffset) {
        Cell[] cells = this.getCellsToTranslateForShip(ship);
        boolean canTranslate = true;

        for (Cell cellToTranslate : cells) {
            Cell boardCell = this.cells[cellToTranslate.getX() + xOffset][cellToTranslate.getY() + yOffset];
            if (boardCell.getShip() != null && !boardCell.getShip().equals(ship)) {
                canTranslate = false;
                break;
            }
        }

        return canTranslate;
    }

    private Cell[] getCellsToTranslateForShip(Ship ship) {
        Cell[] cells = new Cell[ship.getSize()];
        int index = 0;

        for (int y = HEIGHT - 1; y >= 0; y--) {
            for (int x = 0; x < WIDTH; x++) {
                Cell cell = this.getCell(x, y);
                if (null != cell.getShip() && cell.getShip().equals(ship)) {
                    cells[index] = new Cell(ship, cell.getX(), cell.getY());
                    index++;
                }
            }
        }

        return cells;
    }

    @Override
    public String toString() {
        StringBuilder boardRepresentation = new StringBuilder();

        boardRepresentation.append("\n");
        boardRepresentation.append("  |");
        for (int x = 0; x < WIDTH; x++) {
            boardRepresentation.append(String.format(" %d ", x+1));
        }
        boardRepresentation.append("|");

        boardRepresentation.append("\n");
        boardRepresentation.append("  |");
        for (int x = 0; x < WIDTH; x++) {
            boardRepresentation.append("---");
        }
        boardRepresentation.append("|");

        char startLetter = 'A';
        for (int y = HEIGHT - 1; y >= 0; y--) {
            boardRepresentation.append("\n");
            boardRepresentation.append(String.format("%s |", startLetter++));
            for (int x = 0; x < WIDTH; x++) {
                Cell cell = this.getCell(x, y);
                boardRepresentation.append(cell);
            }
            boardRepresentation.append("|");
        }

        boardRepresentation.append("\n");
        boardRepresentation.append("  |");
        for (int x = 0; x < WIDTH; x++) {
            boardRepresentation.append("---");
        }
        boardRepresentation.append("|");

        return boardRepresentation.toString();
    }
}
