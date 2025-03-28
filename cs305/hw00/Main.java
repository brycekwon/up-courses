/**
 * Main class driver to test hw0 classes
 * Other classes include: PlayList and Video that students should implement
 * 
 * CS 305, HW 0
 * @author Martin Cenek
 * @version Spring 2023
 */
public class Main {
    public static void main(String[] args) {
        // create PlayList objects
        PlayList johnList = new PlayList(-6);  //should create a PlayList with default of size 20
        PlayList maryList = new PlayList(2);
        
        // create Videos
        Video maryPop = new Video( "Mary Poppins", 2145, 775);
        Video princess = new Video("Princess Diaries", 3121, 102);
        Video frozen = new Video("Frozen", 1233, 247);
        Video rambo = new Video("Rambo", 6212, -22);
        Video fict = new Video("Pulp Fiction", 1234, 124);
        Video wind = new Video("Gone with the Wind", 4545, -1234);
        Video secr = new Video("Madam Secretary ", 3222, 13);
        Video desp = new Video("Desperatos", 2122, -505);
        Video einst = new Video("Little Einstains", 1121, 224);
        Video pony = new Video("My Little Pony", 2121, -15);
        Video flint = new Video("Flinstones", 3424, 633);
        Video simps = new Video("Simpsons", 8000, 1214);
        
        // put some Videos into PlayList
        int err = 0;
        err = johnList.addVideo(maryPop);
        err = johnList.addVideo(princess);
        err = johnList.addVideo(frozen);
        err = johnList.addVideo(rambo);
        
        // for write-up: draw picture of what johnList's Videos looks like, including any empty array cells
        
        // print PlayList
        johnList.print();
        
        // put more Videos into PlayList
        err = johnList.addVideo(fict);
        err = johnList.addVideo(fict);
        err = johnList.addVideo(wind);
        err = johnList.addVideo(secr);
        err = johnList.addVideo(desp);
        
        // print PlayList
        johnList.print();
        
        // add Videos
        err = johnList.addVideo(einst);
        err = johnList.addVideo(pony);
        
        // print PlayList
        johnList.print();
        
        // add Videos to maryList
        err = maryList.addVideo(princess);
        err = maryList.addVideo(wind);
        maryList.print();
        
        // add another Video
        err = maryList.addVideo(secr);
        
        return;
    }
}
