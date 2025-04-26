import java.awt.*;

// class Run2 is a tester-applet for the Die class
public class Run2 extends RunAbstract {
    protected Die myDie;

    public static void main(String[] args) {
        new Run2().setVisible(true);
    }
    
    public Run2() {
        super();
    }

    @Override
    public void display(Graphics g) {
        super.display(g);
        myDie.paint(g); // paint the first die
    }

    @Override
    protected void processRollButton() {
        myDie.roll(canvas.getGraphics()); // reroll first die
    }

    @Override
    protected void addDice() {
        // create the die at 100,50
        myDie = new Die(100, 50);
    }
}
