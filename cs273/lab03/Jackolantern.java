// import libraries
import java.awt.*;
import javax.swing.*;

/**
 * Class Jackolantern - draws a jack-o-lantern
 * 
 * @author Bryce Kwon
 * @version 2022-09-12
 */
public class Jackolantern extends Canvas {
    /** Paint Method: specifies what to draw on the scren **/
    public void paint(Graphics g) {
        super.paint(g);

        // PUMPKIN
        g.setColor(Color.orange.darker().darker().darker());
        g.drawOval(225,50,50,100);
        g.fillOval(225,50,50,100);
        
        g.setColor(Color.orange);
        g.drawRect(130,130,250,140);
        g.fillRect(130,130,250,140);
        
        g.setColor(Color.orange);
        g.drawOval(100,100,300,200);
        g.fillOval(100,100,300,200);
        
        // EYES R
        g.setColor(Color.white);
        g.drawOval(135,135,80,80);
        g.fillOval(135,135,80,80);
        
        g.setColor(Color.black);
        g.drawOval(145,140,50,60);
        g.fillOval(145,140,50,60);
        
        g.setColor(Color.white);
        g.drawOval(152,150,25,25);
        g.fillOval(152,150,25,25);
        
        // EYES L
        g.setColor(Color.white);
        g.drawOval(265,135,80,80);
        g.fillOval(265,135,80,80);
        
        g.setColor(Color.black);
        g.drawOval(275,140,50,60);
        g.fillOval(275,140,50,60);
        
        g.setColor(Color.white);
        g.drawOval(282,150,25,25);
        g.fillOval(282,150,25,25);
        
        // MOUTH
        g.setColor(Color.black);
        g.drawOval(225,225,20,20);
        g.fillOval(225,225,20,20);
        
        // SOME UWU 
        g.setColor(Color.red);
        g.drawLine(165,230,150,240);
        g.drawLine(175,230,160,240);
        g.drawLine(185,230,170,240);
        g.drawLine(195,230,180,240);
        
        g.drawLine(325,240,340,230);
        g.drawLine(315,240,330,230);
        g.drawLine(305,240,320,230);
        g.drawLine(295,240,310,230);
    }//paint

    /** Main Method: creates the window to display the Java drawing. **/
    public static void main(String[] args)
    {
        // create a window for this program
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(600,600);
        
        // put an instance of this class in the window frame
        Jackolantern myContent = new Jackolantern();
        myContent.setBackground(Color.gray.brighter());
        myFrame.getContentPane().add(myContent);
        
        // make the frame visible
        myFrame.setVisible(true);
    }//main
}//class Jackolantern
