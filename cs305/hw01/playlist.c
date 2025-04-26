/* CS 305, HW 1
 * 
 * playlist.c
 * author: Bryce Kwon
 * version: February 6, 2023
 * purpose: provides functionality for playlist structures
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "video.h"
#include "playlist.h"

/* create_playlist
 * creates a playlist structure with the specified information
 * 
 * @param max_video     max videos the playlist can hold
 * @return playlist*    pointer to the playlist structure
*/
playlist* create_playlist(int max_videos) {
  // allocate memory for a playlist structure
  playlist* mediaList = malloc(sizeof(playlist));
  if (mediaList == NULL) {
    exit(EXIT_FAILURE);
  }

  // set the playlist's current number of videos
  mediaList -> num_videos = 0;

  // set the palylist's video limit
  if (max_videos <= 0) {
    mediaList -> max_videos = 15;
  } else {
    mediaList -> max_videos = max_videos;
  }

  // allocate memory for storing videos
  mediaList -> videos = malloc(sizeof(video) * mediaList -> max_videos);
  if (mediaList -> videos == NULL) {
    exit(EXIT_FAILURE);
  }

  return mediaList;
}

/* add_video
 * adds a video to the playlist if there is space and not a duplicate
 * 
 * @param mediaList   playlist to add videos to
 * @param media       video to be added to the playlist
 * @return int        0 for success, -1 for fail
*/
int add_video(playlist* mediaList, video media) {
  // check if the playlist is full
  if (mediaList -> num_videos >= mediaList -> max_videos) {
    printf("Error: Cannot add another video to playlist. Max playlist size exceeded.\n\n");
    return -1;
  }

  // check playlist for duplicate video instances
  int i;
  for (i = 0; i < mediaList -> num_videos; i++) {
    if (strcmp(mediaList -> videos[i].name, media.name) == 0) {
      printf("Error: Cannot add video to playlist. Duplicate video name.\n\n");
      return -1;
    }
  }

  // add video to the playlist queue
  mediaList -> videos[mediaList -> num_videos] = media;
  mediaList -> num_videos++;
  return 0;
}

/**
External Citation
  Date: 28 January 2023
  Problem: Could not generate unique, random numbers for shuffle function.
  Resource:
    * https://www.cs.yale.edu/homes/aspnes/pinewiki/C(2f)Randomization.html
    * https://cboard.cprogramming.com/c-programming/111610-generating-10-unique-random-numbers-c.html
  Solution: I used example code to create unique numbers. (line 109)
*/

/* shuffle
 * shuffles the playlist queue into a random order
 * 
 * @param mediaList   playlist to shuffle videos from
*/
void shuffle(playlist* mediaList) {
  // check if playlist is empty
  if (mediaList -> num_videos <= 0) {
    printf("Error: Cannot shuffle empty playlist.\n\n");
  }

  // seed the random number generator
  time_t now = time(0);
  srand(time(&now));

  // create an empty array of num_videos length
  int len = mediaList -> num_videos;
  int positions[len];
  int i;
  for (i = 0; i < len; i++) {
    positions[i] = -1;
  }

  // generate unique random numbers for each video in the playlist
  int n;
  int flag = -1;
  int j;
  int k = 0;
  while (k < len) {
    n = (rand() % len);
    for (j = 0; j < len; j++) {
      if (n == positions[j]) {
        flag = 0;
        break;
      } else {
        flag = 1;
      }
    }
    if (flag == 1) {
      positions[k] = n;
      k++;
    }
  }

  // make a temporary holder for the videos
  video* tmp = malloc(sizeof(video) * len);
  for (int xx = 0; xx < len; xx++) {
    tmp[xx] = mediaList -> videos[xx];
  }

  // replace the current position of videos with their randomly assigned ones
  for (int yy = 0; yy < len; yy++) {
    mediaList -> videos[yy] = tmp[positions[yy]];
  }

  // free the temporary holder
  free(tmp);
}

/* calc_min_duration
 * searches for the video with the shortest duration in the playlist
 * 
 * @param mediaList   playlist to search
 * @return double     duration of the shortest video
*/
double calc_min_duration(playlist* mediaList) {
  double min = mediaList -> videos[0].duration;

  // search all videos in the playlist for a shorter duration
  int i;
  for (i = 1; i < mediaList -> num_videos; i++) {
    if (min > mediaList -> videos[i].duration) {
      min = mediaList -> videos[i].duration;
    }
  }

  return min;
}

/* calc_max_duration
 * searches for the video with the longest duration in the playlist
 * 
 * @param mediaList   playlist to search
 * @return double     duration of the longest video
*/
double calc_max_duration(playlist* mediaList) {
  double max = mediaList -> videos[0].duration;

  // search all videos in the playlist for a longer duration
  int i;
  for (i = 1; i < mediaList -> num_videos; i++) {
    if (max < mediaList -> videos[i].duration) {
      max = mediaList -> videos[i].duration;
    }
  }

  return max;
}

/* calc_total_duration
 * calculates the total duration of all videos in the playlist
 * 
 * @param mediaList   playlist to be calculated
 * @return double     total duration of the playlist
*/
double calc_total_duration(playlist* mediaList) {
  double total = 0;

  // add duration of all videos in the playlist
  int i;
  for (i = 0; i < mediaList -> num_videos; i++) {
    total += mediaList -> videos[i].duration;
  }

  return total;
}

/* delete_video
 * deletes a video from the playlist if it exists
 * 
 * @param mediaList   playlist to delete video from
 * @param name        name of the video to be deleted
 * @return int        0 for success, -1 for fail
*/
int delete_video(playlist* mediaList, char* name) {
  // check if the playlist is empty
  if (mediaList -> num_videos <= 0) {
    printf("Error: Video named %s not found. Playlist is empty.\n\n", name);
    return -1;
  }

  // format the name to be exactly 15 characters
  char vidName[MAX_NAME_LENGTH + 1];
  int i;
  int flag = 0;
  char letter;
  for (i = 0; i < MAX_NAME_LENGTH; i++) {
    letter = *(name + i);
    if (flag == 1) {
      letter = ' ';
    } else if (flag == 0 && letter == '\0') {
      flag = 1;
      letter = ' ';
    }
    vidName[i] = letter;
  }
  vidName[MAX_NAME_LENGTH] = '\0';

  // search for and delete video if found, shifting all videos down
  int j;
  for (j = 0; j < mediaList -> num_videos; j++) {
    if (strcmp(mediaList -> videos[j].name, vidName) == 0) {
      mediaList -> videos[j].likes = 0;
      mediaList -> videos[j].duration = 0;
      int k;
      for (k = j; k < mediaList -> num_videos - 1; k++) {
        mediaList -> videos[k] = mediaList -> videos[k+1];
      }
      mediaList -> num_videos--;
      return 0;
    }
  }

  printf("Error: Video named %s not found in the playlist.\n\n", name);
  return -1;
}

/* print_playlist
 * prints information about the playlist
 * 
 * @param mediaList   playlist to delete video from
*/
void print_playlist(playlist* mediaList) {
  printf("PlayList:\n");
  printf("\t\tduration\t\tname\t\t\tlikes\n");

  int i;
  for (i = 0; i < mediaList -> num_videos; i++) {
    print(mediaList -> videos[i]);
  }

  printf("\tShortest:\t%0.2fsec\n", calc_min_duration(mediaList));
  printf("\tLongest:\t%0.2fsec\n", calc_max_duration(mediaList));
  printf("\tTotal Duration:\t%0.2fsec\n\n", calc_total_duration(mediaList));
}

/* free_playlist
 * frees allocated memory
 * 
 * @param mediaList   location of memory to free
*/
void free_playlist(playlist* mediaList) {
  free(mediaList -> videos);
  free(mediaList);
}
