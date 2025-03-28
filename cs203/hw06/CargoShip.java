import java.util.Random;
import java.io.InputStream;

/**
 * class CargoShip
 *
 * is a rudimentary simulation of some of the issues that might be encountered
 * in loading and unloading a cargo ship.  Completing this class is an excercise
 * for learning to use one-dimensional arrays.
 *
 * @author Andrew Nuxoll
 * @author Bryce Kwon
 */

public class CargoShip
{
    // category of contents each crate on the ship contains
    //  'e' - empty
    //  'f' - flammable
    //  'p' - perishable
    //  'i' - inert (non reactive)
    char[] categories;

    // weight of the crate occupying each space on the ship (0 when unoccupied)
    int[] weights;

    /**
     * isValid
     * 
     * @returns true if the index is within the bounds of the categories 
     * array, otherwise false.
     */
    public boolean isValid(int loc)
{
        if (loc < 0 || loc >= categories.length)
    {
            return false;
        } 
        else
    {
            return true;
        }
    }//isValid

    /**
     * initArrays
     *
     * initializes the weights and categories arrays to values that reflect a
     * ship with no cargo loaded on it yet (all spaces empty).
     *
     * @param size  - the number of spaces on the ship.  This will always be a
     * multiple of 4 and will fall in the range 8-40.
     */
    public void initArrays(int size)
{
        categories = new char[size];
        weights = new int[size];
        for (int i = 0; i < size; i++) {
            categories[i] = 'e';
            weights[i] = 0;
        }
    }//initArrays

    /**
     * addCrate
     *
     * places a new crate on the ship in a given space
     *
     * @param space     which space to place the crate into
     * @param weight    the weight of the create
     * @param category  the category of the crate
     *
     * @return true if the crate was added and false if the indicated
     * space is already occupied
     */
    public boolean addCrate(int space, int weight, char category)
{
        // empty (available) spaces have value 0
        if (weights[space] > 0)
    {
            return false;
        }
        else
    {
            categories[space] = category;
            weights[space] = weight;
            return true;
        }
    }//addCrate

    /**
     * removeCrate
     *
     * removes a crate from the ship by setting its space to
     * empty ('e') and the corresponding weight to zero.
     *
     * @param space     space to remove create from
     *
     * @return true if crate was removed and false if the indicated
     * space is already empty
     */
    public boolean removeCrate(int space)
{
        // value 0 is already empty
        if (weights[space] == 0)
    {
            return false;
        }
        else
    {
            weights[space] = 0;
            categories[space] = 'e';
            return true;
        }
    }//removeCrate

    /**
     * isSafeSpace
     *
     * specifies whether it's safe to put a crate with a particular
     * category in a particular space.  Specifically, flammable crates
     * can not be adjacent to perishable crates or other flammable
     * crates.  This method does not make sure that the desired space
     * is available (empty).
     *
     * NOTE:  Since the two halves of the array represent cargo on
     * opposite sides of the boat, the space at the end of the first
     * half is not considered adjacent to the space at the beginning
     * of the second half.
     *
     * @param space     which space to check 
     * @param category  the category of the crate that would be placed there
     *
     * @return true if it is safe to place the crate there and false otherwise
     */
    public boolean isSafeSpace(int space, char category)
{
        if (category == 'i' || space == categories.length / 2)
    {
            return true;
        }
        else if ((isValid(space-1) && category == 'f') && (categories[space-1] == 'f' || categories[space-1] == 'p'))
    {
            return false;
        }
        else if ((isValid(space+1) && category == 'f') && (categories[space+1] == 'f' || categories[space+1] == 'p'))
    {
            return false;
        }
        else if ((isValid(space-1) && category == 'p') && (categories[space-1] == 'f'))
    {
            return false;
        }
        else if ((isValid(space+1) && category == 'p') && (categories[space+1] == 'f'))
    {
            return false;
        }
        else
    {
            return true;
        }
    }//isSafeSpace

    /**
     * findSpace
     *
     * searches the array to find an available space where a crate
     * with a given category can be placed.  If category 'e' (empty)
     * is specified this method searches for crate to remove
     * instead
     *
     * @param category   the category of the crate
     *
     * @return the index of a valid space or -1 if no such space is
     * available
     */
    public int findSpace(char category)
{
        if (category == 'e')
    {
            for (int i = 0; i < categories.length; i++)
        {
                if (categories[i] != 'e')
            {
                    return i;
                }
            }
        }
        else
    {
            for (int i = 0; i < categories.length; i++)
        {
                if (categories[i] == 'e' && isSafeSpace(i, category))
            {
                    return i;
                }
            }
        }

        return -1;
    }//findSpace


    //======================================================================
    //                        ATTENTION STUDENTS!
    //
    // Do not modify the code below this point.  However you are welcome
    // to review it if you wish.
    // ======================================================================

    /**
     * this class is here to remind the students they may not use the
     * Scanner class for this assignment.  It should not be needed.
     */
    public class Scanner
{
        public Scanner(InputStream x) {}

        public String nextLine()
    {
            System.err.println("ERROR!  You may not use the Scanner class for this assignment");
            System.exit(-1);
            return "";
        }

        public String next() { return nextLine(); }
        public int nextInt() { nextLine(); return -1; }
        public char nextChar() { nextLine(); return 'X'; }
        public double nextDouble() { nextLine(); return -1.0; }
    }//class Scanner

    /**
     * This class is here to remind the students they may not use the
     * JOptionPane class for this assignment.  It should not be needed.
     */
    public class JOptionPane {
        public String showInputDialog(String s) {
            return "ERROR!  You may not use the JOptionPane class for this assignment";
        }
    }


    /**
     * getWeights
     *
     * @return a reference to the weights array
     */
    public int[] getWeights() { return this.weights; }

    /**
     * getCategories
     *
     * @return a reference to the categories array
     */
    public char[] getCategories() { return this.categories; }


    /**
     * constructor
     */
    public CargoShip()
{
        Random rand = new Random();
        int arrSize = rand.nextInt(8)*4 + 8;
        initArrays(arrSize);
    }


}//class CargoShip
