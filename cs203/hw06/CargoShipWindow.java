import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.*;
import java.util.Locale;

/**
 * class CargoShipWindow
 *
 * displays the current status of a cargo ship and allows the user to add/remove
 * crates.
 *
 * ATTENTION:  Students may not edit this file.
 */
public class CargoShipWindow extends JPanel implements MouseListener, ActionListener
{

    //These constants define how big the window is.
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 400;

    //These are the coordinates of the cargo area of the ship
    public static final int CARGO_X = 100;
    public static final int CARGO_TOP_Y = 50;
    public static final int CARGO_BOTTOM_Y = 170;
    public static final int CARGO_WIDTH = 400;
    public static final int CARGO_HEIGHT = 200;

    //These parameters define the size and positioning of the crates
    public static final int CRATE_HEIGHT = 80;  //height of a single crate
    public static final int CRATE_TEXT_TOP_MARGIN = 45;  
    public static final int CRATE_TEXT_LEFT_MARGIN = 3;  
    public static final int INDEX_TEXT_TOP_MARGIN = 12;  
    public static final int INDEX_TEXT_LEFT_MARGIN = 6;  
    public static final int INDEX_TEXT_BOTTOM_MARGIN = 4;  

    //A reference to an instnace the student's CargoShip class
    CargoShip ship = new CargoShip();

    //current category for crates
    private char currCat = 'i';  //empty inert

    //We'll need a reference to the field containing the crate's weight
    JFormattedTextField weightEntry = null;

    //current category for crates
    private String msgString = "";  //message to paint for the user

    /** ctor */
    public CargoShipWindow()
{
        super();

    }//ctor


    /** initialize the controls on the user interface */
    public void initGUI(JFrame myFrame)
{
        Container root = myFrame.getContentPane();
        Box topBox = new Box(BoxLayout.PAGE_AXIS);
        root.add(topBox);
        topBox.add(this);
        Box selectionBox = new Box(BoxLayout.LINE_AXIS);
        topBox.add(selectionBox);

        //Setup the weight text field
        JLabel weightText = new JLabel("Weight: ");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);
        this.weightEntry = new JFormattedTextField(decimalFormat);
        weightEntry.setSize(new Dimension(30, 10));
        weightEntry.setMaximumSize(new Dimension(60, 30));
        weightEntry.setMinimumSize(new Dimension(60, 30));
        weightEntry.setColumns(15);
        weightEntry.setText("15");
        selectionBox.add(weightText);
        selectionBox.add(weightEntry);
        selectionBox.add(Box.createRigidArea(new Dimension(20,15)));

        //Setup the radio buttons for categories
        Box categoryBox = new Box(BoxLayout.PAGE_AXIS);
        selectionBox.add(categoryBox);
        JRadioButton emptyButton = new JRadioButton("empty");
        emptyButton.setActionCommand("e");
        emptyButton.addActionListener(this);
        JRadioButton inertButton = new JRadioButton("inert");
        inertButton.setActionCommand("i");
        inertButton.addActionListener(this);
        inertButton.setSelected(true);       
        JRadioButton flammableButton = new JRadioButton("flammable");
        flammableButton.setActionCommand("f");
        flammableButton.addActionListener(this);
        JRadioButton perishableButton = new JRadioButton("perishable");
        perishableButton.setActionCommand("p");
        perishableButton.addActionListener(this);
        ButtonGroup group = new ButtonGroup();
        group.add(emptyButton);
        group.add(inertButton);
        group.add(flammableButton);
        group.add(perishableButton);
        selectionBox.add(emptyButton);
        selectionBox.add(inertButton);
        selectionBox.add(flammableButton);
        selectionBox.add(perishableButton);
        selectionBox.add(Box.createRigidArea(new Dimension(20,15)));

        //Setup the "safe" button
        JButton safeButton = new JButton("Safe Place");
        safeButton.setActionCommand("s");
        safeButton.addActionListener(this);
        selectionBox.add(safeButton);

        //Setup the "close" button
        JButton closeButton = new JButton("Close");
        closeButton.setActionCommand("x");
        closeButton.addActionListener(this);
        selectionBox.add(closeButton);


        //listen for mouse clicks
        addMouseListener(this);

    }//initGUI

    /**
     * paintCrateRow
     *
     * paints a row of crates (half of each array) at a given position
     *
     * @param canvas      to draw upon
     * @param weights     weights array from the cargo ship
     * @param categories  categories array from the cargo ship
     * @param indexOffset offset into the array to get crate data
     * @param yCoord      where to draw the crates on y-axis
     */
    public void paintCrateRow(Graphics canvas, int[] weights, char[] categories,
        int indexOffset, int yCoord)
{
        int crateWidth = 2 * CARGO_WIDTH / weights.length;
        for(int i = 0; i < weights.length/2; ++i)
    {
            //Get weight and category of the crate
            String weight = "" + weights[i + indexOffset];
            char cat = categories[i + indexOffset];

            //Set the crate color
            canvas.setColor(Color.darkGray);  //default to indicate error
            if (cat == 'f')  //flammable
        {
                canvas.setColor(Color.red);
            }
            else if (cat == 'p')  //perishable
        {
                canvas.setColor(Color.green);
            }
            else if (cat == 'i')  //inert
        {
                canvas.setColor(Color.blue);
            }
            else if (cat == 'e')  //empty
        {
                canvas.setColor(Color.white);
            }

            //paint the crate
            canvas.fillRect(CARGO_X + i*crateWidth, yCoord,
                crateWidth, CRATE_HEIGHT);

            //Label the crate with the weight using black text on white background
            canvas.setColor(Color.white);
            Font crateWeightFont = new Font("Times", Font.PLAIN, 16);
            canvas.setFont(crateWeightFont);
            canvas.drawString(weight, CARGO_X + i*crateWidth + CRATE_TEXT_LEFT_MARGIN + 1,
                yCoord + CRATE_TEXT_TOP_MARGIN + 1);
            canvas.drawString(weight, CARGO_X + i*crateWidth + CRATE_TEXT_LEFT_MARGIN - 1,
                yCoord + CRATE_TEXT_TOP_MARGIN - 1);
            canvas.drawString(weight, CARGO_X + i*crateWidth + CRATE_TEXT_LEFT_MARGIN - 1,
                yCoord + CRATE_TEXT_TOP_MARGIN + 1);
            canvas.drawString(weight, CARGO_X + i*crateWidth + CRATE_TEXT_LEFT_MARGIN + 1,
                yCoord + CRATE_TEXT_TOP_MARGIN - 1);
            canvas.setColor(Color.black);
            canvas.drawString(weight, CARGO_X + i*crateWidth + CRATE_TEXT_LEFT_MARGIN,
                yCoord + CRATE_TEXT_TOP_MARGIN);

            //draw a black border around the crate
            canvas.setColor(Color.black);
            canvas.drawRect(CARGO_X + i*crateWidth, yCoord,
                crateWidth, CRATE_HEIGHT);
        }//for


    }//paintCrateRow



    /**
     * paint
     *
     * draws the ship on the screen
     */
    public void paint(Graphics canvas)
{
        //clear any previous drawing with pale blue background
        canvas.setColor(new Color(190,220,240));
        canvas.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);

        //The boat is a medium brown color
        Color boatColor = new Color(139,69,19);
        canvas.setColor(boatColor);

        //draw the prow of the ship
        Polygon prow = new Polygon();
        prow.addPoint(CARGO_X-10, CARGO_TOP_Y-10);
        prow.addPoint(15, 150);
        prow.addPoint(CARGO_X-10, 260);
        canvas.fillPolygon(prow);

        //draw the body and stern
        canvas.fillRect(CARGO_X-10, CARGO_TOP_Y-10, 420, 220);
        canvas.fillArc(460, CARGO_TOP_Y-10, 100, 220, 90, -180);

        //draw the cargo area
        canvas.setColor(Color.white);
        canvas.fillRect(CARGO_X, CARGO_TOP_Y, CARGO_WIDTH, CARGO_HEIGHT);

        //verify that the ship is initializes properly
        int[] weights = ship.getWeights();
        char[] categories = ship.getCategories();
        canvas.setColor(Color.black);
        if (weights == null || categories == null)
    {
            canvas.drawString("arrays not initialized", CARGO_X+10, CARGO_TOP_Y+10);
            return;
        }
        else if (weights.length != categories.length)
    {
            canvas.drawString("arrays improperly initialized", CARGO_X+10, CARGO_TOP_Y+10);
            return;
        }

        //draw the crates
        paintCrateRow(canvas, weights, categories, 0, CARGO_TOP_Y);
        paintCrateRow(canvas, weights, categories, weights.length/2, CARGO_BOTTOM_Y);

        //draw the array indices
        canvas.setColor(Color.black);
        Font indexFont = new Font("Times", Font.PLAIN, 12);
        canvas.setFont(indexFont);
        int crateWidth = 2 * CARGO_WIDTH / weights.length;
        for(int i = 0; i < weights.length/2; ++i)
    {
            canvas.drawString("" + i,
                CARGO_X + i*crateWidth + INDEX_TEXT_LEFT_MARGIN,
                CARGO_TOP_Y + CRATE_HEIGHT + INDEX_TEXT_TOP_MARGIN);
            canvas.drawString("" + (i + weights.length / 2),
                CARGO_X + i*crateWidth + INDEX_TEXT_LEFT_MARGIN,
                CARGO_BOTTOM_Y - INDEX_TEXT_BOTTOM_MARGIN);
        }

        //print the message string
        canvas.setColor(Color.black);
        canvas.drawString(this.msgString, CARGO_X, CARGO_BOTTOM_Y + CRATE_HEIGHT + 40);

    }//paint


    /**
     * getWeight
     *
     * retrieves the user-entered weight and corrects it to a valid value if
     * necessary.
     *
     * SIDE EFFECT:  The weight entry field is modified to match user input
     */
    private int getWeight()
{
        int result = 5;  //5 is default weight
        String rawInput = weightEntry.getText();
        try
    {
            int rawNum = Integer.parseInt(rawInput);
            if (rawNum < 1)
        {
                rawNum = 5;
            }
            else if (rawNum > 20)
        {
                rawNum = 20;
            }
            result = rawNum;
        }
        catch(NumberFormatException nfe)
    {
            result = 5;
        }

        weightEntry.setText("" + result);

        return result;
    }//getWeight

    /**
     * tryCratePlacement
     *
     * attempts to place a crate with the current weight and category
     *
     * @param index  where to place the crate
     */
    private void tryCratePlacement(int index)
{
        boolean retVal = true;
        this.msgString = "";  //no message is success
        if (currCat != 'e')
    {
            retVal = ship.isSafeSpace(index, this.currCat);
            if (!retVal)
        {
                this.msgString = "ERROR:  this space is not safe!.";
            }
            else
        {
                retVal = ship.addCrate(index, this.getWeight(), this.currCat);

                if (!retVal)
            {
                    this.msgString = "ERROR:  addCrate() returned false for index " + index + ".";
                }
            }
        }
        else
    {
            retVal = ship.removeCrate(index);

            if (!retVal)
        {
                this.msgString = "ERROR:  removeCrate() returned false for index " + index + ".";
            }
        }

    }//tryCratePlacement

    /**
     * actionPerformed
     *
     * when the user selects a new category or presses a button, this method is
     * called to handle it
     */
    public void actionPerformed(ActionEvent event) {
        char cmd = event.getActionCommand().charAt(0);
        if (cmd == 'x')  //close button
    {
            System.exit(0);
        }
        else if (cmd == 's') //safe place button
    {
            //find a place to put the crate
            int index = ship.findSpace(this.currCat);
            if (index < 0)
        {
                this.msgString = "ERROR:  No valid space for the current crate category";
            }
            else
        {
                tryCratePlacement(index);
            }
            repaint();
        }
        else  //category selection
    {
            currCat = cmd;
        }
    }//actionPerformed

    public void	mouseReleased(MouseEvent e)
{
        //verify this mouse click was on a crate row
        int x = e.getX();
        int y = e.getY();
        if (x < CARGO_X || x > CARGO_X + CARGO_WIDTH
        || y < CARGO_TOP_Y || y > CARGO_TOP_Y + CARGO_HEIGHT)
    {
            return;  //click out of bounds
        }

        //verify we have valid arrays
        int[] weights = ship.getWeights();
        char[] categories = ship.getCategories();
        if (weights == null || categories == null || weights.length != categories.length)
    {
            return; //arrays not initialized properly
        }

        //calculate the index
        int crateWidth = 2 * CARGO_WIDTH / weights.length;
        int index = (x - CARGO_X) / crateWidth;
        if (y > CARGO_BOTTOM_Y)
    {
            index += weights.length/2;
        }

        //place the crate
        tryCratePlacement(index);
        repaint();
    }//mouseReleased


    //ignore these
    public void	mouseClicked(MouseEvent e) {}
    public void	mouseEntered(MouseEvent e) {}
    public void	mouseExited(MouseEvent e) {}
    public void	mousePressed(MouseEvent e) {}

    /**
     * This method creates a window frame and displays the CargoShip
     * app inside of it.  
     */
    public static void main(String[] args)
{
        //Create a properly sized window for this program
        final JFrame myFrame = new JFrame("Cargo Ship");
        myFrame.setSize(WINDOW_WIDTH+10, WINDOW_HEIGHT+30);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display a canvas in the window
        CargoShipWindow canvas = new CargoShipWindow();
        canvas.initGUI(myFrame);
        myFrame.setVisible(true);

    }//main

}//class CargoShipWindow
