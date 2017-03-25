/*

Langford's Problem

John Miller
Lewis & Clark College (at the time)
Portland, OREGON

Free program for non-profit use only!  Not responsible for results!  :^)

First working version after translating from Pascal.
Successor L.c drops the procedure calls, uses register ints for p&kin main.
This version does reflection testing, but omits state-file, position tally.
Should optimize with 'q' in this version.  q = p+k+1 at various places..
You may want to printout the 'a' array each time a solution is found...

This is not my best shot, but it is more readable than the all-in-one main.
At least it is a starting point for langford hobbyists  :^)

If you are going to count beyond 2 billion, you'd better use a 64-bit counter
or simulate one.

Arrays are based at 0, but I don't use the 0th element, ergo 21, 41.

To compile:

	% cc -o langford langford.c

To Use:

	% ./langford n

where n is 3..20.  Good luck running anything beyond 12.  I am currently
cranking on 19.  Please let me know if you find anything interesting.  See
my webpage for suggestions.

OLD:	http://www.lclark.edu/~miller/langford.html
NEW:	http://dialectrix.com/langford.html

*/

#define MAXPAIRS 21
#define POSITIONS 41
#define FALSE 0
#define TRUE 1

#include <stdio.h>

int n, m, solutions;
int k, p;
int done, successful;
int a[POSITIONS], previous[POSITIONS], position[MAXPAIRS];

main (argc, argv)
int argc;
char *argv[];
{
	n = atoi(argv[1]);
	if (n==0) n=3;
	m = 2*n;
	initialize();

	for ( ; done==FALSE; )
	{
		try();
		if (successful==TRUE) lowerpair(); else higherpair();
	};

	printf( "%d\n", solutions );

	FILE* fichier = NULL;

        fichier = fopen("resultat.txt", "w");

        if (fichier != NULL)
        {
		fprintf(fichier,"%d",solutions); // Écriture du resultat
		fclose(fichier);
    	}
}

initialize ()
{
	solutions = 0;
	for (p=1; p<=m; p++) { a[p]=0; previous[p]=0;}
	for (k=1; k<=n; k++) position[k]=0;
	p=0;
	k=n;
	done = FALSE;
}

removepair ()
{
	p=position[k];
	a[p]=0;
	a[p+k+1]=0;
	/* position[k]=0; */
}

propundity ()
{
	/* test for reflection */
	int q;
	done=TRUE;
	q=m;
	for (p=1;done&&p<q;)
	{
		done = a[p] == previous[q];
		p++; q--;
	}
	if (!done)
	{
		solutions++;
		for (p=1; p<=m; p++) previous[p]=a[p];
		for (k=1; k<=3; k++) removepair();
		k=3;
	}
}

lowerpair ()
{
	k--;
	if (k>0) p=0; else propundity();
}

higherpair ()
{
	k++;
	done = k>n;
	if (!done) removepair();
}


try ()
{
	successful=FALSE;

	for ( ; !successful && p+k+1<m; )
	{
		p++;
		successful=(a[p]==0 && a[p+k+1]==0);
	}

	if (successful)
	{
		a[p]=k;
		a[p+k+1]=k;
		position[k]=p;
	}
}