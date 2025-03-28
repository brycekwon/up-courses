import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class a defines a horizontal row of up to 6 stars.
 *
 * A StarRow object is immutable--there are no public methods that
 * change its state once it is created.
 *
 * Author: Steven R. Vegdahl
 * Date: November 1999
 * tatus: Debugged, but minimally commented
 *
 * Modified by: Steven R. Vegdahl
 * Date: 25 October 2000
 * Reason: Beef up the comments
 */
public class StarRow {

    ////////////////////////
    // instance variables
    ////////////////////////

    public//private
    Star[] stars;

    /**
     *
     * @param numStars
     * @param x - the horizontal position of the top-left corner of the leftmost star
     * @param y - the vertical position of the top-left corner of the leftmost star
     */
    public StarRow(int numStars, int x, int y) {
        stars = new Star[Math.min(Math.max(0, numStars), 6)];

        // fill in the array with stars across a row
        for (int i = 0; i < stars.length; i++) {

            stars[i] = new Star(x + (i * 30), y);

        }
    }

    /**
     * Some of the stars may not exist (i.e., might be null), so we
     * need to check each before we draw it.
     * @param g - the Graphics object on which to paint
     */
    public void paint(Graphics g) {
        // paint any star that is not null
        for (int i = 0; i < stars.length; ++i) {
            if (stars[i] != null) {
                stars[i].paint(g);
            }
        }
    }
}