/**
 * Class UnitTest - This class tests the methods of the Customer and
 * Periodical classes.
 * 
 * @author Bryce Kwon
 * @version 2022-11-09
 */
public class UnitTest {
    public static void main(String[] args) {
        System.out.println("UNIT TEST START\n==============================\n");


        Periodical newspaper1 = new Periodical("The Fed Raises Rates Again!", 137, 200, 12.99);
        Periodical newspaper2 = new Periodical("Gasoline Prices Are Rising!", 217, 100, 14.99);
        Periodical newspaper3 = new Periodical("The Monkeys Are At It Again!", 161, 250, 9.99);
        Periodical newspaper4 = new Periodical("Is the Golden Gate Bridge a Myth?", 232, 150, 19.99);

        Customer person1 = new Customer("John Smith", "Doctor", 1400, true);
        Customer person2 = new Customer("Jane Doe", "Nurse", 1600, true);
        Customer person3 = new Customer("Noah Fukushima", "Scientist", 2000, true);
        Customer person4 = new Customer("Haley Rose", "Short", 1300, true);


        // recordSale functionality
        System.out.println("********** RECORD SALE **********");

        newspaper1.recordSale(person1);
        newspaper2.recordSale(person1);
        newspaper3.recordSale(person1);
        newspaper4.recordSale(person1);

        person1.printSummary();
        newspaper1.printSummary();
        newspaper2.printSummary();


        // restock functionality
        System.out.println("\n********** RESTOCK **********");

        newspaper1.restock(200, "Feburary", 23, 2022);
        newspaper2.restock(200, "March", 22, 2020);

        newspaper1.printSummary();
        newspaper2.printSummary();


        // removePeriodical functionality
        System.out.println("\n********** REMOVE PERIODICAL **********");

        person1.removePeriodical(newspaper1);
        person1.removePeriodical(newspaper2);
        person1.removePeriodical(newspaper3);
        person1.removePeriodical(newspaper3);

        person1.printSummary();
        newspaper1.printSummary();
        newspaper3.printSummary();


        // addPeriodical functionality
        System.out.println("\n********** REMOVE PERIODICAL **********");

        person2.addPeriodical(newspaper1);
        person2.addPeriodical(newspaper2);
        person2.addPeriodical(newspaper3);
        person2.addPeriodical(newspaper3);

        person2.printSummary();
        newspaper1.printSummary();
        newspaper3.printSummary();


        // sellCopy functionality
        System.out.println("\n********** SELL COPY **********");

        newspaper4.sellCopy(50.99);
        newspaper4.sellCopy();

        newspaper4.printSummary();


        System.out.println("==============================\nUNIT TEST END");
    }
}
