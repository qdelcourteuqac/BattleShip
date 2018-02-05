package battleship.model.board;

import battleship.model.ship.Fleet;
import battleship.model.ship.Ship;
import battleship.utils.Coordinate;

public class PersonalBoard extends Board {

    private Fleet fleet;

    public PersonalBoard() {
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
     *
     * @return boolean
     */
    public boolean hitShip(Ship ship) {
        boolean isHit = this.fleet.getShips().contains(ship) && ship.hit();
        if (!ship.isAlive) {
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
     * @param isVertical - Orientation: Vertical if true, horizontal otherwise
     */
    public void placeShip(Ship ship, Coordinate coordinate, boolean isVertical) throws Exception {
        if (coordinate.getX() < 0 || coordinate.getX() >= WIDTH || coordinate.getY() < 0 || coordinate.getY() >= HEIGHT) {
            throw new Exception("X and Y must be between [0, "+(WIDTH-1)+"]");
        }

        ship.setOrientation(isVertical);
        int x = coordinate.getX();
        int y = coordinate.getY();

        if (ship.isVertical()) {
            if ((y+ship.getSize()) >= HEIGHT) {
                throw new Exception("Dépassement de la grille");
            }
        } else {
            if ((x+ship.getSize()) >= WIDTH) {
                throw new Exception("Dépassement de la grille");
            }
        }

        for (int part = 0; part < ship.getSize(); part++) {
            int xCell = ship.isVertical() ? x : x + part;
            int yCell = ship.isVertical() ? y + part : y;
            Cell cell = new Cell(ship, new Coordinate(xCell, yCell));
            this.addCell(cell);
        }

        this.fleet.addShip(ship);
    }

    /**
     * Translate Ship with offset coordinates
     *
     * @param ship              - Ship to translate
     * @param offsetCoordinates - Offsets coordinate
     * @throws Exception
     */
    public void translateShip(Ship ship, Coordinate offsetCoordinates) throws Exception {

        int xOffset = offsetCoordinates.getX();
        int yOffset = offsetCoordinates.getY();

        if (xOffset + yOffset > 2) {
            throw new IllegalArgumentException("You can only perform maximum two cell moving !");
        }

        if (!this.canTranslateShip(ship, xOffset, yOffset)) {
            throw new Exception(String.format("Impossible to move ship %s with offset : %d:%d", ship.getClass().getSimpleName(), xOffset, yOffset));
        }

        Cell[] cellsToTranslate = this.getCellsOfShip(ship);

        int nbCells = cellsToTranslate.length;
        Coordinate head = new Coordinate(cellsToTranslate[0].getX(), cellsToTranslate[0].getY());
        Coordinate queue = new Coordinate(cellsToTranslate[nbCells-1].getX(), cellsToTranslate[nbCells-1].getY());

        if ((head.getX()+xOffset) < 0 || (head.getX()+xOffset) >= WIDTH
                || (queue.getX()+xOffset) < 0 || (queue.getX()+xOffset) >= WIDTH
                || (head.getY()+yOffset) < 0 || (head.getY()+yOffset) >= HEIGHT
                || (queue.getY()+yOffset) < 0 || (queue.getY()+yOffset) >= HEIGHT
                ) {
            throw new Exception("X and Y must be between [0, "+(WIDTH-1)+"]");
        }

        if (ship.isVertical()) {
            if ((head.getY()+ship.getSize()) >= HEIGHT) {
                throw new Exception("Dépassement de la grille");
            }
        } else {
            if ((head.getX()+ship.getSize()) >= WIDTH) {
                throw new Exception("Dépassement de la grille");
            }
        }

        try {
            for (Cell cellToTranslate : cellsToTranslate) {
                this.moveCell(cellToTranslate, cellToTranslate.getX() + xOffset, cellToTranslate.getY() + yOffset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return true if ship can be translate, false otherwise
     *
     * @param ship    - Ship to translate
     * @param xOffset - Offset X
     * @param yOffset - Offset Y
     * @return boolean
     */
    private boolean canTranslateShip(Ship ship, int xOffset, int yOffset) {
        Cell[] cells = this.getCellsOfShip(ship);

        boolean canTranslate = true;

        // 1st Verification : Search at least one ship in cells in which to translate
        for (Cell cellToTranslate : cells) {
            Cell boardCell = this.cells[cellToTranslate.getX() + xOffset][cellToTranslate.getY() + yOffset];
            if (boardCell.getShip() != null && !boardCell.getShip().equals(ship)) {
                // One cell contains a ship -> can't translate the ship
                canTranslate = false;
                break;
            }
        }

        return canTranslate;
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
     * The player can fire at a target only if this cell is in his fire zone
     *
     * @param targetCoordinate - Target coordinate on board
     * @return boolean - Return true if the player can fire at specified cell
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
