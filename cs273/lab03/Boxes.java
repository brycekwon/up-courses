// import libraries
import java.awt.*;
import javax.swing.*;

/**
 * Class Boxes - Draws concentric boxes.
 * 
 * @author Bryce Kwon
 * @version 2022-09-12
 */
public class Boxes extends Canvas {
    /** Paint Method: specifies what to draw on the screen **/
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.blue);
        g.fillRect(100,100,300,100);
        
        g.setColor(Color.red);
        g.fillRect(125,125,250,50);
        
        g.setColor(Color.green);
        g.fillRect(150,137,200,25);
    }//paint
    
    /** Main Method: creates the window tow display the Java drawing **/
    public static void main(String[] args)
    {
        // create a window for this program
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(600,600);
 
        // put an instance of this class in the window frame
        Boxes myContent = new Boxes();
        myContent.setBackground(Color.gray.brighter()); // set background color
        myFrame.getContentPane().add(myContent);
        
        // display the frame
        myFrame.setVisible(true);
    }//main
}//class Boxes
