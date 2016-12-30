String from = message.getFrom();
String body = message.getBody();
System.out.println(String.format("Received message '%1$s' from %2$s", body, from));
// on regarde l'en tete du message apparente a un message xml reponse tel que lid est 1 si reponse 2
//si imposible a executer dans le cas echeant un troisieme champ correspond a lid du job non reussi 
//et 0 si envoie travail 

// on procede donc au build un script ici un peu fictif mais pour prolonger pour voir si ca marche bien
// ici on va juste sommer les results choses assez simple

String regex="[,]";
String[] en_tete = body.split(regex);

// indice 0 = en tete indice 1 = res
if(Integer.parseInt(en_tete[0])==1){
retour_Providing+=Integer.parseInt(en_tete[1]);
recu++;
if(recu==envoyer)
{
	travail_terminer=true;
}
