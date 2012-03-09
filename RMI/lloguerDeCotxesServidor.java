import java.rmi.Naming;

public class lloguerDeCotxesServidor {
  public lloguerDeCotxesServidor() {  // El constructor del servei -- forward 
   try {
    	lloguerDeCotxes c = new lloguerDeCotxesImpl();
	    Naming.rebind("rmi://localhost:1099/ServiciolloguerDeCotxes", c);
   } catch (Exception e) { System.out.println("Problema: " + e); }
  }

  public static void main(String args[]) {
    new lloguerDeCotxesServidor();
  }
}


