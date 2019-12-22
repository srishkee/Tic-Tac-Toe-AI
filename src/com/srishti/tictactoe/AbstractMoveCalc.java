package com.srishti.tictactoe;

import com.srishti.tictactoe.model.BoardState;
import com.srishti.tictactoe.model.Move;
import com.srishti.tictactoe.model.PlayerEnum;
import com.srishti.tictactoe.model.StatusEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMoveCalc
{
    public List<Move> getAvailableMoves(BoardState boardState)
    {
        ArrayList<Move> moveList = new ArrayList<>();
        for(int i = 0; i < boardState.getNumRows(); i++) {
            for(int j = 0; j < boardState.getNumCols(); j++) {
                if(boardState.getStatus(i, j).isEmpty()) { // If position is unoccupied
                    moveList.add(new Move(i, j)); // Add to list of available moves
                }
            }
        }
        // No need to randomize moveList since every single position will be explored anyways!
        Collections.shuffle(moveList);
        return moveList;
    }

    public boolean isLegalMove(BoardState boardState, Move aMove)
    {
        int x = aMove.x;
        int y = aMove.y;
        if(x < 0 || x > boardState.getNumRows() || y < 0 || y > boardState.getNumCols()) return false;
        return (boardState.getStatus(x, y).isEmpty());
    }

    public abstract Move getNextMove(BoardState boardState);

    public void placeMoveOnBoard(BoardState boardState, PlayerEnum aPlayerEnum, Move move)
    {
        StatusEnum playerStatus = (aPlayerEnum.isUser()) ? StatusEnum.USER : StatusEnum.COMPUTER;
        boardState.setStatus(move.x, move.y, playerStatus);
    }

    protected void removeMove(BoardState boardState, Move move)
    {
        boardState.setStatus(move.x, move.y, StatusEnum.EMPTY);
    }

}
