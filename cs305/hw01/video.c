/* CS 305, HW 1
 * 
 * video.c
 * author: Bryce Kwon
 * version: February 6, 2023
 * purpose: provides functionality for video structures
*/
#include <stdio.h>
#include <stdlib.h>
#include "video.h"

/* create_video
 * creates a video structure with the specified information.
 *
 * @param duration    duration of video in seconds
 * @param name        name of video (limited to 15 characters)
 * @param likes       number of likes on the video
 * @return video      video structure
*/
video create_video(int duration, char* name, int likes) {
  video media;

  // set video duration
  if (duration < 0) {
    media.duration = 0;
  } else {
    media.duration = duration;
  }

  // set video likes
  if (likes < 0) {
    media.likes = 0;
  } else {
    media.likes = likes;
  }

  // set name to be exactly 15 characters
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
    media.name[i] = letter;
  }
  media.name[MAX_NAME_LENGTH] = '\0';

  // return video structure
  return media;
}

/* print
 * prints information about the video
 * 
 * @param media   video structure
*/
void print(video media) {
  // calculate duration in minutes and seconds
  int minutes = media.duration / 60;
  int seconds = media.duration % 60;
  double time = (double) media.duration / 60;

  // print out video information
  printf("\tVideo:\t%dmin %dsec (%0.2f)\t%s\t\t%d\n", minutes, seconds, time, media.name, media.likes);
}