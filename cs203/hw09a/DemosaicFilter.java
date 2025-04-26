/**
 * DemosaicFilter - 
 *
 * @author Bryce Kwon
 * @version Nov. 20, 2022
 */
public class DemosaicFilter implements Filter {
    /**
     * filter
     * 
     * @param data  pixel array of the image
     */
    public void filter(Pixel[][] data) {
        for (int row = 1; row < data.length - 1; row++) {
            for (int col = 1; col < data[row].length - 1; col++) {    
                Pixel current = data[row][col];

                Pixel top = data[row+1][col];
                Pixel left = data[row][col-1];
                Pixel right = data[row][col+1];
                Pixel bottom = data[row-1][col];

                Pixel topLeft = data[row+1][col-1];
                Pixel topRight = data[row+1][col+1];
                Pixel bottomLeft = data[row-1][col-1];
                Pixel bottomRight = data[row-1][col+1];

                if (current.getRed() > 0) {
                    current.setGreen((top.getGreen() + left.getGreen() + right.getGreen() + bottom.getGreen()) / 4);
                    current.setBlue((topLeft.getBlue() + topRight.getBlue() + bottomLeft.getBlue() + bottomRight.getBlue()) / 4);
                } else if (current.getGreen() > 0) {
                    if (top.getRed() > 0) {
                        current.setRed((top.getRed() + bottom.getRed()) / 2);
                        current.setBlue((left.getBlue() + right.getBlue()) / 2);
                    } else if (top.getBlue() > 0) {
                        current.setBlue((top.getBlue() + bottom.getBlue()) / 2);
                        current.setRed((left.getRed() + right.getRed()) / 2);
                    }
                } else if (current.getBlue() > 0) {
                    current.setGreen((top.getGreen() + left.getGreen() + right.getGreen() + bottom.getGreen()) / 4);
                    current.setRed((topLeft.getRed() + topRight.getRed() + bottomLeft.getRed() + bottomRight.getRed()) / 4);
                }
            }
        }
    }
}
