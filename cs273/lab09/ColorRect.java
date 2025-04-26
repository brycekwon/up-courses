import java.awt.*;

/**
 * This class defines a rectangle that has a color.
 * 
 * A ColorRect object is immutable--there are no public methods that
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
public class ColorRect {

    /////////////////////////
    // instance variables
    ////////////////////////

    // a polygon that defines our rectangle
    public //private
        Polygon p;

    // the color
    public //private
        Color color;

    /**
     * ColorRect constructor - creates a ColorRect object
     * @param x - the horizontal position of the top-left corner
     * @param y - the vertical position of the top-left corner
     * @param w - the rectangle's width, in pixels
     * @param h - the rectangle's height, in pixels
     * @param c - the rectangle's color
     */
    public ColorRect(int x, int y, int w, int h, Color c) {
        if (c == null || x < 0 || y < 0 || w < 0 || h < 0) {
            return;
        }

        // create polygon with the appropriate points
        p = new Polygon();
        p.addPoint(x,y);
        p.addPoint(x,y+h);
        p.addPoint(x+w,y+h);
        p.addPoint(x+w,y);

        color = c;
    }

    /**
     * Bugs/anomalies:
     *     No checking is done that ColorRect object is well-formed.  This
     *     method could therefore "bomb" under that condition.
     *
     * @param g - the Graphics object on which to paint
     */
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillPolygon(p);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}