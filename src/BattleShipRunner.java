import javax.swing.SwingUtilities;

/**
 * Launches the Battleship GUI.
 */
public class BattleShipRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleShipFrame::new);
    }
}