import javax.swing.*;
import java.awt.*;

/**
 * This frame class that displays an American flag.  It is not
 * interactive, and will therefore not respond to mouse-clicks, etc.
 *
 * Author: Steven R. Vegdahl
 * Date: November 1999
 * Status: Debugged, but minimally commented
 *
 * Modified by: Steven R. Vegdahl
 * Date: 25 October 2000
 * Reason: Beef up the comments
 *
 * Modified by: Steven R. Vegdahl
 * Date: 7 January 2003
 * Reason: Changed from an Applet class to a JFrame
 */
public class FlagViewer extends JFrame {

    /**
     * @param canvas - the FlagCanvas object containing the flag we're displaying
     */
    public FlagViewer(FlagCanvas canvas) {
        this.setBackground(Color.GRAY.brighter());

        setSize(400, 280);
        setTitle("A Flag");

        this.getContentPane().add(canvas);
    }
}