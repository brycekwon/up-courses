// import libraries
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Class ModelRocket - Simulates the path of a model rocket launch based on 
 * parameters provided by the user.
 * 
 * @author Bryce Kwon
 * @version 2022-09-24
 */
public class ModelRocket extends JPanel {
    // variables that define the rocket's path
    double timeInterval = 1.2;
    double launchVelocity = 151.5;
    double windspeed = 19.5;

    // instance variable for the smallest value seen so far
    int smallest = Integer.MAX_VALUE;

    /**
     * isSmallest
     * 
     * checks if the given number is the smallest this method has
     * ever been called with.
     */
    public boolean isSmallest(int num) {
        // check if current value is smaller than current smallest
        if (num < smallest) {
            smallest = num; // set new smallest value
            return true;
        }
        return false;
    } //isSmallest

    /**
     * drawRocket
     * 
     * draws a model rocket on the screen at a given position on a given canvas.
     */
    public void drawRocket(Graphics canvas, int x, int y) {
        // draw booster for launch, parachute for descent
        if (isSmallest(y)) {
            canvas.setColor(Color.RED);
            canvas.fillOval(x-5, y+100, 40, 60);

            canvas.setColor(Color.ORANGE);
            canvas.fillOval(x, y+100, 30, 50);
        } else {
            canvas.setColor(Color.BLACK);
            canvas.drawLine(x+15, y, x-30, y-40);
            canvas.drawLine(x+15, y, x-15, y-40);
            canvas.drawLine(x+15, y, x, y-40);
            canvas.drawLine(x+15, y, x+15, y-40);
            canvas.drawLine(x+15, y, x+30, y-40);
            canvas.drawLine(x+15, y, x+45, y-40);
            canvas.drawLine(x+15, y, x+60, y-40);

            canvas.setColor(Color.WHITE);
            canvas.fillArc(x-30, y-80, 90, 80, 0, 180);

            canvas.setColor(Color.BLACK);
            canvas.drawArc(x-30, y-80, 90, 80, 0, 180);
        }

        // draw rocket engine and tail
        Polygon engine = new Polygon();
        engine.addPoint(x-25, y+120);
        engine.addPoint(x+15, y+80);
        engine.addPoint(x+55, y+120);

        canvas.setColor(Color.BLUE);
        canvas.fillPolygon(engine);

        canvas.setColor(Color.BLACK);
        canvas.drawPolygon(engine);

        // draw rocket body
        canvas.setColor(Color.BLUE);
        canvas.fillRect(x, y, 30, 120);

        canvas.setColor(Color.BLACK);
        canvas.drawRect(x, y, 30, 120);

        // draw rocket label
        canvas.setColor(Color.ORANGE);
        canvas.drawString("U", x+12, y+20);
        canvas.drawString("S", x+12, y+40);
        canvas.drawString("A", x+12, y+60);

        // draw rocket roof
        Polygon roof = new Polygon();
        roof.addPoint(x-15, y);
        roof.addPoint(x+15, y-30);
        roof.addPoint(x+45, y);

        canvas.setColor(Color.RED);
        canvas.fillPolygon(roof);

        canvas.setColor(Color.BLACK);
        canvas.drawPolygon(roof);
    } //drawRocket

    /**
     * calcRocketXPos
     * 
     * calculates the rocket's x-position at a given time.
     */
    double calcRocketXPos(double time) {
        double pos;

        // check if rocket has launched
        if (time > 0) {
            pos = time * windspeed; // calculate position
            return pos;
        }
        return 0; // return nothing for pre-launch
    } //calcRocketXPos

    /**
     * calcRocketYPos
     * 
     * calculates the rocket's y-position as a given time.
     */
    double calcRocketYPos(double time) {
        double pos;

        // check if rocket has launched
        if (time > 0) {
            pos = -launchVelocity * time; // calculate position
            pos += 9.8 * time * time; // account for gravity
            return pos;
        }
        return 0; // return nothing for pre-launch
    } //calcRocketYPos

    /**
     * getValueFromUser
     * 
     * retrieves a double value from the user within a given range,
     * default value if invalid.
     */
    public double getValueFromUser(String valName, double min, double max, double defaultVal) {
        // prompt user for input
        String userStr = JOptionPane.showInputDialog("Enter a " + valName + " in the range [" + min + ".." + max + "]");
        double userVal = convertStringToDouble(userStr); // convert input into double

        // check for input validity
        if (userVal <= max && userVal >= min) {
            return userVal;
        } else if (userVal == -100.0) {
            JOptionPane.showMessageDialog(this, valName + " input was invalid, using default value: " + defaultVal);
            return defaultVal;
        } else if (userVal < min) {
            JOptionPane.showMessageDialog(this, valName + " input was too small, using default value: " + defaultVal);
            return defaultVal;
        } else if (userVal > max) {
            JOptionPane.showMessageDialog(this, valName + " input was too large, using default value: " + defaultVal);
            return defaultVal;
        } else {
            JOptionPane.showMessageDialog(this, valName + " input was invalid, using default value: " + defaultVal);
            return defaultVal;
        }
    } //getValuefromUser


    /*======================================================================
     *      >>    >>    >>    ATTENTION STUDENTS    <<    <<     <<
     *
     * None of the code below this line should be modified.  You are welcome
     * to review this code if you wish to help you understand how the program
     * works.
     *----------------------------------------------------------------------
     */

    //the size of the window (do not modify this)
    public static final int WINDOW_SIZE = 1000;

    //the maximum number of rockets that may be drawn
    public static final int MAX_ITER = 300;

    //These colors are used to draw the background
    public static final Color SKYBLUE = new Color(135, 206, 250);
    public static final Color DIRT = new Color(160,82,45);

    /**
     * convertStringToDouble
     *
     * does the conversion without exposing students to exception handling
     *
     */
    public double convertStringToDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch(Exception npe) {
            //All exceptions handled by returning -100.0
            //which is invalid for any question in this sim
            return -100.0;
        }
    }

    /**
     * init
     *
     * prompts user for information about the simulation
     */
    public void init() {

        // prompt user for a time interval
        timeInterval = getValueFromUser("time interval", 0.1, 4.0, timeInterval);
        launchVelocity = getValueFromUser("launch velocity", 20.0, 200.0, launchVelocity);
        windspeed = getValueFromUser("windspeed", -25.0, 25.0, windspeed);

    }//init



    /**
     * paint
     *
     * Draws the simulation on the graphics window
     *
     * @param  canvas   the Graphics object for the window
     */
    public void paint(Graphics canvas) {
        super.paint(canvas);  // this must be the first line

        // draw the background
        setBackground(SKYBLUE);
        int groundPos = 2 * WINDOW_SIZE / 3;
        canvas.setColor(DIRT);
        canvas.fillRect(0, groundPos, WINDOW_SIZE, groundPos);

        //Each time we paint, reset the smallest to max int
        //so the flames will draw correctly
        smallest = Integer.MAX_VALUE;

        //use a loop to draw the rocket along its trajectory
        double time = -1.0;  //elapsed time since launch
        double rocketX = 0.0;
        double rocketY = 0.0;
        int iterCount = 0;  //number of loop iterations
        boolean anyMove = false;  //keep track of whether the rocket ever goes up
        while( (rocketY <= 0.0) && (iterCount < MAX_ITER)) {
            //time passes
            time = time + timeInterval;
            iterCount++;

            //calculate the position at this time
            rocketX = calcRocketXPos(time);
            rocketY = calcRocketYPos(time);

            //convert to integer coordinate for drawing
            int intRX = (int)Math.round(rocketX);
            int intRY = (int)Math.round(rocketY);

            //Note whether the rocket has moved yet
            if ((! anyMove) && (intRY > 0.0)) {
                anyMove = true;
            }

            //apply to offset so the rocket starts on the ground
            intRX = intRX + WINDOW_SIZE / 2;
            intRY = groundPos + intRY;

            //draw the rocket at that position
            drawRocket(canvas, intRX, intRY);
        }

        //if the rocket failed, inform the user
        canvas.setColor(Color.black);
        if (! anyMove) {
            canvas.drawString("Note: It appears that the rocket never left the ground.", 50, 50);
        }
        else if (iterCount >= MAX_ITER) {
            canvas.drawString("Note: It appears that the rocket never landed.", 50, 50);
        }
        else if (iterCount == 1) {
            canvas.drawString("Note: It appears that the rocket went down instead of up.", 50, 50);
        }

    }//paint

    /**
     * This constructor method creates the window for you.  
     * You should not modify it.
     */
    public ModelRocket() {
        super();
    }

    /**
     * main
     *
     * This method starts the application. 
     */
    public static void main(String[] args) {
        //Create the window
        JFrame frame = new JFrame();
        frame.setSize(WINDOW_SIZE, WINDOW_SIZE);  //do not modify this!
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Gather input from the user
        ModelRocket rockit = new ModelRocket();
        frame.add(rockit);
        rockit.init();

        //Display the window
        frame.setVisible(true);
        frame.repaint();
    }
}
