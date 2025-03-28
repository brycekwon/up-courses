//////////////////////////////////////////////////////
// FaceFrame.java - draws faces
//////////////////////////////////////////////////////

// imports
import javax.swing.*;
import java.awt.*;

//////////////////////////////////////////////////////
// class FaceFrame - draws some faces on the screen
// This frame creates seven faces, and displays them on the screen
//////////////////////////////////////////////////////
public class FaceFrame extends JFrame {

    private static final int FACE_SIZE = 200;
    private static final int Y_COORD_ROW_0 = 50;
    private static final int Y_COORD_ROW_1 = 300;
    private static final int Y_COORD_ROW_2 = 600;
    private static final int X_LABEL_OFFSET = 20;
    private static final int Y_LABEL_OFFSET = 20;

    // //////////////////////////////////////////////////////
    // main - creates and displays the frame
    // //////////////////////////////////////////////////////
    public static void main(String[] args) {
        FaceFrame f = new FaceFrame();        
        f.setVisible(true);
    }

    /////////////////////////
    // INSTANCE VARIABLES
    /////////////////////////

    // The faces
    private Face[] faces;

    // The labels for the faces
    private FaceLabel[] labels;

    ////////////////////////////////////////////////////////
    // constructor
    ////////////////////////////////////////////////////////
    public FaceFrame() {
        setSize(1100,1050);
        setDefaultCloseOperation(EXIT_ON_CLOSE);      
        init();
    }

    ////////////////////////////////////////////////////////
    // init - initializes the display
    //
    // side-effects:
    //   creates the faces to be displayed; sets the background color
    //
    // bugs/anomalies:
    //   none known.
    //
    ////////////////////////////////////////////////////////
    public void init() {        
        getContentPane().setBackground(Color.WHITE);
        
        // create the face-array
        faces = new Face[7];

        // create the label-array
        labels = new FaceLabel[7];

        // create first row (one face)
        faces[0] = new Face(FACE_SIZE, FACE_SIZE);
        faces[0].setLocation(400, Y_COORD_ROW_0);
        labels[0] = new FaceLabel("FACE", 400 + FACE_SIZE/2 - X_LABEL_OFFSET, Y_COORD_ROW_0 + FACE_SIZE + Y_LABEL_OFFSET);

        // create second row (two faces)
        faces[1] = new SimpsonFace(FACE_SIZE, FACE_SIZE);
        faces[1].setLocation(160, Y_COORD_ROW_1);
        labels[1] = new FaceLabel("SIMPSON", 160 + faces[1].getWidth()/2 - X_LABEL_OFFSET-10, Y_COORD_ROW_1 + faces[1].getHeight() + Y_LABEL_OFFSET);             

        faces[2] = new HillFace(FACE_SIZE,FACE_SIZE);
        faces[2].setLocation(680, Y_COORD_ROW_1);
        labels[2] = new FaceLabel("HILL", 680 + faces[2].getWidth()/2 - X_LABEL_OFFSET, Y_COORD_ROW_1 + faces[2].getHeight() + Y_LABEL_OFFSET);

        // create third row (four faces)
        faces[3] = new HomerFace(FACE_SIZE,FACE_SIZE);
        faces[3].setLocation(30, Y_COORD_ROW_2);
        labels[3] = new FaceLabel("HOMER", 30 + faces[3].getWidth()/2 - X_LABEL_OFFSET-5, Y_COORD_ROW_2 + faces[3].getHeight() + Y_LABEL_OFFSET);

        faces[4] = new MargeFace(FACE_SIZE,FACE_SIZE);
        faces[4].setLocation(290, Y_COORD_ROW_2);
        labels[4] = new FaceLabel("MARGE", 290 + faces[4].getWidth()/2 - X_LABEL_OFFSET-5, Y_COORD_ROW_2 + faces[4].getHeight() + Y_LABEL_OFFSET);

        faces[5] = new HankFace(FACE_SIZE,FACE_SIZE);
        faces[5].setLocation(550, Y_COORD_ROW_2);
        labels[5] = new FaceLabel("HANK", 550 + faces[5].getWidth()/2 - X_LABEL_OFFSET, Y_COORD_ROW_2 + faces[5].getHeight() + Y_LABEL_OFFSET);

        faces[6] = new BobbyFace(FACE_SIZE,FACE_SIZE);
        faces[6].setLocation(810, Y_COORD_ROW_2);
        labels[6] = new FaceLabel("BOBBY", 810 + faces[6].getWidth()/2 - X_LABEL_OFFSET, Y_COORD_ROW_2 + faces[6].getHeight() + Y_LABEL_OFFSET);
    }

    ////////////////////////////////////////////////////////
    // paint - paints the window
    //
    // parameters:
    //   g - the Graphics object
    //
    // side-effects:
    //   draws faces on the graphics object.  This method
    //   leaves the graphics object's color unchanged.
    //
    // bugs/anomalies:
    //   none known.
    //
    ////////////////////////////////////////////////////////
    public void paint(Graphics g) {  
        super.paint(g);

        // draw the faces
        for (int i = 0; i < faces.length; i++) {
            faces[i].drawOn(g);
            if (labels[i] != null) {
                labels[i].drawOn(g);
            }
        }
    }
}
