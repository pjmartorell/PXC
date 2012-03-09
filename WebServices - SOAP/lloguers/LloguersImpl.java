package lloguers;

// Implementació del servei -- forward 
import java.util.*;

//Pel Serialize
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.lang.Exception;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

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

public class LloguersImpl 
{
	int num_lloguers = 0;
	ArrayList registreLloguers;
	String usr = "pxc";
	String pw = "1234";


	public int lloga(String model, String submodel, int dies, int numv, int desc, double total) 
	{
		int errors = 0;
		try
		{
			dadesLloguer lloguer = new dadesLloguer(model, submodel, dies, numv, desc, total);
			//Llegim el fitxer de dades i n'extraiem la llista de lloguers
			errors = llegirFitxer();
			if (errors == 1) return 1;
			//Afegim el nou lloguer
			registreLloguers.add(lloguer);
			//Tornem a guardar la llista al fitxer de dades
			errors = escriureFitxer();	
		} catch(Exception ex)
		{
			ex.printStackTrace();
			return 1; // Si es produeixen errors informem al client
		}
		return errors;
  }

	public byte[] llistaLloguers(String user, String pass) 
	{
		int errors = llegirFitxer();
		byte[] retorn = new byte[1];
		if(errors == 1)
		{
			retorn[0] = 3;
			return retorn;
		} 
		Iterator iter = registreLloguers.iterator();
		
		if ((!usr.equals(user)) || (!pw.equals(pass))) 
		{
			retorn[0] = 0;
			return retorn; //User-Password incorrecte
		}
		else
		{
			if(!iter.hasNext())	
			{
				retorn[0] = 1;
				return retorn; //No hi ha cap
			}
			else retorn = serialize();
		}
		return retorn;
	}
	
	public int llegirFitxer() 
	{	
		try 
		{
			// Read from disk using FileInputStream
			FileInputStream f_in = new FileInputStream("lloguers.data");

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream (f_in);

			// Read an object
			Object obj = obj_in.readObject();

			if (obj instanceof ArrayList)
			{
				registreLloguers = (ArrayList)obj;
				return 0;
			}
		} catch(java.io.FileNotFoundException nf)
		{
			registreLloguers = new ArrayList();
			return 0;
		} catch(Exception e) { return 1; }
		return 1;
	}
	
	public int escriureFitxer() 
	{
		try
		{
			// Write to disk with FileOutputStream
			FileOutputStream f_out = new FileOutputStream("lloguers.data");

			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

			// Write object out to disk
			obj_out.writeObject (registreLloguers);
		} catch (Exception e) {return 1; }
		return 0;
	}
	
	public byte[] serialize()
	{
		try
		{
			// Create an ObjectOutputStream
			ByteArrayOutputStream ba_out = new ByteArrayOutputStream();
			ObjectOutputStream obj_out = new ObjectOutputStream (ba_out);

			// Write object to Stream
			obj_out.writeObject (registreLloguers);
			byte[] outBytes = ba_out.toByteArray();
			return outBytes;
		} catch (Exception e) { return null; }
	}
}


