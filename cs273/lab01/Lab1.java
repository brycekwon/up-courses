// import libraries
import java.util.*;

/**
 * Class Lab1 - Conducts a simple text-interaction with the user.
 * 
 * @author Bryce Kwon
 * @version 2022-08-29
 */
public class Lab1 {
    public static void main(String[] args) {
        // print out the secret code word
        String codeWord = "hamster";
        System.out.println("The code word is " + codeWord);
        
        // declare a keyboard scanner
        Scanner keyboard = new Scanner(System.in);
        
        // prompt user for input
        System.out.print("What is your name? ");
        String name = keyboard.nextLine();
        
        // print out a simple greeting
        System.out.println("Hello, " + name);
    } //main
}