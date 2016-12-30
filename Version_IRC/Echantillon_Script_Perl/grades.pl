#!/usr/bin/perl
use v5.14;

open(GRADES, "<:utf8" , "grades") || die "cant open $! \n" ;
binmode(STDOUT, ':utf8');

my %grades;
while (my $line = <GRADES> ){
	my ($student, $grade) = split (" ",$line);
	$grades {$student} .= $grade. " "; 
}

for my $student (sort keys %grades){
	my $scores = 0 ;
	my $total = 0 ; 
	my @grades = split(" ",$grades{$student});
	for my $grade (@grades){
		$total+= $grade;
		$scores++;
	}
	my $average = $total / $scores ;
	print" $student: $grades{$student}\tAverage: $average\n";
}	