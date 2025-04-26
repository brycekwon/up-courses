import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// class RunAbstract is the superclass for the Die-testing methods
public abstract class RunAbstract extends JFrame implements ActionListener {

    // instance variables
    private Button rollButton; 
    protected Button increaseButton;
    protected Button decreaseButton;
    private int numRolls;
    private JTextField msgField;
    protected JPanel canvas;

    // constructor
    public RunAbstract() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Die Rolling");
        this.setSize(520, 500);
        this.setPreferredSize(this.getSize());

        // create top-level panel
        JPanel topPanel = new JPanel();
        this.setContentPane(topPanel);

        canvas = new JPanel() {
            public void paint(Graphics g) {
                super.paint(g);
                display(g);
            }
        };
        canvas.setSize(500, 300);
        canvas.setPreferredSize(canvas.getSize());

        addButtons(topPanel);

        // set the background color to custom green
        canvas.setBackground(new Color(13,192,6));

        addDice();

        topPanel.add(canvas);
        addMessageFields(topPanel);
        printMessages();

        numRolls = 1;
    }

    // displays things on the canvas
    public void display(Graphics g) {
        printMessages(); // print the textual messages
    }

    // adds the message fields
    protected void addMessageFields(Container c) {
        msgField = new JTextField("", 25);
        msgField.setBackground(Color.white);
        c.add(msgField);
    }        

    // This method gets executed anytime the button gets pressed
    public void actionPerformed(ActionEvent ae) {
        // If the activated object is our button, then reroll the dice
        if (ae.getSource() == rollButton) {
            numRolls++;
            processRollButton();
            printMessages();
        }
        else { 
            handleButtonEvent(ae);
        }
    }

    private void addButtons(Container c) { 
        rollButton = new Button("Roll");
        increaseButton = new Button("+ Size");
        decreaseButton = new Button("- Size");
        rollButton.addActionListener(this);
        increaseButton.addActionListener(this);
        decreaseButton.addActionListener(this);
        c.add(rollButton);
        c.add(increaseButton);   
        c.add(decreaseButton);   
    }

    // This method prints the textual messages on the frame
    protected void printMessages() {
        msgField.setText("Number of rolls: "+numRolls);
    }

    // process button-press
    protected abstract void processRollButton();

    // method that adds the dice
    protected abstract void addDice();
    
    /** method to handle sub-class defined events, called
     *  if idx != 0
     */
    protected void handleButtonEvent(ActionEvent ae) { 
        // default behavior is to do nothing        
    }
}