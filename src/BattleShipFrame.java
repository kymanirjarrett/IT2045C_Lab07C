import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing GUI for the Battleship game.
 */
public class BattleShipFrame extends JFrame {

    private final BattleShipGame game = new BattleShipGame();
    private final BoardCellButton[][] cells = new BoardCellButton[10][10];

    private final JLabel missLabel = new JLabel("MISS Counter: 0");
    private final JLabel strikeLabel = new JLabel("STRIKE Counter: 0");
    private final JLabel totalMissLabel = new JLabel("TOTAL MISS Counter: 0");
    private final JLabel totalHitLabel = new JLabel("TOTAL HIT Counter: 0");

    private final JButton playAgainButton = new JButton("Play Again");
    private final JButton quitButton = new JButton("Quit");

    private final ActionListener cellListener = new CellListener();

    public BattleShipFrame() {
        setTitle("Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createBoardPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Battleship", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel statusPanel = new JPanel(new GridLayout(2, 2));
        statusPanel.add(missLabel);
        statusPanel.add(strikeLabel);
        statusPanel.add(totalMissLabel);
        statusPanel.add(totalHitLabel);

        panel.add(statusPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(10, 10));

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                BoardCellButton cell = new BoardCellButton(r, c);
                cell.addActionListener(cellListener);
                cells[r][c] = cell;
                panel.add(cell);
            }
        }

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();

        playAgainButton.addActionListener(e -> promptPlayAgainButton());
        quitButton.addActionListener(e -> promptQuit());

        panel.add(playAgainButton);
        panel.add(quitButton);

        return panel;
    }

    private void updateStatus() {
        missLabel.setText("MISS Counter: " + game.getMissCount());
        strikeLabel.setText("STRIKE Counter: " + game.getStrikeCount());
        totalMissLabel.setText("TOTAL MISS Counter: " + game.getTotalMissCount());
        totalHitLabel.setText("TOTAL HIT Counter: " + game.getTotalHitCount());
    }

    private class CellListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BoardCellButton cell = (BoardCellButton) e.getSource();
            int row = cell.getRow();
            int col = cell.getCol();

            ShotResult result = game.processShot(row, col);
            cell.setEnabled(false);

            if (result == ShotResult.HIT || result == ShotResult.SUNK || result == ShotResult.WIN) {
                cell.showHit();
            } else {
                cell.showMiss();
            }

            updateStatus();

            if (result == ShotResult.SUNK) {
                JOptionPane.showMessageDialog(
                        BattleShipFrame.this,
                        "You sunk the " + game.getLastSunkShipName() + "!"
                );
            } else if (result == ShotResult.WIN) {
                JOptionPane.showMessageDialog(
                        BattleShipFrame.this,
                        "You sunk the " + game.getLastSunkShipName() + "!\nYou win!"
                );
                promptReplayAfterGameEnd();
            } else if (result == ShotResult.LOSS) {
                JOptionPane.showMessageDialog(
                        BattleShipFrame.this,
                        "Three strikes! You lose."
                );
                promptReplayAfterGameEnd();
            }
        }
    }

    private void promptReplayAfterGameEnd() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Play again?",
                "Replay",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        } else {
            System.exit(0);
        }
    }

    private void promptPlayAgainButton() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game?",
                "Play Again",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            startNewGame();
        }
    }

    private void promptQuit() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void startNewGame() {
        game.reset();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                cells[r][c].resetDisplay();
            }
        }

        updateStatus();
    }
}