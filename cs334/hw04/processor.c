#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>
#include <string.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <fcntl.h>
#include <time.h>
#include <sys/types.h>
#include <sys/wait.h>


// keep track of the current file in the list that stills needs to be processed while being
// traversed through by multiple concurrent processes or threads.
typedef struct {
    char inputpath[256];
    char outputpath[256];
    char filename[256];
} SharedData;

// function prototypes
void* image_processing(void*);
void placeholder_delay();

// use global scope to maintain semaphore and mutexes across all concurrent processes or threads
sem_t* concurrency_count_semaphore;     // ensure # of processes/threads do not exceed the max
pthread_mutex_t shared_data_mutex;      // ensure shared data is accessed atomically
pthread_mutex_t output_file_mutex;      // ensure output file is accessed atomically


int main(int argc, char** argv) {
    if (argc != 5) {
        fprintf(stderr, "Usage: %s <max number> <mode> <input dir> <output file>\n", argv[0]);
        exit(EXIT_FAILURE);
    } else if (argv[2][0] != 's' && argv[2][0] != 't') {
        fprintf(stderr, "Invalid Mode: select either 's' processes or 't' threads\n");
        exit(EXIT_FAILURE);
    }

    clock_t start_time, end_time;
    
    start_time = clock();   // being peformance benchmark

    int concurrency_max = atoi(argv[1]);    // max number of processes/thread at any given moment
    char concurrency_mode = argv[2][0];     // mode of concurrency (processes or threads)
    char* input = argv[3];                  // input directory
    char* output = argv[4];                 // ouput file

    // initialize semaphores
    concurrency_count_semaphore = sem_open("concurrency_count", O_CREAT, 0644, concurrency_max);
    if (concurrency_count_semaphore == SEM_FAILED) {
        perror("Error creating semaphore\n");
        exit(EXIT_FAILURE);
    }

    // initialize mutexes
    pthread_mutex_init(&shared_data_mutex, NULL);
    pthread_mutex_init(&output_file_mutex, NULL);
    
    // open input directory for traversal
    DIR* input_data = opendir(input);
    if (input_data == NULL) {
        perror("Error opening directory\n");
        exit(EXIT_FAILURE);
    }

    // create one instance of the shared memory for all concurrent processes or threads.
    SharedData shared_data;
    strcpy(shared_data.inputpath, input);
    strcpy(shared_data.outputpath, output);

    // keep track of processes with a count
    int process_count = 0;
    
    // keep track of threads with an array
    pthread_t threads[concurrency_max];

    if (concurrency_mode == 's') {
        struct dirent* entry;
        while ((entry = readdir(input_data)) != NULL) {
            if (entry->d_type == DT_REG) {
                // do not create a new process or thread if the semaphore slots are all taken
                if (sem_wait(concurrency_count_semaphore) == -1) {
                    perror("sem_wait error\n");
                    exit(EXIT_FAILURE);
                }

                // atomically grab the next file that requires processing
                pthread_mutex_lock(&shared_data_mutex);
                strcpy(shared_data.filename, entry->d_name);
                printf("\t%s\n", shared_data.filename);
                pthread_mutex_unlock(&shared_data_mutex);

                // create a new process
                if (fork() == 0) {
                    image_processing((void*) &shared_data);
                    exit(EXIT_SUCCESS);
                }
                process_count++;

                if (process_count >= concurrency_max) {
                    int status;
                    wait(&status);
                    process_count--;
                }
            }
        }
    } else if (concurrency_mode == 't') {
        int thread_index = 0;
        struct dirent* entry;
        while ((entry = readdir(input_data)) != NULL) {
            // check if each entry is a normal file
            if (entry->d_type == DT_REG) {
                // do not create a new process or thread if the semaphore slots are all taken
                if (sem_wait(concurrency_count_semaphore) == -1) {
                    perror("sem_wait error\n");
                    exit(EXIT_FAILURE);
                }

                // atomically grab the next file that requires processing
                pthread_mutex_lock(&shared_data_mutex);
                strcpy(shared_data.filename, entry->d_name);
                printf("\t%s\n", shared_data.filename);
                pthread_mutex_unlock(&shared_data_mutex);

                // cycle through all indicies and create the max number of threads
                pthread_create(&threads[thread_index], NULL, image_processing, (void*) &shared_data);
                thread_index = (thread_index + 1) % concurrency_max;
            }
        }
    }

    // close the input directory
    closedir(input_data);

    // close all processes and threads
    if (concurrency_mode == 's') {
        while (process_count > 0) {
            int status;
            wait(&status);
            process_count--;
        }
    } else if (concurrency_mode == 't') {
        for (int i = 0; i < concurrency_max; i++) {
            pthread_join(threads[i], NULL);
        }
    }

    // destroy mutexes
    pthread_mutex_destroy(&shared_data_mutex);
    pthread_mutex_destroy(&output_file_mutex);

    // close semaphores
    sem_close(concurrency_count_semaphore);
    sem_unlink("concurrency_count");

    end_time = clock();     // end performance time benchmark
    
    printf("Execution Time: %f seconds\n", (double) (end_time - start_time) / CLOCKS_PER_SEC);
    exit(EXIT_SUCCESS);
}

void* image_processing(void* arg) {
    SharedData* shared_data = (SharedData*) arg;

    char image_path[256];
    sprintf(image_path, "%s%s", shared_data->inputpath, shared_data->filename);

    // placeholder for other function
    placeholder_delay();

    pthread_mutex_lock(&output_file_mutex);

    FILE* output = fopen(shared_data->outputpath, "a");
    if (output == NULL) {
        perror("Error opening file\n");
        exit(EXIT_FAILURE);
    }

    fprintf(output, "%s\n", shared_data->filename);
    
    fclose(output);

    pthread_mutex_unlock(&output_file_mutex);

    sem_post(concurrency_count_semaphore);

    return NULL;
}

void placeholder_delay() {
    usleep(200 * 1000);
}

