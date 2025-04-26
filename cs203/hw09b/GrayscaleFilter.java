/**
 * GrascaleFilter - This class implements a grascale on the image.
 * 
 * @author Bryce Kwon
 * @version Dec. 3, 2022
 */
public class GrayscaleFilter implements Filter {
    /**
     * filter - this method averages the color values of each pixel
     * and applies them to all existing values.
     */
    public void filter(Pixel[][] image) {
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                // calculate the average of all color values
                int avg = (
                image[row][col].getRed() +
                image[row][col].getGreen() +
                image[row][col].getBlue()
            ) / 3;

                // set all existing values to the average
                image[row][col].setRed(avg);
                image[row][col].setGreen(avg);
                image[row][col].setBlue(avg);
            }
        }
    }
}
