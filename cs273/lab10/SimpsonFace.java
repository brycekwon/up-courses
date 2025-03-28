////////////////////////////////////////////////////////
// SimpsonFace.java - a face that knows how to draw itself. 
// At the start, it is a placeholder that needs to be updated
// to look like a Simpson face. 
////////////////////////////////////////////////////////

import java.awt.*;

public class SimpsonFace extends Face {
    public SimpsonFace(int w, int h) {
        super((int)(w * 0.75), (int)(h * 1.25));
    }
    
    @Override
    protected Color mouthColor() {
        return Color.BLACK;
    }
    
    protected Polygon2 createMouth() {
        Polygon2 mouth = new Polygon2(20);
        mouth = mouth.fitIn(pixelX(17.0), pixelY(65.0), 100, 80);
        return mouth;
    }
    
    @Override
    protected void drawMouth(Graphics g) {
        g.setColor(this.mouthColor());
        g.fillPolygon(createMouth());
    }
    
    @Override
    protected Color headColor() {
        return new Color(254, 212, 29);
    }
    
    @Override
    protected void drawHair(Graphics g) {}
    
    @Override
    protected void drawEyeWhites(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(pixelX(18), pixelY(35), distX(31), distY(21));
        g.fillOval(pixelX(55), pixelY(35), distX(31), distY(21));
    }
    
    @Override
    protected Color eyeColor() {
        return Color.BLACK;
    }
    
    @Override
    protected void drawEyeCenters(Graphics g) {
        g.setColor(this.eyeColor());
        g.fillOval(pixelX(28), pixelY(42), distX(8), distY(6));
        g.fillOval(pixelX(66), pixelY(42), distX(8), distY(6));
    }
    
    @Override
    protected Color noseColor() {
        return Color.BLACK;
    }
    
    @Override
    protected void drawNose(Graphics g) {
        Polygon2 p = new Polygon2(10);
        p = p.fitIn(pixelX(35), pixelY(60), distX(25), distY(10));
        p = p.rotateBy(90);
        g.setColor(this.noseColor());
        g.fillPolygon(p);
    }
}
