/**
 * MeltFilter - This class implements a melting effect on
 * the image.
 * 
 * @author Bryce Kwon
 * @version Dec. 3, 2022
 */
public class MeltFilter implements Filter {
    /**
     * filter - this method averages the color values of every pixel above
     * the current.
     */
    public void filter(Pixel[][] image) {
        int[] imageRowRed = new int[image.length];
        int[] imageRowGreen = new int[image.length];
        int[] imageRowBlue = new int[image.length];

        for (int j = 0; j < image[0].length;) {
            for (int i = 0; i < image.length; i++) {
                imageRowRed[i] = calculateAvgRed(i, j, image);
                imageRowGreen[i] = calculateAvgGreen(i, j, image);
                imageRowBlue[i] = calculateAvgBlue(i, j, image);
            }

            for (int k = 0; k < image.length; k++) {
                image[k][j].setRed(imageRowRed[k]);
                image[k][j].setGreen(imageRowGreen[k]);
                image[k][j].setBlue(imageRowBlue[k]);
            }

            j++;
        }
    }

    /**
     * calculateAvgRed - this method helps calculate the average red color value
     * of every pixel above the current.
     */
    private int calculateAvgRed(int row, int col, Pixel[][] data) {
        int total = 0;
        int i = 0;

        for (; i < row; i++) {
            total += data[i][col].getRed();
        }

        int avg = total / (row+1);
        return avg;
    }

    /**
     * calculateAvgGreen - this method helps calculate the average green color value
     * of every pixel above the current.
     */
    private int calculateAvgGreen(int row, int col, Pixel[][] data) {
        int total = 0;
        int i = 0;

        for (; i < row; i++) {
            total += data[i][col].getGreen();
        }

        int avg = total / (row+1);
        return avg;
    }

    /**
     * calculateAvgBlue - this method helps calculate the average blue color value
     * of every pixel above the current.
     */
    private int calculateAvgBlue(int row, int col, Pixel[][] data) {
        int total = 0;
        int i = 0;

        for (; i < row; i++) {
            total += data[i][col].getBlue();
        }

        int avg = total / (row+1);
        return avg;
    }
}
