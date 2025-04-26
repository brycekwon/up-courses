import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <!-- class CreepyPotatoHead -->
 *
 * This is the main class for the program.  It creates the window, draws a
 * potato in it and intercepts mouse click events to send to the EyeFactory
 * to add eyes to the potato.
 *
 * Students:  DO NOT MODIFY THIS FILE!
 *            (But you're encouraged to look at the code)
 *
 * @author Andrew Nuxoll
 * @version January 2018
 */
public class CreepyPotatoHead extends JPanel implements MouseListener
{
    /** the size of the window (height and width) */
    public static final int WINDOW_SIZE = 800;
    
    /** a gentle blue background color for the drawing */
    public static final Color BACKGROUND_COLOR = new Color(100,149,237);

    /** the color of the potato */
    public static final Color POTATO_COLOR = new Color(210,180,140);

    //a list of all the eyes on the potato
    public Vector<BasicEye> eyes = new Vector<BasicEye>();
    
    
    /**
     * drawPotato
     *
     * draws the base potato (no eyes)
     */
    public void drawPotato(Graphics canvas)
    {
        canvas.setColor(POTATO_COLOR);
        for(int i = 0; i < 7; ++i)
        {
            //I tweaked these numbers until I got something
            //roughly potato-like in shape
            canvas.fillOval(WINDOW_SIZE/3 - (i*9),
                            WINDOW_SIZE/(9-i) - 30,
                            (7+i)*WINDOW_SIZE/20,
                            (7+i)*WINDOW_SIZE/20);
        }
    }//drawPotato
     
    
    /**
     * paint
     *
     * draws the potato including subcalls to draw eyes
     */
    @Override
    public void paint(Graphics canvas)
    {
        //Clear the field
        canvas.setColor(BACKGROUND_COLOR);
        canvas.fillRect(0,0,WINDOW_SIZE,WINDOW_SIZE);

        //Draw the stunning artwork
        drawPotato(canvas);
        for(BasicEye sbo : eyes)
        {
            sbo.paint(canvas);
        }
            
    }//paint


    /**
     * mousePressed
     *
     * is called whenever the user clicks on the canvas.  It creates a new eye
     * at that position and repaints the canvas to show the user.
     */
    public void mousePressed(MouseEvent event)
    {
        eyes.add(EyeFactory.createEye(event.getX(), event.getY()));

        //now tell the window to redraw itself
        this.repaint();
    }//mousePressed

               


    //We don't care about these events, so ignore them
    public void mouseClicked(MouseEvent e)   {}
    public void mouseEntered(MouseEvent e)   {}
    public void mouseExited(MouseEvent e)   {}
    public void mouseReleased(MouseEvent e)   {}

    /**
     * creates the window frame and kicks off the program
     */
    public static void main(String[] args)
    {
        //Create a window for this program
        JFrame myFrame = new JFrame();
        myFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                };
            });
        myFrame.setSize(WINDOW_SIZE, WINDOW_SIZE);
        myFrame.setResizable(false);

        //Put an instance of myself in the window
        CreepyPotatoHead myPotato = new CreepyPotatoHead();
        myFrame.add(myPotato);

        //Tell the operating system to send me mouse events
        myPotato.addMouseListener(myPotato);

        //Ready!
        myFrame.setVisible(true);

    }//main
}//class CreepyPotatoHead
