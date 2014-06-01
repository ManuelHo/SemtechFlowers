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
		String title = "Bisher erstellte Str‰uﬂe";
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
			sb.append(getClassName(inv.toString()) +"<a href=\"create?strauss=" + getClassName(inv.toString())+"\"> Auswahl </a></br>");
		}
		System.out.println("\n");
		
	
		out.println(ServletUtilities.headWithTitle(title) + "<body>\n"+ ServletUtilities.getNavBar() + 
				"<div class=\"container\"> <div class=\"page-header\" id=\"banner\"> <div class=\"row\"> <div class=\"col-lg-6\">"
				+ "<h1> Flowershower</h1> <p class=\"lead\">Der Florist in ihrer N‰he</p> </div>"
				+ "<img alt=\"Flowershower der Blumenshop in ihrer N‰he\" width=\"200\""
				+ " src=\"bootstrap/blume.jpg\"> </div> </div> "
				+"<h1>"
				+ title + "</h1>\n" + ""
				+ sb.toString() +
				"</body></html>");
		
		
	


			
		
	}
	
	public String getClassName(String input)
	{
		String[] res=  input.split("#");
		String ready = res[1].substring(0, res[1].length()-1);
	
		return ready;
		
	}

}
