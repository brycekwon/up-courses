import java.util.*;
import java.awt.*;

/**
 * <!-- class BasicEye -->
 *
 * This class represents a single, simple eye on the potato at a specific x,y
 * coordinate
 *
 * @author Andrew Nuxoll
 */
public class BasicEye
{
    protected int x; // my x coordinate
    protected int y; // my y coordinate
    protected int diameter = 10; //my diameter
    protected Color color = Color.BLACK;  //my color
    protected Random gen = new Random(); //for generating random numbers

    protected Color iris;

    /**
     * When a BasicEye is created, the creator must specify where it
     * is located.
     */
    public BasicEye(int initX, int initY)
    {
        this.x = initX;
        this.y = initY;
        this.diameter = 20 + gen.nextInt(31);

        // TODO: create equal chances between all colors

        // green eye shades
        this.iris = new Color(0, 160 + gen.nextInt(95), 50 + gen.nextInt(100));

        // brown eye shades
        this.iris = new Color(85 + gen.nextInt(100), 55 + gen.nextInt(70), 15 + gen.nextInt(15));
        
        // blue eye shades
        this.iris = new Color(23 + gen.nextInt(46), 137 + gen.nextInt(50), 155 + gen.nextInt(80));
    }//ctor

    /**
     * this eye paints itself on a given canvas
     */
    public void paint(Graphics canvas)
    {

        canvas.setColor(this.color);
        canvas.drawOval(x, y, diameter, diameter);

        canvas.setColor(Color.WHITE);
        canvas.fillOval(x, y, diameter, diameter);

        canvas.setColor(iris);
        canvas.fillOval(x+5, y+5, diameter-10, diameter-10);

        canvas.setColor(Color.BLACK);
        canvas.fillOval(x+10, y+10, diameter - 20, diameter - 20);
    }//paint
    
}//class BasicEye
