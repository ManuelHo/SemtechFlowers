package semtech;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import coreservlets.ServletUtilities;
import utils.OWLIndividualFactory;
import utils.OntologyManager;

@WebServlet("/show")
public class ShowOldBunches extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String title = "Alle bisherigen Str‰uﬂe";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		OWLOntologyManager manager = OntologyManager.getManager();
		OWLOntology ontology = OntologyManager.getOntology();
		OWLDataFactory fac = manager.getOWLDataFactory();
		PrefixManager pm = OntologyManager.getPrefixManager();

		OWLClass bunchClass = fac.getOWLClass(":" + OWLIndividualFactory.BUNCH, pm);
		OWLReasoner reasoner = OntologyManager.getReasoner();
		
			
		
		StringBuilder sb = new StringBuilder();
		//reasoner.flush();
		
		NodeSet<OWLNamedIndividual> subClses = reasoner.getInstances(bunchClass, false);
		Set<OWLNamedIndividual> invdi = subClses.getFlattened();
		System.out.println("Alle str‰uﬂe");
		System.out.println("Anzahl:" + invdi.size());
		
		for (OWLNamedIndividual inv : invdi) {
			System.out.println("    " + inv);
			sb.append(ServletUtilities.filter(inv.toString()) +"</br>");
		}
		System.out.println("\n");
		
	

		out.println(ServletUtilities.headWithTitle(title) + "<body>\n"+ ServletUtilities.getNavBar() + "<h1>"
				+ title + "</h1>\n" + "<p>Ihr Strauﬂ</p>\n" + ""
				+ sb.toString() + "test:"+
				"</body></html>");
		
	}
}
