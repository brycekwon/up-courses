import java.util.Scanner;

/**
 * Main
 * 
 * @author Bryce Kwon
 * @version Dec. 13, 2022
 * 
 * I, Bryce Kwon, pledge on my honor that I will not give, receive, or use any unauthorized
 * materials, or assitstance on this exam. I further pledge that I will not engage in cheating,
 * forgery, or plagiarism.
 */
public class Main {
    public static void main(String[] args) {

        // scanner object to prompt user
        Scanner scnr = new Scanner(System.in);

        // obtain tree height
        System.out.print("Enter a tree height: ");
        double treeHeight = scnr.nextDouble();
        scnr.nextLine();

        // obtain tree species
        System.out.print("Enter a tree species: ");
        String treeSpecies = scnr.nextLine();

        // create user generated christmas tree
        ChristmasTree userTree = new ChristmasTree(treeHeight, treeSpecies);

        // create default christmas tree
        ChristmasTree defaultTree = new ChristmasTree();
        defaultTree.setHeight(4);
        defaultTree.setSpecies("alpine");

        // print the computed predicted height based on 5 years of 4-inch annual growth
        System.out.println(userTree.predictHeight(4, 2));

        // put both trees in an array
        ChristmasTree[] collection = new ChristmasTree[]{userTree, defaultTree};

        // print out the smallest tree height (static)
        System.out.println(ChristmasTree.smallestTree(collection));

        // print out a list of unique species (static)
        System.out.println(ChristmasTree.uniqueSpecies(collection));

        // close scanner
        scnr.close();
    }
}
