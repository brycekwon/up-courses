import java.util.ArrayList;
import java.lang.Math;

/**
 * MyInput
 * 
 * @author Bryce Kwon
 * @version Dec. 11, 2022
 * 
 * I Bryce Kwon, pledge on my honor that I will not give, receive, or use any unauthorized
 * materials or assistance on this exam. I further pledge that I will not engage in cheating,
 * forgery, or plagiarism.
 */
public class MathLib {
    public static ArrayList<Integer> randomArrayList(int size) {
        if (size % 2 == 0) {
            size /= 2;
        } else {
            size *= 2;
        }

        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            arr.add(Integer.valueOf((int)(Math.random() * 40) + 10));
        }

        return arr;
    }
}
