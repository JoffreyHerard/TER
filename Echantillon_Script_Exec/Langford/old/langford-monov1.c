#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int count =0;
/* string, used, order, index */
void langford(char *s, char *u, int n, int i)
{
    if (i == 2 * n) {
        puts(s);
	count=count+1;
        return;
    }
    if (s[i]) {
        langford(s, u, n, i + 1);
        return;
    }
    for (int j = 1; j <= n && i + j + 1 < 2 * n; j++) {
        if (u[j - 1] || s[i + j + 1])
            continue;
        u[j - 1] = s[i] = s[i + j + 1] = 'A' + j - 1;
        langford(s, u, n, i + 1);
        u[j - 1] = s[i] = s[i + j + 1] = 0;
    }
}

int main(int argc, char **argv)
{
    if (argc != 2)
        return 1;

    int n = atoi(argv[1]);
    char u[n], s[2 * n + 1];
    memset(s, 0, sizeof(s));
    memset(u, 0, sizeof(u));
    langford(s, u, n, 0);
    FILE* fichier = NULL;
 
    fichier = fopen("resultat.txt", "w");
 
    if (fichier != NULL)
    {
        fprintf(fichier,"%i",count); // Écriture du resultat
        fclose(fichier);
    }
    return 0;
}
