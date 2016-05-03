package smart.open.data.resource;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class OpenLinkData {

	protected DatatypeProperty createDataTypeProperty(OntModel model, OntClass domain, String uri, Resource range) {
		DatatypeProperty dtProperty = model.createDatatypeProperty( uri);
		dtProperty.addDomain(domain);
		dtProperty.addRange(range);
		return dtProperty;
	}

	protected ObjectProperty createObjectTypeProperty(OntModel model, OntClass domain, String uri, OntClass range) {
		ObjectProperty objProperty = model.createObjectProperty(uri);
		objProperty.addDomain(domain);
		objProperty.addRange(range);
		return objProperty;
	}

	protected Statement createStatement(OntModel model, Individual individual, ObjectProperty property, String uri) {
		Statement statement = model.createStatement(individual, property, uri);
		model.add(statement);
		return statement;
	}
	
}
