package lloguers;

//Llegir per teclat
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;


//Classe que defineix un lloguer - Coneguda pel Client i Servidor
class dadesLloguer implements java.io.Serializable
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

//Classe del client lloguerTester
public class lloguerTester
{
    public static void main(String[] args) throws Exception
	{
		BufferedReader stdin= new BufferedReader(new InputStreamReader(System.in));
		int op;
		String model;
		String submodel;
		String usr="";
		String pswd="";
		int dies_lloguer;
		int num_vehicles;
		int preumodel;
		int desc;
		double total;
		double subtotal;
	
		while((op=menu())!= 0)
		{	
		
			try {
				if (op == 1){	//Cas en que s'enregistra un lloguer
					System.out.println("Pas 1: Introdueix el numero del model de vehicle");
					System.out.println("[1] Economic");
					System.out.println("[2] Semi-Luxe");
					System.out.println("[3] Luxe");
					System.out.println("[4] Limusina");
					
					op = Integer.parseInt(stdin.readLine());
					switch (op) {
						case 1:
							model = "Economic";
							preumodel = 55;
							break;
						case 2:
							model = "Semi-Luxe";
							preumodel = 75;
							break;
						case 3:
							model = "Luxe";
							preumodel = 95;
							break;
						case 4:
							model = "Limusina";
							preumodel = 145;
							break;
						default: 
							error();
							continue;
					}

					System.out.println("Pas 2: Introdueix el numero de sub-model");
					System.out.println("[1] Diesel");
					System.out.println("[2] Gasolina");

					op = Integer.parseInt(stdin.readLine());
				
					if (op == 1) submodel = "Diesel";
					else if (op == 2) submodel = "Gasolina";
					else {error(); continue;}

					System.out.println("Pas 3: Introdueix el nombre de dies de lloguer");
					op = Integer.parseInt(stdin.readLine());
					dies_lloguer = op;
					if (dies_lloguer<0) {error(); continue;}
			
					System.out.println("Pas 4: Introdueix la quantitat de vehicles");
					op = Integer.parseInt(stdin.readLine());
					num_vehicles = op;
					if (num_vehicles<0){error(); continue;}
			
					System.out.println("Pas 5: Introdueix el descompte aplicable al lloguer (entre 0 i 100)");
					op = Integer.parseInt(stdin.readLine());
					if (op<0||op>100) {error(); continue;}
					desc = op;
					subtotal = preumodel * dies_lloguer * num_vehicles;
					total = subtotal - (subtotal * (desc*0.01));
					lloga(model, submodel, dies_lloguer, num_vehicles, desc, total);
				}
				else if (op == 2){	//Cas en que es llisten els lloguers
					System.out.println("Introdueix el nom d'usuari:");
					usr = stdin.readLine();
					System.out.println("Introdueix la contrasenya:");
					pswd = stdin.readLine();
					llistaLloguers(usr,pswd);
				}
				else error();
			}
			catch(Exception e) {e.printStackTrace();}

		}
    }
    public static int menu() throws Exception
    {
		BufferedReader stdin= new BufferedReader(new InputStreamReader(System.in));
		System.out.println( "=======================" );
		System.out.println( "+ Lloguer de Vehicles +" );
		System.out.println( "=======================\n" );
		int op;
		System.out.println("Escull una opcio:");
		System.out.println("[1] Llogar vehicle");
		System.out.println("[2] Llistar lloguers");
		System.out.println("[0] Sortir\n");

		op = Integer.parseInt(stdin.readLine());
		if(op<0||op>2) return -1;

		return op;
    }
	
    public static void lloga(String model, String submodel, int dies, int numv, int desc, double total) throws Exception
	{
		String text = "";
		// crear un servei
		lloguers.ws.LloguersService service = new lloguers.ws.LloguersServiceLocator();
		// usar el servei per a obtenir un stub del servei
		lloguers.ws.Lloguers lloguers = service.getlloguers();
		// cridar el servei
		int errors = lloguers.lloga(model, submodel, dies, numv, desc, total);
		if(errors == 0) 
		{
			text += "\n---------------------------------------------\n";
			text += "<< Lloguer realitzat satisfactoriament >>\n";
			text += "Model: " + model + "\n";
			text += "Sub-model: " + submodel + "\n";
			text += "Dies de lloguer: " + dies + "\n";
			text += "Nombre de vehicles: " + numv + "\n";
			text += "Descompte: " + desc + "%\n";
			text += "Import: " + total +" €\n";
			text += "---------------------------------------------\n";
			System.out.println(text);
		}
		else System.out.println("S'han produit errors");
	}

    public static void llistaLloguers(String user, String pw) throws Exception
	{
		String text = "";
		
		// crear un servei
		lloguers.ws.LloguersService service = new lloguers.ws.LloguersServiceLocator();

		// usar el servei per a obtenir un stub del servei
		lloguers.ws.Lloguers lloguers = service.getlloguers();

		// cridar el servei
		byte[] bllista = lloguers.llistaLloguers(user, pw);	
		try 
		{
			//Recollim els errors, enmagatzemats en el primer i unic byte de bllista
			if (bllista.length == 1)
			{
				if(bllista[0] == 0) System.out.println("\nError: Nom d'usuari o password incorrectes\n");
				else if(bllista[0] == 1) System.out.println("\nNo hi ha cap lloguer registrat");
				else System.out.println("\n S'han produit errors\n");
			}
			else //Des-serialitzem la llista de dadesLloger i la mostrem per pantalla
			{
				ByteArrayInputStream ba_in = new ByteArrayInputStream(bllista);

				// Read object using ObjectInputStream
				ObjectInputStream obj_in = new ObjectInputStream (ba_in);
				Object obj = obj_in.readObject();		
				if (obj instanceof ArrayList)
				{
					ArrayList llista = (ArrayList) obj;
					Iterator iter = llista.iterator();
					text += "\nLlistat de lloguers:\n\n";
					int num = 1;
					do
					{
						dadesLloguer lloguer = (dadesLloguer) iter.next();
						text += "-------------------------\n";
						text += "<< Lloguer " + num++ + " >>\n";
						text += "Model: " + lloguer.model_vehicle + "\n";
						text += "Sub-model: " + lloguer.sub_model_vehicle + "\n";
						text += "Dies de lloguer: " + lloguer.dies_lloguer + "\n";
						text += "Nombre de vehicles: " + lloguer.num_vehicles + "\n";
						text += "Descompte: " + lloguer.descompte + "%\n";
						text += "Import: " + lloguer.total +" €\n";
					} while (iter.hasNext());
					System.out.println(text);
				}
			}
		} catch(Exception ex) { ex.printStackTrace(); }		
	}
		
    public static void error() throws Exception
    {
		System.out.println("Error: Has introduit un valor invalid:\n");
    }

 }


