import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToeGame extends JFrame implements ActionListener {
    private final JButton[][] buttons = new JButton[3][3];
    private final char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private boolean easyMode = true; 
    private JMenu levelMenu;

    public TicTacToeGame() {
        super("Tic Tac Toe");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
                button.setFocusPainted(false);
                button.addActionListener(this);
                buttons[i][j] = button;
                board[i][j] = '-';
                gamePanel.add(button); 
            }
        }
        add(gamePanel, BorderLayout.CENTER); 

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        levelMenu = new JMenu(); 
        updateLevelMenuTitle();
        menuBar.add(levelMenu);

        JMenuItem easyItem = new JMenuItem("Easy");
        easyItem.addActionListener(e -> {
            easyMode = true;
            updateLevelMenuTitle(); 
        });
        levelMenu.add(easyItem);

        JMenuItem notEasyItem = new JMenuItem("Not Easy");
        notEasyItem.addActionListener(e -> {
            easyMode = false;
            updateLevelMenuTitle();
        });
        levelMenu.add(notEasyItem);

        setVisible(true);
    }

    private void updateLevelMenuTitle() {
        String level = (easyMode ? "Easy" : "Not Easy");
        levelMenu.setText("Level : " + level);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        clickedButton.setText(String.valueOf(currentPlayer));
        clickedButton.setEnabled(false);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == clickedButton) {
                    board[i][j] = currentPlayer;
                    if (checkForWin()) {
                        JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                        resetBoard();
                        return;
                    }
                    if (isBoardFull()) {
                        JOptionPane.showMessageDialog(this, "It's a tie!");
                        resetBoard();
                        return;
                    }
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                    aiMove();
                }
            }
        }
    }

    private void aiMove() {
        if (easyMode) {
            easyAiMove();
        } else {
            notEasyAiMove();
        }
    }

    private void easyAiMove() {
        Random rand = new Random();
        int i, j;
        do {
            i = rand.nextInt(3);
            j = rand.nextInt(3);
        } while (board[i][j] != '-');

        buttons[i][j].setText(String.valueOf(currentPlayer));
        buttons[i][j].setEnabled(false);
        board[i][j] = currentPlayer;
        checkEndGame();
    }

    private void notEasyAiMove() {
        

        int cnt=0;
        for (int row = 0; row < 3; row++) {
            cnt = 0;
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("O")) {
                    cnt++;
                }
            }
            if (cnt == 2) {
                for (int col = 0; col < 3; col++) {
                    if (buttons[row][col].getText().equals("")) {
                        buttons[row][col].setText("O"); 
                        buttons[row][col].setEnabled(false);
                        board[row][col] = 'O';
                        checkEndGame();
                        return; 
                    }
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            cnt = 0;
            for (int row = 0; row < 3; row++) {
                if (buttons[row][col].getText().equals("O")) {
                    cnt++;
                }
            }
            if (cnt == 2) {
                for (int row = 0; row < 3; row++) {
                    if (buttons[row][col].getText().equals("")) {
                        buttons[row][col].setText("O");
                        buttons[row][col].setEnabled(false);
                        board[row][col] = 'O';
                        checkEndGame();
                        return;
                    }
                }
            }
        }

        cnt = 0;
        for (int i = 0; i < 3; i++) {
            if (buttons[i][i].getText().equals("O")) {
                cnt++;
            }
        }
        if (cnt == 2) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][i].getText().equals("")) {
                    buttons[i][i].setText("O");
                    buttons[i][i].setEnabled(false);
                    board[i][i] = 'O';
                    checkEndGame();
                    return; 
                }
            }
        }

        cnt = 0;
        for (int i = 0; i < 3; i++) {
            if (buttons[i][2 - i].getText().equals("O")) {
                cnt++;
            }
        }
        if (cnt == 2) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][2 - i].getText().equals("")) {
                    buttons[i][2 - i].setText("O");
                    buttons[i][2 - i].setEnabled(false);
                    board[i][2 - i] = 'O';
                    checkEndGame();
                    return;
                }
            }
        }
        

        // -----------
        // Check for a one-move win for the opponent in rows and block it
        for (int row = 0; row < 3; row++) {
            cnt = 0;
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals("X")) { // Check for opponent's symbol
                    cnt++;
                }
            }
            if (cnt == 2) {
                for (int col = 0; col < 3; col++) {
                    if (buttons[row][col].getText().equals("")) {
                        // BLOCK MOVE
                        buttons[row][col].setText("O"); // Place AI/player's symbol to block
                        buttons[row][col].setEnabled(false);
                        board[row][col] = 'O';
                        checkEndGame();
                        return; 
                    }
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            cnt = 0;
            for (int row = 0; row < 3; row++) {
                if (buttons[row][col].getText().equals("X")) {
                    cnt++;
                }
            }
            if (cnt == 2) {
                for (int row = 0; row < 3; row++) {
                    if (buttons[row][col].getText().equals("")) {
                        buttons[row][col].setText("O");
                        buttons[row][col].setEnabled(false);
                        board[row][col] = 'O';
                        checkEndGame();
                        return; 
                    }
                }
            }
        }

        cnt = 0;
        for (int i = 0; i < 3; i++) {
            if (buttons[i][i].getText().equals("X")) { 
                cnt++;
            }
        }
        if (cnt == 2) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][i].getText().equals("")) {
                    buttons[i][i].setText("O");
                    buttons[i][i].setEnabled(false);
                    board[i][i] = 'O';
                    checkEndGame();
                    return;
                }
            }
        }

        cnt = 0;
        for (int i = 0; i < 3; i++) {
            if (buttons[i][2 - i].getText().equals("X")) { 
                cnt++;
            }
        }
        if (cnt == 2) {
            for (int i = 0; i < 3; i++) {
                if (buttons[i][2 - i].getText().equals("")) {
                    buttons[i][2 - i].setText("O");
                    buttons[i][2 - i].setEnabled(false);
                    board[i][2 - i] = 'O';
                    checkEndGame();
                    return; 
                }
            }
        }

        if (buttons[1][1].getText().equals("")) {
            buttons[1][1].setText("O");
            buttons[1][1].setEnabled(false);
            board[1][1] = 'O';
            checkEndGame();
            return;
        }
        
        if (buttons[1][1].getText().equals("X")) {
            
                if (buttons[0][0].getText().equals("")) { // Cell 2
                    buttons[0][0].setText("O");
                    buttons[0][0].setEnabled(false);
                    board[0][0] = 'O';
                    checkEndGame();
                    return;
                } else if (buttons[1][0].getText().equals("")) { // Cell 4
                    buttons[0][2].setText("O");
                    buttons[0][2].setEnabled(false);
                    board[0][2] = 'O';
                    checkEndGame();
                    return;
                } else if (buttons[2][0].getText().equals("")) { // Cell 6
                    buttons[2][0].setText("O");
                    buttons[2][0].setEnabled(false);
                    board[2][0] = 'O';
                    checkEndGame();
                    return;
                } else if (buttons[2][2].getText().equals("")) { // Cell 8
                    buttons[2][2].setText("O");
                    buttons[2][2].setEnabled(false);
                    board[2][2] = 'O';
                    checkEndGame();
                    return;
                }
        }
        else{
            // get edge
            // get near to X cell

            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[row].length; col++) {
                    if (board[row][col] == 'X') {
                        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
                        for (int[] dir : directions) {
                            int newRow = row + dir[0];
                            int newCol = col + dir[1];
                            if (newRow >= 0 && newRow < buttons.length && newCol >= 0 && newCol < buttons[0].length) {
                                if (buttons[newRow][newCol].getText().equals("")) {
                                    buttons[newRow][newCol].setText("O");
                                    buttons[newRow][newCol].setEnabled(false);
                                    board[newRow][newCol] = 'O';
                                    checkEndGame();
                                    return; 
                                }
                            }
                        }
                    }
                }
            }
        }
        // ------
        
        // -------------------------


        

        if (buttons[0][0].getText().equals("")) {
            buttons[0][0].setText("O");
            buttons[0][0].setEnabled(false);
            board[0][0] = 'O';
            checkEndGame();
            return;
        }

        if (buttons[0][2].getText().equals("")) {
            buttons[0][2].setText("O");
            buttons[0][2].setEnabled(false);
            board[0][2] = 'O';
            checkEndGame();
            return;
        }

        if (buttons[2][0].getText().equals("")) {
            buttons[2][0].setText("O");
            buttons[2][0].setEnabled(false);
            board[2][0] = 'O';
            checkEndGame();
            return;
        }

        if (buttons[2][2].getText().equals("")) {
            buttons[2][2].setText("O");
            buttons[2][2].setEnabled(false);
            board[2][2] = 'O';
            checkEndGame();
            return;
        }


        // -----
        // GO TO RANDOM MOVE
        // take opposite corner
        // if center taken
        // other wise 
        easyAiMove();
    }

    private void checkEndGame() {
        if (checkForWin()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a tie!");
            resetBoard();
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true;
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        currentPlayer = 'X';
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGame::new);
    }
}
