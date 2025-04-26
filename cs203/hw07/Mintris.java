import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Class Mintris - This program creates a simpler version of the classic
 * Tetris game.  Mintris is turn-based and has only 2x2 pieces.
 * 
 * @author Profs Andrew Nuxoll and Karen Ward
 * @author Bryce Kwon
 * 
 */
public class Mintris extends JPanel implements KeyListener {

    /* ============================================================
     * Constants
     * ============================================================
     */

    public static final int NUM_ROWS        = 20;   // number of rows in the playing field
    public static final int NUM_COLS        = 10;   // number of columns in the playing field
    public static final int BLOCK_SIZE      = 20;   // size of a block in pixels

    // these constants define the possible value of each cell in the playing field
    public static final int NUM_COLORS      = 3;
    public static final int INVALID_COLOR   = 0;
    public static final int RED_BLOCK       = 1;
    public static final int GREEN_BLOCK     = 2;
    public static final int BLUE_BLOCK      = 3;
    public static final int EMPTY           = 4;

    // these constants define the possible movement of each block on the playing field
    public static final int LEFT    = -1;
    public static final int RIGHT   = 1;
    public static final int DOWN    = 0;


    /* ============================================================
     * Instance Variables
     * ============================================================
     */

    // a 2D array to store the playing field locations
    private int[][] field = new int[NUM_ROWS][NUM_COLS];

    // current game score
    private int score = 0;

    // determines if the current block can move
    private boolean moveable = true;


    /* ============================================================
     * Methods
     * ============================================================
     */

    /**
     * clearField
     *
     * creates a new playing field and sets all cells in the field to EMPTY.
     */
    public void clearField() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                field[i][j] = EMPTY;
            }
        }
    }//clearField


    /**
     * rotate
     * 
     * rotates the 2x2 piece by 90 degrees to the right.
     *
     * @param row   the row of the upper-left corner of the 2x2 block
     * @param col   the column of the upper-left block
     */
    public void rotate(int row, int col) {
        // temporary variables to hold block values
        int topLeft = field[row][col];
        int topRight = field[row][col+1];
        int bottomLeft = field[row+1][col];
        int bottomRight = field[row+1][col+1];

        // replace blocks in a clockwise location
        field[row][col+1] = topLeft;
        field[row+1][col+1] = topRight;
        field[row+1][col] = bottomRight;
        field[row][col] = bottomLeft;
    }//rotate


    /**
     * move
     *
     * moves the 2x2 piece by one space (left, right, or down).
     *
     * @param row           the row of the upper-left corner of the block
     * @param col           the column of the upper-left corner of the block
     * @param direction     the direction to move (LEFT, DOWN, or RIGHT)
     */
    public void move(int row, int col, int direction) {
        // temporary variables to hold block values
        int topLeft = field[row][col];
        int topRight = field[row][col+1];
        int bottomLeft = field[row+1][col];
        int bottomRight = field[row+1][col+1];

        // determine the direction of movement
        switch(direction) {
            case LEFT:
            // account for empty spaces to the left
            if (topLeft == EMPTY && field[row][col-1] != EMPTY) {
                field[row][col-1] = field[row][col-1];
                moveable = false;
            } else {
                field[row][col-1] = topLeft;
            }
            if (bottomLeft == EMPTY && field[row+1][col-1] != EMPTY) {
                field[row+1][col-1] = field[row+1][col-1];
                moveable = false;
            } else {
                field[row+1][col-1] = bottomLeft;
            }

            // shift remaining blocks left
            field[row][col] = topRight;
            field[row+1][col] = bottomRight;

            // empty initial positions of blocks
            field[row][col+1] = EMPTY;
            field[row+1][col+1] = EMPTY;
            break;
            case RIGHT:
            // account for empty spaces to the right
            if (topRight == EMPTY && field[row][col+2] != EMPTY) {
                field[row][col+2] = field[row][col-1];
                moveable = false;
            } else {
                field[row][col+2] = topRight;
            }
            if (bottomRight == EMPTY && field[row+1][col+2] != EMPTY) {
                field[row+1][col+2] = field[row+1][col+2];
                moveable = false;
            } else {
                field[row+1][col+2] = bottomRight;
            }

            // shift remaining blocks right
            field[row][col+1] = topLeft;
            field[row+1][col+1] = bottomLeft;

            // empty initial positions of blocks
            field[row][col] = EMPTY;
            field[row+1][col] = EMPTY;
            break;
            case DOWN:
            // account for empty spaces to the down
            if (bottomLeft == EMPTY && field[row+2][col] != EMPTY) {
                field[row+2][col] = field[row+2][col];
                moveable = false;
            } else {
                field[row+2][col] = bottomLeft;
            }
            if (bottomRight == EMPTY && field[row+2][col+1] != EMPTY) {
                field[row+2][col+1] = field[row+2][col+1];
                moveable = false;
            } else {
                field[row+2][col+1] = bottomRight;
            }

            // shift remaining blocks down
            field[row+1][col] = topLeft;
            field[row+1][col+1] = topRight;

            // empty initial positions of blocks
            field[row][col] = EMPTY;
            field[row][col+1] = EMPTY;
            break;
            default:
            break;
        }
    }//move


    /**
     * validMove
     *
     * calculates if a block may be moved one space in a particular direction.
     *
     * @param row           the row of the upper-left corner of the block
     * @param col           the column of the upper-left corner of the block 
     * @param direction     direction to move (LEFT, RIGHT, or DOWN)
     *
     * @return              true if the movement is legal and false otherwise
     */
    private boolean validMove(int row, int col, int direction) {
        // check if the block can be moved
        if (!moveable) {
            moveable = true;
            return false;
        }

        // determine the direction of movement
        switch(direction) {
            case LEFT:
            // check for out-of-bounds placment
            if (col - 1 < 0) {
                return false;
            }

            // check target space for pieces
            if (field[row][col] != EMPTY && field[row][col-1] != EMPTY) {
                return false;
            }
            if (field[row+1][col] != EMPTY && field[row+1][col-1] != EMPTY) {
                return false;
            }
            break;
            case RIGHT:
            // check for out-of-bounds placment
            if (col + 2 >= NUM_COLS) {
                return false;
            }

            // check target space for pieces
            if (field[row][col+1] != EMPTY && field[row][col+2] != EMPTY) {
                return false;
            }
            if (field[row+1][col+1] != EMPTY && field[row+1][col+2] != EMPTY) {
                return false;
            }
            break;
            case DOWN:
            // check for out-of-bounds placment
            if (row + 2 >= NUM_ROWS) {
                return false;
            }

            // check target space for pieces
            if (field[row+1][col] != EMPTY && field[row+2][col] != EMPTY) {
                return false;
            }
            if (field[row+1][col+1] != EMPTY && field[row+2][col+1] != EMPTY) {
                return false;
            }
            break;
            default:
            break;
        }
        return true;
    }//validMove


    /**
     * removeRows
     *
     * searches the field for any complete rows of blocks and removes them. 
     * rows above the removed show shift down one row. the score is incremented
     * for each complete row that is removed.
     */
    private void removeRows() {
        // determines if the current row is filled
        boolean filled = false;

        for (int i = NUM_ROWS - 1; i > 0;) {
            for (int j = 0; j < NUM_COLS; j++) {
                // if the current row is empty, move on to the next
                if (field[i][j] == EMPTY) {
                    filled = false;
                    i--;
                    break;
                } else {
                    filled = true;
                }
            }

            // delete row and shift down if full row is found
            if (filled) {
                for (int k = i; k > 0; k--) {
                    for (int l = 0; l < field[k].length; l++) {
                        field[k][l] = EMPTY;
                        field[k][l] = field[k-1][l];
                    }
                }
                // incremement score by 1
                score++;
            }
        }
    }//removeRows



    /*======================================================================
     *                    ATTENTION STUDENTS!
     *
     * The code below this point should not be edited.  However, you are
     * encouraged to examine the code to learn a little about how the rest
     * of the game was implemented.
     * ----------------------------------------------------------------------
     */
    /*======================================================================
     * More Instance Variables and Constants
     *
     * ==> You should not modify the values of these variables <==
     *----------------------------------------------------------------------
     */
    public static final int WINDOW_WIDTH = 230;
    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_MARGIN = 10;

    //The location of the current piece.
    //Students: DO NOT USED THESE VARIABLES IN YOUR CODE
    private int currRow = 0;
    private int currCol = 0;

    //random number generator
    private Random randGen = new Random();

    // colors array for drawing the pieces
    // Constants for valid colors, INVALID_COLOR, and EMPTY are defined above, in the
    // area that students are expected to study
    // Additional block colors, if desired, should be inserted before EMPTY (the last 
    // entry below), and the defined constant NUM_COLORS (defined above) adjusted accordingly

    // possible block colors
    private Color[] blockColors = { 
        Color.MAGENTA,          // invalid (so must be cleared explicitly)
        Color.RED,              // red
        new Color(0, 110, 0),   // green
        new Color(0,0,170),     // blue
        Color.BLACK };          // EMPTY (should never be displayed)

    //A flag used to keep multiple keypresses from being handled simultaneously
    private boolean synch = false;

    /**
     * createRandomPiece
     *
     * creates a new piece at the top of the Mintris board
     *
     */
    public void createRandomPiece() {
        //Select a random starting column and color
        int col = randGen.nextInt(NUM_COLS - 1);
        int type = randGen.nextInt(NUM_COLORS) + 1;

        //Fill the indicated 2x2 area
        for(int x = 0; x < 2; ++x) {
            for(int y = 0; y < 2; ++y) 
        {
                this.field[x][y + col] = type;
            }
        }

        //randomly select which block in the 2x2 area of the piece will be empty
        int which = randGen.nextInt(4);
        int x = which / 2;
        int y = which % 2;
        field[x][y+col] = EMPTY;

        //record the location of this new piece
        this.currRow = 0;
        this.currCol = col;

    }//createRandomPiece

    /**
     * drawBlock
     *
     * a helper method for {@link paint}.  This method draws a Mintris block of a
     * given color at a given x,y coordinate.
     *
     * @param  g          the Graphics object for this application
     * @param  x, y       the coordinates of the block
     * @param  blockColor the main color of the block
     */
    public void drawBlock(Graphics g, int x, int y, Color blockColor) {
        //draw the main block
        g.setColor(blockColor);
        g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

        //draw some shading on the edges for a 3D effect
        g.setColor(Color.white); //blockColor.brighter());
        g.drawLine(x, y+1, x + BLOCK_SIZE, y+1);
        g.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE);
        g.setColor(blockColor.darker());
        g.drawLine(x+1, y, x+1, y + BLOCK_SIZE);
        g.drawLine(x+1, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);

        //draw a black border around it
        g.setColor(Color.BLACK);
        g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

    }//drawBlock

    /**
     * paint
     *
     * This methods draws the current state of the game on a given canvas.  The
     * field occupies the bottom left corner.  A title is at the top and the
     * current score is shown at right.
     * 
     * @param  g   the Graphics object for this application
     */
    public void paint(Graphics g) {
        //start with the background color
        Color bgColor = new Color(0x330088);  //medium-dark purple
        g.setColor(bgColor);
        g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);

        //Calculate the position of the playing field
        int margin = 5;
        int topSide = WINDOW_HEIGHT - ( NUM_ROWS * BLOCK_SIZE + margin + WINDOW_MARGIN);
        int bottomSide = topSide + NUM_ROWS * BLOCK_SIZE;
        int leftSide = WINDOW_MARGIN + margin;
        int rightSide = leftSide + NUM_COLS * BLOCK_SIZE;

        //Draw the playing field
        Color fieldColor = new Color(0x9966FF);  //lavender
        g.setColor(fieldColor);
        g.fillRect(leftSide, topSide, NUM_COLS * BLOCK_SIZE, NUM_ROWS * BLOCK_SIZE);

        //Draw a thick border around the playing field 
        g.setColor(Color.WHITE);
        for(int i = 1; i <= 5; ++i) {
            g.drawRect(leftSide - i, topSide - i,
                NUM_COLS * BLOCK_SIZE + margin , NUM_ROWS * BLOCK_SIZE + margin);
        }

        //Draw the blocks
        for(int row = 0; row < field.length; ++row) {
            for (int col = 0; col < field[row].length; ++col) {
                //calculate block position
                int xPos = leftSide + col * BLOCK_SIZE;
                int yPos = topSide + row * BLOCK_SIZE;

                //Verify the color index is valid
                // (NUM_COLORS + 1 is EMPTY)
                if ( (field[row][col] < 0) || (field[row][col] > EMPTY)) {
                    field[row][col] = INVALID_COLOR;
                }

                //draw the block
                if (field[row][col] != EMPTY) {
                    drawBlock(g, xPos, yPos, blockColors[field[row][col]]);
                }
            }//for
        }//for

        //draw the title
        g.setColor(Color.WHITE);
        Font bigFont = new Font("SansSerif", Font.BOLD, 32);
        g.setFont(bigFont);
        g.drawString("Mintris",45,50);

        //draw the score
        g.setColor(Color.WHITE);
        Font medFont = new Font("SansSerif", Font.PLAIN, 18);
        g.setFont(medFont);
        int leftMargin = rightSide + 15;
        g.drawString("Score:" + this.score, 70, 75);

    }//paint

    /**
     * keyPressed
     *
     * when the user presses a key, this method examines it to see
     * if the key is one that the program responds to and then calls the
     * appropriate method.
     */
    public void keyPressed(KeyEvent e) {
        //Don't handle the keypress until the prev one is handled
        synchronized(this) {
            while(synch);
            synch = true;
        }

        //Call the appropriate student method(s) based upon the key pressed
        int key = e.getKeyCode();
        switch(key) {
            //Move the piece left
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_KP_LEFT:
            case 'a':
            case 'A':
            if (validMove(currRow, currCol, LEFT)) {
                move(currRow, currCol, LEFT);
                --currCol;
            }
            break;

            //Move the piece right
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_KP_RIGHT:
            case 'd':
            case 'D':
            if (validMove(currRow, currCol, RIGHT)) {
                move(currRow, currCol, RIGHT);
                ++currCol;
            }
            break;

            //Drop the current piece down one row
            case KeyEvent.VK_DOWN:
            case 's':
            case 'S':
            if (validMove(currRow, currCol, DOWN)) {
                move(currRow, currCol, DOWN);
                ++currRow;
            }
            break;

            //Drop the current piece all the way down
            case ' ':
            while (validMove(currRow, currCol, DOWN)) {
                move(currRow, currCol, DOWN);
                ++currRow;
            }
            break;

            case KeyEvent.VK_UP:
            case 'w':
            case 'W':
            case 'r':
            case 'R':
            rotate(currRow, currCol);
            break;

            //Create a new game
            case 'n':
            case 'N':
            clearField();
            createRandomPiece();
            score = 0;
            break;

            //create a quick layout to aid in testing
            case 't':
            case 'T':
            clearField();
            for(int i = 3; i < field.length; ++i) {
                field[i][NUM_COLS/2] = BLUE_BLOCK;
            }
            for(int x = field.length - 2; x < field.length; ++x) {
                for (int y = 0; y < field[x].length; ++y) {
                    field[x][y] = RED_BLOCK;
                }
            }
            int lastRow = field.length - 1;
            field[lastRow][0]   = EMPTY;
            field[lastRow-1][1] = EMPTY;
            field[lastRow-1][0] = EMPTY;
            createRandomPiece();
            break;

            //Quit the game
            case 'q':
            case 'Q':
            System.exit(0);

        }//switch

        //Regardless of keypress check for a piece that has bottomed out
        if (! validMove(currRow, currCol, DOWN)) {
            removeRows();
            createRandomPiece();
        }

        //Release the synch for the next keypress
        synch = false;

        //redraw the screen so user can see changes
        repaint();
    }//keyPressed

    //These two method must be implemented but we don't care about these events.
    //We only care about key presses (see method above)
    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}

    /**
     * This method creates a window frame and displays the Mintris
     * game inside of it.  
     */
    public static void main(String[] args) {
        //Create a properly sized window for this program
        final JFrame myFrame = new JFrame();
        myFrame.setSize(WINDOW_WIDTH+10, WINDOW_HEIGHT+30);

        //Tell this window to close when someone presses the close button
        myFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });

        //Display a new Mintris object in the window
        Mintris mintrisGame = new Mintris();
        mintrisGame.clearField();
        mintrisGame.createRandomPiece();
        myFrame.addKeyListener(mintrisGame);
        myFrame.getContentPane().add(mintrisGame);

        //show the user
        myFrame.setVisible(true);

    }//main

}//class Mintris
