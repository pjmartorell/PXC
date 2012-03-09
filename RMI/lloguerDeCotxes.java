import java.util.*;

// Interficie per execució remota -- forward 
public interface lloguerDeCotxes extends java.rmi.Remote 
{	
	public String reglloguer(String model, String submodel, int dies, int numv, int desc, double total) throws java.rmi.RemoteException;
	public List<String> llistarLloguers(String user, String pass) throws java.rmi.RemoteException;
}

