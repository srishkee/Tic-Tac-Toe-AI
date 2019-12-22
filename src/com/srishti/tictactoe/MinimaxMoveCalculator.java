package com.srishti.tictactoe;

import com.srishti.tictactoe.model.BoardState;
import com.srishti.tictactoe.model.LevelEnum;
import com.srishti.tictactoe.model.Move;
import com.srishti.tictactoe.model.PlayerEnum;

import java.util.List;

public class MinimaxMoveCalculator extends AbstractMoveCalc
{
    private static final int LOSER_SCORE = -1;
    private static final int TIE_SCORE = 0;
    private static final int DEFAULT_DEPTH = 6;
    private int maxDepth = DEFAULT_DEPTH;

    public MinimaxMoveCalculator() {}

    public Move getNextMove(BoardState boardState)
    {
        PlayerEnum player = PlayerEnum.COMPUTER;
        return minimax(boardState, player , 0, new Move());
    }

    public void setLevel(LevelEnum level)
    {
        maxDepth = (level.isEasy()) ? 1 : ((level.isMedium()) ? 4 : 6);
    }

    private Move minimax(BoardState boardState, PlayerEnum aPlayer, int depth, Move m)
    {
        // Check terminal states
        if(boardState.isLoser(aPlayer)) {
            if(aPlayer.isComputer()) m.score = (double)LOSER_SCORE/depth;
            else if(aPlayer.isUser()) m.score = (double)-LOSER_SCORE/depth;
            return m;
        }
        else if(boardState.isTie()) {m.score = (double)TIE_SCORE/depth; return m;}
        else if(depth == maxDepth) {m.score = 0; return m;}

        // Get all available moves
        List<Move> availableMovesList = getAvailableMoves(boardState);
        double maxScore = -100000; double minScore = 100000;
        Move userMove = null, computerMove = null;

        // Explore all possible moves to determine most optimal move
        for (Move move : availableMovesList) {

            placeMoveOnBoard(boardState, aPlayer, move); // Try a move
            PlayerEnum opponent = (aPlayer.isUser()) ? PlayerEnum.COMPUTER : PlayerEnum.USER;
            Move nextMove = minimax(boardState, opponent , depth+1, move); // Evaluate move
            removeMove(boardState, move); // Remove move
            nextMove.x = move.x; nextMove.y = move.y; // Keep old coordinates

            // If computer made a more optimal move
            if(aPlayer.isComputer() && (nextMove.score > maxScore)) {
                computerMove = nextMove;
                maxScore = nextMove.score;
            }
            // If user made a more optimal move
            if(aPlayer.isUser() && (nextMove.score < minScore)) {
                userMove = nextMove;
                minScore = nextMove.score;
            }
        }
        return (aPlayer.isUser()) ? userMove : computerMove;
    }



}
