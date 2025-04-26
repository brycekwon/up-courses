/**
 * Class Periodical - This class defines details about a periodical
 * being sold by the newstand.
 * 
 * @author Bryce Kwon
 * @version 2022-11-09
 */
public class Periodical {

    /*
     * ======================================================================
     * Instance Variables
     * ======================================================================
     */

    // these variables contain periodical information
    private String title;
    private int idNum;
    private int numSold;
    private int numInStock;
    private double coverPrice;
    private double totalSales;

    // these variables contain the date of publication
    private String month;
    private int day;
    private int year;

    // periodical class constructor
    public Periodical(String title, int idNum, int numInStock, double coverPrice) {
        this.title = title;
        this.idNum = idNum;
        this.numInStock = numInStock;
        this.coverPrice = coverPrice;
        this.numSold = 0;
        this.totalSales = 0;

        this.month = "November";
        this.day = 9;
        this.year = 2022;
    }


    /*
     * ======================================================================
     * Methods
     * ======================================================================
     */

    /**
     * printSummary
     * 
     * displays information of periodical information.
     */
    public void printSummary() {
        System.out.println("Title: " + title + " [#" + idNum + "]");
        System.out.println("Published: " + month + " " + day + ", " + year);
        System.out.println("Current Stock: " + numInStock);
        System.out.println("Amount Sold: " + numSold);
        System.out.println("Cover Price: " + coverPrice);
        System.out.println("Total Sales: " + totalSales);
        System.out.println();
    }

    /**
     * getId
     * 
     * @returns the id of the periodical
     */
    public int getId() {
        return this.idNum;
    }

    /**
     * getTitle
     * 
     * @returns the title of the periodical
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * getStock
     * 
     * @returns the current stock of the periodical
     */
    public int getStock() {
        return this.numInStock;
    }

    /**
     * sellCopy
     * 
     * updates the number in stock, total amount sold, and total sales.
     * Will print an error if no stock left.
     * 
     * @param salePrice
     */
    public void sellCopy(double salePrice) {
        if (numInStock > 0) {
            numInStock--;
            numSold++;
            this.totalSales += salePrice;
        } else {
            System.out.println("No more periodicals left in stock!");
        }
    }

    /**
     * sellCopy
     * 
     * updates the number in stock, total amount sold, and total sales.
     * Will print an error if no stock left.
     */
    public void sellCopy() {
        sellCopy(coverPrice);
    }

    /**
     * recordSale
     * 
     * records the sale of a periodical to a given customer at cover price.
     */
    public void recordSale(Customer person) {
        sellCopy();
        person.addPeriodical(this);
    }

    /**
     * restock
     * 
     * restocks a periodical, resets total sold and total sales.
     */
    public void restock(int stock, String month, int day, int year) {
        // reset periodical information
        this.numInStock = stock;
        this.numSold = 0;
        this.totalSales = 0;

        // set periodical publication
        this.month = month;
        this.day = day;
        this.year = year;
    }
}
