package com.srishti.tictactoe.view;

import com.intellij.openapi.ui.ComboBox;
import com.srishti.tictactoe.model.LevelEnum;
import com.srishti.tictactoe.model.Move;
import com.srishti.tictactoe.model.PlayerEnum;
import com.srishti.tictactoe.controller.TicTacToeController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

public class View extends JFrame implements ActionListener
{
    private JPanel p;
    private JButton buttons[][];
    private ImageIcon X, O;
    private JFrame msgFrame;
    private JFrame userFrame;
    private int boardSize;
    private TicTacToeController controller;
    private Semaphore semaphore;
    private JComboBox comboBox;


    public View(int aBoardSize, TicTacToeController controller)
    {
        this.boardSize = aBoardSize;
        this.controller = controller;
        semaphore = new Semaphore(0); // Used to sync 1st and 2nd GUI window
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
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
        controller.placeMoveOnBoard( PlayerEnum.USER, new Move(row, col)); // GameState uses Internal board
        buttons[row][col].setIcon(X); // Place move on GUI
        String gameState = controller.getGameState();
        if(gameState != null) {processTerminalState(gameState); return;}

        // Computer move
        Move m = controller.getNextMove();
        row = m.x; col = m.y; // Get coordinates (always legal)
        controller.placeMoveOnBoard(PlayerEnum.COMPUTER, new Move(row, col)); // GameState uses internal board
        buttons[row][col].setIcon(O); // Place move on GUI
        gameState = controller.getGameState();
        if(gameState != null) processTerminalState(gameState);
    }

    public void initGUI() {

        getUserInput();
        try { // Only proceed once semaphore has been acquired (after game level has been selected)
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p = new JPanel();
        X = new ImageIcon(this.getClass().getResource("../../../../XX.png"));
        O = new ImageIcon(this.getClass().getResource("../../../../OO.png"));
        setSize(800, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userFrame.setVisible(false); // Remove user window
        setVisible(true);
        setResizable(false);
        p.setLayout(new GridLayout(boardSize, boardSize));

        initButtons();
        this.add(p); // Add panel to GUI
        setVisible(true);
    }

    public void getUserInput()
    {
        // Panel
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = new GridBagConstraints();

        // JLabel
        JLabel levelLabel = new JLabel("Select game level: ");
        levelLabel.setFont(new Font("Verdana", 1, 16));
        String levels[] = {"Select Level", "Easy", "Medium", "Hard"};

        // ComboBox
        comboBox = new ComboBox(levels);
        comboBox.setFont(new Font("Verdana", 1, 16));
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = comboBox.getSelectedIndex();
                controller.setLevel(LevelEnum.parseFromInt(level));
                semaphore.release(); // Program can only continue once game level has been selected
            }
        });
        p.add(levelLabel, gridConstraints);
        p.add(comboBox);

        // JFrame
        userFrame = new JFrame();
        userFrame.setLayout(new GridBagLayout());
        userFrame.add(p, gridConstraints);
        userFrame.setSize(400, 260);
        userFrame.setLocation(200, 290);
        userFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userFrame.setVisible(true);
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
