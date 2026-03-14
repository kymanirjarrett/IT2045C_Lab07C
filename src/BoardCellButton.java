import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

/**
 * A JButton representing one board cell.
 */
public class BoardCellButton extends JButton {
    private final int row;
    private final int col;

    public BoardCellButton(int row, int col) {
        this.row = row;
        this.col = col;

        setFont(new Font("SansSerif", Font.BOLD, 20));
        setBackground(new Color(173, 216, 230)); // light blue
        setOpaque(true);
        setBorderPainted(true);
        setText("~");
        setFocusPainted(false);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void showHit() {
        setText("X");
        setForeground(Color.RED);
    }

    public void showMiss() {
        setText("M");
        setForeground(Color.YELLOW.darker());
    }

    public void resetDisplay() {
        setText("~");
        setForeground(Color.BLACK);
        setBackground(new Color(173, 216, 230));
        setEnabled(true);
    }
}