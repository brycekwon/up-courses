/*****************************************************************
 * Checkerboard.java - Defines a Java app that draws some boxes
 *
 * Name: ****PUT YOUR NAME HERE****
 * Date: ****PUT THE DATE OF COMPLETTION HERE****
 * Status: incomplete
 *****************************************************************/

import java.awt.*;

/*****************************************************************
 * The class Checkerboard displays an NxN checkerboard on the screen. It extends
 * the CheckerboardMain class which handles all the mouse interactions.
 *
 * This class inherits the numSquares variable from CheckerboardMain. That
 * variable specifies the number of squares along one side of the checkerboard.
 * So, for an NxN checkerboard, numSquares = N. The CheckerboardMain class will
 * modify this variable in response to mouse-clicks.
 *****************************************************************/
public class Checkerboard extends CheckerboardMain {
    /**
     * Paints the app's image on the graphics window.
     *
     * @param g - The app's Graphics object
     */
    public void drawCheckerboard(Graphics g) {          
        for (int i = 0; i < numSquares; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < numSquares; j++) {
                    if (j % 2 == 0) {
                        g.setColor(Color.BLACK);
                        g.fillRect(0+(j*10),0+(i*10),10,10);
                    } else {
                        g.setColor(Color.RED);
                        g.fillRect(0+j*10,0+(i*10),10,10);
                    }
                }
            } else {
                for (int j = 0; j < numSquares; j++) {
                    if (j % 2 == 0) {
                        g.setColor(Color.RED);
                        g.fillRect(0+(j*10),0+(i*10),10,10);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect(0+(j*10),0+(i*10),10,10);
                    }
                }
            }
        }
        
        
    }//drawCheckerboard
}//class Checkerboard
