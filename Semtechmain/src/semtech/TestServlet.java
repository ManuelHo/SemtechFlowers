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
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

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

		// Reasoner

		// Create Strauﬂ

		OWLClass strauﬂ = fac.getOWLClass(":Strauﬂ", pm);
		OWLNamedIndividual strauﬂ1 = fac.getOWLNamedIndividual(":Strauﬂ1", pm);
		OWLClassAssertionAxiom b = fac.getOWLClassAssertionAxiom(strauﬂ,
				strauﬂ1);
		int preis = 0;
		manager.addAxiom(ont, b);

		// create FLowers ///createFlowers

		// getParameters into ArrayList
		ArrayList<String> parameterNames = new ArrayList<String>();
		Enumeration<String> enumeration = req.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			parameterNames.add(parameterName);
		}

		OWLObjectProperty enthaeltBlume = fac.getOWLObjectProperty(
				":enthaeltBlume", pm);
		OWLObjectProperty hatFarbe = fac.getOWLObjectProperty(":hatFarbe", pm);
		OWLObjectProperty enthaeltThings = fac.getOWLObjectProperty(
				":enthaeltThings", pm);

		// create Individuals
		int zaehler = 0;
		Set<OWLNamedIndividual> blumenset = new HashSet<OWLNamedIndividual>();
		for (int i = 0; i < parameterNames.size(); i++) {
			int count = Integer.parseInt(req.getParameterValues(parameterNames
					.get(i))[0]);
			String data = req.getParameterValues(parameterNames.get(i))[1];
			// flower0 = type, flower1 = color
			String[] flower = data.split(",");
			OWLClass type = fac.getOWLClass(":" + flower[0], pm);
			OWLNamedIndividual farbe = fac.getOWLNamedIndividual(":a"
					+ flower[1], pm);

			for (int k = 0; k < count; k++) {
				preis += Double.parseDouble(flower[2]);
				OWLNamedIndividual blume = fac.getOWLNamedIndividual(":Blume"
						+ zaehler, pm);
				OWLClassAssertionAxiom a = fac.getOWLClassAssertionAxiom(type,
						blume);
				manager.addAxiom(ont, a);
				OWLObjectPropertyAssertionAxiom hatdiesefarbe = fac
						.getOWLObjectPropertyAssertionAxiom(hatFarbe, blume,
								farbe);
				manager.addAxiom(ont, hatdiesefarbe);
				OWLObjectPropertyAssertionAxiom propertyAssertion = fac
						.getOWLObjectPropertyAssertionAxiom(enthaeltBlume,
								strauﬂ1, blume);
				manager.addAxiom(ont, propertyAssertion);
				blumenset.add(blume);
				zaehler++;

			}

		}

		OWLObjectExactCardinality hasFlowerRestriction = fac
				.getOWLObjectExactCardinality(zaehler, enthaeltThings);

		OWLAxiom axiom = fac.getOWLClassAssertionAxiom(hasFlowerRestriction,
				strauﬂ1);
		manager.addAxiom(ont, axiom);
		// manager.saveOntology(ont);

		OWLDifferentIndividualsAxiom ia = fac
				.getOWLDifferentIndividualsAxiom(blumenset);
		manager.addAxiom(ont, ia);

		// Reasoner

		OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(
				progressMonitor);
		OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);
		reasoner.precomputeInferences();

		// Ausgabe erstellen.

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

}
