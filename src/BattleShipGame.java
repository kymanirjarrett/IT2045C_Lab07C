/**
 * Controls Battleship game rules and counters.
 */
public class BattleShipGame {
    private final BattleShipBoard board;

    private int missCount;
    private int strikeCount;
    private int totalMissCount;
    private int totalHitCount;

    private String lastSunkShipName = "";

    public BattleShipGame() {
        board = new BattleShipBoard();
        reset();
    }

    public void reset() {
        board.reset();
        missCount = 0;
        strikeCount = 0;
        totalMissCount = 0;
        totalHitCount = 0;
        lastSunkShipName = "";
    }

    public BattleShipBoard getBoard() {
        return board;
    }

    public int getMissCount() {
        return missCount;
    }

    public int getStrikeCount() {
        return strikeCount;
    }

    public int getTotalMissCount() {
        return totalMissCount;
    }

    public int getTotalHitCount() {
        return totalHitCount;
    }

    public String getLastSunkShipName() {
        return lastSunkShipName;
    }

    public ShotResult processShot(int row, int col) {
        board.markShot(row, col);

        if (board.isHit(row, col)) {
            Ship ship = board.getShipAt(row, col);
            ship.registerHit();

            totalHitCount++;
            missCount = 0;

            if (ship.isSunk()) {
                lastSunkShipName = ship.getName();

                if (totalHitCount == 17) {
                    return ShotResult.WIN;
                }
                return ShotResult.SUNK;
            }

            return ShotResult.HIT;
        } else {
            missCount++;
            totalMissCount++;

            if (missCount == 5) {
                strikeCount++;
                missCount = 0;
            }

            if (strikeCount == 3) {
                return ShotResult.LOSS;
            }

            return ShotResult.MISS;
        }
    }
}