import java.awt.*;

/**
 * This class defines a blue rectangle that that contains stars.
 *
 * A StarRect object is immutable--there are no public methods that
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
public class StarRect {

    ///////////////////////////
    // Instance variables
    ///////////////////////////

    // The rows of stars
    public//private
      StarRow[] rows;

    // The basic rectangle (with a color)
    public//private
      ColorRect rect;

    /**
     * @param x - the horizontal position of the top-left corner
     * @param y - the vertical position of the top-left corner
     */
    public StarRect(int x, int y) {
        rows = new StarRow[9];

        // create rows of stars and fill in the array
        for (int i = 0; i < rows.length; i += 2) {
            rows[i] = new StarRow(6,  x + 8,  y + 6 + (i * 12));
        }
        for (int i = 1; i < rows.length; i += 2) {
            rows[i] = new StarRow(5, x + 23,  y + 6 + (i * 12));
        }

        rect = new ColorRect(x, y, 180, 7 * 17, Color.BLUE.darker().darker());
    }

    /**
     *
     * @param g - the Graphics object on which to paint
     */
    public void paint(Graphics g) {
        rect.paint(g);

        for (StarRow row : rows) {
            row.paint(g);
        }
    }
}