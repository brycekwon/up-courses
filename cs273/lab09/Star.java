import java.awt.*;
import java.util.Arrays;
import java.util.List;

/***********************************************************************
 * CS 273 students - this class contains some concepts (e.g., Lists,
 * superclass constructors) that we have not studied yet.  Don't worry if
 * you don't understand all the code, especially inside the constructor.
 ***********************************************************************/

/**
 * This class defines a star-shaped object, in addition to a standard
 * points (as with a normal Polygon), a Star also has a color.  (The
 * present implementation, however only provides a constructor that
 * allows a white star to be created.)
 *
 * A Star object is mutable because it inherits some destructive
 * methods from Polygon.
 *
 * Author: Steven R. Vegdahl
 * Date: November 1999
 * Status: Debugged, but minimally commented
 *
 * Modified by: Steven R. Vegdahl
 * Date: 25 October 2000
 * Reason: Beef up the comments
 */
public class Star extends Polygon {

    //////////////////
    // instance variables
    //////////////////

    public//private
            Color color;

    //////////////////
    // class variables
    //////////////////

    // the respective x and y coordinates for the point in the star
    public//private
      static final List<Integer> starXs = Arrays.asList(4, 5, 8, 6, 7, 4, 1, 2, 0, 3);
    public//private
      static final List<Integer> starYs = Arrays.asList(0, 4, 4, 6, 10, 7, 10, 6, 4, 4);

    /**
     * Star constructor - creates a star
     * @param x- the horizontal position of the top-left corner of a
     *         bounding-box for the star
     * @param y- the vertical position of the top-left corner of a
     *         bounding-box for the star
     */
    public Star(int x, int y) {
        // Invoke the superclass's (Polygon) constructor
        super();

        int limit = Math.min(starXs.size(), starYs.size());

        for (int i = 0; i < limit; ++i) {
            this.addPoint(x + starXs.get(i), y + starYs.get(i));
        }
        color = Color.WHITE;
    }

    /**
     * @param g - the Graphics object on which to paint
     */
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillPolygon(this);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}