package com.srishti.tictactoe.controller;

import com.srishti.tictactoe.*;
import com.srishti.tictactoe.model.BoardState;
import com.srishti.tictactoe.model.LevelEnum;
import com.srishti.tictactoe.model.Move;
import com.srishti.tictactoe.model.PlayerEnum;
import com.srishti.tictactoe.view.View;

public class TicTacToeController
{
    private static final int BOARD_SIZE = 3;
    private View view;
    private BoardState boardState;
    private MinimaxMoveCalculator calculator;

    public TicTacToeController()
    {
        boardState = new BoardState(BOARD_SIZE, BOARD_SIZE);
        calculator = new MinimaxMoveCalculator();
        view = new View(BOARD_SIZE, this);
        view.initGUI();
    }

    public boolean isLegalMove(Move aMove)
    {
        return calculator.isLegalMove(boardState, aMove);
    }

    public void placeMoveOnBoard(PlayerEnum aPlayer, Move move)
    {
        calculator.placeMoveOnBoard(boardState, aPlayer, move);
    }

    public boolean isWinner(PlayerEnum aPlayer)
    {
        return boardState.isWinner(aPlayer);
    }

    public boolean isLoser(PlayerEnum aPlayer)
    {
        return boardState.isLoser(aPlayer);
    }

    public boolean isTie()
    {
        return boardState.isTie();
    }

    public Move getNextMove()
    {
        return calculator.getNextMove(boardState);
    }

    public String getGameState()
    {
        if(isWinner(PlayerEnum.USER)) return "WINNER";
        else if(isLoser(PlayerEnum.USER)) return "LOSER";
        else if(isTie()) return "TIE";
        else return null; // Case never encountered
    }

    public void setLevel(LevelEnum level)
    {
        calculator.setLevel(level);
    }

    public static void main (String [] args)
    {
        TicTacToeController controller = new TicTacToeController();

    }
}
