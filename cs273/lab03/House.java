// import libraries
import java.awt.*;
import javax.swing.*;

/**
 * Class House - draws a house
 * 
 * @author Bryce Kwon
 * @version 2022-09-12
 */
public class House extends Canvas {    
    /** Pain Method: specifies what to draw on the screen **/
    public void paint(Graphics g) {
        super.paint(g);
        
        // THE HOUSE
        g.setColor(Color.red);
        g.drawRect(300,300,200,200);
        g.fillRect(300,300,200,200);
        
        // ROOF OF THE HOUSE
        Polygon roof = new Polygon();
        roof.addPoint(200,300);
        roof.addPoint(600,300);
        roof.addPoint(400,150);
        g.setColor(Color.green);
        g.drawPolygon(roof);
        g.fillPolygon(roof);
        
        // WINDOW OF THE HOUSE
        Polygon window = new Polygon();
        window.addPoint(375,250);
        window.addPoint(425,250);
        window.addPoint(425,200);
        window.addPoint(375,200);
        g.setColor(Color.blue);
        g.drawPolygon(window);
        g.fillPolygon(window);
        
        // DOOR OF THE HOUSE
        Polygon door = new Polygon();
        door.addPoint(375,500);
        door.addPoint(425,500);
        door.addPoint(425,400);
        door.addPoint(375,400);
        g.setColor(Color.orange.darker().darker().darker());
        g.drawPolygon(door);
        g.fillPolygon(door);
        
        // DOOR KNOB OF THE HOUSE
        g.setColor(Color.orange);
        g.drawOval(410,445,10,10);
        g.fillOval(410,445,10,10);
    }//paint
    
    /** Main Method: creates the window to display the Java drawing **/
    public static void main(String[] args) {
        // create a window for this program
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(600,600);
        
        // put an instance of this class in the window frame
        House myContent = new House();
        myContent.setBackground(Color.gray.brighter());
        myFrame.getContentPane().add(myContent);
        
        // display the frame
        myFrame.setVisible(true);
    }//main
}//class House
