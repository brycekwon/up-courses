// import libraries
import java.util.*;

/**
 * Class Fortune - Prints a custom fortune and lucky number
 * based on user input.
 * 
 * @author Bryce Kwon
 * @version 2022-09-24
 */
public class Fortune {
    public static void main(String[] args) {
        // define a keyboard listener
        Scanner keyboard = new Scanner(System.in);

        // define variables
        String name;
        int favNum;
        int birthMonth;
        int birthDay;
        int birthYear;
        String location;
        int siblings;
        int fortune;
        String zodiac = "unknown";

        // display the program title
        System.out.println("Welcome to the Wacky Fortune Teller\n");

        // prompt user for name
        System.out.print("What is your name? ");
        name = keyboard.nextLine();

        // prompt user for favorite number
        System.out.print("What is your favorite number? ");
        favNum = keyboard.nextInt();

        // prompt user for birth month
        while (true) {
            System.out.print("What is your birth month? ");
            birthMonth = keyboard.nextInt();

            // error check user input
            if (birthMonth >= 1 && birthMonth <= 12) {
                break;
            } else {
                System.out.println("Please enter a valid month (1-12)");
            }
        }

        // prompt user for birth day
        while (true) {
            System.out.print("What is your birth day? ");
            birthDay = keyboard.nextInt();

            // error check user input
            if (birthDay >= 1 && birthDay <= 31) {
                break;
            } else {
                System.out.println("Please enter a valid day (1-31)");
            }
        }

        // prompt user for birth year
        while (true) {
            System.out.print("What is your birth year? ");
            birthYear = keyboard.nextInt();

            // error check user input
            if (birthYear >= 1900 && birthYear <= 2022) {
                break;
            } else {
                System.out.println("Please enter a valid year (1900-2022)");
            }
        }
        keyboard.nextLine();

        // prompt user for state of residence
        System.out.print("What state do you live in? ");
        location = keyboard.nextLine();

        // prompt user for number of siblings
        System.out.print("How many siblings do you have?  ");
        siblings = keyboard.nextInt();

        // prompt user for desired prediction
        while (true) {
            System.out.print("Which prediction would you like to hear (1, 2, 3)? ");
            fortune = keyboard.nextInt();

            // error check user's input
            if (fortune >= 1 && fortune <= 3) {
                break;
            } else {
                System.out.println("Please enter a valid fortune (1, 2, 3) ");
            }
        }

        // calculate astrological sign based on birth date
        if (birthMonth == 1) {
            if (birthDay >= 1 && birthDay <= 19) {
                zodiac = "Capricorn";
            } else if (birthDay >= 20 && birthDay <= 31) {
                zodiac = "Aquarius";
            }
        } else if (birthMonth == 2) {
            if (birthDay >= 1 && birthDay <= 18) {
                zodiac = "Aquarius";
            } else if (birthDay >= 19 && birthDay <= 31) {
                zodiac = "Pisces";
            }
        } else if (birthMonth == 3) {
            if (birthDay >= 1 && birthDay <= 20) {
                zodiac = "Pisces";
            } else if (birthDay >= 21 && birthDay <= 31) {
                zodiac = "Aries";
            }
        } else if (birthMonth == 4) {
            if (birthDay >= 1 && birthDay <= 19) {
                zodiac = "Aries";
            } else if (birthDay >= 20 && birthDay <= 31) {
                zodiac = "Taurus";
            }
        } else if (birthMonth == 5) {
            if (birthDay >= 1 && birthDay <= 20) {
                zodiac = "Taurus";
            } else if (birthDay >= 21 && birthDay <= 31) {
                zodiac = "Gemini";
            }
        } else if (birthMonth == 6) {
            if (birthDay >= 1 && birthDay <= 20) {
                zodiac = "Gemini";
            } else if (birthDay >= 21 && birthDay <= 31) {
                zodiac = "Cancer";
            }
        } else if (birthMonth == 7) {
            if (birthDay >= 1 && birthDay <= 22) {
                zodiac = "Cancer";
            } else if (birthDay >= 23 && birthDay <= 31) {
                zodiac = "Leo";
            }
        } else if (birthMonth == 8) {
            if (birthDay >= 1 && birthDay <= 22) {
                zodiac = "Leo";
            } else if (birthDay >= 23 && birthDay <= 31) {
                zodiac = "Virgo";
            }
        } else if (birthMonth == 9) {
            if (birthDay >= 1 && birthDay <= 22) {
                zodiac = "Virgo";
            } else if (birthDay >= 23 && birthDay <= 31) {
                zodiac = "Libra";
            }
        } else if (birthMonth == 10) {
            if (birthDay >= 1 && birthDay <= 22) {
                zodiac = "Libra";
            } else if (birthDay >= 23 && birthDay <= 31) {
                zodiac = "Scorpio";
            }
        } else if (birthMonth == 11) {
            if (birthDay >= 1 && birthDay <= 21) {
                zodiac = "Scorpio";
            } else if (birthDay >= 22 && birthDay <= 31) {
                zodiac = "Sagittarius";
            }
        } else if (birthMonth == 12) {
            if (birthDay >= 1 && birthDay <= 21) {
                zodiac = "Sagittarius";
            } else if (birthDay >= 22 && birthDay <= 31) {
                zodiac = "Capricorn";
            }
        }

        // calculate the user's prediction number
        int luckyNum = (((birthDay - siblings) * birthMonth) + birthYear) % favNum;

        // display the appropriate fortune
        if (fortune == 1) {
            System.out.println("\nYour Fortune:");
            System.out.println("Today is your lucky day!");
            System.out.println("Your zodiac sign is a " + zodiac + " and your lucky number is " + luckyNum + "!");
            System.out.println("The outlook for your week is looking well!");
            System.out.println("The weather in " + location + " will be sunny and cool!");
            System.out.println("All " + siblings + " of your siblings will also have good fortune!\n");
        } else if (fortune == 2) {
            System.out.println("\nYour Fortune:");
            System.out.println("Today is a normal day.");
            System.out.println("Your zodiac sign is a " + zodiac + " and your lucky number is " + luckyNum + ".");
            System.out.println("The outlook for your week is looking average.");
            System.out.println("The weather in " + location + " will be cloudy with a chance of meatballs.");
            System.out.println("All " + siblings + " of your siblings will have great fortune.\n");
        } else if (fortune == 3) {
            System.out.println("\nYour Fortune:");
            System.out.println("Today is your unlucky day...");
            System.out.println("Your zodiac sign is a " + zodiac + " and your lucky number is " + luckyNum + "...");
            System.out.println("The outlook for your week is looking bad...");
            System.out.println("The weather in " + location + " will be rainy with thunder...");
            System.out.println("All " + siblings + " of your siblings will have awesome fortune...\n");
        }

        // thank the user for using the program
        System.out.println("Thank you, " + name + ", for using the Wacky Fortune Teller!");
    }
}
