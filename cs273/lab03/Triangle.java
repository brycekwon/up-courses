// import libraries
import java.awt.*;
import javax.swing.*;

/**
 * Class Triangle - draws a triangle.
 * 
 * @author Bryce Kwon
 * @version 2022-09-12
 */
public class Triangle extends Canvas {    
    /** Pain Method: specifies what to draw on the screen **/
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.yellow);
        g.drawRect(0,0,1000,1000);
        g.fillRect(0,0,1000,1000);
        
        Polygon largeTriangle = new Polygon();
        largeTriangle.addPoint(100,100);
        largeTriangle.addPoint(50,200);
        largeTriangle.addPoint(150,200);
        
        g.setColor(Color.green);
        g.drawPolygon(largeTriangle);
        g.fillPolygon(largeTriangle);
    }//paint

    /** Main Method: creates the window to display your Java drawing **/
    public static void main(String[] args)
    {
        // create a window for this program
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(600,600);
 
       
        // put an instance of this class in the window frame
        Triangle myContent = new Triangle();
        myContent.setBackground(Color.gray.brighter());
        myFrame.getContentPane().add(myContent);
        
        // display the frame
        myFrame.setVisible(true);
        
    }//main
}//class Triangle
