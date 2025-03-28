import java.util.*;
import javax.sound.sampled.*;

/**
 * class ToneGen
 *
 * is used to play simple notes.  This code was adapted from an original posted
 * here:  https://groups.google.com/g/comp.lang.java.help/c/7vR_AWw1AwQ?pli=1
 *
 * @author Knute Johnson (original)
 * @author Andrew Nuxoll (modifications)
 * @version Fall 2022
 */
public class ToneGen {
    public static final float SAMPLE_RATE = 8000f;

    //Here are some songs. (An uppercase letter is half note; lowercase
    //is a quarter note.)
    String mary = "edcdeeEddDeeEedcdeeEeddedC";
    String twinkle = "ccggaaGffeeddCggffeeDggffeeDccggaagffeeddC";
    String jingle = "gedcGgggedcAAafedBBggfde-gedcGgedcAaaafedgggg";
    String london = "gagfefGdeFefGgagfefGDGec";


    /**
     * sound
     * 
     * plays a single tone with given parameters.
     *
     * @param hz    frequency in hertz
     * @param msecs duration in milliseconds
     * @param vol   volume as a value in the range [0.0..1.0]
     */
    public void sound(int hz, int msecs, double vol) {
        //check for invalid input
        if (hz <= 0) return;
        if (msecs <= 0) return;
        if (vol > 1.0 || vol < 0.0) return;

        //Create a sound
        byte[] buf = new byte[(int)SAMPLE_RATE * msecs / 1000];
        for (int i=0; i<buf.length; i++) {
            double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
            buf[i] = (byte)(Math.sin(angle) * 127.0 * vol);
        }

        // shape the front and back 10ms of the wave form
        for (int i=0; i < SAMPLE_RATE / 100.0 && i < buf.length / 2; i++) {
            buf[i] = (byte)(buf[i] * i / (SAMPLE_RATE / 100.0));
            buf[buf.length-1-i] =
        (byte)(buf[buf.length-1-i] * i / (SAMPLE_RATE / 100.0));
        }

        //play the sound
        AudioFormat af = new AudioFormat(SAMPLE_RATE,8,1,true,false);
        SourceDataLine sdl = null;
        try {
            sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
        } catch(LineUnavailableException lue) {
            System.err.println("Error:  Could not access a sound output device");
            return;
        }
        sdl.start();
        sdl.write(buf,0,buf.length);
        sdl.drain();
        sdl.close();
    }//sound

    /**
     * rest
     *
     * pauses for the indicated period of time
     *
     * @param msecs duration of the note in milliseconds
     */
    public void rest(int msecs) {

        try {
            Thread.sleep(msecs);
        } catch(InterruptedException ie) {
        }
    }

    /**
     * playNote
     * 
     * plays a note given a letter: ABCDEFG
     *
     * @param note  the letter for the note
     * @param msecs duration of the note in milliseconds
     */
    public void playNote(char note, int msecs) {
        note = Character.toUpperCase(note);
        if (note == 'A') {
            sound(440 ,msecs, 0.2);  
        }
        else if (note == 'B') {
            sound(494 ,msecs, 0.2);  
        }
        else if (note == 'C') {
            sound(262 ,msecs, 0.2);  
        }
        else if (note == 'D') {
            sound(294, msecs, 0.2);  
        }
        else if (note == 'E') {
            sound(330 ,msecs, 0.2);  
        }
        else if (note == 'F') {
            sound(349, msecs, 0.2);  
        }
        else if (note == 'G') {
            sound(392,msecs, 0.2);  
        }
    }//playNote

}//class ToneGen
