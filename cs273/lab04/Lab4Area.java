// import libraries
import java.awt.*;

/**
 * class Lab4Area - draws a robot whose appearance is customized based
 * on which boxes are checked.
 */
public class Lab4Area extends Lab4BaseArea {
    
    public void paint(Graphics g) {
        /*** draw the robot's body ***/
        
        // declare custom attributes
        Color lightGreen = new Color(166,255,166);
        Color skyBlue = new Color(135,206,235);
        Color silver = new Color(192,192,192);
        
        // set default attributes
        g.setColor(silver);
        
        // monitor checked boxes for selected features (body color)
        if (isChecked("Silver Body")) {
            g.setColor(silver);
        } else if (isChecked("Green Body")) {
            // if green eyes feature checked brighten body
            if (isChecked("Green Eyes"))  {
                g.setColor(lightGreen);
            } else {
                g.setColor(Color.GREEN);
            }
        } else if (isChecked("Pink Body")) {
            g.setColor(Color.PINK);
        } else if (isChecked("Sky Blue Body")) {
            g.setColor(skyBlue);
        }
        
        // fill polygon with selected attributes
        g.fillPolygon(body);

        
        /*** draw the robot's ears (pink, for now) ***/
        
        // declare custom attributes
        Color purple = new Color(223,81,223);
        
        // set default attributes
        Polygon myRightEar = rightEar;
        Polygon myLeftEar = leftEar;
        g.setColor(Color.PINK);
        
        // monitor checked boxes for selected features (ear size)
        if (isChecked("Big Ears")) {
            myRightEar = bigRightEar;
            myLeftEar = bigLeftEar;
        }
        
        // monitor checked boxes for selected features (ear color)
        if (isChecked("Red Ears") && isChecked("Blue Ears")) {
            g.setColor(purple);
        } else if (isChecked("Red Ears")) {
            g.setColor(Color.RED);
        } else if (isChecked("Blue Ears")) {
            g.setColor(Color.BLUE);
        }
        
        // fill polygon with selected attributes
        g.fillPolygon(myRightEar);
        if (isChecked("Left Normal")) {
            g.setColor(Color.PINK);
            myLeftEar = leftEar;
        }
        g.fillPolygon(myLeftEar);


        /*** draw the mouth (red, for now) ***/
        
        // set default attributes
        Polygon myMouth = smileMouth;
        
        // monitor checked boxes for selected features (mouth shape)
        if (isChecked("Frown") && isChecked("Whistle")) {
            // most recent checked box gets precedence.
            if (whistleMoreRecent()) {
                myMouth = whistleMouth;
            } else {
                myMouth = frownMouth;
            }
        } else if (isChecked("Frown")) {
            myMouth = frownMouth;
        } else if (isChecked("Whistle")) {
            myMouth = whistleMouth;
        }
        
        // monitor checked boxes for selected features (mouth color)
        if (totalNumChecked() > 10) {
            g.setColor(Color.BLACK);
        } else if (totalNumChecked() % 2 == 0) {
            g.setColor(Color.WHITE);
        } else if (totalNumChecked() > 1 && totalNumChecked() < 9) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.RED);
        }
        
        // fill polygon with selected attributes
        g.fillPolygon(myMouth);


        /*** draw the eyes (blue) ***/
        
        // set default attributes
        Polygon myRightEye = rightEye;
        Polygon myLeftEye = leftEye;
        g.setColor(Color.BLUE);
        
        // monitor checked boxes for selected features (eye orientation)
        if (isChecked("Cross-Eyed")) {
            myRightEye = rightEyeIn;
            myLeftEye = leftEyeIn;
        } else if (isChecked("Look Robot's Right") && isChecked("Look Robot's Left")) {
            myRightEye = rightEye;
            myLeftEye = leftEye;
        } else if (isChecked("Look Robot's Right")) {
            myRightEye = rightEyeOut;
            myLeftEye = leftEyeIn;
        } else if (isChecked("Look Robot's Left")) {
            myRightEye = rightEyeIn;
            myLeftEye = leftEyeOut;
        }
        
        // monitor checked boxes for selected features (eye color)
        if (isChecked("Green Eyes")) {
            g.setColor(Color.GREEN);
        }
        
        // fill polygon with selected attributes
        g.fillPolygon(myRightEye);
        if (isChecked("Left Normal")) {
            g.setColor(Color.BLUE);
            myLeftEye = leftEye;
        }
        g.fillPolygon(myLeftEye);

        
        /*** draw the robot's legs (green) ***/
        
        // set default attributes
        Polygon myRightLeg = rightLeg;
        Polygon myLeftLeg = leftLeg;
        g.setColor(Color.GREEN);
        
        // monitor checked boxes for selected features (leg orientation)
        if (isChecked("Bent Legs") && isChecked("Big Feet")) {
            myRightLeg = rightLegBentBigFeet;
            myLeftLeg = leftLegBentBigFeet;
        } else if (isChecked("Big Feet")) {
            myRightLeg = rightLegBigFeet;
            myLeftLeg = leftLegBigFeet;
        } else if (isChecked("Bent Legs")) {
            myRightLeg = rightLegBent;
            myLeftLeg = leftLegBent;
        }
        
        // fill polygon with selected attributes
        g.fillPolygon(myRightLeg);
        if (isChecked("Left Normal")) {
            g.setColor(Color.GREEN);
            myLeftLeg = leftLeg;
        }
        g.fillPolygon(myLeftLeg);
        

        /*** draw the robot's arms (black) ***/
        
        // set default attributes
        Polygon myRightArm = rightArm;
        Polygon myLeftArm = leftArm;
        g.setColor(Color.BLACK);
        
        // monitor checked boxes for selected features (arm orientation)
        if (isChecked("Short Arms")) {
            myRightArm = shortRightArm;
            myLeftArm = shortLeftArm;
        } else if (isChecked("Long Arms")) {
            // arms bent down take precedence
            if (isChecked("Arms Bent Down")) {
                myRightArm = longRightArmBentDown;
                myLeftArm = longLeftArmBentDown;
            } else if (isChecked("Arms Bent Ep")) {
                myRightArm = longRightArmBentUp;
                myLeftArm = longLeftArmBentUp;
            } else {
                myRightArm = longRightArm;
                myLeftArm = longLeftArm;
            }
        } else {
            // arms bent down take precedence
            if (isChecked("Arms Bent Down")) {
                myRightArm = rightArmBentDown;
                myLeftArm = leftArmBentDown;
            } else if (isChecked("Arms Bent Up")) {
                myRightArm = rightArmBentUp;
                myLeftArm = leftArmBentUp;
            }
        }
        
        // fill polygon with selected attributes
        g.fillPolygon(myRightArm);
        if (isChecked("Left Normal")) {
            g.setColor(Color.BLACK);
            myLeftArm = leftArm;
        }
        g.fillPolygon(myLeftArm);
    }
}