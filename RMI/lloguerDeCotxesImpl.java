// Implementació del servei -- forward 
import java.util.*;

//Classe que defineix un lloguer
class dadesLloguer
{
	public String model_vehicle = null;
	public String sub_model_vehicle = null;
	public int dies_lloguer;
	public int num_vehicles;
	public int descompte;
	public double total;
	
	public dadesLloguer(String mv, String smv, int dl, int nv, int d, double t)
	{
		model_vehicle = mv;
		sub_model_vehicle = smv;
		dies_lloguer = dl;
		num_vehicles = nv;
		descompte = d;
		total = t;
	}	
}

public class lloguerDeCotxesImpl extends java.rmi.server.UnicastRemoteObject implements lloguerDeCotxes {
  // Constructor para declarar que puede ocurrir "RemoteException"

  float total_lloguers;
  List<dadesLloguer> registreLloguers = new ArrayList<dadesLloguer>(); 
  String usr;
  String pw;
  
  public lloguerDeCotxesImpl() throws java.rmi.RemoteException {
	super();
	usr = "pxc";
	pw = "rmi1234";
	total_lloguers = 0;
  }

  public String reglloguer(String model, String submodel, int dies, int numv, int desc, double total) throws java.rmi.RemoteException 
 {
	String retorn = "";
	dadesLloguer lloguer = new dadesLloguer(model, submodel, dies, numv, desc, total);
	total_lloguers += total;
	registreLloguers.add(lloguer);
	retorn = "\n\nEl vostre lloguer s'ha registrat satisfactoriament..\n";
	retorn += "Model: " + model + "\n";
	retorn += "Sub-model: " + submodel + "\n";
	retorn += "Dies de lloguer: " + dies + "\n";
	retorn += "Nombre de vehicles: " + numv + "\n";
	retorn += "Descompte: " + desc + "%\n";
	retorn += "Import: " + total +" €\n";
	return retorn;
  }

  public List<String> llistarLloguers(String user, String pass) throws java.rmi.RemoteException {

	List<String> retorn = new ArrayList<String>();
	Iterator iter = registreLloguers.iterator();
	int num = 0;

	if ((!usr.equals(user)) || (!pw.equals(pass)))
	{
		retorn.add("\nError: Nom d'usuari o password incorrectes\n");
		return retorn;
	}
	else
	{
		if(!iter.hasNext())
		{
			retorn.add("\nNo hi ha lloguers registrats\n");
			return retorn;
		} 
		else 
		{
			retorn.add("\nLlistat de lloguers:\n\n");
			retorn.add("--------------------\n");
			do
			{
				dadesLloguer lloguer = (dadesLloguer) iter.next();
				num++;
				retorn.add("-------------------------\n");
				retorn.add("<< Lloguer " + num + " >>\n");
				retorn.add("Model: " + lloguer.model_vehicle + "\n");
				retorn.add("Sub-model: " + lloguer.sub_model_vehicle + "\n");
				retorn.add("Dies de lloguer: " + lloguer.dies_lloguer + "\n");
				retorn.add("Nombre de vehicles: " + lloguer.num_vehicles + "\n");
				retorn.add("Descompte: " + lloguer.descompte + "%\n");
				retorn.add("Import: " + lloguer.total +" €\n");
			} while (iter.hasNext());
			retorn.add("\nSuma total dels lloguers: " + total_lloguers + " €\n");
		}	
	}
	return retorn;		
  }
}

