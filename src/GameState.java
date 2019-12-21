
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GameState extends JFrame implements ActionListener {

    private String USER_SYMBOL = "X";
    private String COMPUTER_SYMBOL = "O";
    private int USER_IDX = 1;
    private int BOARD_SIZE = 3;
    private int LOSER_SCORE = -1;
    private int TIE_SCORE = 0;
    private int MAX_DEPTH = 6; // 1 = Easy, 4 = Medium, 6 = Hard
    private TicTacToe ticTacToe;
    private Scanner scanner;
    private JPanel p;
    private JButton buttons[][];
    private ImageIcon X, O;
    private JFrame msgFrame;

    private void getUserInput()
    {
        System.out.println("Welcome to the Classic game of Tic-Tac-Toe!");
        System.out.println("Choose game level: 1 for Easy, 2 for Medium, 3 for Hard");
        int level = Integer.parseInt(scanner.nextLine());
        if(level == 1) MAX_DEPTH = 1;
        else if(level == 2) MAX_DEPTH = 4;
        else MAX_DEPTH = 6;
    }

    public GameState()
    {
        ticTacToe = new TicTacToe(BOARD_SIZE);
        scanner = new Scanner(System.in);
        getUserInput();
        initGUI();
    }

    private void initGUI() {
        p = new JPanel();
        X = new ImageIcon(this.getClass().getResource("XX.png"));
        O = new ImageIcon(this.getClass().getResource("OO.png"));
        setSize(800, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        p.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        p = initButtons(p);
        this.add(p); // Add panel to GUI
        setVisible(true);
    }

    private JPanel initButtons(JPanel p) {
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                p.add(buttons[i][j]);
            }
        }
        return p;
    }

    // Print the Pythonic way
    void print(String str) { System.out.println(str); }
    void print(String str, int num) { System.out.println(str + num); }
    void print(String str, double num) { System.out.println(str + num); }

    private void displayBoard() {
        for(int i = 0; i < ticTacToe.board.length; i++) {
            for(int j = 0; j < ticTacToe.board.length; j++) {
                if(ticTacToe.board[i][j] == USER_IDX) System.out.print(USER_SYMBOL);
                else if(ticTacToe.board[i][j] == 1-USER_IDX) System.out.print(COMPUTER_SYMBOL);
                else if(ticTacToe.board[i][j] == -1) System.out.print("_");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isWinner(int playerIdx)
    {
        return ticTacToe.isWinner(playerIdx);
    }

    private boolean isLoser(int playerIdx)
    {
        return ticTacToe.isLoser(playerIdx);
    }

    private boolean isTie()
    {
        return ticTacToe.isTie();
    }

    private void placeMoveOnBoard(int playerIdx, Move move)
    {
        ticTacToe.board[move.x][move.y] = playerIdx;
    }

    private ArrayList<Move> getAvailableMoves() {
        ArrayList<Move> moveList = new ArrayList<>();
        for(int i = 0; i < ticTacToe.board.length; i++) {
            for(int j = 0; j < ticTacToe.board.length; j++) {
                if(ticTacToe.board[i][j] == -1) { // If position is unoccupied
                    moveList.add(new Move(i, j)); // Add to list of available moves
                }
            }
        }
        // No need to randomize moveList since every single position will be explored anyways!
        Collections.shuffle(moveList);
        return moveList;
    }

    public Move minimax(int playerIdx, int depth, Move m)
    {
        // Check terminal states
        if(isLoser(playerIdx)) {
            if(playerIdx == 0) m.score = (double)LOSER_SCORE/depth;
            else if(playerIdx == 1) m.score = (double)-LOSER_SCORE/depth;
            return m;
        }
        else if(isTie()) {m.score = (double)TIE_SCORE/depth; return m;}
        else if(depth == MAX_DEPTH) {m.score = 0; return m;}

        // Get all available moves
        ArrayList<Move> availableMovesList = getAvailableMoves();
        Move[] bestMoveArr = new Move[2]; // Max player, min player
        double maxScore = -100000; double minScore = 100000;
        int minDepth = 100000;

        // Explore all possible moves to determine most optimal move
        for (Move move : availableMovesList) {

            placeMoveOnBoard(playerIdx, move); // Try a move
            Move nextMove = minimax(1 - playerIdx, depth+1, move); // Evaluate move
            placeMoveOnBoard(-1, move); // Remove move
            nextMove.x = move.x; nextMove.y = move.y; // Keep old coordinates

            // If computer made a more optimal move
            if(playerIdx == 0 && (nextMove.score > maxScore)) {
                bestMoveArr[playerIdx] = nextMove;
                maxScore = nextMove.score;
            }
            // If user made a more optimal move
            if(playerIdx == 1 && (nextMove.score < minScore)) {
                bestMoveArr[playerIdx] = nextMove;
                minScore = nextMove.score;
            }
        }
        return bestMoveArr[playerIdx];
    }

    private Move getComputerMove(int playerIdx) {
        return minimax(playerIdx, 0, new Move());
    }

    private Move parseInput(String coordinates) {
        int x = 0, y = 0;
        try {
            x = Integer.parseInt(coordinates.substring(0, 1));
            y = Integer.parseInt(coordinates.substring(2, 3));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Please enter a move in valid format! E.g. '1 3'");
        }
        return new Move(x, y);
    }

    private boolean isLegalMove(Move move) {
        int x = move.x;
        int y = move.y;
        if(x < 0 || x > BOARD_SIZE || y < 0 || y > BOARD_SIZE) return false; // throw new IllegalArgumentException("Position must be between 1 and 3!");
        if(ticTacToe.board[x][y] != -1) return false; // throw new IllegalArgumentException("Invalid move! Position already occupied");
        return true;
    }

    private Move getUserMove() {
        Move move = parseInput(scanner.nextLine());
        if(isLegalMove(move)) return move; // Return legal move
        return new Move(); // Else, return empty move (case is never encountered since error is thrown)
    }

    private Move getMove(int playerIdx) {
        if(playerIdx == 0) return getComputerMove(playerIdx);
        else return getUserMove();
    }

    public void play() {
        System.out.println("Welcome to the Classic game of Tic-Tac-Toe!");
        System.out.println("Choose game level: 1 for Easy, 2 for Medium");
        MAX_DEPTH = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter 1 to go first, or 2 to go second");
        USER_IDX = 1 - (Integer.parseInt(scanner.nextLine()) - 1); // Change to 0-index, then swap players so computer=0, user=1
        int playerIdx = USER_IDX;

        displayBoard();
        while(true) {
            Move m = getMove(playerIdx);
            placeMoveOnBoard(playerIdx, m);
            displayBoard();
            if(isWinner(playerIdx)) {
                print("Winner is Player ", playerIdx);
                break;
            }
            else if(isTie()) {
                print("Tie!");
                break;
            }
            playerIdx = 1-playerIdx;
        }
    }

    private String evaluateTerminalState()
    {
        int userIdx = 1;
        if(isWinner(userIdx)) return "WINNER";
        else if(isLoser(userIdx)) return "LOSER";
        else if(isTie()) return "TIE";
        else return ""; // Case never encountered
    }

    private void displayMessage(String eval)
    {
        Color color = null;
        switch(eval) // Determine message color
        {
            case "WINNER":
                color = Color.GREEN; break;
            case "LOSER":
                color = Color.RED; break;
            case "TIE ":
                color = Color.BLACK; break;
        }

        msgFrame = new JFrame();
        msgFrame.setLayout(new GridBagLayout());
        JPanel p = new JPanel();
        JLabel label = new JLabel(eval + "!");
        label.setFont(new Font("Verdana", 1, 55));
        label.setForeground(color);
        p.add(label);
        p.setBorder(new LineBorder(color)); // Enhance message with a border
        msgFrame.add(p, new GridBagConstraints());
        msgFrame.setSize(400, 260);
        msgFrame.setLocation(200, 290);
        msgFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        msgFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        int row = -1; int col = -1;
        // User move
        int playerIdx = 1;
        for(int i = 0; i < BOARD_SIZE; i++) { // Determine user's coordinates
            for(int j = 0; j < BOARD_SIZE; j++) {
                if(buttons[i][j] == e.getSource()) {
                    row = i;
                    col = j;
                }
            }
        }
        if(!isLegalMove(new Move(row, col))) return; // Don't allow illegal move
        placeMoveOnBoard(playerIdx, new Move(row, col)); // GameState uses Internal board
        buttons[row][col].setIcon(X);
        String eval = evaluateTerminalState();
        if(!eval.equals("")) {displayMessage(eval); return;}

        // Computer move
        playerIdx = 0;
        Move m = getMove(playerIdx);
        row = m.x; col = m.y; // Get coordinates (always legal)
        placeMoveOnBoard(playerIdx, new Move(row, col)); // GameState uses internal board
        buttons[row][col].setIcon(O);
        eval = evaluateTerminalState();
        if(!eval.equals("")) displayMessage(eval);
    }

}
