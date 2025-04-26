/**
 * Implements certain aspects of the game of "Go"
 */
public class GoFrame extends GoBaseFrame {

    // symbolic constants, representing stones on the board
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int EMPTY = 2;
    public static final int WHITE_IN_PERIL = 3;
    public static final int BLACK_IN_PERIL = 4;
    
    /**
     * Returns true if and only if (bx, by) is a valid location on the board
     */
    boolean isValid(int bx, int by) {
        if (bx < 0 || by < 0) {
            return false;
        } else if (bx >= board.length || by >= board.length) {
            return false;
        }
        return true;
    }
    
    /**
     * Clears the board of all stones
     */
    @Override
    protected void clearBoard() {

        // **** YOUR CODE FOR CHECKPOINT 9 OF LAB 6 SHOULD GO HERE ****
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY;
            }
        }

    }
    /**
     * If the given column and row positions are a legal location on the board,
     *  modifies the location by either changing the stone's color,
     *  or by adding or removing a stone.
     *
     * @param col The horizontal position of the spot on the board,
     *            with 0 representing the left of the board.
     * @param row The vertical position of the spot on the board,
     *            with 0 representing the top of the board.
     */
    @Override
    protected void pressedOnSpace(int row, int col) {

        // at this point, col contains the column-coordinate denoting the square
        // that was pressed, and row contains the row-coordinate       
        // **** YOUR CODE FOR CHECKPOINT 10 OF LAB 6 SHOULD GO HERE ****
        if ((row < board.length && row >= 0) && (col < board.length && col >= 0)) {
            switch(board[row][col]) {
                case 0:
                    board[row][col] = BLACK;
                    break;
                case 1:
                    board[row][col] = EMPTY;
                    break;
                case 2:
                    board[row][col] = WHITE;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Places stones randomly on the board, with
     * approximately 20% of the spaces blank, 40% black, 40% white
     */
    @Override
    protected void randomizeBoard() {
        // **** YOUR CODE FOR CHECKPOINT 1 OF LAB 7 SHOULD GO HERE ****
        double blankSpace = 0.2;
        double whiteSpace = 0.6;
        double blackSpace = 1.00;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                double piece = Math.random();
                if (piece < blankSpace) {
                    board[i][j] = EMPTY;
                } else if (piece >= blankSpace && piece < whiteSpace) {
                    board[i][j] = WHITE;
                } else if (piece >= whiteSpace && piece < blackSpace) {
                    board[i][j] = BLACK;
                }
            }
        }
    }

    /**
     * Determine which stones are captured and remove them from the board
     */
    @Override
    protected void removeCapturedStones() {

        // **** YOUR CODE FOR CHECKPOINTS 2+ LAB 7 SHOULD GO HERE ****
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == WHITE) {
                    board[i][j] = WHITE_IN_PERIL;
                }
                if (board[i][j] == BLACK) {
                    board[i][j] = BLACK_IN_PERIL;
                }
            }
        }

        // continue to loop trough board if changed to peril or safe
        boolean change = true;
        while (change) {
            change = false;

            // loop through entire board
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    
                    // check if the current white in peril is safe
                    if (board[i][j] == WHITE_IN_PERIL) {
                        if (isValid(i+1,j) && (board[i+1][j] == WHITE || board[i+1][j] == EMPTY)) {
                            board[i][j] = WHITE;
                            change = true;
                        } else if (isValid(i-1,j) && (board[i-1][j] == WHITE || board[i-1][j] == EMPTY)) {
                            board[i][j] = WHITE;
                            change = true;
                        } else if (isValid(i,j+1) && (board[i][j+1] == WHITE || board[i][j+1] == EMPTY)) {
                            board[i][j] = WHITE;
                            change = true;
                        } else if (isValid(i,j-1) && (board[i][j-1] == WHITE || board[i][j-1] == EMPTY)) {
                            board[i][j] = WHITE;
                            change = true;
                        }
                    }

                    // check if the current black in peril is safe
                    if (board[i][j] == BLACK_IN_PERIL) {
                        if (isValid(i+1,j) && (board[i+1][j] == BLACK || board[i+1][j] == EMPTY)) {
                            board[i][j] = BLACK;
                            change = true;
                        } else if (isValid(i-1,j) && (board[i-1][j] == BLACK || board[i-1][j] == EMPTY)) {
                            board[i][j] = BLACK;
                            change = true;
                        } else if (isValid(i,j+1) && (board[i][j+1] == BLACK || board[i][j+1] == EMPTY)) {
                            board[i][j] = BLACK;
                            change = true;
                        } else if (isValid(i,j-1) && (board[i][j-1] == BLACK || board[i][j-1] == EMPTY)) {
                            board[i][j] = BLACK;
                            change = true;
                        }
                    }
                }
            }
        }

        // remove all filtered pieces still in peril
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == WHITE_IN_PERIL || board[i][j] == BLACK_IN_PERIL) {
                    board[i][j] = EMPTY;
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////
    //*****************************************************************
    //**CS 273 students should not need to modify anything below here**
    //*****************************************************************
    ///////////////////////////////////////////////////////////////////

    /**
     * Creates and displays frame
     * @param args
     */
    public static void main(String[] args) {
        new GoFrame().setVisible(true);
    }
}