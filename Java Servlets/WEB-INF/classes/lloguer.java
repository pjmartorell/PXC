import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

class dadesLloguer
{
	public Integer model_vehicle = null;
	public String sub_model_vehicle = null;
	public Integer dies_lloguer = null;
	public Integer num_vehicles = null;
	public Double descompte = null;
	public Double total = null;
	
	public dadesLloguer(Integer mv, String smv, Integer dl, Integer nv, Double d, Double t)
	{
		model_vehicle = mv;
		sub_model_vehicle = smv;
		dies_lloguer = dl;
		num_vehicles = nv;
		descompte = d;
		total = t;
	}	
}

public class lloguer extends HttpServlet 
{	
	List<dadesLloguer> registreLloguers = new ArrayList<dadesLloguer>();
	Integer numLloguer = 0;
	String user = "periberni";
	String pass = "1234";


	public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException 
	{
    	res.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = res.getWriter();
	
	Integer model_vehicle, dies_lloguer, num_vehicles, subtotal;
	String sub_model_vehicle;
	Double descompte, total;
	try
	{
		model_vehicle = Integer.parseInt(req.getParameter("model_vehicle"));
		sub_model_vehicle = req.getParameter("sub_model_vehicle");
		dies_lloguer = Integer.parseInt(req.getParameter("dies_lloguer"));
		num_vehicles = Integer.parseInt(req.getParameter("num_vehicles"));
		descompte = Double.parseDouble(req.getParameter("descompte"));
		subtotal = (model_vehicle * dies_lloguer * num_vehicles);
	 	total = subtotal - (subtotal * descompte);
		if (dies_lloguer <= 0 | num_vehicles <= 0 | descompte < 0 | descompte > 1) throw new NumberFormatException();
	}
	catch (NumberFormatException ignored) {
		out.println("<head><title>Error</title></head><b>Error:</b> Els parametres introduits son incorrectes");
		return;
	};
		synchronized(this) 
		{
			dadesLloguer lloguer = new dadesLloguer(model_vehicle, sub_model_vehicle, dies_lloguer, num_vehicles, descompte, total);
			registreLloguers.add(lloguer);
		
			out.println("<html><head><title>Lloguer efectuat</title></head><body><h1>Detalls lloguer</h1>");
		
			out.println("<b>Model:</b> " + model_vehicle + "<br /><b>Sub-model:</b> " + sub_model_vehicle + "<br /><b>Dies de lloguer:</b> " + dies_lloguer +	"<br /><b>Número de vehicles:</b> " + num_vehicles + "<br /><b>Descompte:</b> " + descompte + "<br /><br /><b>TOTAL:</b> " + total);
		
			out.println("<br /><br /><br /><a href='/lloguers/main.html'>Tornar enrere</a></body></html>");
		}
		
		
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException 
	{		
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		String uid = req.getParameter("userid");
		String pw = req.getParameter("password");
		Iterator iter = registreLloguers.iterator();
		
		if ((!uid.equals(user)) || (!pw.equals(pass)))
		{
			out.println("<b>Error:</b> Nom d'usuari o password incorrectes");
		}
		else
		{	
			if(!iter.hasNext())
			{
				out.println("<html><notice>No hi ha cap lloguer registrat</notice></html>");
			} 
			else 
			{
				out.println("<html><title>Llistar Lloguers</title><h1>Llista lloguers</h1><table border='1' style='border-collapse:collapse; text-align:center;'><tr><td><b>Model de Vehicle</b></td><td><b>Tipus de motor</b></td><td><b>Dies del lloguer</b></td><td><b>Quantitat</b></td><td><b>Descompte</b></td><td><b>Import total</b></td></tr>");
				do
				{
				      dadesLloguer lloguer = (dadesLloguer) iter.next();
					out.println("<tr><td>" + lloguer.model_vehicle + "</td>");
					out.println("<td>" + lloguer.sub_model_vehicle + "</td>");
					out.println("<td>" + lloguer.dies_lloguer + "</td>");
					out.println("<td>" + lloguer.num_vehicles + "</td>");
					out.println("<td>" + lloguer.descompte + "</td>");
					out.println("<td><b>" + lloguer.total + "</b></td></tr>");
				 } while (iter.hasNext());
				out.println("</table><br /><br /><a href='/lloguers/main.html'>Tornar enrere</a></html>");
	  		}
		}		
	}
}
