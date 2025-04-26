#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

#define BUFF_LINE 128


void execute_cmd(const char *cmd) {
    char *argv[] = {"/bin/sh", "-c", (char *)cmd, NULL};
    if (execv(argv[0], argv) == -1) {
        printf("Error executing command\n");
        return;
    }
}

void main(int argc, char* argv[]){
    char line[BUFF_LINE];

    while(1) {
        printf("> ");
        fgets(line, sizeof(line), stdin);
        line[strcspn(line, "\n")] = 0;

        int invalid_char = 0;
        char* illegal = "|$&<>";
        for (int i = 0; i < strlen(illegal); i++) {
            char* loc = strchr(line, illegal[i]);
            if (loc != NULL) {
                int index = loc - line + 1;
                printf("Illegal character %c at position %d\n", illegal[i], index);

                invalid_char = 1;
                break;
            }
        }
        if (invalid_char == 1) continue;

        if (strcmp(line, "q") == 0) {
            printf("Good Bye!\n");
            break;
        }

        char* token = strtok(line, "=");
        char* lhs_cmd = token;
        char* rhs_cmds = strtok(NULL, "=");

        token = strtok(rhs_cmds, ";");
        // while (token != NULL) {
        //     token = strtok(NULL, ";");
        // }

        printf("%s\t%s\n", lhs_cmd, rhs_cmds);

        // int pipefd[2];
        // pid_t pid;
        //
        // if (pipe(pipefd) == -1) {
        //     perror("error with piping");
        //     exit(EXIT_FAILURE);
        // }
        //
        // pid = fork();
        // if (pid == -1) {
        //     perror("error with forking");
        //     exit(EXIT_FAILURE);
        // }
        // 
        // if (pid == 0) {
        //     close(pipefd[0]);   // close unused read end
        //
        //     // redirect stdout to write end of pipe
        //     if (dup2(pipefd[1], STDIN_FILENO) == -1) {
        //         perror("child dup2");
        //         exit(EXIT_FAILURE);
        //     }
        //
        //     // execute command
        //     printf("Executing: %s", lhs_cmd);
        //     // execute_cmd(lhs_cmd);
        // } else {
        //     close(pipefd[1]);   // close unused write end
        //     
        //     // redirect stdin to the read end of the pipe
        //     if (dup2(pipefd[0], STDIN_FILENO) == -1) {
        //         perror("parent dup2");
        //         exit(EXIT_FAILURE);
        //     }
        //     
        //     // execute command
        //     printf("Executing: %s", token);
        //     // execute_cmd(token);
        // }
    }

    return;
}

