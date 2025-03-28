/**
 * DemosaicFilter - This class implements a demosaic filter
 * on the image.
 * 
 * @author Bryce Kwon
 * @version Dec. 3, 2022
 */
public class DemosaicFilter implements Filter {
    /**
     * filter - this method averages the missing values of a pixel
     * from the values of those adjacent to the current.
     */
    public void filter(Pixel[][] image) {
        for (int row = 1; row < image.length - 1; row++) {
            for (int col = 1; col < image[row].length -1; col++) {
                Pixel current = image[row][col];

                Pixel top = image[row+1][col];
                Pixel left = image[row][col-1];
                Pixel right = image[row][col+1];
                Pixel bottom = image[row-1][col];

                Pixel topLeft = image[row+1][col-1];
                Pixel topRight = image[row+1][col+1];
                Pixel bottomLeft = image[row-1][col-1];
                Pixel bottomRight = image[row-1][col+1];

                if (current.getRed() > 0) {
                    // calcualte and set values for red pixels
                    current.setGreen(
                    (top.getGreen() + left.getGreen() +
                        right.getGreen() + bottom.getGreen()) / 4);
                    current.setBlue(
                    (topLeft.getBlue() + topRight.getBlue() +
                        bottomLeft.getBlue() + bottomRight.getBlue()) / 4);
                } else if (current.getGreen() > 0) {
                    // calculate and set values for green pixels
                    if (top.getRed() > 0) {
                        // check if top and bottom pixels are red
                        current.setRed((top.getRed() + bottom.getRed()) / 2);
                        current.setBlue((left.getBlue() + right.getBlue()) / 2);
                    } else if (top.getBlue() > 0) {
                        // check if top and bottom pixels are blue
                        current.setBlue((top.getBlue() + bottom.getBlue()) / 2);
                        current.setRed((left.getRed() + right.getRed()) / 2);
                    }
                } else if (current.getBlue() > 0) {
                    // calculate and set values for blue pixels
                    current.setGreen(
                    (top.getGreen() + left.getGreen() +
                        right.getGreen() + bottom.getGreen()) / 4);
                    current.setRed(
                    (topLeft.getRed() + topRight.getRed() +
                        bottomLeft.getRed() + bottomRight.getRed()) / 4);
                }
            }
        }
    }
}
