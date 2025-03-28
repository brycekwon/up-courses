////////////////////////////////////////////////////////
// FaceLabel.java - a label for our face-applet
////////////////////////////////////////////////////////

// imports
import java.awt.*;

////////////////////////////////////////////////////////
// class FaceLabel - a label that can be drawn on an applet
//
// A FaceLabel contains a string, position, font and color. The drawOn method
// can be used to draw it on a Graphics object.
////////////////////////////////////////////////////////
public class FaceLabel
{
    // x- and y-positions for our label
    private int x;
    private int y;
    
    // the font and Color
    private Font myFont;
    private Color myColor;
    
    // the text
    private String myString;

    ////////////////////////////////////////////////////////
    // FaceLabel - constructor
    //
    // calling sequence:
    //   aFaceLabel = new FaceLabel(str, x, y, font, color);
    //
    // parameters:
    //   str - the label's string
    //   x - the x-position for the label
    //   y - the y-position for the label
    //   font - the label's font
    //   color- the label's text-color
    //
    // side-effects:
    //   the label object is created
    //
    // bugs/anomalies:
    //   none known
    //
    ////////////////////////////////////////////////////////
    public FaceLabel(String str, int xx, int yy, Font font, Color color) {
        // initialize the x- and y-position
        x = xx;
        y = yy;
        
        // initialize the string
        myString = str;
        
        // initialize the font and color
        myFont = font;
        myColor = color;
    }

    ////////////////////////////////////////////////////////
    // FaceLabel - constructor
    //
    // calling sequence:
    //   aFaceLabel = new FaceLabel(str, x, y);
    //
    // parameters:
    //   str - the label's string
    //   x - the x-position for the label
    //   y - the y-position for the labe
    //
    // side-effects:
    //   the label object is created.  A default font and color are used
    //
    // bugs/anomalies:
    //   none known
    //
    ////////////////////////////////////////////////////////
    public FaceLabel(String str, int xx, int yy) {
        // invoke the main constructor, filling in a default font and label
        this(str, xx, yy, new Font("TimesRoman", Font.BOLD, 14), Color.RED);
    }
    
    

    ////////////////////////////////////////////////////////
    // drawOn - draws the label on a graphics object
    //
    // calling sequence:
    //   aFaceLabel.drawOn(g);
    //
    // parameters:
    //   g - the Graphics object on which to draw the label
    //
    // side-effects:
    //   the label object is drawn on the graphics object
    //
    // bugs/anomalies:
    //   none known
    //
    ////////////////////////////////////////////////////////
    public void drawOn(Graphics g) {
        
        // set the color and font, then draw the string
        g.setColor(myColor);
        g.setFont(myFont);
        g.drawString(myString, x, y);
    }
}
