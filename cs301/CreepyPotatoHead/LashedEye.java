import java.util.*;
import java.awt.*;

/**
 * <!-- class LashedEye -->
 *
 * This class represents a single, lashed eye on the potato at a specific x,y
 * coordinate
 *
 * @author Bryce Kwon
 */
public class LashedEye extends BasicEye {

  public LashedEye(int x, int y) {
    super(x, y);
  }

  public void paint(Graphics canvas) {
    super.paint(canvas);

    int r = diameter / 2;
    int x_center = x + r;
    int y_center = y + r;

    for (double i = 3.14; i < 6; i += 0.2) {
      double x_coor = x_center + (r * Math.cos(i));
      double y_coor = y_center + (r * Math.sin(i));

      canvas.setColor(Color.BLACK);
      canvas.drawLine((int) x_coor, (int) y_coor, (int) x_coor + (int) (r * Math.cos(i)) / 2, (int) y_coor + (int) (r * Math.sin(i)) / 2);
    }
  }
}
