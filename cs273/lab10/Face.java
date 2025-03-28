////////////////////////////////////////////////////////
// Face.java - a face that knows how to draw itself
////////////////////////////////////////////////////////

// imports
import java.awt.*;

////////////////////////////////////////////////////////
// class Face - a face, which can draw itself
//
// This class represents a face, which has eyes, nose, mouth, etc.
// A head portion is typically shaped as an oval, with the
// other parts being proportional.
//
// A face has a height and a width, and an x- and y- position. These
// four components delimit the bounding-box of the oval-shaped head.
//
// A Face object provides the following public methods:
//   - Face - the constructor
//   - setLocation - moves the face to a different position
//   - drawOn - draws the face on a graphics object
//   - getWidth - tells the face's width
//   - getHeight - tells the face's height
//
////////////////////////////////////////////////////////
public class Face {

    ///////////////////////////////
    // instance variables
    ///////////////////////////////
    private int width; // width of the head
    private int height; // height of the head
    private int x; // position of head's left
    private int y; // position of head's top

    ////////////////////////////////////////////////////////
    // Face - face constructor
    //
    // calling sequence:
    //   aFace = new Face(w, h);
    //
    // parameters:
    //   w - the face's width
    //   h - the face's height
    //
    // side-effects:
    //   the face object is created and initialized. The bounding box
    //   for the head is set at (0,0).
    //
    // bugs/anomalies:
    //   the height and width denote those of the head.  It is possible that
    //   ears, hair, etc. may extend beyond those bounds.
    //
    ////////////////////////////////////////////////////////
    public Face(int w, int h) {

        // set the height and width from the parameters
        width = w;
        height = h;

        // set the position to (0,0)
        x = 0;
        y = 0;
    }

    ////////////////////////////////////////////////////////
    // setLocation - puts the Face at a new location
    //
    // calling sequence:
    //   aFace.setLocation(xVal, yVal);
    //
    // parameters:
    //   xVal - the horizontal position of the leftmost part of the head
    //   yVal - the vertical position of the topmost part of the head
    //
    // side-effects:
    //   the location of the face is changed
    //
    // bugs/anomalies:
    //   the x and y position denote those of the head.  It is possible that
    //   ears, hair, etc. may extend beyond those bounds.
    //
    ////////////////////////////////////////////////////////
    public void setLocation(int xVal, int yVal) {
        // change the position
        x = xVal;
        y = yVal;
    }

    ////////////////////////////////////////////////////////
    // drawOn - draws the face
    //
    // calling sequence:
    //   aFace.drawOn(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the face
    //
    // side-effects:
    //   the face is drawn on the graphics object.  The graphics object's
    //   color is unchanged.
    //
    // bugs/anomalies:
    //   The 'drawOn' method is declared as 'final', meaning that no subclass is
    //   allowed to override it. The reason for this is to guarantee that
    //   the drawOn method always preserves the graphics object's color.  A
    //   subclass may effectively override this method by overriding the
    //   'helpDrawOn' method, which is the method that actually does the
    //   drawing.  The 'drawOn' method consists simply of a call to 'drawOn',
    //   which is "wrapped" in a graphics-color save/restore.
    //
    ////////////////////////////////////////////////////////
    public final void drawOn(Graphics g) {
        Color temp = g.getColor();
        this.helpDrawOn(g);
        g.setColor(temp);
    }

    ////////////////////////////////////////////////////////
    // getWidth - tells the face's width
    //
    // calling sequence:
    //   anInt = aFace.getWidth();
    //
    // return value:
    //   the face's width
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   the width denotes that of the head.  It is possible that
    //   ears, hair, etc. may extend beyond this.
    //
    ////////////////////////////////////////////////////////
    public int getWidth() { return width; }

    ////////////////////////////////////////////////////////
    // getHeight - tells the face's height
    //
    // calling sequence:
    //   anInt = aFace.getHeight();
    //
    // return value:
    //   the face's height
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   the height denotes that of the head. It is possible that
    //   ears, hair, etc. may extend beyond this.
    //
    ////////////////////////////////////////////////////////
    public int getHeight() { return height; }

    ////////////////////////////////////////////////////////
    // helpDrawOn - draws the face
    //
    // calling sequence:
    //   aFace.helpDrawOn(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the face
    //
    // side-effects:
    //   the face is drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This method does not preserve the graphics object's color, because
    //   it is assumed that anyone who wants this done will use the (public)
    //   method, 'drawOn'.
    //
    ////////////////////////////////////////////////////////
    protected void helpDrawOn(Graphics g) {
        // draw the head, first so that other features will be visible over it
        this.drawHead(g);

        // draw the other features
        this.drawEyes(g);
        this.drawMouth(g);
        this.drawNose(g);
        this.drawHair(g);
        this.drawEars(g);
    }

    ////////////////////////////////////////////////////////
    // drawHead - draws the "head" portion of the face
    //
    // calling sequence:
    //   aFace.drawHead(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the face
    //
    // side-effects:
    //   the head is drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This only draws a bare head (i.e., a blank oval).  Other features must
    //   be drawn after the head portion is drawn.
    //
    ////////////////////////////////////////////////////////
    protected final void drawHead(Graphics g) {
        g.setColor(headColor());
        g.fillOval(x, y, width, height);
    }

    ////////////////////////////////////////////////////////
    // drawEyes - draws the eyes on the head
    //
    // calling sequence:
    //   aFace.drawEyes(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the eyes
    //
    // side-effects:
    //   the eyes are drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This must be called after 'drawHead' otherwise, the eyes will not
    //   be visible, having been occluded by the head.
    //
    ////////////////////////////////////////////////////////
    protected void drawEyes(Graphics g) {
        // draw the white portion of the eyes.
        drawEyeWhites(g);

        // draw the colored portion of the eyes.
        drawEyeCenters(g);

    }

    ////////////////////////////////////////////////////////
    // drawEyeWhites - draws the whites of the eyes on the head
    //
    // calling sequence:
    //   aFace.drawEyeWhites(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the eyes
    //
    // side-effects:
    //   the eye-whites are drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This must be called after 'drawHead' otherwise, the eyes will not
    //   be visible, having been occluded by the head.
    //
    ////////////////////////////////////////////////////////
    protected void drawEyeWhites(Graphics g) {
        // draw the white portion of the eyes.  The right eye should start
        // 18% in from the left portion of the head and 38% of the way down
        // from the top of the head.  The height and width respectively be
        // 11% of those of the head.  The left eye should be similarly placed,
        // but on the other side.    
        g.setColor(Color.white);
        g.fillOval(pixelX(18), pixelY(38), distX(11), distY(11));
        g.fillOval(pixelX(74), pixelY(38), distX(11), distY(11));
    }

    ////////////////////////////////////////////////////////
    // drawEyeCenters - draws the eye-centers on the head
    //
    // calling sequence:
    //   aFace.drawEyeCenters(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the eyes
    //
    // side-effects:
    //   the eye-centers are drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This must be called after 'drawEyeWhites' otherwise, the whites of the
    //   eyes will cover the centers.
    //
    ////////////////////////////////////////////////////////
    protected void drawEyeCenters(Graphics g) {
        // draw the colored portion of the eyes.  They should be inside
        // the white portions, as drawn by drawEyeWhites.
        g.setColor(eyeColor());
        g.fillOval(pixelX(20), pixelY(40), distX(8), distY(8));
        g.fillOval(pixelX(76), pixelY(40), distX(8), distY(8));
    }

    ////////////////////////////////////////////////////////
    // drawNose - draws the nose on the head
    //
    // calling sequence:
    //   aFace.drawNose(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the nose
    //
    // side-effects:
    //   the nose is drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This must be called after 'drawHead' otherwise, the nose will not
    //   be visible, having been occluded by the head.
    //
    ////////////////////////////////////////////////////////
    protected void drawNose(Graphics g) {
        // create a triangular nose, fitting it inside a bounding-box that
        // is approximately in the middle of the face, and is 20% of the
        // face's width and 15% of its height
        Polygon2 p = new Polygon2();
        p.addPoint(100,0);
        p.addPoint(200,200);
        p.addPoint(0,200);
        p = p.fitIn(pixelX(40), pixelY(50), distX(20), distY(15));

        // draw the nose object as brown (dark orange)
        g.setColor(noseColor());
        g.fillPolygon(p);
    }

    ////////////////////////////////////////////////////////
    // drawMouth - draws the mouth on the head
    //
    // calling sequence:
    //   aFace.drawMouth(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the mouth
    //
    // side-effects:
    //   the mouth is drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This must be called after 'drawHead' otherwise, the mouth will not
    //   be visible, having been occluded by the head.
    //
    ////////////////////////////////////////////////////////
    protected void drawMouth(Graphics g) {
        // draw a mouth that is a thin horizontal rectangle, whose color
        // is the appropriate one.  The mouth should be hear the bottom
        // of the face, and be a bit more than half of the face's width
        g.setColor(this.mouthColor());
        g.fillRect(pixelX(20), pixelY(80), distX(60), distY(5));
    }

    ////////////////////////////////////////////////////////
    // drawHair - draws the hair on the head
    //
    // calling sequence:
    //   aFace.drawHair(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the hair
    //
    // side-effects:
    //   the hair is drawn on the graphics object. The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   This must be called after 'drawHead' otherwise, the hair will not
    //   be visible, having been occluded by the head.
    //
    ////////////////////////////////////////////////////////
    protected void drawHair(Graphics g) {
        // Use the appropiate color. Then draw an upside down half circle to resemble hair        
        g.setColor(this.hairColor());
        Polygon2 hair = new Polygon2(50);
        hair = hair.rotateBy(180);
        hair = hair.fitIn(pixelX(10),pixelY(0),distX(80),distY(20));
        g.fillPolygon(hair);
    }

    ////////////////////////////////////////////////////////
    // drawEars - draws the ears on the head
    //
    // calling sequence:
    //   aFace.drawEars(g);
    //
    // parameters:
    //   g - the graphics object on which to draw the ears
    //
    // side-effects:
    //   the hair is drawn on the graphics object.  The graphics object's
    //   color is modified.
    //
    // bugs/anomalies:
    //   None known.
    //
    ////////////////////////////////////////////////////////
    protected void drawEars(Graphics g) {
        // use the same color for the ears as for the the head
        g.setColor(headColor());

        // draw the ears as ovals that extend a bit beyond the head
        g.fillOval(pixelX(-10), pixelY(45), distX(12), distY(20));
        g.fillOval(pixelX(98), pixelY(45), distX(12), distY(20));
    }

    ////////////////////////////////////////////////////////
    // mouthColor - tells the color of the mouth
    //
    // calling sequence:
    //   aColor = aFace.mouthColor();
    //
    // return-value:
    //   the color of the mouth.
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   None known.
    //
    ////////////////////////////////////////////////////////
    protected Color mouthColor() { return Color.red; }

    ////////////////////////////////////////////////////////
    // hairColor - tells the color of the hair
    //
    // calling sequence:
    //   aColor = aFace.hairColor();
    //
    // return-value:
    //   the color of the hair.
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   None known.
    //
    ////////////////////////////////////////////////////////
    protected Color hairColor() { return Color.yellow; }

    ////////////////////////////////////////////////////////
    // eyeColor - tells the color of the eyes
    //
    // calling sequence:
    //   aColor = aFace.eyeColor();
    //
    // return-value:
    //   the color of the eyes.
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   None known.
    //
    ////////////////////////////////////////////////////////
    protected Color eyeColor() { return Color.blue; }

    ////////////////////////////////////////////////////////
    // noseColor - tells the color of the nose
    //
    // calling sequence:
    //   aColor = aFace.noseColor();
    //
    // return-value:
    //   the color of the nose
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   None known.
    //
    ////////////////////////////////////////////////////////
    protected Color noseColor() { return Color.RED; }
    
    ////////////////////////////////////////////////////////
    // headColor - tells the color of the head
    //
    // calling sequence:
    //   aColor = aFace.headColor();
    //
    // return-value:
    //   the color of the head.
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   None known.
    //
    ////////////////////////////////////////////////////////
    protected Color headColor() { return Color.pink; }

    ////////////////////////////////////////////////////////
    // pixelX - computes a pixel location that is the given percentage
    //   of the way across the head (from the left)
    //
    // calling sequence:
    //   anInt = aFace.pixelX(xv);
    //
    // parameters:
    //   xv - the percentage of the way across the head.  This is typically
    //        between 0.0 and 100.0, but may be smaller or larger if you want
    //        to be beyond the bounds of the head.
    //
    // return-value:
    //   the horizontal pixel-location that best approximates the given
    //   percentage
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   Rounding will occur, so the result will be inaccurate by as much as
    //   half a pixel.
    //
    ////////////////////////////////////////////////////////
    protected int pixelX(double xv) {
        // compute/return the appropriate value
        return (int)Math.round(x + xv*width/100.0);
    }

    ////////////////////////////////////////////////////////
    // pixelY - computes a pixel location that is the given percentage
    //   of the way down from the top of the head
    //
    // calling sequence:
    //   anInt = aFace.pixelY(yv);
    //
    // parameters:
    //   yv - the percentage of the way down the head.  This is typically
    //        between 0.0 and 100.0, but may be smaller or larger if you want
    //        to be beyond the bounds of the head.
    //
    // return-value:
    //   the vertical pixel-location that best approximates the given percentage
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   Rounding will occur, so the result will be inaccurate by as much as
    //   half a pixel.
    //
    ////////////////////////////////////////////////////////
    protected int pixelY(double yv) {
        return (int)Math.round(y + yv*height/100.0);
    }

    ////////////////////////////////////////////////////////
    // distX - computes a horizontal pixel-distance, as a percentage of
    //         the width of the head
    //
    // calling sequence:
    //   anInt = aFace.distX(xv);
    //
    // parameters:
    //   xv - the percentage of the width of the head.  This is typically
    //        between 0.0 and 100.0, but may be larger.
    //
    // return-value:
    //   the horizontal pixel-distance that best approximates the given
    //   percentage
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   Rounding will occur, so the result will be inaccurate by as much as
    //   half a pixel.
    //
    ////////////////////////////////////////////////////////
    protected int distX(double xv) {
        return (int)Math.round(xv*width/100.0);
    }

    ////////////////////////////////////////////////////////
    // distY - computes a vertical pixel-distance, as a percentage of
    //         the height of the head
    //
    // calling sequence:
    //   anInt = aFace.distY(yv);
    //
    // parameters:
    //   yv - the percentage of the height of the head.  This is typically
    //        between 0.0 and 100.0, but may be larger.
    //
    // return-value:
    //   the vertical pixel-distance that best approximates the given
    //   percentage
    //
    // side-effects:
    //   none
    //
    // bugs/anomalies:
    //   Rounding will occur, so the result will be inaccurate by as much as
    //   half a pixel.
    //
    ////////////////////////////////////////////////////////
    protected int distY(double yv) {
        return (int)Math.round(yv*height/100.0);
    }
}
