
/**
 * Write a description of class SlimeFilter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SlimeFilter implements Filter {
    public void filter(Pixel[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                int avg = (data[i][j].getRed() + data[i][j].getGreen() + data[i][j].getBlue()) / 3;
                data[i][j].setRed(0);
                data[i][j].setGreen(avg);
                data[i][j].setBlue(0);
            }
        }
    }
}
