package lloguers;
public interface Lloguers 
{ 
	// Mètode que ens retorna un valor que indica si el lloguer s'ha registrat correctament
	public int lloga(String model, String submodel, int dies, int numv, int desc, double total);
	// Mètode que ens retorna la llista de lloguers serialitzada
	public byte[] llistaLloguers(String user, String password);
} 
