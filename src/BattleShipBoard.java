import java.util.ArrayList;
import java.util.Random;

/**
 * Stores ship placement and shot history for the board.
 */
public class BattleShipBoard {
    public static final int SIZE = 10;

    private final Ship[][] shipGrid = new Ship[SIZE][SIZE];
    private final boolean[][] shotGrid = new boolean[SIZE][SIZE];
    private final ArrayList<Ship> ships = new ArrayList<>();
    private final Random rng = new Random();

    public BattleShipBoard() {
        placeShipsRandomly();
    }

    public void reset() {
        ships.clear();

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                shipGrid[r][c] = null;
                shotGrid[r][c] = false;
            }
        }

        placeShipsRandomly();
    }

    public boolean hasBeenShot(int row, int col) {
        return shotGrid[row][col];
    }

    public Ship getShipAt(int row, int col) {
        return shipGrid[row][col];
    }

    public boolean isHit(int row, int col) {
        return shipGrid[row][col] != null;
    }

    public void markShot(int row, int col) {
        shotGrid[row][col] = true;
    }

    private void placeShipsRandomly() {
        placeOneShip("Carrier", 5);
        placeOneShip("Battleship", 4);
        placeOneShip("Cruiser", 3);
        placeOneShip("Submarine", 3);
        placeOneShip("Destroyer", 2);
    }

    private void placeOneShip(String name, int length) {
        boolean placed = false;

        while (!placed) {
            boolean horizontal = rng.nextBoolean();
            int row = rng.nextInt(SIZE);
            int col = rng.nextInt(SIZE);

            if (canPlaceShip(row, col, length, horizontal)) {
                Ship ship = new Ship(name, length);
                ships.add(ship);

                for (int i = 0; i < length; i++) {
                    int r = horizontal ? row : row + i;
                    int c = horizontal ? col + i : col;
                    shipGrid[r][c] = ship;
                }
                placed = true;
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int length, boolean horizontal) {
        if (horizontal) {
            if (col + length > SIZE) return false;
            for (int i = 0; i < length; i++) {
                if (shipGrid[row][col + i] != null) return false;
            }
        } else {
            if (row + length > SIZE) return false;
            for (int i = 0; i < length; i++) {
                if (shipGrid[row + i][col] != null) return false;
            }
        }
        return true;
    }
}