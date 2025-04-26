import java.awt.*;

/**
 * class Lab4BaseArea -- a panel for drawing a robot, suitable for
 * subclassing
 *
 * @author Steven R. Vegdahl
 * @version 2 Feb 2019
 */
public class Lab4BaseArea extends Canvas {

    // the frame object with which we're working
    private Lab4Frame frame;

    // tells whether we've already translated our polygons
    private boolean alreadyTranslated = false;
    
    /**
     * constructor
     */
    public Lab4BaseArea() {
        // create all our polygons
        init();
        
        // set our size
        setSize(500,800);
        setPreferredSize(getSize());
        setMinimumSize(getSize());
        setMaximumSize(getSize());
    }
    
    /**
     * setFrame -- lets us know who our frame is
     */
    public void setFrame(Lab4Frame f) {
        // set the 'frame' instance variable unless it's already been done
        if (frame == null) {
            frame = f;
        }
    }
    
    /**
     * isChecked -- tells whether a checkbox is checked
     * 
     * @param s the string that names the checkbox
     * @return true if that checkbox is checked
     */
    public boolean isChecked(String s) {
        // ask our frame
        return frame.isChecked(s);
    }
    
    /**
     * whistleMoreRecent -- tells whether 'whistle' was checked more recently than
     * 'frown'
     * 
     * @return true if 'whistle' was checked more recently
     */
    public boolean whistleMoreRecent() {
        // ask our frame
        return frame.whistleMoreRecent();
    }
    
    /**
     * totalNumChecked -- the total number of check-boxes that are checked
     * 
     * @return the total number of checkboxes that are checked
     */
    public int totalNumChecked() {
        // ask the frame
        return frame.totalNumChecked();
    }    
     
    /**
     * repaint -- repaints the visible area
     */
    public final void repaint() {
        // get our graphics object
        Graphics g = this.getGraphics();
        
        // return if graphics object does not exist
        if (g == null) {
            return;
        }
        // paint our background by drawing a large rectangle that is the same color as
        // as our frame's
        g.setColor(frame.getBackground());
        g.fillRect(0,0,3000,3000);
        
        // invoke our 'paint' method to draw the robot
        this.paint(g);
    }

    // instance variables for all the body parts
    protected final Polygon body = new Polygon();
    protected final Polygon longLeftArm = new Polygon();
    protected final Polygon leftArm = new Polygon();
    protected final Polygon shortLeftArm = new Polygon();
    protected final Polygon longLeftArmBentUp = new Polygon();
    protected final Polygon leftArmBentUp = new Polygon();
    protected final Polygon longLeftArmBentDown = new Polygon();
    protected final Polygon leftArmBentDown = new Polygon();
    protected final Polygon longRightArm = new Polygon();
    protected final Polygon rightArm = new Polygon();
    protected final Polygon shortRightArm = new Polygon();
    protected final Polygon longRightArmBentUp = new Polygon();
    protected final Polygon rightArmBentUp = new Polygon();
    protected final Polygon longRightArmBentDown = new Polygon();
    protected final Polygon rightArmBentDown = new Polygon();
    protected final Polygon rightLeg = new Polygon();
    protected final Polygon rightLegBent = new Polygon();
    protected final Polygon leftLeg = new Polygon();
    protected final Polygon leftLegBent = new Polygon();
    protected final Polygon rightLegBigFeet = new Polygon();
    protected final Polygon leftLegBigFeet = new Polygon();
    protected final Polygon rightLegBentBigFeet = new Polygon();
    protected final Polygon leftLegBentBigFeet = new Polygon();
    protected final Polygon rightEar = new Polygon();
    protected final Polygon bigRightEar = new Polygon();
    protected final Polygon leftEar = new Polygon();
    protected final Polygon bigLeftEar = new Polygon();
    protected final Polygon smileMouth = new Polygon();
    protected final Polygon frownMouth = new Polygon();
    protected final Polygon whistleMouth = new Polygon();
    protected final Polygon leftEye = new Polygon();
    protected final Polygon leftEyeOut = new Polygon();
    protected final Polygon leftEyeIn = new Polygon();
    protected final Polygon rightEye = new Polygon();
    protected final Polygon rightEyeIn = new Polygon();
    protected final Polygon rightEyeOut = new Polygon();

    // an array containing all our polygons
    private Polygon[] allPolygons = {
            body,
            longLeftArm, leftArm, shortLeftArm, longLeftArmBentUp,
            leftArmBentUp, longLeftArmBentDown, leftArmBentDown,
            longRightArm, rightArm, shortRightArm, longRightArmBentUp,
            rightArmBentUp, longRightArmBentDown, rightArmBentDown,
            rightLeg, rightLegBent, leftLeg, leftLegBent,
            rightLegBigFeet, leftLegBigFeet, rightLegBentBigFeet,
            leftLegBentBigFeet,
            rightEar, bigRightEar, leftEar, bigLeftEar,
            smileMouth, frownMouth, whistleMouth,leftEye, leftEyeOut, leftEyeIn, rightEye, rightEyeIn,
            rightEyeOut,
    };
    
    /**
     * init = initialized all our polygons
     */
    private void init() {

        // points for the body
        body.addPoint(175,100);
        body.addPoint(325,100);
        body.addPoint(325,160);
        body.addPoint(265,160);
        body.addPoint(265,185);
        body.addPoint(325,185);
        body.addPoint(325,385);
        body.addPoint(175,385);
        body.addPoint(175,185);
        body.addPoint(235,185);
        body.addPoint(235,160);
        body.addPoint(175,160);

        // points for the long left arm
        longLeftArm.addPoint(325,225);
        longLeftArm.addPoint(325,205);
        longLeftArm.addPoint(500,215);

        // points for the left arm
        leftArm.addPoint(325,225);
        leftArm.addPoint(325,205);
        leftArm.addPoint(400,215);

        // points for the short left arm 
        shortLeftArm.addPoint(325,225);
        shortLeftArm.addPoint(325,205);
        shortLeftArm.addPoint(345,215);

        // points for the long left arm bent up
        longLeftArmBentUp.addPoint(325,225);
        longLeftArmBentUp.addPoint(325,205);
        longLeftArmBentUp.addPoint(415,205);
        longLeftArmBentUp.addPoint(420,100);
        longLeftArmBentUp.addPoint(425,220);

        // points for the left arm bent up
        leftArmBentUp.addPoint(325,225);
        leftArmBentUp.addPoint(325,205);
        leftArmBentUp.addPoint(375,205);
        leftArmBentUp.addPoint(380,150);
        leftArmBentUp.addPoint(385,220);

        // set points for the down-bent arms by reflecting the up-bent ones
        reflectY(longLeftArmBentUp, longLeftArmBentDown, 217);
        reflectY(leftArmBentUp, leftArmBentDown, 217);
        
        // set points for all the kinds of right arms by reflecting the respective
        // left arms
        reflectX(longLeftArm, longRightArm, 250);
        reflectX(leftArm, rightArm, 250);
        reflectX(shortLeftArm, shortRightArm, 250);
        reflectX(longLeftArmBentUp, longRightArmBentUp, 250);
        reflectX(leftArmBentUp, rightArmBentUp, 250);
        reflectX(longLeftArmBentDown, longRightArmBentDown, 250);
        reflectX(leftArmBentDown, rightArmBentDown, 250);
       
        // set points for the left leg
        leftLeg.addPoint(250,385);
        leftLeg.addPoint(290,385);
        leftLeg.addPoint(300,465);
        leftLeg.addPoint(340,475);
        leftLeg.addPoint(270,475);

        // set points for the bent left leg
        leftLegBent.addPoint(250,385);
        leftLegBent.addPoint(290,385);
        leftLegBent.addPoint(295,420);
        leftLegBent.addPoint(335,415);
        leftLegBent.addPoint(340,450);
        leftLegBent.addPoint(320,435);
        leftLegBent.addPoint(275,440);

        // set points for the left leg with big feet
        leftLegBigFeet.addPoint(250,385);
        leftLegBigFeet.addPoint(290,385);
        leftLegBigFeet.addPoint(300,440);
        leftLegBigFeet.addPoint(380,425);
        leftLegBigFeet.addPoint(340,475);
        leftLegBigFeet.addPoint(270,475);
        
        // set points for the bent left leg with big feet
        leftLegBentBigFeet.addPoint(250,385);
        leftLegBentBigFeet.addPoint(290,385);
        leftLegBentBigFeet.addPoint(295,420);
        leftLegBentBigFeet.addPoint(385,410);
        leftLegBentBigFeet.addPoint(390,450);
        leftLegBentBigFeet.addPoint(365,475);
        leftLegBentBigFeet.addPoint(320,435);
        leftLegBentBigFeet.addPoint(275,440);
        
        // set points for the right-leg versions by reflecting
        // the left-leg ones
        reflectX(leftLeg, rightLeg, 250);
        reflectX(leftLegBent, rightLegBent, 250);
        reflectX(leftLegBigFeet, rightLegBigFeet, 250);
        reflectX(leftLegBentBigFeet, rightLegBentBigFeet, 250);
        
        // set points for the left ear
        leftEar.addPoint(325,120);
        leftEar.addPoint(330,115);
        leftEar.addPoint(330,130);
        leftEar.addPoint(325,125);
        
        // set points for the big left ear        
        bigLeftEar.addPoint(325,130);
        bigLeftEar.addPoint(400,100);
        bigLeftEar.addPoint(355,185);
        bigLeftEar.addPoint(325,135);
        
        // set points for the right ears by reflecting the
        // respective left ears
        reflectX(leftEar, rightEar, 250);
        reflectX(bigLeftEar, bigRightEar, 250);
        
        // set points for the smile mouth
        smileMouth.addPoint(200,140);
        smileMouth.addPoint(225,150);
        smileMouth.addPoint(250,155);
        smileMouth.addPoint(275,150);
        smileMouth.addPoint(300,140);
        smileMouth.addPoint(275,145);
        smileMouth.addPoint(250,150);
        smileMouth.addPoint(225,145);
        
        // set points for the frown mouth by reflecting
        // the smile mouth        
        reflectY(smileMouth, frownMouth, 148);
        
        // set points for the whistle mouth
        whistleMouth.addPoint(248,145);
        whistleMouth.addPoint(252,145);
        whistleMouth.addPoint(254,147);
        whistleMouth.addPoint(254,149);
        whistleMouth.addPoint(252,151);
        whistleMouth.addPoint(248,151);
        whistleMouth.addPoint(246,149);
        whistleMouth.addPoint(246,147);
        
        // set points for the right eye looking
        // straight ahead
        rightEye.addPoint(225,115);
        rightEye.addPoint(231,115);
        rightEye.addPoint(235,118);
        rightEye.addPoint(237,121);
        rightEye.addPoint(237,124);
        rightEye.addPoint(235,127);
        rightEye.addPoint(231,130);
        rightEye.addPoint(228,124);
        rightEye.addPoint(230,122);
        rightEye.addPoint(227,120);
        rightEye.addPoint(226,120);
        rightEye.addPoint(225,122);
        rightEye.addPoint(226,124);
        rightEye.addPoint(228,124);
        rightEye.addPoint(231,130);
        rightEye.addPoint(225,130);
        rightEye.addPoint(220,127);
        rightEye.addPoint(217,124);
        rightEye.addPoint(217,121);
        rightEye.addPoint(220,118);
        
        // set points for the right eye looking in
        rightEyeIn.addPoint(225,115);
        rightEyeIn.addPoint(231,115);
        rightEyeIn.addPoint(235,118);
        rightEyeIn.addPoint(237,121);
        rightEyeIn.addPoint(237,124);
        rightEyeIn.addPoint(235,127);
        rightEyeIn.addPoint(231,130);
        rightEyeIn.addPoint(7+228,124);
        rightEyeIn.addPoint(7+230,122);
        rightEyeIn.addPoint(7+227,120);
        rightEyeIn.addPoint(7+226,120);
        rightEyeIn.addPoint(7+225,122);
        rightEyeIn.addPoint(7+226,124);
        rightEyeIn.addPoint(7+228,124);
        rightEyeIn.addPoint(231,130);
        rightEyeIn.addPoint(225,130);
        rightEyeIn.addPoint(220,127);
        rightEyeIn.addPoint(217,124);
        rightEyeIn.addPoint(217,121);
        rightEyeIn.addPoint(220,118);
        
        // set points for the right eye looking out
        rightEyeOut.addPoint(225,115);
        rightEyeOut.addPoint(231,115);
        rightEyeOut.addPoint(235,118);
        rightEyeOut.addPoint(237,121);
        rightEyeOut.addPoint(237,124);
        rightEyeOut.addPoint(235,127);
        rightEyeOut.addPoint(231,130);
        rightEyeOut.addPoint(-7+228,124);
        rightEyeOut.addPoint(-7+230,122);
        rightEyeOut.addPoint(-7+227,120);
        rightEyeOut.addPoint(-7+226,120);
        rightEyeOut.addPoint(-7+225,122);
        rightEyeOut.addPoint(-7+226,124);
        rightEyeOut.addPoint(-7+228,124);
        rightEyeOut.addPoint(231,130);
        rightEyeOut.addPoint(225,130);
        rightEyeOut.addPoint(220,127);
        rightEyeOut.addPoint(217,124);
        rightEyeOut.addPoint(217,121);
        rightEyeOut.addPoint(220,118);
        
        // set points for the respective left-eye versions by
        // reflecting the right-eye versions
        reflectX(rightEye,leftEye,250);
        reflectX(rightEyeOut,leftEyeOut,250);
        reflectX(rightEyeIn,leftEyeIn,250);
        
        // translate all the polygons to put them into
        // the position we want them
        translateAll(20,-50);
    }
   
    /**
     * reflectX -- add points to a polygon based on reflecting from another polygon
     * horizontally
     * 
     * @param p the original polygon
     * @param p2 the empty polygon to which points will be added
     * @param val the reflection position
     */
    private static void reflectX(Polygon p, Polygon p2, int val) {
        // create the x-point and y-point arrays for the new polygon
        p2.xpoints = new int[p.xpoints.length];
        p2.ypoints = new int[p.ypoints.length];
        
        // set the number of points for the new polygon
        p2.npoints = p.npoints;
        
        // perform the reflection in the x-direction, copying the y-positions exactly
        for (int i = 0; i < p.npoints; i++) {
            p2.xpoints[i] = 2*val-p.xpoints[i];
            p2.ypoints[i] = p.ypoints[i];
        }
    }

    /**
     * reflectY -- add points to a polygon based on reflecting from another polygon
     * vertically
     * 
     * @param p the original polygon
     * @param p2 the empty polygon to which points will be added
     * @param val the reflection position
     */
    private static void reflectY(Polygon p, Polygon p2, int val) {
        // create the x-point and y-point arrays for the new polygon
        p2.xpoints = new int[p.xpoints.length];
        p2.ypoints = new int[p.ypoints.length];
        
        // set the number of points for the new polygon
        p2.npoints = p.npoints;
        
        // perform the reflection in the y-direction, copying the x-positions exactly
        for (int i = 0; i < p.npoints; i++) {
            p2.xpoints[i] = p.xpoints[i];
            p2.ypoints[i] = 2*val-p.ypoints[i];
        }
    }

    /**
     * translateAll -- translates all our polygons by the given amount
     * 
     * @param x the x-delta
     * @param y the y-delta
     */
    private void translateAll(int x, int y) {
        // bail if we've already done a translation
        if (alreadyTranslated) {
            return;
        }
        
        // mark that we've already done a translation
        alreadyTranslated = true;
        
        // translate each polygon
        for (Polygon polygon : allPolygons) {
            polygon.translate(x, y);
        }
    }
}