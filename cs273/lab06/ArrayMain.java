import java.io.*;
import java.util.*;

public class ArrayMain {

    /**
     * Our main method.
     *  - Prompt user for a # of elements.
     *  - Create array of that size, using random values in the range
     *    -1000 to 1000, inclusive.
     *  - Compute and print various things about the array's contents.
     * @param args
     */
    public static void main(String[] args) {
        int numElements = getNumElements();
        if (numElements <= 0) {
            System.out.println("We need a positive number.");
            return;
        }

        // declare and initialize our array
        int[] numberArray = createNumberArray(numElements);

        /////////////////////////////////////////////////////////////////
        //   At this point, the array has been created and initialized.
        /////////////////////////////////////////////////////////////////

        // ***** CHECKPOINT 1 *****
        // Print the numbers, followed by a line of dashes
        for (int i = 0; i < numberArray.length; i++) {
            System.out.print(numberArray[i] + " ");
        }
        System.out.println();
        for (int j = 0; j < 20; j++) {
            System.out.print("-");
        }
        System.out.println();


        // ***** CHECKPOINT 2 *****
        // Compute/print the average of the numbers
        int total = 0;        
        for (int i = 0; i < numberArray.length; i++) {
            total += numberArray[i];
        }
        System.out.println("The average of the numbers is " + ((double) total / (double) numberArray.length));
        
        
        // ***** CHECKPOINT 3 *****
        // Set a breakpoint somewhere above and show the contents of the array
        

        // ***** CHECKPOINT 4 *****
        // Find/print the smallest number in the array
        int smallest = numberArray[0];
        for (int i = 1; i < numberArray.length; i++) {
            if (numberArray[i] < smallest) {
                smallest = numberArray[i];
            }
        }
        System.out.println("The minimum element is " + smallest);


        // ***** CHECKPOINT 5 *****
        // Find/print the percentage of numbers that is even
        int evenNums = 0;
        for (int i = 0; i < numberArray.length; i++) {
            if (numberArray[i] % 2 == 0) {
                evenNums++;
            }
        }
        double evens = (double)evenNums / (double)numberArray.length * 100;
        System.out.println("The percentage of even numbers is " + evens + "%");


        // ***** CHECKPOINT 6 *****
        // Compute/print the percentage of numbers in the range -300 to 300
        int rangeNums = 0;
        for (int i = 0; i < numberArray.length; i++) {
            if (numberArray[i] >= -300 && numberArray[i] <= 300) {
                rangeNums++;
            }
        }
        double range = (double)rangeNums / (double)numberArray.length * 100;
        System.out.println("The percentage of numbers in the range -300..300 is " + range + "%");


        // ***** CHECKPOINT 7 *****
        // Sort the numbers and print them out
        for (int i = 0; i < numberArray.length; i++) {
            int currentNum = numberArray[i];
            int index = i;
            int tmp;
            for (int j = i; j < numberArray.length; j++) {
                if (numberArray[j] >= currentNum) {
                    currentNum = numberArray[j];
                    index = j;
                }
            }
            tmp = numberArray[i];
            
            numberArray[i] = currentNum;
            numberArray[index] = tmp;
        }
        
        for (int i = 0; i < numberArray.length; i++) {
            System.out.print(numberArray[i] + " ");
        }
        System.out.println();
        for (int j = 0; j < 20; j++) {
            System.out.print("-");
        }
        System.out.println();
        
        
        // ***** CHECKPOINT 8 *****
        // Print the sorted numbers up to 10 per line
        for (int i = 0; i < numberArray.length; i++) {
            int currentNum = numberArray[i];
            int index = i;
            int tmp;
            for (int j = i; j < numberArray.length; j++) {
                if (numberArray[j] >= currentNum) {
                    currentNum = numberArray[j];
                    index = j;
                }
            }
            tmp = numberArray[i];
            
            numberArray[i] = currentNum;
            numberArray[index] = tmp;
        }
        
        int row = 0;
        for (int i = 0; i < numberArray.length; i++) {
            row++;
            if (row < 10) {
                System.out.print(numberArray[i] + " ");
            } else {
                System.out.println(numberArray[i] + " ");
                row = 0;
            }
        }
        System.out.println();

    }
    
    private static int getNumElements() {
        Scanner keyboard = new Scanner(System.in); // set up for keyboard input

        // prompt user for file name; read file name
        System.out.print("Number of elements: ");
        System.out.flush();
        
        return keyboard.nextInt();
    }

    private static int[] createNumberArray(int numElements) {
        Random rand = new Random();
        return rand.ints(numElements, -1000, 1001).toArray();
    }
}