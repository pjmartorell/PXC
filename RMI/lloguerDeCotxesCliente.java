import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.util.*;

public class lloguerDeCotxesCliente 
{
    public static void main(String[] args) throws Exception
	{
		InOut io = new InOut();
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
					
					op = io.readint();
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

					op = io.readint();
				
					if (op == 1) submodel = "Diesel";
					else if (op == 2) submodel = "Gasolina";
					else {error(); continue;}

					System.out.println("Pas 3: Introdueix el nombre de dies de lloguer");
					op = io.readint();
					dies_lloguer = op;
					if (dies_lloguer<0) {error(); continue;}
			
					System.out.println("Pas 4: Introdueix la quantitat de vehicles");
					op = io.readint();
					num_vehicles = op;
					if (num_vehicles<0){error(); continue;}
			
					System.out.println("Pas 5: Introdueix el descompte aplicable al lloguer (entre 0 i 100)");
					op = io.readint();
					if (op<0||op>100) {error(); continue;}
					desc = op;
					subtotal = preumodel * dies_lloguer * num_vehicles;
					total = subtotal - (subtotal * (desc*0.01));
					lloga(model, submodel, dies_lloguer, num_vehicles, desc, total);
				}
				else if (op == 2){	//Cas en que es llisten els lloguers
					System.out.println("Introdueix el nom d'usuari:");
					usr=io.readword();
					System.out.println("Introdueix la contrasenya:");
					pswd=io.readword();
					llistaLloguers(usr,pswd);
				}
				else error();
			}
			catch(Exception e) {error();}

		}
    }
    public static int menu()
    {
		System.out.println( "=======================" );
		System.out.println( "+ Lloguer de Vehicles +" );
		System.out.println( "=======================\n" );
		InOut io = new InOut();
		int op;
		System.out.println("Escull una opcio:");
		System.out.println("[1] Llogar vehicle");
		System.out.println("[2] Llistar lloguers");
		System.out.println("[0] Sortir\n");
		try{
			op = io.readint();
			if(op<0||op>2) return -1;
		}
		catch(Exception e){
			return -1;
		}
		return op;
    }
	
    public static void lloga(String model, String submodel, int dies, int numv, int desc, double total) throws Exception
	{
    
		try { // Es busca el servei ServicioX -- forward 
			lloguerDeCotxes c = (lloguerDeCotxes)Naming.lookup("rmi://localhost:1099/ServiciolloguerDeCotxes");
			System.out.println(c.reglloguer(model, submodel, dies, numv, desc, total));
		}
		catch (MalformedURLException murle) {
			System.out.println("MalformedURLException: " + murle);
		}
		catch (RemoteException re) {
			System.out.println("RemoteException: " + re);
		}
		catch (NotBoundException nbe) {
			System.out.println("NotBoundException: " + nbe);
		}
		catch (java.lang.ArithmeticException ae) {
			System.out.println("java.lang.ArithmeticException: " + ae);
		}
    }

    public static void llistaLloguers(String user, String pw)throws Exception
	{
		List<String> resultat;
		
		try { // Es busca el servei ServicioX -- forward 
			lloguerDeCotxes c = (lloguerDeCotxes)Naming.lookup("rmi://localhost:1099/ServiciolloguerDeCotxes");
			resultat = c.llistarLloguers(user,pw);
			Iterator iter = resultat.iterator();
			do System.out.print((String)iter.next());
			while (iter.hasNext());
		}
		catch (MalformedURLException murle) {
			System.out.println("MalformedURLException: " + murle);
		}
		catch (RemoteException re) {
			System.out.println("RemoteException: " + re);
		}
		catch (NotBoundException nbe) {
			System.out.println("NotBoundException: " + nbe);
		}
		catch (java.lang.ArithmeticException ae) {
			System.out.println("java.lang.ArithmeticException: " + ae);
		}
    }

    public static void error()
    {
		System.out.println("Error: Has introduit un valor invalid.");
		System.out.println("");
    }

 }


