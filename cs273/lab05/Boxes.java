/*****************************************************************
 * Boxes.java - Defines a Java program that draws some boxes
 *
 * Name: ****PUT YOUR NAME HERE****
 * Date: ****PUT THE DATE OF COMPLETTION HERE****
 * Status: incomplete
 *****************************************************************/

import java.awt.*;

/*****************************************************************
 * The class Boxes displays concentric rectangles on the screen in alternating
 * colors. It extends the BoxesMain class which handles all the mouse
 * interactions.
 *
 * This class inherits the numBoxes variables from BoxesMain. That variable
 * specifies the number of boxes to be drawn. The BoxesMain class will modify
 * this variable in response to mouse-clicks.
 *****************************************************************/
public class Boxes extends BoxesMain {

    /**
     * Paints the image on the graphics window
     *
     * @param g - a Graphics object
     */
    public void drawBoxes(Graphics g) {

       // for now, draw a black 10x10 box, centered at (250,250)
       
       for (int i = numBoxes; i >= 1; i--) {
            if (i % 2 == 0) {
               g.setColor(Color.RED);
               g.fillRect(245-(i*10/2),245-(i*10/2),10*i,10*i);
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(245-(i*10/2),245-(i*10/2),10*i,10*i);
            }
       }
       
       

    }//drawBoxes
}//class Boxes