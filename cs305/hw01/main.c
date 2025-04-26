/* CS 305, Spring 2023
 * HOMEWORK 1 - driver file
 *
 * main.c
 * author: Martin Cenek
 * Spring 2023 - 
 * v1: 01/20/2023
 * v2: 01/23/2023
 * creates playlist of videos
 * main.c driver provides tests of playlist and vidoes 
 *
 * compile with playlist.c and video.c
 * gcc playlist.c video.c main.c
 * OR
 * gcc -o playlistTest playlist.c video.c main.c
 *
 * If creating playlistTest object file, then run with command:
 * ./playlistTest
 * Otherwise, run with command:
 * ./a.out
 */

#include <stdio.h>
#include <stdlib.h>
#include "playlist.h"
#include "video.h"

/* prototypes */
int run_test(void);

/* main 
 * using void paramater since we are not using command line arguments
 * for this program
 */
int main(void) {
  run_test();
}

/* run_test
 * runs a test, creating playlist and adding/deleting videos
 */
int run_test(void) {
  /* create two playlists */
  /* note: using pointers for store inventories so the playlist data can be 
     modified */
  playlist* johnList = create_playlist(-3);
  playlist* maryList = create_playlist(2);

  /* create videos */
  /* note: not using pointers to videos since once they are created
   * they are not later modified -- only used as data */
  // create videos
  video maryPop = create_video(2145, "Mary Poppins", 775);
  video princess = create_video(3121, "Princess Diaries", 102);
  video frozen = create_video(1233, "Frozen", 247);
  video rambo = create_video(6212, "Rambo", -22);
  video fict = create_video(1234, "Pulp Fiction", 124);
  video wind = create_video(4545, "Gone with the Wind", -1234);
  video secr = create_video(3222, "Madam Secretary ", 13);
  video desp = create_video(2122, "Desperatos", -505);
  video einst = create_video(1121, "Little Einstains", 224);
  video pony = create_video(2121, "My Little Pony", -15);
  video flint = create_video(3424, "Flinstones", 633);
  video simps = create_video(8000, "Simpsons", 1214);

  // put some videos into playlist
  add_video(johnList,maryPop);
  add_video(johnList,princess);
  add_video(johnList,frozen);
  add_video(johnList,rambo);
  
  // for write-up: draw a picture of what johnList's playlist datastructure looks like
  
  // print playlist
  print_playlist(johnList);

  // put more videos into johnList's playlist
  add_video(johnList,fict);
  add_video(johnList,wind);
  add_video(johnList,secr);
  add_video(johnList,desp);
  
  // print playlist
  print_playlist(johnList);
  
  // add videos
  add_video(johnList,einst);
  add_video(johnList,pony);
  
  // print playlist
  print_playlist(johnList);
  
  // add videos to maryList
  add_video(maryList,princess);
  add_video(maryList,wind);
  print_playlist(maryList);
  
  // add another video
  add_video(maryList,secr);
  print_playlist(maryList);

  /* put more videos in */
  add_video(johnList, einst);
  printf("Trying to add alrady existing video\n");
  add_video(johnList, einst);
  add_video(johnList, flint);
  add_video(johnList, simps);

   /* delete videos */
  //printf("Trying to delete video with ID 11\n"); 
  delete_video(johnList, "Desperatos");
  delete_video(johnList, "Mary Poppins");
  /* print the playlist */
  print_playlist(johnList);

  /* at point to draw picture 1 in report */

  //playlist shuffled
  shuffle(johnList);
  /* print the playlist */
  print_playlist(johnList);

  /* at point to draw picture 2 in report */

  /* delete video */
  //printf("Trying to delete video that does not exist\n");
  delete_video(johnList, "This one does not exist");


  /* add videos to maryList */
  add_video(maryList, simps);
  add_video(maryList, flint);

  /* print playlist */
  print_playlist(maryList);

  /* add another videos to maryList */
  add_video(maryList, maryPop);
  add_video(maryList, fict);

  /* delete videos */
  //printf("Trying to delete video \n");
  delete_video(maryList,"Princess Diaries");
  //printf("Trying to delete video \n");
  delete_video(maryList, "Gone with the Wind");
  delete_video(maryList, "Madam Secretary ");
  print_playlist(maryList);
  //printf("Trying to delete video \n");
  delete_video(maryList, "Flinstones");
  print_playlist(maryList);
  //printf("Trying to add video\n");
  add_video(maryList, princess);
  print_playlist(maryList);

  //printf("Trying to delete video 2\n");
  delete_video(maryList, "Simpsons");
  delete_video(maryList,"Princess Diaries");
  delete_video(maryList,"Princess Diaries");
  print_playlist(maryList);

  /* add videos to maryList */
  add_video(maryList, secr);
  add_video(maryList, desp);
  print_playlist(maryList);

  /* free memory for store inventories */
  free_playlist(maryList);
  free_playlist(johnList);

  return EXIT_SUCCESS;
} /* end main */
