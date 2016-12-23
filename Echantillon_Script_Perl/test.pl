#!/usr/bin/perl

print "Howdy shit \n" ;

my $phrase = "bonjour";
printf $phrase ;

# $ for sigular variable , @ for array 

my $answer = 42;
my $pi =14.159265;
my $avocados = 6.02e23;
my $pet = "CAMEL";
my $sign ="i love my $pet";
my @cost = 'it cost $100';
my $thence =$whence;
my $salsa = $moles * $avocados ;
my $exit= system("vi $file"); 
my $cwd = `pwd`;

my $fido = Camel->new("Amelia");
if (not $fido){ die "dead Camel"};

package Camel ;
use Camel ;


given($gogol)
{
	when(true) {say "OMG"}
	when(['toto','tata','titi','fifi','loulou']){
		say " I think $gogol il pretty cool ";
	}
	default{
		say "toto";
	}
}