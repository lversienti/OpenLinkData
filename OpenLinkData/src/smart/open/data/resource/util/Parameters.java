package smart.open.data.resource.util;


import java.io.IOException;
import java.util.Properties;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtModel;

public class Parameters {
	
	
	private VirtGraph set;
	private static Parameters parameters;
	
	  Properties configFile = new Properties();
	  OntModel ontModel;
	  Model baseModel;
	  
	  private Parameters() throws IOException{
		  configFile.load(Parameters.class.getClassLoader().getResourceAsStream("config.properties"));
		  String conn_str = configFile.getProperty("conn_str").trim();
		  String login = configFile.getProperty("login").trim();
		  String pw = configFile.getProperty("pw").trim();
		  String iriSmartCampus = configFile.getProperty("iriSmartCampus").trim();
		  set = new VirtGraph (conn_str, login, pw);//mi collego al db
		  Model baseModel = VirtModel.openDatabaseModel(iriSmartCampus, conn_str, login, pw);
		  ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, baseModel);
		 
	  }
	 
	  public static synchronized Parameters getInstance() throws IOException {
		  if (parameters == null)
			  parameters = new Parameters();
		  return parameters;
	  }

	  public OntModel getModel(){
		  return ontModel;
	  }

	  public VirtGraph getVirtGraph(){
		  return set;
	  }


	 
}
