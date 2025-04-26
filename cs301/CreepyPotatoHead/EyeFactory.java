import java.awt.*;
import java.util.*;

/**
 * <!-- class EyeFactory -->
 *
 * This class is used to create eyes.  The eyes it creates are of a random type
 * and color.
 *
 * @author Andrew Nuxoll
 * @author Bryce Kwon
 */
public class EyeFactory
{
    /**
     * createEye
     *
     * creates a new object whose type is BasicEye (though it may be a subclass
     * thereof) and returns it to the caller
     */
    public static BasicEye createEye(int x, int y)
    {
        Random gen = new Random();

        int chance = gen.nextInt(3);

        if (chance == 1) {
            return new LashedEye(x, y);
        } else if (chance == 2) {
            return new BasicEye(x, y);
        } else {
            return new ReptileEye(x, y);
        }
    }//createEye
    
}//class EyeFactory
