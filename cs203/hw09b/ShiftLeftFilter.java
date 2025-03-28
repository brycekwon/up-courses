/**
 * ShiftLeftFilter - This class shifts the image left by one pixel,
 * moving the leftmost column onto the rightmost column
 * 
 * @author Bryce Kwon
 * @version Dec. 3, 2022
 */
public class ShiftLeftFilter implements Filter {
    /**
     * filter - this method moves each pixel to the left, replacing
     * the leftmost column as the rightmost.
     */
    public void filter(Pixel[][] image) {
        for (int row = 0; row < image.length; row++) {
            // save the leftmost pixel
            Pixel tmp = image[row][0];
            for (int col = 0; col < image[row].length; col++) {
                if (col + 1 < image[row].length) {
                    // shift every other pixel to the left
                    image[row][col] = image[row][col+1];
                } else {
                    // replace the rightmost column with the leftmost pixel
                    image[row][col] = tmp;
                }
            }
        }
    }
}