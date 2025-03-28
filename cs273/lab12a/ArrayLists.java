import java.util.*;

/**
 * Write a description of class ArrayLists here.
 *
 * @author 
 * @version 
 */
public class ArrayLists
{
    public static void main(String[] args) {   
        /* Do not use any hardcoded String values in the methods below */ 
        /* Uncomment the code in the main method below as you progress throughout the lab */
       
        String scarf = "Scarf";
        String[] items = {"Coat", "Shoes", "Shirt", "Pants"};
        ArrayList al = initList(items);
        System.out.println("\n**Contents of Initialized ArrayList**");
        printArrayList(al); 

        
        String[] biggerArray = addToArray(items, scarf); 
        System.out.println("\n**Contents of Collections After Adding Item**");
        printArray(biggerArray); 
        addToArrayList(al, scarf); 
        printArrayList(al); 
        

        System.out.println("\n**Contents of Array After Removal**");
        removeFromArray(biggerArray,"Shoes");
        printArray(biggerArray); 
       
        System.out.println("\n**Contents of ArrayList After Removal**");
        removeFromArrayList(al,"Shoes");
        printArrayList(al); 
    }

    public static ArrayList<String> initList(String[] clothing) { 
        // Create an ArrayList
        ArrayList<String> list = new ArrayList<>(clothing.length);
        
        // Copy contents of clothing array into ArrayList
        for (String clothes : clothing) {
            list.add(clothes);
        }

        // Return the ArrayList
        return list;
    }

    public static String[] addToArray(String[] clothing, String addItem) {         
        // Create new array and add contents
        String[] newArray = new String[clothing.length + 1];
        
        for (int i = 0; i < clothing.length; i++) {
            newArray[i] = clothing[i];
        }
        newArray[clothing.length] = addItem;

        // This is a placeholder. You will need to update it. 
        return newArray;
    }

    public static void addToArrayList(ArrayList<String> al, String addItem) {         
        // Add item to ArrayList
        al.add(addItem);
    }

    public static void printArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }
    
    public static void printArrayList(ArrayList<String> al) { 
        System.out.println(al);
    }
    
    public static void removeFromArray(String[] clothing, String removeItem) {         
        for (int i = 0; i < clothing.length; i++) {
            if (clothing[i].equals(removeItem)) {
                clothing[i] = null;
                break;
            }
        }
        
        for (int i = 0; i < clothing.length; i++) {
            if (clothing[i] == null && i < clothing.length - 1) {
                clothing[i] = clothing[i+1];
                clothing[i+1] = null;
                i--;
            }
        }
    }

    public static void removeFromArrayList(ArrayList<String> al, String removeItem) {         
        al.remove(removeItem);
    }
}
