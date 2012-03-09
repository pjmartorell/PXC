/**
 * LloguersSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package lloguers.ws;
import lloguers.LloguersImpl;

public class LloguersSoapBindingImpl implements lloguers.ws.Lloguers
{
	LloguersImpl llog = new LloguersImpl();
	
    public int lloga(java.lang.String in0, java.lang.String in1, int in2, int in3, int in4, double in5) throws java.rmi.RemoteException 
	{
        return llog.lloga(in0, in1, in2, in3, in4, in5);
    }

    public byte[] llistaLloguers(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException 
	{
        return llog.llistaLloguers(in0, in1);
    }

}
