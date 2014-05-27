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
import coreservlets.ServletUtilities;

@WebServlet("/create")
public class TestServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OWLOntologyManager manager;
	
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

		String title = "Ihr gekaufter Strauﬂ";
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		// initialize
		manager = OWLManager.createOWLOntologyManager();
		URL documentIRI = getClass().getClassLoader()
				.getResource("florist.owl");
		OWLOntology ont = null;
		try {
			IRI docIRI = IRI.create(documentIRI);
			ont = manager.loadOntologyFromOntologyDocument(docIRI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			// TODO: generate error message
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			// TODO: generate error message
		}
		OWLDataFactory fac = manager.getOWLDataFactory();
		PrefixManager pm = new DefaultPrefixManager(
				"http://www.semanticweb.org/owltutorial/florist#");

		// create strauﬂ
		OWLClass strauﬂ = fac.getOWLClass(":Strauﬂ", pm);
		OWLNamedIndividual strauﬂ1 = fac.getOWLNamedIndividual(":Strauﬂ1", pm);
		OWLClassAssertionAxiom b = fac.getOWLClassAssertionAxiom(strauﬂ,
				strauﬂ1);
		
		manager.addAxiom(ont, b);

		// create flowers

		// get request parameters
		ArrayList<Flower> flowers = parseRequestFlowers(req);
		
		OWLObjectProperty enthaeltBlume = fac.getOWLObjectProperty(
				":enthaeltBlume", pm);
		OWLObjectProperty hatFarbe = fac.getOWLObjectProperty(":hatFarbe", pm);
		OWLObjectProperty enthaeltThings = fac.getOWLObjectProperty(
				":enthaeltThings", pm);

		// create Individuals
		
		Set<OWLNamedIndividual> blumenset = new HashSet<OWLNamedIndividual>();
		
		//counter for individuals
		int zaehler = 0;
		double preis = 0;
		
		for (int i = 0; i < flowers.size(); i++) {			
			OWLClass type = fac.getOWLClass(":" + flowers.get(i).getType(), pm);
			// colors are e.g. arot, ablau, agelb
			OWLNamedIndividual colour = fac.getOWLNamedIndividual(":a"
					+ flowers.get(i).getColour(), pm);
			//create individuals
			for (int k = 0; k < flowers.get(i).getQuantity(); k++) {
				OWLNamedIndividual flower = fac.getOWLNamedIndividual(":Blume"
						+ zaehler, pm);
				OWLClassAssertionAxiom a = fac.getOWLClassAssertionAxiom(type,
						flower);
				manager.addAxiom(ont, a);
				
				addObjectProperty(ont, hatFarbe, flower, colour);
				addObjectProperty(ont, enthaeltBlume, strauﬂ1, flower);
				
				blumenset.add(flower);
				zaehler++;
			}
			preis += (flowers.get(i).getPricePerUnit() * flowers.get(i).getQuantity());
		}

		// strauﬂ consits of exactly zaehler flowers
		OWLObjectExactCardinality hasFlowerRestriction = fac
				.getOWLObjectExactCardinality(zaehler, enthaeltThings);
		OWLAxiom axiom = fac.getOWLClassAssertionAxiom(hasFlowerRestriction,
				strauﬂ1);
		manager.addAxiom(ont, axiom);

		// flowers are different from each other
		OWLDifferentIndividualsAxiom ia = fac
				.getOWLDifferentIndividualsAxiom(blumenset);
		manager.addAxiom(ont, ia);

		OWLReasoner reasoner = initializeReasoner(ont);

		// output
		StringBuilder sb = new StringBuilder();
		NodeSet<OWLClass> subClses = reasoner.getTypes(strauﬂ1, true);
		Set<OWLClass> clses = subClses.getFlattened();
		System.out.println("Typen des neuen Strauﬂ");
		for (OWLClass cls : clses) {
			System.out.println("    " + cls);
			sb.append(ServletUtilities.filter(cls.toString()) + "     \n");
		}
		System.out.println("\n");

		out.println(ServletUtilities.headWithTitle(title) + "<body>\n" + "<h1>"
				+ title + "</h1>\n" + "<p>Ihr Strauﬂ</p>\n" + ""
				+ sb.toString() +

				"Preis:" + preis + "</body></html>");
	}

	private OWLReasoner initializeReasoner(OWLOntology ont) {
		// initialize reasoner
		OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(
				progressMonitor);
		OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);
		// TODO: check if needed
		// -- try more than 5 flowers
		reasoner.precomputeInferences();
		return reasoner;
	}

	private void addObjectProperty(OWLOntology ont, OWLObjectProperty property,
			OWLNamedIndividual domain, OWLNamedIndividual range) {
		OWLObjectPropertyAssertionAxiom axiom = manager.getOWLDataFactory()
				.getOWLObjectPropertyAssertionAxiom(property, domain, range);
		manager.addAxiom(ont, axiom);
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
