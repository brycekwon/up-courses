import java.awt.*;

/**
 * A class onto which a Flag can be drawn
 * 
 * @author Steven R. Vegdahl 
 * @version 13 March 2003
 */
public class FlagCanvas extends Canvas
{
    // instance variable--the flag we draw
    public//private
        Flag flag;

    /**
     * FlagCanvas constructor - creates a FlagCanvas object
     * @param flag - the flag we draw
     */
    public FlagCanvas(Flag flag) {
        this.flag = flag;
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * @param g - the Graphics object on which to paint
     */
    public void paint(Graphics g) {
        // paint the flag
        flag.paint(g);
    }
}