import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

/**
 * Class Composer - Draws and plays music by the user
 * 
 * @author Bryce Kwon
 * @version 2022-10-03
 */
public class Composer extends JPanel implements KeyListener {

    // describes the song being played
    String song = "";

    /**
     * paint - draws the music in the window
     * 
     * @param canvas
     */
    public void paint(Graphics canvas) {
        // create window for graphic
        super.paint(canvas);
        setBackground(Color.WHITE);

        // music character unicodes
        String trebleClef = "\ud834\udd1e";
        String quarterNote = "\u2669";
        String quarterRest = "\ud834\udd3d";
        String halfNote = "\uD834\udd5e";

        // y-axis location of notes
        int aNote = WINDOW_HEIGHT - 225;
        int bNote = WINDOW_HEIGHT - 233;
        int cNote = WINDOW_HEIGHT - 185;
        int dNote = WINDOW_HEIGHT - 193;
        int eNote = WINDOW_HEIGHT - 201;
        int fNote = WINDOW_HEIGHT - 209;
        int gNote = WINDOW_HEIGHT - 217;
        int restNote  = WINDOW_HEIGHT - 169;

        // draw music bars
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(50, (WINDOW_HEIGHT - 150) - (i*15), 850, (WINDOW_HEIGHT - 150) - (i*15));
            canvas.drawLine(50 + (i*200), (WINDOW_HEIGHT - 150), 50 + (i*200), (WINDOW_HEIGHT - 150) - (15*4));
        }

        // draw treble clef
        canvas.setFont(new Font("Sans", Font.PLAIN, 110));
        canvas.drawString(trebleClef, 50, WINDOW_HEIGHT - 140);

        // draw 4/4 time signature
        canvas.setFont(new Font("Sans", Font.PLAIN, 30));
        canvas.drawString("4", 100, WINDOW_HEIGHT - 155);
        canvas.drawString("4", 100, WINDOW_HEIGHT - 180);

        // find last 12 notes of user input
        String music = findNotes(song);

        // draw notes on the music bars
        for (int i = 0, j = 0; i < music.length(); i++) {
            int beat = 250 + (i*50) + (j*50);

            switch(music.charAt(i)) {
                case ' ':
                canvas.drawString(quarterRest, beat, restNote);
                break;
                case 'a':
                canvas.drawString(quarterNote, beat, aNote);
                break;
                case 'b':
                canvas.drawString(quarterNote, beat, bNote);
                break;
                case 'c':
                canvas.drawString(quarterNote, beat, cNote);
                break;
                case 'd':
                canvas.drawString(quarterNote, beat, dNote);
                break;
                case 'e':
                canvas.drawString(quarterNote, beat, eNote);
                break;
                case 'f':
                canvas.drawString(quarterNote, beat, fNote);
                break;
                case 'g':
                canvas.drawString(quarterNote, beat, gNote);
                break;
                case 'A':
                canvas.drawString(halfNote, beat, aNote);
                j++;
                break;
                case 'B':
                canvas.drawString(halfNote, beat, bNote);
                j++;
                break;
                case 'C':
                canvas.drawString(halfNote, beat, cNote);
                j++;
                break;
                case 'D':
                canvas.drawString(halfNote, beat, dNote);
                j++;
                break;
                case 'E':
                canvas.drawString(halfNote, beat, eNote);
                j++;
                break;
                case 'F':
                canvas.drawString(halfNote, beat, fNote);
                j++;
                break;
                case 'G':
                canvas.drawString(halfNote, beat, gNote);
                j++;
                break;
                default:
                break;
            }
        }
    }//paint

    /**
     * findNotes - finds the last 12 notes of the user input
     * 
     * @param song
     * @return last 12 notes, otherwise all
     */
    public String findNotes(String song) {

        // create a new string with last 12 characters of user input
        String newSong = "";
        int songLen = 0;
        for (int i = song.length() - 1; i >= 0; i--) {
            if (songLen >= 12 || song.length() <= 0) {
                break;
            } else if (Character.isUpperCase(song.charAt(i))) {
                if (songLen + 2 > 12) {
                    break;
                } else {
                    newSong = song.charAt(i) + newSong;
                    songLen += 2;
                }
            } else if (Character.isLowerCase(song.charAt(i))) {
                if (songLen + 1 > 12) {
                    break;
                } else {
                    newSong = song.charAt(i) + newSong;
                    songLen += 1;
                }
            } else {
                if (songLen + 1 > 12) {
                    break;
                } else {
                    newSong = song.charAt(i) + newSong;
                    songLen += 1;
                }
            }
        }

        return newSong;
    }//findNotes

    public void play() {
        // create ToneGen object
        ToneGen player = new ToneGen();

        // find notes on the music bar
        String music = findNotes(song);

        // play notes on the music bar
        while (music.length() > 0) {
            if (Character.isUpperCase(music.charAt(0))) {
                player.playNote(music.charAt(0), 500);
                music = music.substring(1);
            } else if (Character.isLowerCase(music.charAt(0))) {
                player.playNote(music.charAt(0), 250);
                music = music.substring(1);
            }  else {
                player.rest(250);
                music = music.substring(1);
            }
        }
    }//play

    /*======================================================================
     *      >>    >>    >>    ATTENTION STUDENTS    <<    <<     <<
     *
     * None of the code below this line should be modified.  You are welcome
     * to review this code if you wish to help you understand how the program
     * works.
     *----------------------------------------------------------------------
     */

    //the size of the window (do not modify this)
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;

    //these variables are used to track how long the user holds down a key
    public char lastKey = '\0';
    public long pressTime = -1;

    /**
     * keyPressed
     *
     * is called each time any key is pressed.
     */
    public void keyPressed(KeyEvent keyEvent) {
        //We have to check for a backspace separately since it's
        //a special key code
        if (keyEvent.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
            //remove the last note (if there is one)
            int len = song.length();
            if (len > 0) {
                song = song.substring(0, len - 1);
            }
            repaint();
            return;
        }

        //Ignore invalid keys
        String validKeys = "abcdefgABCDEFG pqr";
        char key = keyEvent.getKeyChar();
        if (validKeys.indexOf(key) == -1) {
            return;
        }

        //Handle the keypress
        switch(key) {
            case 'q':  //quit
            System.exit(0);  
            case 'p':
            play();
            break;
            case 'r':
            song = "";
            repaint();
            break;
            default:  //must be a note!
            if (key != lastKey) {
                pressTime = System.currentTimeMillis();
                lastKey = key;
            }
            break;
        }

    }//keyPressed

    /**
     * keyReleased
     *
     * is called each time a key is released.
     */
    public void keyReleased(KeyEvent keyEvent) {
        //Ignore invalid keys
        char key = keyEvent.getKeyChar();
        if (key != lastKey) {
            return;
        }

        //Calculate duration
        long releaseTime = System.currentTimeMillis();
        int totalTime = (int)(releaseTime - pressTime);

        //Report the note played
        //375+ ms is a half note (quarter note is default)
        if (totalTime >= 375) {
            key = Character.toUpperCase(key);
        }
        song += key;  //Add the note to the song


        //Reset for next keypress
        lastKey = '\0';
        pressTime = -1;

        //draw the updated song
        repaint();
    }//keyReleased


    //not used
    public void keyTyped(KeyEvent keyEvent) { /** do nothing */ }



    /**
     * main
     *
     * This method starts the application. 
     */
    public static void main(String[] args) {
        //Create the window
        JFrame frame = new JFrame();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Gather input from the user
        Composer music = new Composer();
        frame.add(music);

        //Listen for keystrokes
        frame.addKeyListener(music);

        //Display the window
        frame.setVisible(true);
        frame.repaint();
    }

}//class Composer
