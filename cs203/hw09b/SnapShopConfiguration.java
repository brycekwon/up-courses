/**
 * SnapShopConfiguration
 * A class to configure the SnapShop application
 * 
 * @author Bryce Kwon
 * @version Dec. 3, 2022
 * 
 */
public class SnapShopConfiguration {
    /**
     * configure
     * Method to configure the SnapShop.  
     * Call methods like addFilter
     * and setDefaultFilename in this method.
     * @param theShop   The SnapShop application
     */
    public static void configure(SnapShop theShop) {
        // set default directory
        theShop.setDefaultDirectory("./Images/");

        // implementing filter buttons
        theShop.addFilter(new FlipVerticalFilter(), "Flip Vertical");
        theShop.addFilter(new DemosaicFilter(), "Demosaic Filter");
        theShop.addFilter(new FlipHorizontalFilter(), "Flip Horizontal");
        theShop.addFilter(new SlimeFilter(), "Slime Filter");
        theShop.addFilter(new ShiftLeftFilter(), "Shift Left");
        theShop.addFilter(new GrayscaleFilter(), "Grayscale Filter");
        theShop.addFilter(new MeltFilter(), "Melt Filter");
    }

    /** 
     * main
     * creates a new SnapShop object
     */
    public static void main(String args[]) {
        SnapShop theShop = new SnapShop();
    }  
}
