
/**
 * Write a description of class ShiftLeftFilter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ShiftLeftFilter implements Filter {
    public void filter(Pixel[][] data) {
        for (int i = 0; i < data.length; i++) {
            Pixel tmp = data[i][0];
            for (int j = 0; j < data[i].length; j++) {

                if (j + 1 < data[i].length) {
                    data[i][j] = data[i][j+1];
                } else {
                    data[i][j] = tmp;
                }
            }
        }
    }
}
