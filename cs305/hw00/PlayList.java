/**
 * PlayList models a collection of Videos
 * Includes a maximum number of videos that can be contained
 * 
 * CS 305, HW 0
 * @author Bryce Kwon
 * @version January 25, 2023
 */
public class PlayList {
    // these variables contain information about the PlayList
    private int numVideos;
    private int maxVideos;
    private Video[] videos;

    /**
     * PlayList constructor
     * 
     * each PlayList instance must have a maximum number of videos.
     * the max value will default to 10 for an invalid input.
     * 
     * @param maxVideos     max number of videos
     */
    public PlayList(int maxVideos) {
        this.numVideos = 0;
        this.maxVideos = maxVideos > 0 ? maxVideos : 10;
        this.videos = new Video[this.maxVideos];
    }

    /**
     * addVideo
     * 
     * this method adds a Video to the end of the PlayList
     * 
     * @param video     video to be added
     * @return int      0 for success, -1 for fail
     */
    public int addVideo(Video video) {
        // check if the PlayList is full
        if (numVideos >= maxVideos) {
            System.out.println("\nCannot add Video to PlayList. Maximum size has been reached");
            return -1;
        }

        // add video to the next empty position
        videos[numVideos] = video;
        numVideos++;
        return 0;
    }

    /**
     * deleteVideo
     * 
     * this method deletes a Video from the PlayList
     * 
     * @param video     video to be deleted
     * @return int      0 for success, -1 for fail
     */
    public int deleteVideo(Video video) {
        // check if the PlayList is empty
        if (numVideos == 0) {
            System.out.println("\nCannot delete Video from PlayList. PlayList is empty.");
            return -1;
        }

        // search for video in PlayList
        for (int i = 0; i < numVideos; i++) {
            if (videos[i].getName().equals(video.getName())) {
                // delete video if found and shift list down
                for (int j = i; j < numVideos - 1; j++) {
                    videos[j] = videos[j+1];
                }
                numVideos--;
                return 0;
            }
        }

        // check if video not in PlayList
        System.out.println("\nCannot delete Video from PlayList. No Video by that name.");
        return -1;
    }

    /**
     * print
     * 
     * this method displays information about the PlayList
     */
    public void print() {
        // track total duration of PlayList
        int total = 0;

        // print out all Videos in PlayList
        System.out.println("\nVideoList:");
        for (int i = 0; i < numVideos; i++) {
            videos[i].print();
            total += videos[i].getDuration();
        }

        // convert duration into minutes and seconds
        int min = total / 60;
        int sec = total % 60;

        // display total PlayList duration
        System.out.println("Total Play Time: " + min + "min " + sec + "sec");
    }
}
