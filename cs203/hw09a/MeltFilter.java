/**
 * Write a description of class MeltFilter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MeltFilter implements Filter {
    public void filter(Pixel[][] data) {
        int[] dataRowRed = new int[data.length];
        int[] dataRowGreen = new int[data.length];
        int[] dataRowBlue = new int[data.length];

        for (int j = 0; j < data[0].length;) {
            for (int i = 0; i < data.length; i++) {
                dataRowRed[i] = calculateAvgRed(i, j, data);
                dataRowGreen[i] = calculateAvgGreen(i, j, data);
                dataRowBlue[i] = calculateAvgBlue(i, j, data);
            }

            for (int k = 0; k < data.length; k++) {
                data[k][j].setRed(dataRowRed[k]);
                data[k][j].setGreen(dataRowGreen[k]);
                data[k][j].setBlue(dataRowBlue[k]);
            }

            j++;
        }
    }

    private int calculateAvgRed(int row, int col, Pixel[][] data) {
        int total = 0;
        int i = 0;

        for (; i < row; i++) {
            total += data[i][col].getRed();
        }

        int avg = total / (row+1);
        return avg;
    }

    private int calculateAvgGreen(int row, int col, Pixel[][] data) {
        int total = 0;
        int i = 0;

        for (; i < row; i++) {
            total += data[i][col].getGreen();
        }

        int avg = total / (row+1);
        return avg;
    }

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
