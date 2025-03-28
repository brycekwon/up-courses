import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import javax.swing.*;

/**
 * class Lab4Frame - defines the GUI for the CS 273 Lab 4 exercises
 * @author Steven R. Vegdahl
 * @version 2 Feb 2019
 */
public class Lab4Frame extends JFrame implements ItemListener {

    // background color
    private static final Color ANTIQUE_WHITE = new Color(250,235,215);
   
    // instance variables
    private Lab4BaseArea drawPanel; // robot-drawing panel
    private boolean whistleMoreRecentThanFrown; // whether whistle or frown more recently checked

    // list of check-box labels, arranged in vertical groups
    private static final List<List<String>> checkBoxLabels = Arrays.asList(
        Arrays.asList("Silver Body", "Big Ears", "Look Robot's Left", "Short Arms", "Arms Bent Up"),
        Arrays.asList("Green Body", "Red Ears", "Look Robot's Right", "Long Arms", "Arms Bent Down"),
        Arrays.asList("Pink Body", "Blue Ears", "Cross-Eyed", "Bent Legs", "Left Normal"),
        Arrays.asList("Sky Blue Body", "Frown", "Whistle", "Big Feet", "Green Eyes")
    );

    private List<List<JCheckBox>> checkBoxes;

    /** 
     * constructor
     * 
     * @param a the Lab4BaseArea object that paints the robot
     */
    public Lab4Frame(Lab4BaseArea a) {
        // initialize our drawing panel; notify the panel that I am its frame
        drawPanel = a;
        drawPanel.setFrame(this);
        
        // initialize the GUI
        init();
        
        setSize(600,750);
        setBackground(ANTIQUE_WHITE);
        
        // set to exit on window-close
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * init - initializes our frame
     */
    private void init() {
        // create a vertical box as our top element. It will contain two subparts:
        // one for the check-boxes, and one for the drawing
        this.setContentPane(Box.createVerticalBox());
        
        // create the container (box) for the check-boxes; add it to our top element
        Box top = Box.createHorizontalBox();
        this.getContentPane().add(top);

        // create vertical boxes within our horizontal container, which contains all the
        // check-boxes. Have our object listen to each one.
        checkBoxes = new ArrayList<>();
        
        // loop though each top-level element of our check-box strings
        for (List<String> checkboxLabelColumn : checkBoxLabels) {
            // create next vertical box and add it
            Box thisBox = Box.createVerticalBox();
            top.add(thisBox);

            List<JCheckBox> checkBoxColumn = new ArrayList<>();

            // loop through the check-box labels for this column; create the checkbox
            // object; also add it to the check-box array, the GUI. Also, set the frame
            // as its listener
            for (String checkboxLabel : checkboxLabelColumn) {
                JCheckBox checkBox = createJCheckBox(checkboxLabel);
                checkBoxColumn.add(checkBox);
                thisBox.add(checkBox);
            }

            // create new check-box array
            checkBoxes.add(checkBoxColumn);
        }
              
        whistleMoreRecentThanFrown = false;
        
        this.getContentPane().add(drawPanel);
        
        // add some glue to the GUI in case the window is resized
        this.getContentPane().add(Box.createVerticalGlue());
    }

    private JCheckBox createJCheckBox(String label) {
        JCheckBox checkBox = new JCheckBox(label);
        checkBox.addItemListener(this);
        return checkBox;
    }

    /**
     * isChecked - tells whether a particular checkbox is checked
     *
     * @param str the string whose checkbox-label we're trying to match
     * @return true if we found a match that was checked
     */
    public boolean isChecked(String str) {
        // run through the 2D array of check-boxes, looking for a match; if we find
        // one, return 'true' if it is checked
        return checkBoxes.stream()
                .flatMap(Collection::stream)
                .anyMatch(checkBox -> checkBox.isSelected() &&
                        checkBox.getText().equalsIgnoreCase(str));
    }

    /**
     * totalNumChecked - the total number of check-boxes that are checked
     *
     * @return the number of our check-boxes that are checked
     */
    public int totalNumChecked() {
        return Math.toIntExact(checkBoxes.stream()
                .flatMap(Collection::stream)
                .filter(AbstractButton::isSelected)
                .count());
    }

    /**
     * itemStateChanged -- callback-method, called whenever a checkbox is checked or unchecked
     * 
     * @param ie the item-event
     */
    public void itemStateChanged(ItemEvent ie) {
        // get the source of the object
        JCheckBox src = (JCheckBox)ie.getSource();
        
        // set the whistle/frown instance variable if appropriate
        if (src.isSelected()) {
            String checkText = src.getText();
            if (checkText.equals("Whistle")) {
                // "whistle" just checked
                whistleMoreRecentThanFrown = true;
            }
            else if (checkText.equals("Frown")) {
                // "frown" just checked
                whistleMoreRecentThanFrown = false;
            }
        }
        
        // have our draw-panel repaint itself
        drawPanel.repaint();
    }
    
    /**
     * whistleMoreRecent -- tells whether a the 'whistle' check-box was checked more recently
     * than the 'frown' one
     * 
     * @return true iff whistle was checked more recently than 'frown'
     */
    public boolean whistleMoreRecent() {
        return whistleMoreRecentThanFrown;
    }
}