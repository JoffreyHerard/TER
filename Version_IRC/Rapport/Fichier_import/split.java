public int split(ArrayList<identity> Liste_user,int Nombre_Participants,
	String ProblemeCourant,XmppManager xmppManager,String choix)
{
	for(int i=0;i<Nombre_Participants-1;i++)
	{
		String buddyJID = Liste_user.get(i).getId();
		String buddyName = Liste_user.get(i).getName();

		try {
			xmppManager.createEntry(buddyJID, buddyName);

			/* Recherche de la liste des exec*/

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			File fileXML = new File("DB_JOBS/"+choix);

			Document xml = builder.parse(fileXML);

			Element root = xml.getDocumentElement();

			XPathFactory xpf = XPathFactory.newInstance();

			XPath path = xpf.newXPath();

				 

			String expression = "/JOB/code_exec";

			String strexec = (String)path.evaluate(expression, root);
			System.out.print("DEBUT STR EXEC");
			System.out.print(strexec);
			System.out.print("FIN STR EXEC");

			expression = "/JOB/code_Perl";

			String strperl = (String)path.evaluate(expression, root);

			expression = "/JOB/cmd";

			String strcmd = (String)path.evaluate(expression, root);


			String delims = "[,]";
			String[] tokens =strcmd.split(delims);
			System.out.print("affichage des tokens");
			tokens[tokens.length-1]=tokens[tokens.length-1].substring(
			0, tokens[tokens.length-1].length()-1
			);
			for(int j=0;j<tokens.length;j++)
				System.out.println(j+" : "+tokens[j]);

			final Document document= builder.newDocument();

			final Element racine = document.createElement("JOB");
			document.appendChild(racine);	
			final Element exec = document.createElement("exec");
			exec.appendChild(document.createTextNode(strexec));

			final Element contraintes = document.createElement("contraintes");
			contraintes.appendChild(document.createTextNode(strperl));


			final Element cmd = document.createElement("cmd");
			cmd.appendChild(document.createTextNode(tokens[i]));
			final Element id = document.createElement("id");
			id.appendChild(document.createTextNode(""+i));
			racine.appendChild(id);
			racine.appendChild(contraintes);
			racine.appendChild(exec);
			racine.appendChild(cmd);

			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			final DOMSource source = new DOMSource(document);
			final StreamResult sortie = new StreamResult(new File("JOB_SEND/XML_send_"+i));
			transformer.transform(source, sortie);	

			String Probleme_individuel=FileToString("JOB_SEND/XML_send_"+i);

			xmppManager.sendMessage(Probleme_individuel, buddyName+"@"+xmppManager.NOM_HOTE);

			}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			// Ici on fait apelle a la fonction split 

	}

	return 0;
}
