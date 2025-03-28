import java.lang.Math;
import java.util.ArrayList;

/**
 * ChristmasTree
 * 
 * @author Bryce Kwon
 * @version Dec. 13, 2022
 * 
 * I, Bryce Kwon, pledge on my honor that I will not give, receive, or use any unauthorized
 * materials, or assitstance on this exam. I further pledge that I will not engage in cheating,
 * forgery, or plagiarism.
 */
public class ChristmasTree {
    private double height;
    private String species;

    // default constructor
    public ChristmasTree() {
        height = 0;
        species = "Spruce";
    }

    // non-default constructor
    public ChristmasTree(double height, String species) {
        this.height = height;
        this.species = species;
    }

    /**
     * getHeight - gets this christmas tree's height
     * 
     * @return double       height in feet
     */
    public double getHeight() {
        return height;
    }

    /**
     * getSpecies - gets this christmas tree's species
     * 
     * @return String       tree species
     */
    public String getSpecies() {
        return species;
    }

    /**
     * setHeight - sets this christmas tree's height
     * 
     * @param newHeight     new tree height in feet
     */
    public void setHeight(double newHeight) {
        height = newHeight;
    }

    /**
     * setSpecies - sets this christmas tree's species
     * 
     * @param newSpecies    new tree species
     */
    public void setSpecies(String newSpecies) {
        species = newSpecies;
    }

    /**
     * predictHeight - calculates the predicted height of a tree based on the
     * annual growth rate and years passed with a 50% chance of growing 1 extra inch.
     * 
     * @param growth        annual growth rate in inches
     * @param years         years allowed to grow
     * @return double       tree growth in feet
     */
    public double predictHeight(int growth, int years) {
        double chance = Math.random();

        int inchesGrown = growth * years;
        if (chance < 0.5) {
            inchesGrown++;
        }

        return ((double)inchesGrown / 12) + height;
    }

    /**
     * smallestTree - finds the smallest tree height in an array of christmas trees.
     * 
     * @param trees     an array of ChristmasTree objects
     * @return double   smallest tree height in feet
     */
    public static double smallestTree(ChristmasTree[] trees) {
        double smallest = trees[0].getHeight();

        for (int i = 1; i < trees.length; i++) {
            if (trees[i].getHeight() < smallest) {
                smallest = trees[i].getHeight();
            }
        }

        return smallest;
    }

    /**
     * uniqueSpecies - records a list of unique christmas tree species.
     * 
     * @param trees                 an array of ChristmasTree objects
     * @return ArrayList<String>    an array of unique species
     */
    public static ArrayList<String> uniqueSpecies(ChristmasTree[] trees) {
        ArrayList<String> treeSpecies = new ArrayList<String>();

        for (int i = 0; i < trees.length; i++) {
            if (!treeSpecies.contains(trees[i].getSpecies())) {
                treeSpecies.add(trees[i].getSpecies());
            }
        }

        return treeSpecies;
    }
}
