#!/usr/bin/perl
use v5.14;



# Création du fichier 'fichier.txt'
open (FICHIER, ">resultat.txt") || die ("Vous ne pouvez pas créer le fichier \"fichier.txt\"");
# On écrit dans le fichier...
print FICHIER  $ARGV[0] +$ARGV[1] ;

exit 0;
