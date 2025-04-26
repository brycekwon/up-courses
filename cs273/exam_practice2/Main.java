import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main
 * 
 * @author Bryce Kwon
 * @version Dec. 11, 2022
 * 
 * I, Bryce Kwon, pledge on my honor that I will not give, receive, or use any unauthorized
 * materials or assistance on this exam. I further pledge that I will not engage in cheating,
 * forgery, or plagiarism.
 */
public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Name of Game: ");
        String gameName = keyboard.nextLine();

        System.out.print("Average Play Time: ");
        int gamePlayers = keyboard.nextInt();
        keyboard.nextLine();

        BoardGame userBoardGame = new BoardGame(gameName, gamePlayers);
        BoardGame defaultBoardGame = new BoardGame();

        System.out.println("User Play Time: " + userBoardGame.computeActualPlayTime(5));

        ArrayList<BoardGame> games = new ArrayList<BoardGame>();
        games.add(userBoardGame);
        games.add(defaultBoardGame);

        System.out.println("Longest Play Time: " + BoardGame.longestAveragePlayTime(games));
        System.out.println("Count of Letters: " + BoardGame.letterCount(games, 'o'));

        keyboard.close();
    }
}
