#!/usr/bin/perl
use v5.14;

my $osname = $^O;

if ($osname eq 'linux') {
	print "We are on Linux...\n";
	my $nm =`gcc -std=c11 -pedantic -Wall -Wextra -O3    langford.c   -o langford`;
	exit(3);
}
else
{
	exit(0);
}
