
/**
 * Write a description of class FlipHorizontalFilter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FlipHorizontalFilter implements Filter {
    public void filter(Pixel[][] data) {
        for (int  i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length / 2; j++) {
                Pixel tmp = data[i][j];
                data[i][j] = data[i][data[i].length - j - 1];
                data[i][data[i].length - j - 1] = tmp;
            }
        }
    }
}
