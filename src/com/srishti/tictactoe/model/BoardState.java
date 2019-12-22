package com.srishti.tictactoe.model;

public class BoardState
{
    private int numRows;
    private int numCols;
    private StatusEnum[][] board;

    public BoardState(int aNumRows, int aNumCols)
    {
        numRows = aNumRows;
        numCols = aNumCols;

        board = new StatusEnum[numRows][numCols];

        for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < numCols; j++) {
                board[i][j] = StatusEnum.EMPTY;
            }
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public StatusEnum getStatus(int row, int col) {
        return board[row][col];
    }

    public void setStatus(int row, int col, StatusEnum playerStatus) {
        board[row][col] = playerStatus;
    }

    public StatusEnum convertToStatus(PlayerEnum aPlayer)
    {
        return (aPlayer.isUser()) ? StatusEnum.USER : StatusEnum.COMPUTER;
    }

    public PlayerEnum convertToPlayer(StatusEnum aStatus)
    {
        if(aStatus.isEmpty()) return null;
        return (aStatus.isUser()) ? PlayerEnum.USER : PlayerEnum.COMPUTER;
    }

    public boolean isWinner(PlayerEnum aPlayer)
    {
        StatusEnum playerStatus = convertToStatus(aPlayer);

        // Check horizontal rows
        for(int i = 0; i < getNumRows(); i++) {
            for(int j = 0; j < getNumCols(); j++) {
                if(getStatus(i, j) != (playerStatus)) break;
                if(j == getNumCols()-1) return true;
            }
        }

        // Check vertical rows
        for(int i = 0; i < getNumRows(); i++) {
            for(int j = 0; j < getNumCols(); j++) {
                if(getStatus(j, i) != (playerStatus)) break;
                if(j == getNumRows()-1) return true;
            }
        }

        // Check 1st diagonal
        for(int i = 0; i < getNumRows(); i++) {
            if(getStatus(i, i) != (playerStatus)) break;
            if(i == getNumRows()-1) return true;
        }

        // Check 2nd diagonal
        for(int i = 0; i < getNumCols(); i++) {
            if(getStatus(i, getNumCols() - i - 1) != (playerStatus)) break;
            if(i == getNumCols()-1) return true;
        }
        return false;
    }

    public boolean isLoser(PlayerEnum aPlayer)
    {
        PlayerEnum opponent = (aPlayer.isUser()) ? PlayerEnum.COMPUTER : PlayerEnum.USER;
        return isWinner(opponent);
    }

    public boolean isTie()
    {
        for(int i = 0; i < getNumRows(); i++) {
            for(int j = 0; j < getNumCols(); j++) {
                if(getStatus(i, j).isEmpty()) return false; // Unoccupied position!
            }
        }
        return true; // All positions are occupied
    }


}
