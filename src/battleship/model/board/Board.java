package battleship.model.board;

import battleship.model.ship.Ship;

public abstract class Board {

    private static int HEIGHT = 10;
    private static int WIDTH = 10;

    private Cell[][] cells;

    public Board() {
        this.cells = new Cell[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Coordinate coordinate = new Coordinate(x, y);
                this.clearCell(coordinate);
            }
        }
    }

    private Cell getCell(Coordinate $coordinate) {
        return this.cells[$coordinate.getX()][$coordinate.getY()];
    }

    /**
     * TODO: trouver un meilleur nom de variable
     * <p>
     * Format d'entrée : A1 jusqu'à J10
     * où 10 est la valeur maximale en x
     */
    public Cell getCell(String key) {
        if (!key.matches("^[A-J]([1-9]|10)$")) {
            throw new IllegalArgumentException(String.format("The matching key \"%s\" doesn't exists", key));
        }

        // Get x and y for cell array
        int x = Integer.parseInt(key.substring(1, key.length())) - 1;
        int y = WIDTH - (Character.getNumericValue(key.charAt(0)) - Character.getNumericValue('A')) - 1;

        Coordinate coordinate = new Coordinate(x, y);
        return this.getCell(coordinate);
    }

    private void clearCell(Coordinate coordinate) {
        this.cells[coordinate.getX()][coordinate.getY()] = new Cell(null, coordinate.getX(), coordinate.getY());
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
        Coordinate coordinate = new Coordinate(fromX, fromY);
        this.clearCell(coordinate);
    }

    public void placeShip(Ship ship, Coordinate coordinate, boolean isVertical) {
        for (int part = 0; part < ship.getSize(); part++) {
            try {
                int x = coordinate.getX();
                int y = coordinate.getY();

                int xCell = isVertical ? x : x + part;
                int yCell = isVertical ? y + part : y;
                Cell cell = new Cell(ship, xCell, yCell);
                this.addCell(cell, xCell, yCell);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void translateShip(Ship ship, int xOffset, int yOffset) throws Exception {
        if (xOffset + yOffset > 2) {
            throw new IllegalArgumentException("You can only perform maximum two cell moving !");
        }

        if (!this.canTranslateShip(ship, xOffset, yOffset)) {
            throw new Exception(String.format("Impossible to move ship %s with offset : %d:%d", ship.getClass().getSimpleName(), xOffset, yOffset));
        }


        Cell[] cellsToTranslate = this.getCellsOfShip(ship);
        try {
            for (Cell cellToTranslate : cellsToTranslate) {
                this.moveCell(cellToTranslate, cellToTranslate.getX() + xOffset, cellToTranslate.getY() + yOffset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean canTranslateShip(Ship ship, int xOffset, int yOffset) {
        Cell[] cells = this.getCellsOfShip(ship);
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

    private Cell[] getCellsOfShip(Ship ship) {
        Cell[] cells = new Cell[ship.getSize()];
        int index = 0;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Cell cell = this.getCell(coordinate);

                if (null != cell.getShip() && cell.getShip().equals(ship)) {
                    cells[index] = new Cell(ship, cell.getX(), cell.getY());
                    index++;
                }
            }
        }

        return cells;
    }

    /**
     * The player can fire at a target only if this cell is in his fire zone
     *
     * @param targetCell - Target cell on board
     * @return boolean - Return true if the player can fire at specified cell
     */
    public boolean canFireAt(String targetCell) {
        return true;
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
