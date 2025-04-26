/**
 * SlimeFilter - This class implements a slime filter on the image
 * 
 * @author Bryce Kwon
 * @version Dec. 3, 2022
 */
public class SlimeFilter implements Filter {
    /**
     * filter - this image sets red and blue color values to 0 and
     * sets green to the average of all previous color values.
     */
    public void filter(Pixel[][] image) {
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[row].length; col++) {
                // calculate the average of all color values in the pixel
                int avg = (
                image[row][col].getRed() +
                image[row][col].getGreen() +
                image[row][col].getBlue()
            ) / 4;

                // use the average as green brightness for pixel
                image[row][col].setRed(0);
                image[row][col].setGreen(avg);
                image[row][col].setBlue(0);
            }
        }
    }
}
