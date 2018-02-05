package battleship.model.board;

import battleship.exception.*;
import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;
import battleship.utils.Coordinate;

public class PersonalBoard extends Board {

    private Fleet fleet;

    public PersonalBoard() {
        super();
        this.fleet = new Fleet();
    }

    public Fleet getFleet() {
        return this.fleet;
    }

    /**
     * Return true if the ship is hit, false if the ship is dead
     * The ship is removed from the fleet if dead
     *
     * @param ship - Target Ship
     * @return boolean
     */
    public boolean hitShip(Ship ship) {
        boolean isHit = this.fleet.getShips().contains(ship) && ship.hit();
        if (!ship.isAlive()) {
            this.fleet.getShips().remove(ship);
            Cell[] cells = this.getCellsOfShip(ship);
            for (Cell cell : cells) {
                this.clearCell(cell.getCoordinate());
            }
        }

        return isHit;
    }

    /**
     * Place ship on the board
     *
     * @param ship       - Ship to place
     * @param coordinate - Coordinate to place the ship
     * @throws CellNotEmptyException
     * @throws CoordinateOutOfBoardException
     * @throws ShipOutOfBoardException
     */
    public void placeShip(Ship ship, Coordinate coordinate) throws CoordinateOutOfBoardException, ShipOutOfBoardException, CellNotEmptyException {
        canPlaceShip(ship, coordinate);

        int x = coordinate.getX();
        int y = coordinate.getY();
        Cell[] cells = new Cell[ship.getSize()];
        for (int part = 0; part < ship.getSize(); part++) {
            int xCell = ship.isVertical() ? x : x + part;
            int yCell = ship.isVertical() ? y + part : y;
            Cell cell = new Cell(ship, new Coordinate(xCell, yCell));
            if (!canAddCell(cell)) {
                throw new CellNotEmptyException();
            }
            cells[part] = cell;
        }

        for (Cell cell : cells) {
            this.addCell(cell);
        }

        this.fleet.addShip(ship);
    }

    /**
     * Verify if a ship can be placed in a required coordinate
     *
     * @param ship - Ship to place
     * @param coordinate - Coordinate to place
     *
     * @return boolean
     *
     * @throws CoordinateOutOfBoardException
     * @throws ShipOutOfBoardException
     */
    private boolean canPlaceShip(Ship ship, Coordinate coordinate) throws CoordinateOutOfBoardException, ShipOutOfBoardException {
        System.out.println("Check in : "+coordinate);
        if (coordinate.getX() < 0 || coordinate.getX() >= WIDTH || coordinate.getY() < 0 || coordinate.getY() >= HEIGHT) {
            throw new CoordinateOutOfBoardException();
        }

        int x = coordinate.getX();
        int y = coordinate.getY();

        if (ship.isVertical()) {
            if ((y + ship.getSize()) > HEIGHT) {
                throw new ShipOutOfBoardException();
            }
        } else {
            if ((x + ship.getSize()) > WIDTH) {
                throw new ShipOutOfBoardException();
            }
        }

        return true;
    }

    /**
     * Translate Ship with offset coordinates
     *
     * @param ship              - Ship to translate
     * @param offsetCoordinates - Offsets coordinate
     * @throws NullTranslationException
     * @throws LimitTranslationException
     * @throws CoordinateOutOfBoardException
     * @throws ShipOutOfBoardException
     * @throws CellNotEmptyException
     */
    public void translateShip(Ship ship, Coordinate offsetCoordinates) throws NullTranslationException, LimitTranslationException, CoordinateOutOfBoardException, ShipOutOfBoardException, CellNotEmptyException {
        if (offsetCoordinates.getX() == 0 && offsetCoordinates.getY() == 0) {
            throw new NullTranslationException();
        }

        if (offsetCoordinates.getLength() > 2) {
            throw new LimitTranslationException();
        }

        Cell[] cellsToTranslate = this.getCellsOfShip(ship);

        // Check if ship can be placed to the required cell
        Coordinate checkCoord = new Coordinate(cellsToTranslate[0].getCoordinate().getX(), cellsToTranslate[0].getCoordinate().getY());
        canPlaceShip(ship, checkCoord.translate(offsetCoordinates));

        // Remove ship from the board
        for (Cell cellToTranslate : cellsToTranslate) {
            this.clearCell(cellToTranslate.getCoordinate());
        }

        try {
            // Verify for each cell that there is not other ship in desired cells
            for (Cell cellToTranslate : cellsToTranslate) {
                if (!this.canAddCell(cellToTranslate)) {
                    throw new CellNotEmptyException();
                }
            }

            // So, let's place the ship in the required position
            for (Cell cellToTranslate : cellsToTranslate) {
                // Update new coordinate of ship
                cellToTranslate.getCoordinate().translate(offsetCoordinates);
                // Add (move) cell to another position
                this.addCell(cellToTranslate);
            }
        } catch (CellNotEmptyException e) {
            // Rollback, if something goes wrong
            for (Cell cellToTranslate : cellsToTranslate) {
                this.addCell(cellToTranslate);
            }
            throw new CellNotEmptyException();
        }
    }

    /**
     * Return array of cell in which the ship is inside
     *
     * @param ship - Ship
     * @return Cell[]
     */
    protected Cell[] getCellsOfShip(Ship ship) {
        Cell[] cells = new Cell[ship.getSize()];
        int index = 0;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Cell cell = this.getCell(coordinate);

                if (null != cell.getShip() && cell.getShip().equals(ship)) {
                    cells[index] = new Cell(ship, new Coordinate(cell.getX(), cell.getY()));
                    index++;
                }
            }
        }

        return cells;
    }

    /**
     * The player can fire at a target only if target cell is in his fire zone
     *
     * @param targetCoordinate - Target coordinate on board
     * @return boolean - Return true if the player can fire at specified cell, false otherwise
     */
    public boolean canFireAt(Coordinate targetCoordinate) {
        boolean[][] matrix = new boolean[WIDTH][HEIGHT];

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Cell cell = this.getCell(new Coordinate(x, y));

                if (cell.getShip() != null) {
                    matrix[x][y] = true;
                    for (int k = 1; k <= cell.getShip().getRange(); k++) {
                        if ((x + k) < WIDTH) {
                            matrix[x + k][y] = true;
                        }
                        if ((x - k) >= 0) {
                            matrix[x - k][y] = true;
                        }
                        if ((y + k) < HEIGHT) {
                            matrix[x][y + k] = true;
                        }
                        if ((y - k) >= 0) {
                            matrix[x][y - k] = true;
                        }
                    }
                }
            }
        }

        return matrix[targetCoordinate.getX()][targetCoordinate.getY()];
    }

    @Override
    public String toString() {
        return super.toString() + this.fleet;
    }
}
