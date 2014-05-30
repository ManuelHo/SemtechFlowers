package utils;

import java.net.URISyntaxException;
import java.net.URL;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class OntologyManager {

	private static OWLOntologyManager manager;
	private static OWLOntology ontology;
	private static PrefixManager prefixManager;
	private static OWLReasoner reasoner;
	
	public static OWLOntologyManager getManager(){
		if(manager == null){
			// initialize
			manager = OWLManager.createOWLOntologyManager();
		}
		return manager;
	}
	
	public static OWLOntology getOntology(){
		if(ontology == null){
			URL documentIRI = OntologyManager.class.getClassLoader()
					.getResource("florist.owl");
			try {
				IRI docIRI = IRI.create(documentIRI);
				ontology = manager.loadOntologyFromOntologyDocument(docIRI);
			} catch (URISyntaxException e) {
				e.printStackTrace();
				// TODO: generate error message
			} catch (OWLOntologyCreationException e) {
				e.printStackTrace();
				// TODO: generate error message
			}
		}
		return ontology;
	}
	
	public static PrefixManager getPrefixManager(){
		if(prefixManager == null){
			prefixManager = new DefaultPrefixManager(
					"http://www.semanticweb.org/semtech/flowers#");
		}
		return prefixManager;
	}

	public static OWLReasoner getReasoner() {
		if (reasoner == null) {
			// initialize reasoner
			OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
			ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
			OWLReasonerConfiguration config = new SimpleConfiguration(
					progressMonitor);
			reasoner = reasonerFactory.createReasoner(ontology, config);
			// TODO: check if needed
			// -- try more than 5 flowers
			reasoner.precomputeInferences();
		}else{
			reasoner.flush();
		}
		return reasoner;
	}
}
