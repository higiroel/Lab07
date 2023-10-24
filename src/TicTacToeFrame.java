import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private JButton[][] buttons;
    private JButton quitButton;
    private boolean xTurn;
    private int moves;
    private boolean gameWon;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(300, 400); // Adjusted height to fit the additional Quit button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the grid of buttons
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                gridPanel.add(buttons[row][col]);
            }
        }

        // Create the Quit button and place it at the bottom
        quitButton = new JButton("Quit");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(quitButton);

        // Set the overall layout
        setLayout(new BorderLayout());
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize game variables
        xTurn = true;
        moves = 0;
        gameWon = false;

        // Add an ActionListener for the buttons
        ButtonListener buttonListener = new ButtonListener();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].addActionListener(buttonListener);
            }
        }

        // Add an ActionListener for the Quit button
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(TicTacToeFrame.this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameWon) {
                return; // Don't allow moves after the game is won
            }

            JButton button = (JButton) e.getSource();
            if (button.getText().equals("")) {
                if (xTurn) {
                    button.setText("X");
                } else {
                    button.setText("O");
                }
                xTurn = !xTurn;
                moves++;
            } else {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Invalid move. Please select an empty square.");
                return; // Continue to wait for a valid move
            }

            if (checkForWin()) {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, (xTurn ? "O" : "X") + " wins!");
                gameWon = true;
            } else {
                if (moves == 9) {
                    JOptionPane.showMessageDialog(TicTacToeFrame.this, "It's a tie!");
                }
            }
        }

        private boolean checkForWin() {
            for (int i = 0; i < 3; i++) {
                // Check rows and columns
                if (buttons[i][0].getText().equals(buttons[i][1].getText()) && buttons[i][0].getText().equals(buttons[i][2].getText())
                        && !buttons[i][0].getText().equals("")) {
                    return true; // Row win
                }
                if (buttons[0][i].getText().equals(buttons[1][i].getText()) && buttons[0][i].getText().equals(buttons[2][i].getText())
                        && !buttons[0][i].getText().equals("")) {
                    return true; // Column win
                }
            }

            // Check diagonals
            if (buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0][0].getText().equals(buttons[2][2].getText())
                    && !buttons[0][0].getText().equals("")) {
                return true; // Diagonal from top-left to bottom-right
            }
            if (buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0][2].getText().equals(buttons[2][0].getText())
                    && !buttons[0][2].getText().equals("")) {
                return true; // Diagonal from top-right to bottom-left
            }
            return false;
        }
    }
}
