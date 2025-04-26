// import libraries
import java.awt.*;
import javax.swing.*;

/**
 * Class Art - Generates a graphics based on user input.
 *
 * @author Bryce Kwon
 * @version 2022-09-20
 */

public class Art extends JPanel {

    // declare variables
    int numCars;
    Color yourColor = new Color(100,100,100);

    // initialize
    public void init() {

        // welcome the user
        JOptionPane.showMessageDialog(null, "Welcome to Bryce's Graphics Project!");

        // ask the user for input (number of cars)
        String question1 = "How many cars would you like in the picture? (1, 2, 3, 4)";
        String numCarStr = JOptionPane.showInputDialog(question1);

        // parse input into integer value
        numCars = Integer.parseInt(numCarStr);

        // ask the user for input (favorite color)
        String question2 = "What is your favorite color? (Grey, Green, Orange)";
        String favColorStr = JOptionPane.showInputDialog(question2);

        // parse input into color value
        if (favColorStr.toLowerCase().equals("grey")) {
            yourColor = new Color(200,200,200);
        } else if (favColorStr.toLowerCase().equals("green")) {
            yourColor = new Color(101,201,50);
        } else if (favColorStr.toLowerCase().equals("orange")) {
            yourColor = new Color(200,99,44);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Input detected, the default grey will be used!");
        }

        Color lightBlue = new Color(100,160,240);
        setBackground(lightBlue);
    }

    // draw a car graphics
    public void drawCar(Graphics canvas, int x, int y, Color carColor) {

        // declare required polygon graphics
        Polygon roof = new Polygon();
        Polygon window = new Polygon();

        // add desired points
        roof.addPoint(x+40,y+50);
        roof.addPoint(x+60,y+20);
        roof.addPoint(x+100,y+20);
        roof.addPoint(x+130,y+50);
        window.addPoint(x+100,y+20);
        window.addPoint(x+100,y+50);
        window.addPoint(x+130,y+50);

        // paint the frame of the car
        canvas.setColor(carColor);
        canvas.fillRect(x+20,y+50,120,40);
        canvas.fillPolygon(roof);

        // paint the window of the car
        canvas.setColor(Color.BLUE);
        canvas.fillPolygon(window);

        // paint the tires of the car
        canvas.setColor(Color.BLACK);
        canvas.fillOval(x+40,y+80,30,30);
        canvas.fillOval(x+90,y+80,30,30);

    }

    // paint canvas
    public void paint(Graphics canvas) {

        super.paint(canvas);

        Color coolGrey = new Color(100,100,100);
        Color coolGray = new Color(200,200,200);
        Color coolGreen = new Color(101,201,50);
        Color coolBrown = new Color(200,99,44);

        canvas.setColor(new Color(250,250,250));
        canvas.fillRect(0,150,1000,1000);

        drawCar(canvas, 150, 300, yourColor);

        if (numCars > 1) {
            drawCar(canvas, 50, 175, Color.YELLOW);
        }

        if (numCars > 2) {
            drawCar(canvas, 300, 355, Color.RED);
        }

        if (numCars > 3) {
            drawCar(canvas, 400, 150, Color.CYAN);
        }

    }

    // main method
    public static void main(String[] args) {

        // create a new panel to display graphics
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(600,500);

        // initialize art onto displayed window
        Art myArt = new Art();
        myArt.init();
        myFrame.add(myArt);
        myFrame.setVisible(true);
    }

}
