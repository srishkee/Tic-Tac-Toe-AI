
public class TicTacToe {

    public int [][] board; // Change TO PRIVATE?
    private int BOARD_SIZE = 3;

    public TicTacToe(int BOARD_SIZE)
    {
        this.BOARD_SIZE = BOARD_SIZE;
        initBoard();
    }

    private void initBoard()
    {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                board[i][j] = -1;
            }
        }
//        int[][] board1 = {{1,1,-1},{0,0,1},{1,0,-1}}; // REMOVE!!!
//        int[][] board1 = {{1,-1,0},{-1,0,-1},{-1,-1,1}}; // REMOVE!!!
//        int[][] board1 = {{0,-1,-1},{0,-1,-1},{-1,-1,-1}}; // REMOVE!!!
//        int[][] board1 = {{0,0,-1},{-1,-1,-1},{-1,-1,-1}}; // REMOVE!!!
//        int[][] board1 = {{0,-1,-1},{-1,0,-1},{-1,-1,-1}}; // REMOVE!!!
//        int[][] board1 = {{1,-1,1},{-1,0,-1},{1,-1,-1}}; // REMOVE!!!
//        int[][] board1 = {{0,-1,1},{-1,-1,-1},{-1,-1,1}}; // REMOVE!!!
//        int[][] board1 = {{0,-1,1},{-1,1,-1},{-1,-1,-1}}; // REMOVE!!!
//        board = board1; // REMOVE!!!
    }

    public boolean isWinner(int playerIdx)
    {
        // Check horizontal rows
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                if(board[i][j] != (playerIdx)) break;
                if(j == 2) return true;
            }
        }

        // Check vertical rows
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                if(board[j][i] != (playerIdx)) break;
                if(j == 2) return true;
            }
        }

        // Check 1st diagonal
        for(int i = 0; i < board.length; i++)
        {
            if(board[i][i] != (playerIdx)) break;
            if(i == 2) return true;
        }

        // Check 2nd diagonal
        for(int i = 0; i < board.length; i++)
        {
            if(board[i][BOARD_SIZE - i - 1] != (playerIdx)) break;
            if(i == 2) return true;
        }
        return false;
    }

    public boolean isLoser(int playerIdx)
    {
        return isWinner(1-playerIdx);
    }

    public boolean isTie()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board.length; j++)
            {
                if(board[i][j] == -1) return false; // Unoccupied position!
            }
        }
        return true; // All positions are occupied
    }

////    Test the Tic-Tac-Toe class
//    public static void main(String[] argv) throws Exception
//    {
//        TicTacToe ticTacToe = new TicTacToe();
//
//        ticTacToe.board[0][2] = 0;
//        ticTacToe.board[1][2] = 0;
//        ticTacToe.board[2][2] = 1;
//        ticTacToe.board[0][0] = 0;
//        ticTacToe.board[1][0] = 0;
//        ticTacToe.board[2][0] = 1;
//        ticTacToe.board[0][1] = 1;
//        ticTacToe.board[1][1] = 1;
//        ticTacToe.board[2][1] = 0;
//
//        System.out.println(ticTacToe.isWinner(0));
//        System.out.println(ticTacToe.isLoser(1));
//        System.out.println(ticTacToe.isTie());
//    }



} // Closing class brace