import java.lang.Math;
import java.util.ArrayList;

/**
 * BoardGame
 * 
 * @author Bryce Kwon
 * @version Dec. 11, 2022
 * 
 * I, Bryce Kwon, pledge on my honor that I will not give, receive, or use any unauthorized
 * materials or assistance on this exam. I further pledge that I will not engage in cheating,
 * forgery, or plagiarism.
 */
public class BoardGame {
    private String name;
    private int averagePlayTime;

    public BoardGame() {
        this.name = "Monopoly";
        this.averagePlayTime = 30;
    }

    public BoardGame(String name, int averagePlayTime) {
        this.name = name;
        this.averagePlayTime = averagePlayTime;
    }

    public String getName() {
        return name;
    }

    public int getPlayTime() {
        return averagePlayTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayTime(int averagePlayTime) {
        this.averagePlayTime = averagePlayTime;
    }

    public int computeActualPlayTime(int players) {
        int time = 0;

        if (players == 1) {
            time = averagePlayTime - 5;
        } else if (players <= 3) {
            double probability = (Math.random());
            if (probability <= 0.3) {
                time = averagePlayTime + 10;
            } else if (probability > 0.3) {
                time = averagePlayTime + 5;
            }
        } else if (players >= 4) {
            time = averagePlayTime + (players * 5);
        }

        return time;
    }

    public static String longestAveragePlayTime(ArrayList<BoardGame> games) {
        BoardGame maxTime = games.get(0);

        for (int i = 1; i < games.size(); i++) {
            if (games.get(i).getPlayTime() > maxTime.getPlayTime()) {
                maxTime = games.get(i);
            }
        }

        return maxTime.getName() + " takes the longest to play: " + (int)(maxTime.getPlayTime() / 60) + " hours and " + maxTime.getPlayTime() % 60 + " minutes";
    }

    public static int letterCount(ArrayList<BoardGame> games, char letter) {
        int count = 0;

        for (int i = 0; i < games.size(); i++) {
            String current = games.get(i).getName();

            for (int j = 0; j < current.length(); j++) {
                if (current.charAt(j) == (Character.toUpperCase(letter))) {
                    count++;
                } else if (current.charAt(j) == Character.toLowerCase(letter)) {
                    count++;
                }
            }
        }

        return count;
    }
}
