////////////////////////////////////////////////////////
// HomerFace.java - a face that knows how to draw itself. 
// At the start, it is a placeholder that needs to be updated
// to look like Homer Simpson's face. 
////////////////////////////////////////////////////////

import java.awt.*;

public class HomerFace extends SimpsonFace
{
    public HomerFace(int w, int h) {
        super(w, h);
    }
    
    @Override
    protected Color hairColor() {
        return Color.BLACK;
    }
    
    @Override
    protected void drawHair(Graphics g) {
        g.setColor(this.hairColor());
        Polygon2 hair = new Polygon2(10);
        Polygon2 hair2 = new Polygon2(10);
        
        hair = hair.rotateBy(180);
        hair2 = hair2.rotateBy(180);
        
        hair = hair.fitIn(pixelX(15),pixelY(5),distX(80),distY(20));
        hair2 = hair2.fitIn(pixelX(15), pixelY(10), distX(80), distY(20));
        
        hair = hair.rotateBy(20);
        hair2 = hair2.rotateBy(20);
        
        g.fillPolygon(hair);
        g.fillPolygon(hair2);
    }
    
    
    @Override
    protected void drawMouth(Graphics g) {
        g.setColor(new Color(209, 178, 113));
        g.fillOval(pixelX(12), pixelY(64), distX(78), distY(35));
        
        g.setColor(mouthColor());
        g.fillPolygon(createMouth());
    }
}
