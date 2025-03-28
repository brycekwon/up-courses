import java.util.ArrayList;
import java.util.Scanner;

/**
 * Initializes an ArrayList with a set of numbers in a jumbled order
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ArrayListSearch
{
    public static void main(String[] args) { 
        Integer[] integers = {0, 20, 10, 30, 60, 40, 50, 80, 70}; 
        ArrayList<Integer> al = new ArrayList<>();
        
        for(Integer num : integers) { 
            al.add(num);
        }
        
        /* add your code here */
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        Integer num = new Integer(keyboard.nextInt());
        
        int index = al.indexOf(num);
        if (index == -1) {
            System.out.println("Number not found!");
        } else {
            System.out.println("Number found at index " + index);
        }
    }
}
