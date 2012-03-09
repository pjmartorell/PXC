import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

//Llegir per teclat
import java.io.BufferedReader;
import java.io.InputStreamReader;

//Pretty Format
import org.jdom.output.Format;

//Per recorrer una llista
import java.util.List;
import java.util.Iterator;

public class Lloguers {


    public static void error()
    {
		System.out.println("Error: Has introduit un valor invalid.\n\n");
    }

    /**
     * Read and parse an xml document from the file at xml/llistaLloguers.xml.
     * This method corresponds to the code in Listing 12.
     * @return the JDOM document parsed from the file.
     */
    public static Document readDocument() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File("xml/llistaLloguers.xml"));
            return anotherDocument;
        } catch(JDOMException e) {
            e.printStackTrace();
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aquest metode llegeix un document JDOM del fitxer XML, si existeix, i sino
     * el crea. Posteriorment afegeix un lloguer amb les dades que entren per teclat. 
     * @return un document JDOM que representa el conjunt de lloguers.
     */
	public static Document createDocument() 
	{

		BufferedReader stdin= new BufferedReader(new InputStreamReader(System.in));
		boolean acabat = false;
	    int op = 0;
		String model = "";
		String submodel = "";
		int dies_lloguer = 0;
		int num_vehicles = 0;
		int preumodel = 0;
		int desc = 0;
		double total = 0;
		double subtotal = 0;

		while (!acabat) 
		{
			try 
			{
			
				System.out.println("Pas 1: Introdueix el numero del model de vehicle");
				System.out.println("[1] Economic");
				System.out.println("[2] Semi-Luxe");
				System.out.println("[3] Luxe");
				System.out.println("[4] Limusina");
			
				op = Integer.parseInt(stdin.readLine());
				switch (op) 
				{
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
				acabat = true;
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}	
		}

	
		Element rootElement;
		Document myDocument = readDocument();
		
		//Si no existeix el fitxer xml creem una nova arrel
		if (myDocument==null)
		{
			// Create the root element
			System.out.println("S'ha creat un fitxer XML nou!\n");
			rootElement = new Element("llistaLloguers");
			rootElement.addContent(new Comment("Caracteristiques del lloguer"));
			//create the document
			myDocument = new Document(rootElement); 
		}
	
		//si ja existeix el fitxer xml, llegim l'element arrel
		else rootElement = myDocument.getRootElement();

		//creem un Element de lloguer
		Element lloguer = new Element("lloguer");
		//afegim el contingut dins del lloguer (afegim subchilds)
		lloguer.addContent(new Element("model").addContent(model));
		lloguer.addContent(new Element("submodel").addContent(submodel));
		lloguer.addContent(new Element("dies_lloguer").addContent(Integer.toString(dies_lloguer)));
		lloguer.addContent(new Element("num_vehicles").addContent(Integer.toString(num_vehicles)));
		lloguer.addContent(new Element("desc").addContent(Integer.toString(desc)));
		lloguer.addContent(new Element("total").addContent(Double.toString(total)));

		//afegim el lloguer com a child de l'arrel
		rootElement.addContent(lloguer);
	
        return myDocument;
    }

   

     /**
     * Aquest metode elimina tots el fills de lloguers de l'arrel.
     * @param myDocument un document JDOM
     */
    public static void removeChildrenElement(Document myDocument) 
	{
    	//obtenim l'arrel
	    Element llistaLloguersElement = myDocument.getRootElement();
		//eliminem tots els seus lloguers-fills
		boolean eliminat = llistaLloguersElement.removeChildren("lloguer");
		//comprovem si s'han eliminat amb èxit
		if(eliminat) 
		{
			System.out.println("S'han eliminat els lloguers satisfactoriament.");
			outputDocumentToFile(myDocument);
		} else {
			System.out.println("Ha estat impossible eliminar els lloguers.");
		}
		outputDocumentToFile(myDocument);
    }

    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * the stdout.
     * This method corresponds to Listing 10.
     * @param myDocument the JDOM document built from Listing 2.
     */
    public static void outputDocument(Document myDocument) 
	{
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter();
            outputter.output(myDocument, System.out);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows how to use XMLOutputter to output a JDOM document to
     * a file located at xml/myFile.xml.
     * This method corresponds to Listing 11.
     * @param myDocument the JDOM document built from Listing 2.
     */
    public static void outputDocumentToFile(Document myDocument) 
	{
        //setup this like outputDocument
        try {
            // XMLOutputter outputter = new XMLOutputter("  ", true);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

            //output to a file
            FileWriter writer = new FileWriter("xml/llistaLloguers.xml");
            outputter.output(myDocument, writer);
            writer.close();

        } catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes a JDOM document in memory, an xsl file at xml/car.xsl,
     * and outputs the results to stdout.
     * This method corresponds to Listing 14.
     * @param myDocument the JDOM document built from Listing 2.
     */
    public static void executeXSL(Document myDocument) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
            // Make the input sources for the XML and XSLT documents
            org.jdom.output.DOMOutputter outputter = new org.jdom.output.DOMOutputter();
            org.w3c.dom.Document domDocument = outputter.output(myDocument);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.dom.DOMSource(domDocument);
            StreamSource xsltSource = new StreamSource(new FileInputStream("xml/lloguer.xsl"));
			//Make the output result for the finished document
            /*
             * Note that here we are just going to output the results to the
             * System.out, since we don't actually have a HTTPResponse object
             * in this example
             */
            //StreamResult xmlResult = new StreamResult(response.getOutputStream());
            StreamResult xmlResult = new StreamResult(System.out);
			//Get a XSLT transformer
			Transformer transformer = tFactory.newTransformer(xsltSource);
			//do the transform
			transformer.transform(xmlSource, xmlResult);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(TransformerConfigurationException e) {
            e.printStackTrace();
		} catch(TransformerException e) {
            e.printStackTrace();
        } catch(org.jdom.JDOMException e) {
            e.printStackTrace();
        }
	}

    /**
     * Main method that allows the various methods to be used.
     * It takes a single command line parameter.  If none are
     * specified, or the parameter is not understood, it prints
     * its usage.
     */
    public static void main(String argv[]) {
        if(argv.length == 1) {
            String command = argv[0];
            if(command.equals("reset")) removeChildrenElement(readDocument());
            else if(command.equals("lloguer")) outputDocumentToFile(createDocument());
            else if(command.equals("llistar")) outputDocument(readDocument());
            else if(command.equals("xsl")) executeXSL(readDocument());
            else {
                System.out.println(command + " no és una opció vàlida.");
                printUsage();
            }
        } else {
            printUsage();
        }
    }

    /**
     * Convience method to print the usage options for the class.
     */
    public static void printUsage() {
        System.out.println("Usatge: Lloguers [opció] on opció és una de les que segueixen:");
        System.out.println("reset - Deixa la llista de lloguers buida"); 
        System.out.println("lloguer - Obté per teclat les dades de lloguer i l'afegeix al xml"); 
        System.out.println("llistar - Llista tots els lloguers en xml");
        System.out.println("xsl - Llista els lloguers en html (transformat amb un xsl)");
    }
}
