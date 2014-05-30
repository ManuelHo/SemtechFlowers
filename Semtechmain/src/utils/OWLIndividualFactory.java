package utils;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

public class OWLIndividualFactory {

	public final static String FLOWER = "Blume";
	public final static String BUNCH = "Strauss";
	
	public static final String INDIVIDUAL_SUFFIX = "Ind";
	public static final String HAS_COLOUR = ":hatFarbe";
	public static final String CONTAINS_FLOWER = ":enthaeltBlume";

	private static int counterFlower = 0;
	private static int counterBunch = 0;

	private static OWLIndividualFactory factory;

	public static OWLIndividualFactory getInstance() {
		if (factory == null) {
			factory = new OWLIndividualFactory();
		}
		return factory;
	}

	public OWLNamedIndividual createFlower() {
		OWLNamedIndividual flower = OntologyManager
				.getManager()
				.getOWLDataFactory()
				.getOWLNamedIndividual(":" + FLOWER + counterFlower,
						OntologyManager.getPrefixManager());
		counterFlower++;
		return flower;
	}

	public OWLNamedIndividual createBunch() {
		OWLNamedIndividual bunch = OntologyManager
				.getManager()
				.getOWLDataFactory()
				.getOWLNamedIndividual(":" + BUNCH + counterBunch,
						OntologyManager.getPrefixManager());
		counterBunch++;
		return bunch;
	}
}
