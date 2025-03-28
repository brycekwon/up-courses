#ifndef VIDEO_H
#define VIDEO_H

#define MAX_NAME_LENGTH 15

/* video struct */
typedef struct {
  int duration;
  char name[MAX_NAME_LENGTH + 1];
  int likes;
} video;

/* function prototypes */
video create_video (int, char*, int);
void print(video);

#endif
