package battleship.model.player;

import battleship.exception.*;
import battleship.model.board.Board;
import battleship.model.ship.*;
import battleship.utils.Coordinate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

    @Override
    public void initializeFleet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome in ship placement system !");

        Class[] ships = new Class[]{Battleship.class, Carrier.class, Destroyer.class, Cruiser.class};

        System.out.printf("%s it's up to you to choose !\n", this.getName());
        for (Class shipClass : ships) {
            boolean shipPlaced = false;
            while (!shipPlaced) {
                try {
                    System.out.println(this);

                    System.out.println(shipClass.getSimpleName());
                    System.out.println("In vertical (y/n) : ");
                    String response = scanner.nextLine();
                    Ship.Orientation orientation = response.equals("y") ? Ship.Orientation.VERTICAL : Ship.Orientation.HORIZONTAL;

                    Ship ship = (Ship) shipClass.getConstructor(Ship.Orientation.class).newInstance(orientation);

                    System.out.printf("Place a %s(%d) in (i.e: J2) : ", shipClass.getSimpleName(), ship.getSize());

                    Coordinate targetCoordinate = Board.getCoordinate(scanner.nextLine());

                    this.placeShip(ship, targetCoordinate);
                    shipPlaced = true;
                } catch (ShipOutOfBoardException e) {
                    System.out.println("You cannot place your ship here : is getting out the board");
                } catch (CoordinateOutOfBoardException e) {
                    System.out.println("You cannot place your ship here : coordinate are out the board");
                } catch (CellNotEmptyException e) {
                    System.out.println("You cannot place your ship here : it's make a collision");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        scanner.close();
    }

    @Override
    public Coordinate requestToFire() {
        System.out.print("\nType the target cell to fire (i.e : I3) : ");
        String targetCell = (new Scanner(System.in)).nextLine();
        return Board.getCoordinate(targetCell);
    }

    @Override
    public void requestToMoveShip() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your opponent have missed his shot.");
        System.out.print("Do you want move one of your ships ? (y/n) : ");

        String response = scanner.nextLine();

        if (!response.equals("y")) {
            return;
        }

        boolean shipMoved = false;
        while (!shipMoved) {
            System.out.println("Your fleet : ");

            ArrayList<Ship> ships = this.getFleet().getShips();
            for (int i = 0; i < ships.size(); i++) {
                System.out.printf("%d) %s\n", i, ships.get(i).toString());
            }

            int index;
            do {
                System.out.printf("Which one do you want to move ? [0;%d] : ", ships.size() - 1);
                index = Integer.parseInt(scanner.nextLine());
            } while (index < 0 || index >= ships.size());

            Ship shipToMove = ships.get(index);

            System.out.println("Enter offset (i.e : 1 1) : ");
            String offsetsResponse = scanner.nextLine();

            String[] offsets = offsetsResponse.split(" ");
            int x = Integer.parseInt(offsets[0]);
            int y = Integer.parseInt(offsets[1]);
            Coordinate offsetCoordinate = new Coordinate(x, y);

            try {
                this.getPersonalBoard().translateShip(shipToMove, offsetCoordinate);
                shipMoved = true;
            } catch (ShipOutOfBoardException e) {
                System.out.println("You cannot place your ship here : is getting out the board");
            } catch (CoordinateOutOfBoardException e) {
                System.out.println("You cannot place your ship here : coordinate are out the board");
            } catch (CellNotEmptyException e) {
                System.out.println("You cannot place your ship here : it's make a collision");
            } catch (LimitTranslationException | NullTranslationException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(this);
    }
}