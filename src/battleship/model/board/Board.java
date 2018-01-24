package battleship.model.board;

import battleship.model.ship.Ship;

import java.util.ArrayList;

public class Board {

    private static int HEIGHT = 10;
    private static int WIDTH = 10;


    private Cell[][] cells;

    public Board() {
        this.cells = new Cell[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.eraseCell(x, y);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder boardRepresentation = new StringBuilder();

        for (int y = HEIGHT - 1; y >= 0; y--) {
            boardRepresentation.append("\n");
            for (int x = 0; x < WIDTH; x++) {
                Cell cell = this.getCell(x, y);
                boardRepresentation.append(cell);
            }
        }
        return boardRepresentation.toString();
    }


    public Cell getCell(int x, int y) {
        return this.cells[x][y];
    }

    public void addShip(Ship ship, int x, int y, boolean isVertical) {
        for (int part = 0; part < ship.getSize(); part++) {
            Cell cell = new Cell(ship);
            int xCell = isVertical ? x : x + part;
            int yCell = isVertical ? y + part : y;
            try {
                this.addCell(cell, xCell, yCell);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addCell(Cell cell, int x, int y) throws Exception {
        Cell previousCell = this.cells[x][y];

        if (previousCell.getShip() != null) {
            throw new Exception("This case contain a ship !");
        }

        this.cells[x][y] = cell;
    }

    public void moveShip(Ship ship, int xOffset, int yOffset) {
        if (xOffset + yOffset > 2) {
            throw new IllegalArgumentException("You can only two cell mooving !");
        }

        ArrayList<Cell> cells = this.getShipCells(ship);
        cells.forEach(cell -> {
            int[] coordinatesToMove = this.getCellCoordinate(cell);
            this.moveCell(cell, coordinatesToMove[0], coordinatesToMove[1]);
        });

    }

    private ArrayList<Cell> getShipCells(Ship ship) {
        ArrayList<Cell> cells = new ArrayList<>(ship.getSize());
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                cells.add(this.getCell(x, y));
            }
        }

        return cells;
    }

    private void eraseCell(int x, int y) {
        this.cells[x][y] = new Cell(null);
    }

    private int[] getCellCoordinate(Cell cell) {
        int[] coordinates = new int[2];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (this.getCell(x, y) == cell) {
                    coordinates[0] = x;
                    coordinates[1] = y;
                }
            }
        }
        return coordinates;
    }

    private void moveCell(Cell cell, int toX, int toY) {
        int[] coordinates = this.getCellCoordinate(cell);
        int fromX = coordinates[0];
        int fromY = coordinates[1];

        System.out.printf("Mooving from %s:%s, to %s:%s.\n", fromX, fromY, toX, toY);
        this.eraseCell(fromX, fromY);
        try {
            this.addCell(cell, toX, toY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
