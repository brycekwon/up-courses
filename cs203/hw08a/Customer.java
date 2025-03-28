public class Customer {

    // these variables contain customer information
    private String nickname;
    private String description;
    private String profession;
    private int visitHour;
    private boolean visitsDaily;

    public Customer(String nickname, String profession, int visitHour, boolean visitsDaily) {
        this.nickname = nickname;
        this.profession = profession;
        this.description = "";
        this.visitHour = visitHour;
        this.visitsDaily = visitsDaily;
    }

    /**
     * printSummary
     * 
     * displays a summary of the customer's information.
     */
    public void printSummary() {
        System.out.println("Name: " + nickname);
        System.out.println("Description: " + description);
        System.out.println("Profession: " + profession);
        System.out.println("Visit Hour: " + visitHour);
        if (visitsDaily) {
            System.out.println("Visits Daily");
        } else if (!visitsDaily) {
            System.out.println("Visits Weekly");
        }
        System.out.println();
    }

    /**
     * isDaily
     * 
     * @returns visit frequency
     */
    public boolean isDaily() {
        return this.visitsDaily;
    }

    /**
     * getProfession
     * 
     * @returns customer profession
     */
    public String getProfession() {
        return this.profession;
    }

    /**
     * setTime
     * 
     * sets the time of visitation. Must be between 0 and 2359.
     */
    public void setTime(int time) {
        if (time >= 0 && time <= 2359) {
            this.visitHour = time;
        }
    }
}
