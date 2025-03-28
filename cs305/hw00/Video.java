/**
 * Video class models a specific video
 * Includes information about the video that can be added to PlayList
 * 
 * CS 305, HW 0
 * @author Bryce Kwon
 * @version January 25, 2023
 */
public class Video {
    // these variables contain information about the Video
    private String name;
    private int duration;
    private int likes;

    /**
     * Video constructor
     * 
     * each Video contain a name, duration, and number of likes.
     * 
     * @param name          name of the video
     * @param duration      length of the video in seconds
     * @param likes         number of likes on the video
     */
    public Video(String name, int duration, int likes) {
        this.name = name.length() > 0 ? name : "N/A";
        this.duration = duration > 0 ? duration : 0;
        this.likes = likes > 0 ? likes : 0;
    }

    /**
     * print
     * 
     * this method displays information about the Video
     */
    public void print() {
        // convert duration into minutes and seconds
        int min = duration / 60;
        int sec = duration % 60;

        // display Video information
        System.out.print("Video: " + min + "min " + sec + "sec" + "\t\t");
        System.out.print(name + "\t");
        System.out.println(likes);
    }

    /**
     * getName
     * 
     * this method retrieves the video's name
     * 
     * @return String       name of video
     */
    public String getName() {
        return name;
    }

    /**
     * getDuration
     * 
     * this method retrieves the video's duration
     * 
     * @return int      length of video in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * getLikes
     * 
     * this method retrieves the video's likes
     * 
     * @return int      number of likes
     */
    public int getLikes() {
        return likes;
    }
}
