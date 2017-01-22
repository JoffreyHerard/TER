#!/usr/bin/perl
use v5.14;

my $osname = $^O;
my $mypath = echo $PATH;

if ($osname eq 'linux') {
	print "We are on Linux...\n";
	system("gcc -std=c11 -pedantic -Wall -Wextra -O3    langford-mono.c   -o langford");
	system('export PATH=$PATH:'.$mypath);
	exit(3);
}

