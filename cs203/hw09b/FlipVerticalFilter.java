/**
 * Filter that flips the image horizontally.
 * @author Tammy VanDeGrift
 * modified kw
 * @version October 2007
 * @version March 2014
 * 
 * !!! DO NOT MODIFY THIS CLASS !!!
 */
public class FlipVerticalFilter implements Filter {
    // no instance variables -- no state of objects

    /** 
     * filter
     * flips pixel image vertically around horizontal
     * center line
     * @param data The Pixel array object to modify
     */
    public void filter(Pixel[][] data) {

        for (int row = 0; row < data.length / 2; row++) {
            for (int col = 0; col < data[row].length; col++) {
                // swap values across center line
                Pixel temp = data[row][col];
                data[row][col] = data[ data.length - row - 1][col];
                data[ data.length - row - 1][col] = temp;
            }
        }  
    }
}
