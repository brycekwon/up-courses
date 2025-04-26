import java.awt.*;

/**
 * This class defines an object that represents an American flag.
 * In addition to a constructor, it provides a paint method that
 * allows it to be painted on a graphics window.
 * 
 * A Flag object is immutable--there are no public methods that
 * change its state once it is created.
 *
 * Author: Steven R. Vegdahl
 * Date: November 1999
 * Status: Debugged, but minimally commented
 *
 * Modified by: Steven R. Vegdahl
 * Date: 25 October 2000
 * Reason: Beef up the comments 
 */
public class Flag {

    ///////////////////////
    // instance variables
    /////////////////////

    // the rectangle with stars
    public //private
        StarRect starRect;

    public //private
        ColorRect[] stripes;

    ///////////////////////
    // class variables
    /////////////////////
    
    private static Color myRed = Color.RED.darker();

    public Flag() {
        this(5, 5);
    }
    
    /**
     * Flag constructor - creates a flag
     * @param x - the horizontal position of the top-left corner of the flag
     * @param y - the vertical position of the top-left corner of the flag
     */
    public Flag(int x, int y) {
        stripes = new ColorRect[13];

        // create the stripes, alternating red and white
        for (int i = 0; i < stripes.length; i += 2) {
            stripes[i] = new ColorRect(x, y + (17 * i), 375, 17, myRed);
        }
        for (int i = 1; i < stripes.length; i += 2) {
            stripes[i] = new ColorRect(x, y + (17 * i), 375, 17, Color.WHITE);
        }
        

        // create rectangle with stars
        starRect = new StarRect(x, y);
    }

    /**
     * paint - paints the object
     * @param g - the Graphics object on which to paint
     */
    public void paint(Graphics g) {
        // paint the stripes
        for (ColorRect stripe : stripes) {
            stripe.paint(g);
        }

        // paint the rectangle with stars
        starRect.paint(g);
    }

    public void view() {
        FlagCanvas canv = new FlagCanvas(this);
        FlagViewer fv = new FlagViewer(canv);
        fv.setVisible(true);
    }
}