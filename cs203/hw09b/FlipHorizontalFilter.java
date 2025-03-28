/**
 * FlipHorizontalFilter - This method flips the image horizontally
 * 
 * @author Bryce Kwon
 * @version Dec. 3, 2022
 */
public class FlipHorizontalFilter implements Filter {
    /**
     * filter - this method replaces the image pixels on
     * each side of the image.
     */
    public void filter(Pixel[][] image) {
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length / 2; col++) {
                // save current pixel temporarily
                Pixel tmp = image[row][col];

                // replace the current pixel with its opposite counterpart
                image[row][col] = image[row][image[row].length - col - 1];

                // replace the counterpart pixel with the temporary
                image[row][image[row].length - col - 1] = tmp;
            }
        }
    }
}
