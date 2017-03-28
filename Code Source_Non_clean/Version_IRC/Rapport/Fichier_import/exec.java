File file_exec = new File("JOB_REC/DATA_EXTRACT/calcul.pl");
file.createNewFile();
writer = new PrintWriter(file_exec);
writer.write(strexec);
writer.close();
// execution
Runtime runtime = Runtime.getRuntime();
System.out.println("execution du fichier de contrainte ");
// on execute le fichier de contrainte 3 = GOOD different = NOGOOD

Process p_cunt =runtime.exec("perl JOB_REC/DATA_EXTRACT/contraintes.pl");
int resultat_con=p_cunt.waitFor();
