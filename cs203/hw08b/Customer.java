/**
 * Class Customer - This class defines details about each customer
 * that visits and purchases from the newstand.
 * 
 * @author Bryce Kwon
 * @version 2022-11-09
 */
public class Customer {

    /*
     * ======================================================================
     * Instance Variables
     * ======================================================================
     */

    // these variables contain basic customer information
    private String nickname;
    private String profession;
    private String description;
    private int visitHour;
    private boolean visitsDaily;

    // these variables contain customer purchases
    private int numPurchases;
    private Periodical[] purchaseList = new Periodical[10];

    // customer class constructor
    public Customer(String nickname, String profession, int visitHour, boolean visitsDaily) {
        // demographic information
        this.nickname = nickname;
        this.profession = profession;
        this.description = "";
        this.visitHour = visitHour;
        this.visitsDaily = visitsDaily;

        // purchase information
        this.numPurchases = 0;
        for (int i = 0; i < purchaseList.length; i++) {
            purchaseList[i] = null;
        }
    }


    /*
     * ======================================================================
     * Methods
     * ======================================================================
     */

    /**
     * printSummary
     * 
     * displays a summary of customer information.
     */
    public void printSummary() {
        System.out.println("Name: " + nickname);
        System.out.println("Profession: " + profession);
        System.out.println("Description: " + description);
        System.out.println("Visit Hour: " + visitHour);
        System.out.println("Visits Daily: " + visitsDaily);
        for (int i = 0; i < purchaseList.length; i++) {
        if (purchaseList[i] != null) {
                System.out.println("  - " + purchaseList[i].getTitle() + " [#" +  purchaseList[i].getId() + "]");
            }
        }
        System.out.println();
    }

    /**
     * isDaily
     * 
     * checks if the customer visits the newstand daily.
     */
    public boolean isDaily() {
        return this.visitsDaily;
    }

    /**
     * getProfession
     * 
     * @returns the current profession of the customer.
     */
    public String getProfession() {
        return this.profession;
    }

    /**
     * setVisitTime
     * 
     * sets the time of day that the customer visists.
     * 
     * @param time
     */
    public void setVisitTime(int time) {
        this.visitHour = time;
    }

    /**
     * inList
     * 
     * checks if the periodical already exists in the purchase list.
     */
    private boolean inList(Periodical article) {
        for (int i = 0; i < purchaseList.length; i++) {
            if (purchaseList[i] != null && purchaseList[i].getId() == article.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * addPeriodical
     * 
     * adds a given periodical to the list of purchases made.
     */
    public void addPeriodical(Periodical article) {
        if (!inList(article)) {
            for (int i = 0; i < purchaseList.length; i++) {
                if (purchaseList[i] == null) {
                    purchaseList[i] = article;
                    numPurchases++;
                    break;
                }
            }
        }
    }

    /**
     * removePeriodical
     * 
     * removes a given periodical from the list of purchases made.
     */
    public void removePeriodical(Periodical article) {
        for (int i = 0; i < purchaseList.length; i++) {
            if (purchaseList[i] != null && purchaseList[i].getId() == article.getId()) {
                purchaseList[i] = null;
                numPurchases--;
                break;
            }
        }
    }
}
