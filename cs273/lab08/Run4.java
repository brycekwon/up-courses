import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Run4 extends Run3 {

    public static void main(String[] args) {
        new Run4().setVisible(true);
    }

    public Run4() {
        super();        
    }

    @Override
    protected void addDice() {
        super.addDice();
        myDie2.setSize(150);
    }

    @Override
    protected void handleButtonEvent(ActionEvent ae) { 
        if(ae.getSource() == increaseButton) { 
            myDie2.setSize(myDie2.getSize() + 10);
        }
        else if(ae.getSource() == decreaseButton) { 
            myDie2.setSize(myDie2.getSize() - 10);
        }        
        
        repaint();
    }    
}