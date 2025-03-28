import java.awt.*;

public class Die {
    private int x;
    private int y;
    private int currentVal;
    private int size;
    
    public Die(int x_val, int y_val) {
        x = x_val;
        y = y_val;
        reRoll();
        this.setSize(50);
    }
    
    private void reRoll() {
        currentVal = (int)(Math.random() * 6) + 1;
    }
    
    public void paint(Graphics canvas) {
        canvas.setColor(Color.WHITE);
        canvas.fillRect(x+1,y+1,size-1,size-1);
        
        canvas.setColor(Color.BLACK);
        canvas.drawRect(x,y,size,size);
        
        int center = size/2;
        int close = size / 5;
        int far = size - close;
        
        switch (currentVal) {
            case 1:
                drawSpot(canvas, center, center);
                break;
            case 2:
                drawSpot(canvas, close, close);
                drawSpot(canvas, far, far);
                break;
            case 3:
                drawSpot(canvas, center, center);
                drawSpot(canvas, far, close);
                drawSpot(canvas, close, far);
                break;
            case 4:
                drawSpot(canvas, far, close);
                drawSpot(canvas, close, far);
                drawSpot(canvas, close, close);
                drawSpot(canvas, far, far);
                break;
            case 5:
                drawSpot(canvas, center, center);
                drawSpot(canvas, far, close);
                drawSpot(canvas, close, far);
                drawSpot(canvas, close, close);
                drawSpot(canvas, far, far);
                break;
            case 6:
                drawSpot(canvas, far, close);
                drawSpot(canvas, close, far);
                drawSpot(canvas, close, center);
                drawSpot(canvas, far, center);
                drawSpot(canvas, close, close);
                drawSpot(canvas, far, far);
                break;
            default:
                break;
        }
    }
    
    public void roll(Graphics canvas) {
        reRoll();
        paint(canvas);
    }
    
    private void drawSpot(Graphics canvas, int x_corr, int y_corr) {
        canvas.setColor(Color.BLACK);
        canvas.fillOval(x + x_corr - (size/10), y + y_corr - (size/10), size/5, size/5);
    }
    
    public int getCurrentValue() {
        return currentVal;
    }
    
    public String toString() {
        return Integer.toString(currentVal);
    }
    
    public boolean equals(Die dice) {
        if (this.currentVal == dice.getCurrentValue()) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setSize(int scale) {
        size = scale;
    }
    
    public int getSize() {
        return size;
    }
}