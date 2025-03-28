#ifndef PLAYLIST_H
#define PLAYLIST_H

#include "video.h"

/* playlist struct */
typedef struct {
  video* videos;
  int num_videos;
  int max_videos;
} playlist;

/* function prototypes */
playlist* create_playlist(int);
int add_video(playlist*, video);
void shuffle(playlist*);
double calc_min_duration(playlist*);
double calc_max_duration(playlist*);
double calc_total_duration(playlist*);
int delete_video(playlist*, char*);
void print_playlist(playlist*);
void free_playlist(playlist*);

#endif
