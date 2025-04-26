// import libraries
import java.util.*;

/**
 * Class MadLibs - Prints a story with custom user input and phrases.
 * 
 * @author Bryce Kwon
 * @version 2022-09-11
 */
public class Madlib {
    public static void main(String[] args) {

        // declare a keyboard listener
        Scanner keyboard = new Scanner(System.in);

        // display welcome message
        System.out.println("Welcome to the Madlibs Generator\n");

        // declare global variables
        String sillyName;
        String sillyWord;
        String verb1;
        String noun1;
        String bodyPart;
        String femaleName;
        String verb2;
        String noun2;
        String noun3;
        String verb3;
        String occupation;
        double number;
        String verb4;
        String sillyWord2;
        String sillyName2;

        // prompt user for input
        System.out.print("Please enter a silly name (capitalize): ");
        // capitalize first letter and lowercase rest
        sillyName = keyboard.nextLine().trim();
        sillyName = sillyName.substring(0, 1).toUpperCase() + sillyName.substring(1).toLowerCase();

        System.out.print("Please enter a silly word (capitalize): ");
        // capitalize first letter and lowercase rest
        sillyWord = keyboard.nextLine().trim();
        sillyWord = sillyWord.substring(0, 1).toUpperCase() + sillyWord.substring(1).toLowerCase();

        System.out.print("Please enter a verb: ");
        // uppercase input
        verb1 = keyboard.nextLine().trim().toUpperCase();

        System.out.print("Please enter a noun: ");
        noun1 = keyboard.nextLine().trim().toLowerCase();

        System.out.print("Please enter a body part (plural): ");
        bodyPart = keyboard.nextLine().trim().toLowerCase();

        // convert input to 1337 speak
        if (bodyPart.indexOf("a") >= 0) {
            bodyPart = bodyPart.substring(0, bodyPart.indexOf("a")) + "4" + bodyPart.substring(bodyPart.indexOf("a")+1);
        }
        if (bodyPart.indexOf("b") >= 0) {
            bodyPart = bodyPart.substring(0, bodyPart.indexOf("b")) + "13" + bodyPart.substring(bodyPart.indexOf("b")+1);
        }
        if (bodyPart.indexOf("c") >= 0) {
            bodyPart = bodyPart.substring(0, bodyPart.indexOf("c")) + "(" + bodyPart.substring(bodyPart.indexOf("c")+1);
        }
        if (bodyPart.indexOf("d") >= 0) {
            bodyPart = bodyPart.substring(0, bodyPart.indexOf("d")) + "[)" + bodyPart.substring(bodyPart.indexOf("d")+1);
        }
        if (bodyPart.indexOf("e") >= 0) {
            bodyPart = bodyPart.substring(0, bodyPart.indexOf("e")) + "3" + bodyPart.substring(bodyPart.indexOf("e")+1);
        }
        if (bodyPart.indexOf("f") >= 0) {
            bodyPart = bodyPart.substring(0, bodyPart.indexOf("f")) + "|=" + bodyPart.substring(bodyPart.indexOf("f")+1);
        }

        System.out.print("Please enter a female name: ");
        // capitalize first letter and lowercase rest
        femaleName = keyboard.nextLine().trim().toLowerCase();
        femaleName = femaleName.substring(0, 1).toUpperCase() + femaleName.substring(1).toLowerCase();

        System.out.print("Please enter a verb (ending in -ed): ");
        verb2 = keyboard.nextLine().trim().toLowerCase();

        System.out.print("Please enter a verb: ");
        noun2 = keyboard.nextLine().trim().toLowerCase();

        System.out.print("Please enter a noun (plural): ");
        noun3 = keyboard.nextLine().trim().toLowerCase();

        System.out.print("Please enter a verb: ");
        verb3 = keyboard.nextLine().trim().toLowerCase();

        System.out.print("Please enter a occupation: ");
        occupation = keyboard.nextLine().trim().toLowerCase();

        System.out.print("Please enter a number: ");
        number = keyboard.nextDouble();
        keyboard.nextLine();

        System.out.print("Please enter a verb: ");
        verb4 = keyboard.nextLine().trim().toLowerCase();

        System.out.print("Please enter a silly word (capitalize): ");
        // capitalize first letter and lowercase rest
        sillyWord2 = keyboard.nextLine().trim();
        sillyWord2 = sillyWord2.substring(0, 1).toUpperCase() + sillyWord2.substring(1).toLowerCase();

        System.out.print("Please enter a silly name (two words): ");
        sillyName2 = keyboard.nextLine().trim();
        int space = sillyName2.indexOf(" ");
        // reverse the order of a two word name
        sillyName2 = sillyName2.substring(0, 1).toUpperCase() + sillyName2.substring(1).toLowerCase();
        // hyphenate in the (somewhat) middle of the input
        sillyName2 = sillyName2.substring(space+1, space+2).toUpperCase() + sillyName2.substring(space+2) + "-" + sillyName2.substring(0, space);

        // display madlibs (tabbing new paragrpahs)
        System.out.println("\n\n\n\t\tCan I Have Your Daughter's Hand?");
        System.out.println("\n\nDear Mr. and Mrs. " + sillyName + " " + sillyWord + ",\n");
        System.out.println("\tWill you let me " + verb1 + " your " + noun1 + "? ");
        System.out.println("Ever since I laid " + bodyPart + " on " + femaleName + ", I have " + verb2 + " madly in love with her.");
        System.out.println("I wish that she will be the " + noun2 + " of my " + noun3 + " and that someday we will " + verb3 + " happily ever after.\n");
        System.out.println("\tI have a job as a/an " + occupation + " that pays $" + number + " each month.");
        System.out.println("I promise to " + verb4 + " " + femaleName + " with kindness and respect.\n");
        System.out.println("Sincerely, \n" + sillyWord2 + " " + sillyName2 + " (spelled with " + (sillyWord2.length() + sillyName2.length()) + " letters)");
    }

}
