#!/usr/bin/perl

use v5.14;


my $value= system("python -V");

if( $value eq 0)
{
	exit(3);
}
else
{
	exit(0);
}

