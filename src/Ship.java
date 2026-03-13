/**
 * Represents a ship in Battleship.
 */
public class Ship {
    private final String name;
    private final int length;
    private int hits;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.hits = 0;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public void registerHit() {
        hits++;
    }

    public boolean isSunk() {
        return hits >= length;
    }
}