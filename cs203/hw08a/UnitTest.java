public class UnitTest {
    public static void main(String[] args) {

        System.out.println("********** CUSTOMER **********");
        Customer person1 = new Customer("John Doe", "Doctor", 1400, true);
        person1.printSummary();

        System.out.println("Visits Daily: " + person1.isDaily());
        System.out.println("Profession: " + person1.getProfession());
        System.out.println();

        person1.setTime(1600);
        person1.printSummary();


        System.out.println("********** PERIODICAL **********");
        Periodical newspaper = new Periodical(137, 200, "The Fed Raised Rates Again!", 12.99);
        newspaper.printSummary();

        System.out.println("ID: " + newspaper.getIdNum());
        System.out.println("Stock: " + newspaper.getStock());
        System.out.println();

        for (int i = 0; i < 200; i++) {
            newspaper.sellCopy(15);
        }
        newspaper.printSummary();

        newspaper.sellCopy(15);
    }
}
