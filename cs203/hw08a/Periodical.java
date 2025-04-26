public class Periodical {

    // these variables contain periodical information
    private int idNum;
    private String title;
    private int numInStock;
    private int numSold;
    private double coverPrice;
    private double totalSales;

    public Periodical(int idNum, int numInStock, String title, double coverPrice) {
        this.idNum = idNum;
        this.numInStock = numInStock;
        this.title = title;
        this.coverPrice = coverPrice;
        this.numSold = 0;
        this.totalSales = 0;
    }

    /**
     * printSummary
     * 
     * displays a summary of the periodical's information.
     */
    public void printSummary() {
        System.out.println("Title: " + title + " [#" + idNum + "]");
        System.out.println("Current Stock: " + numInStock);
        System.out.println("Amount Sold: " + numSold);
        System.out.println("Cover Price: ¥" + coverPrice);
        System.out.println("Total Sales: ¥" + totalSales);
        System.out.println();
    }

    /**
     * getIdNum
     * 
     * @returns id of periodical
     */
    public int getIdNum() {
        return this.idNum;
    }

    /**
     * getStock
     * 
     * @returns current stock of the periodical
     */
    public int getStock() {
        return this.numInStock;
    }

    /**
     * sellCopy
     * 
     * handles the sale of a transaction. Will only run if stock is not empty.
     */
    public void sellCopy(double salePrice) {
        if (numInStock > 0) {
            numInStock--;
            numSold++;
            totalSales += salePrice;
        } else {
            System.out.println("No more periodicals in stock!");
        }
    }
}
