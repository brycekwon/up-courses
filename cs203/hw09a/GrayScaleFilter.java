public class GrayScaleFilter implements Filter {
    public void filter(Pixel[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j].setRed((data[i][j].getRed() + data[i][j].getGreen() + data[i][j].getBlue()) / 3);
                data[i][j].setGreen((data[i][j].getRed() + data[i][j].getGreen() + data[i][j].getBlue()) / 3);
                data[i][j].setBlue((data[i][j].getRed() + data[i][j].getGreen() + data[i][j].getBlue()) / 3);
            }
        }
    }
}