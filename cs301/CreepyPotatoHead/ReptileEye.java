import java.awt.*;

/**
 * <!-- class ReptileEye -->
 *
 * This class represents a single, reptile eye on the potato at a specific x,y
 * coordinate
 *
 * @author Bryce Kwon
 */
public class ReptileEye extends BasicEye {
  public ReptileEye(int x, int y) {
    super(x, y);
  }

  public void paint(Graphics canvas) {
    canvas.setColor(Color.BLACK);
    canvas.drawOval(x, y, diameter, diameter);

    canvas.setColor(Color.WHITE);
    canvas.fillOval(x, y, diameter, diameter);

    canvas.setColor(iris);
    canvas.fillOval(x+5, y+5, diameter-10, diameter-10);

    canvas.setColor(Color.BLACK);
    canvas.fillOval(x+(diameter/2), y+(diameter/4), diameter / 8, diameter / 2);  
  }
}