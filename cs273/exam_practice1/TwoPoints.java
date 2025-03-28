/**
 * TwoPoints
 * 
 * @author Bryce Kwon
 * @version Dec. 11, 2022
 * 
 * I Bryce Kwon, pledge on my honor that I will not give, receive, or use any unauthorized
 * materials or assistance on this exam. I further pledge that I will not engage in cheating,
 * forgery, or plagiarism.
 */
public class TwoPoints {
    private int x_coor;
    private int y_coor;

    // default constructor initializes coordinates to 0
    public TwoPoints() {
        x_coor = 0;
        y_coor = 0;
    }

    // constructor initializes coordinates to given arguments
    public TwoPoints(int x_coor, int y_coor) {
        this.x_coor = x_coor;
        this.y_coor = y_coor;
    }

    /**
     * getX - this method returns the current x coordinate.
     * 
     * @return int      x-coordinate
     */
    public int getX() {
        return this.x_coor;
    }

    /**
     * getY - this method returns the current y coordinate.
     * 
     * @return int      y-coordinate
     */
    public int getY() {
        return this.y_coor;
    }

    /**
     * setX - this method returns the current x coordinate.
     * 
     * @param x_coor        new x coordinate
     */
    public void setX(int x_coor) {
        this.x_coor = x_coor;
    }

    /**
     * setY - this method returns the current y coordinate.
     * 
     * @param y_coor        new y coordinate
     */
    public void setY(int y_coor) {
        this.y_coor = y_coor;
    }

    /**
     * midX
     */
    public static double midX(TwoPoints location) {
        return (double)location.getX() / 2;
    }

    /**
     * maxValue - returns the max value of this object's x or y coordinate. If both values are
     * equal, that value will be returned.
     */
    public int maxValue() {
        if (this.x_coor > this.y_coor) {
            return this.x_coor;
        } else {
            return this.y_coor;
        }
    }


}
