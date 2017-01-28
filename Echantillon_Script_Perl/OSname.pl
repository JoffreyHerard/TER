#!/usr/bin/perl
use v5.14;

my $osname = $^O;


if( $osname eq 'MSWin32' ){
  print"We are on windows";
  # work around for historical reasons
  exit(1);
} else {
    print "Test si on est sur Mac\n";
    if ($osname eq 'darwin') {
        print "We are on Mac OS X ...\n";
        exit(2);
    }
    else {
        print "Test si on est sur Linux\n";
        if ($osname eq 'linux') {
            print "We are on Linux...\n";
            exit(3);
        }
    }
}
