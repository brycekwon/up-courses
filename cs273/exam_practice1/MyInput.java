import java.util.Scanner;
import java.util.ArrayList;

/**
 * MyInput
 * 
 * @author Bryce Kwon
 * @version Dec. 11, 2022
 * 
 * I Bryce Kwon, pledge on my honor that I will not give, receive, or use any unauthorized
 * materials or assistance on this exam. I further pledge that I will not engage in cheating,
 * forgery, or plagiarism.
 */
public class MyInput {
    public ArrayList<String> gatherData() {
        Scanner keyboard = new Scanner(System.in);

        ArrayList<String> words = new ArrayList<String>();

        System.out.print("How Many Words: ");
        int numWords = keyboard.nextInt();
        keyboard.nextLine();

        for (int i = 0; i < numWords; i++) {
            System.out.print("Enter Word #" + (i + 1) + ": ");
            words.add(keyboard.nextLine());
        }

        keyboard.close();

        return words;
    }

    public void printCollection(ArrayList<String> words) {
        String spaces = getSpaces(words);

        for (int j = 0; j < words.size(); j++) {
            for (int i = 0; i < words.size(); i++) {
                System.out.print(words.get(i) + spaces);
            }
            System.out.println();
        }
    }

    private String getSpaces(ArrayList<String> words) {
        String spaces = "";

        for (int i = 0; i < words.size(); i++) {
            spaces += " ";
        }

        return spaces;
    }
}
