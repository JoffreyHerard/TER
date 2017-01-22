#!/usr/bin/perl
use v5.14;
use Cwd 'abs_path';
use Data::Dumper qw(Dumper);

my $osname = $^O;

if ($osname eq 'linux') {
	print "We are on Linux...\n";
	my $str = abs_path($0);
	print $str."\n";
	my $taille = length $str;
	my $taille_fichier = length "contraintes.pl";
	print $taille_fichier."\n";
	print $taille."\n";
	
 	my $path  = substr $str,0,$taille-$taille_fichier; 
	print "My path is ; ".$path."\n"; 
  	system("gcc -std=c11 -pedantic -Wall -Wextra -O3  ".$path."langford.c -o ".$path."langford");
	exit(3);
}
else
{
	exit(0);
}

