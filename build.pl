#!/usr/bin/perl
#---------------------#
#  PROGRAM:  argv.pl  #
#---------------------#

$numArgs = $#ARGV + 1;
print "thanks, you gave me $numArgs command-line arguments:\n";
my $sum;
foreach $argnum (0 .. $#ARGV) {
  $sum=$sum+ $ARGV[$argnum];
}
print $sum;

# Création du fichier '.txt'
open (FICHIER, ">resultatF.txt") || die ("Vous ne pouvez pas créer le fichier \"resultatF.txt\"");
# On écrit dans le fichier...
print FICHIER  $sum ;

exit 0;