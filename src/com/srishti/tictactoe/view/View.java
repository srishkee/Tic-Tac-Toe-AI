package com.srishti.tictactoe.view;

import com.srishti.tictactoe.model.LevelEnum;
import com.srishti.tictactoe.model.Move;
import com.srishti.tictactoe.model.PlayerEnum;
import com.srishti.tictactoe.controller.TicTacToeController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class View extends JFrame implements ActionListener
{
    private JPanel p;
    private JButton buttons[][];
    private ImageIcon X, O;
    private JFrame msgFrame;
    private int boardSize;
    private TicTacToeController controller;
    private Scanner scanner;


    public View(int aBoardSize, TicTacToeController controller)
    {
        this.boardSize = aBoardSize;
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void actionPerformed(ActionEvent e) {

        int row = -1; int col = -1;

        // User move
        for(int i = 0; i < boardSize; i++) { // Determine user's coordinates
            for(int j = 0; j < boardSize; j++) {
                if(buttons[i][j] == e.getSource()) {
                    row = i;
                    col = j;
                }
            }
        }
        if(!controller.isLegalMove(new Move(row, col))) return; // Don't allow illegal move
        controller.placeMoveOnBoard( PlayerEnum.USER, new Move(row, col)); // GameState2 uses Internal board
        buttons[row][col].setIcon(X); // Place move on GUI
        String gameState = controller.getGameState();
        if(gameState != null) {processTerminalState(gameState); return;}

        // Computer move
        Move m = controller.getNextMove();
        row = m.x; col = m.y; // Get coordinates (always legal)
        controller.placeMoveOnBoard(PlayerEnum.COMPUTER, new Move(row, col)); // GameState2 uses internal board
        buttons[row][col].setIcon(O); // Place move on GUI
        gameState = controller.getGameState();
        if(gameState != null) processTerminalState(gameState);
    }

    public void initGUI() {

        getUserInput();
        p = new JPanel();
        X = new ImageIcon(this.getClass().getResource("../../../../XX.png"));
        O = new ImageIcon(this.getClass().getResource("../../../../OO.png"));
        setSize(800, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        p.setLayout(new GridLayout(boardSize, boardSize));

        initButtons();
        this.add(p); // Add panel to GUI
        setVisible(true);
    }

    private void getUserInput()
    {
        System.out.println("Welcome to the Classic game of Tic-Tac-Toe!");
        System.out.println("Choose game level: 1 for Easy, 2 for Medium, 3 for Hard");
        int level = Integer.parseInt(scanner.nextLine());
        controller.setLevel(LevelEnum.parseFromInt(level));
    }

    private void initButtons() {
        buttons = new JButton[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                p.add(buttons[i][j]);
            }
        }
    }

    private void processTerminalState(String gameState)
    {
        Color color = null;
        switch(gameState) // Determine message color
        {
            case "WINNER":
                color = Color.GREEN; break;
            case "LOSER":
                color = Color.RED; break;
            case "TIE ":
                color = Color.BLACK; break;
        }
        displayMessage(color, gameState);
    }

    private void displayMessage(Color color, String msg)
    {
        msgFrame = new JFrame();
        msgFrame.setLayout(new GridBagLayout());
        JPanel p = new JPanel();
        JLabel label = new JLabel(msg + "!");
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

}
