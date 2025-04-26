////////////////////////////////////////////////////////
// MargeFace.java - a face that knows how to draw itself. 
// At the start, it is a placeholder that needs to be updated
// to look like Marge Simpson's face. 
////////////////////////////////////////////////////////

import java.awt.*;

public class MargeFace extends SimpsonFace
{
    public MargeFace(int w, int h) { 
        super(w, h);
    }
    
    @Override
    protected Color hairColor() {
        return new Color(57, 123, 187);
    }
    
    @Override
    protected void drawHair(Graphics g) {
        g.setColor(this.hairColor());
        Polygon2 hair = new Polygon2(50);
        hair = hair.rotateBy(180);
        
        for (int i = 0; i < 7; i++) {
            hair = hair.fitIn(pixelX(10), pixelY(0-(i*10)), distX(80), distY(20));
            g.fillPolygon(hair);
        }
        
        Polygon2 side = new Polygon2(100);
        for (int i = 0; i < 4; i++) {
            side = side.fitIn(pixelX(2-i), pixelY(15+(i*5)), distX(15), distY(10));
            g.fillPolygon(side);
            
            side = side.fitIn(pixelX(80+i), pixelY(15+(i*5)), distX(15), distY(10));
            g.fillPolygon(side);
        }
        
    }
}