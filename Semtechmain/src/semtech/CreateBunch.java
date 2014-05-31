package semtech;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import utils.Flower;
import utils.OWLIndividualFactory;
import utils.OntologyManager;
import coreservlets.ServletUtilities;

@WebServlet("/create")
public class CreateBunch extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Test Servlet with Utilities";
		out.println(ServletUtilities.headWithTitle(title)
				+ "<body bgcolor=\"#fdf5e6\">\n" + "<h1>" + title + "</h1>\n"
				+ "<p>Simple servlet for testing.</p>\n" + "Anzahl" + 8
				+ "</body></html>");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String title = "Ihr zusammengestellter Strauﬂ";
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		// get manager and ontology
		OWLOntologyManager manager = OntologyManager.getManager();
		OWLOntology ontology = OntologyManager.getOntology();
		OWLDataFactory fac = manager.getOWLDataFactory();
		PrefixManager pm = OntologyManager.getPrefixManager();

		// create strauss
		OWLClass bunchClass = fac.getOWLClass(":" + OWLIndividualFactory.BUNCH, pm);
		OWLNamedIndividual bunch = OWLIndividualFactory.getInstance().createBunch();
		OWLClassAssertionAxiom b = fac.getOWLClassAssertionAxiom(bunchClass,
				bunch);
		
		manager.addAxiom(ontology, b);

		// get request parameters
		ArrayList<Flower> flowers = parseRequestFlowers(req);
		
		// object properties
		OWLObjectProperty containsFlower = fac.getOWLObjectProperty(
				OWLIndividualFactory.CONTAINS_FLOWER, pm);
		OWLObjectProperty hasColour = fac.getOWLObjectProperty(OWLIndividualFactory.HAS_COLOUR, pm);

		Set<OWLNamedIndividual> blumenset = new HashSet<OWLNamedIndividual>();
		
		//counter for individuals
		
		//TODO: remove and calculate in ontology
		double price = 0;
		for (int i = 0; i < flowers.size(); i++) {			
			OWLClass type = fac.getOWLClass(":" + flowers.get(i).getType(), pm);
			// colors are named like e.g. arot, ablau, agelb
			OWLNamedIndividual colour = fac.getOWLNamedIndividual(":"
					+ flowers.get(i).getColour() + OWLIndividualFactory.INDIVIDUAL_SUFFIX, pm);
			//create individuals
			for (int k = 0; k < flowers.get(i).getQuantity(); k++) {
				OWLNamedIndividual flower = OWLIndividualFactory.getInstance().createFlower();
				OWLClassAssertionAxiom a = fac.getOWLClassAssertionAxiom(type,
						flower);
				manager.addAxiom(ontology, a);
				
				addObjectProperty(hasColour, flower, colour);
				addObjectProperty(containsFlower, bunch, flower);
				
				blumenset.add(flower);
			}
			price += (flowers.get(i).getPricePerUnit() * flowers.get(i).getQuantity());
		}
		
		// strauﬂ consits of exactly zaehler flowers
		OWLObjectExactCardinality hasFlowerRestriction = fac
				.getOWLObjectExactCardinality(blumenset.size(), containsFlower);
		OWLAxiom axiom = fac.getOWLClassAssertionAxiom(hasFlowerRestriction,
				bunch);
		manager.addAxiom(ontology, axiom);
		
		// flowers are different from each other
		OWLDifferentIndividualsAxiom ia = fac
				.getOWLDifferentIndividualsAxiom(blumenset);
		manager.addAxiom(ontology, ia);
		
		OWLReasoner reasoner = OntologyManager.getReasoner();
		
		// output
		StringBuilder sb = new StringBuilder();
		NodeSet<OWLClass> subClses = reasoner.getTypes(bunch, false);
		Set<OWLClass> clses = subClses.getFlattened();
		System.out.println("Typen des neuen Strauﬂ");
		
		
		for (OWLClass cls : clses) {
			System.out.println("    " + cls);
			System.out.println(getClassName(cls.toString()));
			System.out.println(pm.getPrefix(cls.toString()));
			sb.append(getClassName(cls.toString()) + "</br>");
		}
		System.out.println("\n");
		
		StringBuilder dazupassen = new StringBuilder();
		OWLObjectProperty dazupasst = fac.getOWLObjectProperty(OWLIndividualFactory.DAZU_PASST, pm);
		
  	    NodeSet<OWLNamedIndividual> propval = reasoner.getObjectPropertyValues(bunch, dazupasst);
	        Set<OWLNamedIndividual> propert = propval.getFlattened();
	        System.out.println("Dazu passen");
	        for (OWLNamedIndividual prop : propert) {
	            System.out.println("    " + prop);
	            dazupassen.append(ServletUtilities.filter(prop.toString()) +"</br>");
	        }
	        System.out.println("\n");

	       
		     StringBuilder sbblumen = new StringBuilder();

	     for (int i = 0; i < flowers.size(); i++) {	
	    	 
	    	 Flower f = flowers.get(i);
	    	 sbblumen.append(f.getQuantity() + "x " + f.getType() + " , "+ f.getColour()+ "    \n");
	     }
	     
		out.println(ServletUtilities.headWithTitle(title) + "<body>\n"+ ServletUtilities.getNavBar() + "<h1>"
				+ title + "</h1>\n" + "<p>Ihr zusammengestellter Strauﬂ</p>\n" + ""
						+ "Aus den gew‰hlten Blumen " +"</br>" + sbblumen.toString()+ 
						"</br> wurde als </br>" + sb.toString() + "klassifiziert"  +"     \n" +
				
				"Preis:" + price+  "</body></html>");
		
		
		
		//TODO: try saving ontology
//		try {
//			manager.saveOntology(ont, manager.getOntologyFormat(ont), IRI.create(documentIRI));
//		} catch (OWLOntologyStorageException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnknownOWLOntologyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void addObjectProperty(OWLObjectProperty property,
			OWLNamedIndividual domain, OWLNamedIndividual range) {
		OWLObjectPropertyAssertionAxiom axiom = OntologyManager.getManager().getOWLDataFactory()
				.getOWLObjectPropertyAssertionAxiom(property, domain, range);
		OntologyManager.getManager().addAxiom(OntologyManager.getOntology(), axiom);
	}
	
	public String getClassName(String input)
	{
		//String[] res=  input.split("#");
		//String ready = res[1].substring(0, res[1].length()-1);
	
		return input;
		
	}

	
	private ArrayList<Flower> parseRequestFlowers(HttpServletRequest req){
		Enumeration<String> enumeration = req.getParameterNames();
		
		ArrayList<Flower> flowers = new ArrayList<Flower>();
		
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			int count = Integer.parseInt(req.getParameterValues(parameterName)[0]);
			
			String[] params = req.getParameterValues(parameterName)[1].split(",");
			String type = params[0];
			String colour = params[1];
			double price = Double.parseDouble(params[2]);
			// create flower and add to list
			flowers.add(new Flower(parameterName, type, colour, price, count));
		}
		
		return flowers;
	}
	
}
