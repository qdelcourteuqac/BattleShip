//package battleship.model.player;
//
//import battleship.model.board.BoardController;
//import battleship.model.board.PersonalBoard;
//import battleship.model.board.TacticalBoard;
//import battleship.model.ship.Fleet;
//import battleship.model.ship.Ship;
//
//public class IAPlayer extends Player {
//    private BoardController boardController;
//
//    public IAPlayer() {
//        this.boardController = new BoardController();
//    }
//
//    @Override
//    public BoardController getBoardController() {
//        return this.boardController;
//    }
//
//    @Override
//    public void placeShip(Ship ship, String targetCell, boolean isVertical) {
//
//    }
//
//    @Override
//    public boolean hasFleet() {
//        return this.boardController.getFleet().isEmpty();
//    }
//
//    @Override
//    public Fleet getFleet() {
//        return null;
//    }
//
//    @Override
//    public boolean fire(Player opponent, String targetCell) {
//        return false;
//    }
//
//    @Override
//    public PersonalBoard getPersonalBoard() {
//        return null;
//    }
//
//    @Override
//    public TacticalBoard getTacticalBoard() {
//        return null;
//    }
//}
