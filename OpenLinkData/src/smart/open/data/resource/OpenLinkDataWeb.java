package smart.open.data.resource;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



import smart.open.data.resource.bean.AssociaSensoriStanzaBean;
import smart.open.data.resource.bean.CaricaDatiBuildingBean;
import smart.open.data.resource.bean.CaricaDatiSensoriStalloBean;
import smart.open.data.resource.bean.DatiSensoriBean;
import smart.open.data.resource.bean.DatiSensoriBuildingBean;
import smart.open.data.resource.bean.SensoreBean;
import smart.open.data.resource.bean.SensoreBuildingBean;
import smart.open.data.resource.bean.SensoreStalliBean;
import smart.open.data.resource.util.DataUtil;
import smart.open.data.resource.util.URINameSpace;
import smart.open.data.resource.util.URINameSpaceEnglish;
import smart.open.data.resource.util.Parameters;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

import com.google.gson.Gson;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;


/**
 *  @author Loredana Versienti
 */

@Path("/webservice")  
public class OpenLinkDataWeb extends OpenLinkData{


	@GET  
	@Path("/applicationName/{idApp}")  
	@Produces("application/json")   
	public String  registraApplicazione(@PathParam("idApp") String idApp){  
		String message = "";
		try{
			String uriApplicazione = URINameSpace.applicazione+"/"+idApp;
			OntModel ontModel = Parameters.getInstance().getModel();
			//controllo che l'applicazione inn sia stata già registrata
			OntClass applicazioneName_class = ontModel.getOntClass(URINameSpace.applicazione);
			//vuol dire che nn c'è
			if(ontModel.getIndividual(uriApplicazione)==null){
				Individual applicationName_individual = applicazioneName_class.createIndividual(uriApplicazione);
				DatatypeProperty  nomeApplicazione_property = ontModel.getDatatypeProperty(URINameSpace.nomeApp);
				Literal nomeApp_literal = ontModel.createLiteral(idApp);
				applicationName_individual.addProperty(nomeApplicazione_property, nomeApp_literal);

				DatatypeProperty  haStatoApp_property = ontModel.getDatatypeProperty(URINameSpace.statoApp);
				Literal statoApp_literal = ontModel.createLiteral("Attiva");
				applicationName_individual.addProperty(haStatoApp_property, statoApp_literal);
				message = uriApplicazione;
			}
			else
				message = "Failure: the application already exist"; 
		}
		catch (Exception e){
		//	Log.info("Errore:: "+e);
			System.out.println("Error into method registraApplicazione: "+e);
			e.printStackTrace();
			message = "Failure::"+e.getStackTrace().toString();
		}
		return message;
	}


	private String chechMaxIdEvento() {
		String maxIdEv = null ;
		int maxIdInt = 0;
		try{
			VirtGraph set = Parameters.getInstance().getVirtGraph();
			String searchMaxIdEvento = DataUtil.prefix+ " SELECT (COUNT(?URIAppl) AS ?count) FROM " 
					+ DataUtil.IRISmartCampus
					+" WHERE {"
					+ "?URIAppl campus:generates ?ev.  "
					+" }";
			//SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
			//log.info("data "+ft.format(dNow) +" >>> query searchNameApp   "+searchNameApp );
			System.out.println("chechMaxIdEvento:: "+searchMaxIdEvento);
			VirtuosoQueryExecution virtQueryMaxIdApp = VirtuosoQueryExecutionFactory.create(searchMaxIdEvento, set);
			ResultSet idMaxEv = virtQueryMaxIdApp.execSelect();
			while (idMaxEv.hasNext()) {
				QuerySolution resultIdMaxEv = idMaxEv.nextSolution();
				maxIdEv = resultIdMaxEv.get("count").toString();
			}
			maxIdInt = (Integer.parseInt(clearStringResult(maxIdEv))+1);
		} 
		catch (Exception e){
			System.out.println("Error into method chechMaxIdEvento: "+e);
			
		}
		return String.valueOf(maxIdInt);
	}

	

	@POST 
	@Path("/sensorRegister")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({"application/json","application/xml"})
	public String  registraSensore(@FormParam("sensoreStringGson") String sensoreStringGson){ 
		String message ="";
		try{
			OntModel ontModel = Parameters.getInstance().getModel();
			Gson gson = new Gson();
			SensoreBean sensoreBean = gson.fromJson(sensoreStringGson, SensoreBean.class);
			String idApp = sensoreBean.getIdApp();
			String idSensore = sensoreBean.getIdLocalSensor();
			String characteristic = sensoreBean.getCharacteristic();
			String geoInfo = sensoreBean.getGeoPosition();

			//prima controllo che esiste l'applicazione id
			String uriApplicazione = URINameSpaceEnglish.applicazione+"/"+idApp;
			System.out.print("uriApplicazione "+uriApplicazione);
			String uriSensore = URINameSpaceEnglish.sensoreSpecifico+"/"+idApp+"/idSensore_"+idSensore;
			System.out.println(uriSensore);
			if(ontModel.getIndividual(uriApplicazione)==null)
				return message = "Failure: the application doesn't exist";

			//devo controllare che nn esiste già nel db per questa applicazione il sensore di id passato nel metodo
			else {
				if(ontModel.getIndividual(uriSensore)!=null)
					return message = "Failure: the sensor is already existing";
				else{

					//preparo i dati longitudine e latitudine
					String[] infoGeo_split = geoInfo.split(";");
					String longitudine = infoGeo_split[0];
					String latitudine = infoGeo_split[1];

					//tripla URI Sensore Property "associatoAdApp" URI Applicazione
					OntClass sensoreSpecifico_class = ontModel.getOntClass(URINameSpaceEnglish.sensoreSpecifico);
					Individual sensoreUri_individual = sensoreSpecifico_class.createIndividual(URINameSpaceEnglish.sensoreSpecifico+"/"+idApp+"/idSensore_"+idSensore);

					ObjectProperty associatoAdApp_property = ontModel.getObjectProperty(URINameSpaceEnglish.associatoAdApp);

					Individual applicationName_individual = ontModel.getIndividual(uriApplicazione);
					sensoreUri_individual.addProperty(associatoAdApp_property, applicationName_individual);
					//tripla URI Sensore Property  "idSensoreLocale" val
					DatatypeProperty idSensoreLocale_property = ontModel.getDatatypeProperty(URINameSpaceEnglish.idSensoreLocale);
					Literal idSensoreLocale_literal = ontModel.createLiteral(idSensore);
					sensoreUri_individual.addProperty(idSensoreLocale_property, idSensoreLocale_literal);

					//tripla URI Sensore Property "associatoAdApp" literal valore del parametro characteristic
					DatatypeProperty caratteristiche_property = ontModel.getDatatypeProperty(URINameSpaceEnglish.caratteristicheSensore);
					Literal caratteristicheApp_literal = ontModel.createLiteral(characteristic);
					sensoreUri_individual.addProperty(caratteristiche_property, caratteristicheApp_literal);


					//tripla sensoreid "riempie" Regione dello spazio
					ObjectProperty staInUn_property = ontModel.getObjectProperty(URINameSpaceEnglish.staInUn);
					OntClass punto3d_class = ontModel.getOntClass(URINameSpaceEnglish.punto3d);
					Individual regioneDelloSpazioURI_individual = punto3d_class.createIndividual(URINameSpaceEnglish.punto3d+"/idPunto3d_"+idSensore);
					sensoreUri_individual.addProperty(staInUn_property, regioneDelloSpazioURI_individual);
					//tripla Regione dello spazio "haLongitudine" "val"
					Literal longitudine_literal = ontModel.createLiteral(longitudine);
					DatatypeProperty haLongitudine_property = ontModel.getDatatypeProperty(URINameSpaceEnglish.longitudine);

					regioneDelloSpazioURI_individual.addProperty(haLongitudine_property, longitudine_literal);

					//tripla Regione dello spazio "haLatitudine" "val"
					Literal latitudine_literal = ontModel.createLiteral(latitudine);
					DatatypeProperty haLatitudine_property = ontModel.getDatatypeProperty(URINameSpaceEnglish.latitudine);
					regioneDelloSpazioURI_individual.addProperty(haLatitudine_property, latitudine_literal);
					message = "Successful";
				}
			}

		}
		catch (Exception e){
			System.out.println("Error into method registraSensore: "+e);
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 
	 * @param sensoreStringGson
	 * @return a message to comunicate the failure or success of the operation
	 */
	@POST 
	@Path("/buildingSensorRegister")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({"application/json","application/xml"})
	public String  registraSensoreBuilding(@FormParam("sensoreStringGson") String sensoreStringGson){ 
		String message ="";
		System.out.println("sensoreStringGson : "+sensoreStringGson);
		//Log.info("sensoreStringGson : "+sensoreStringGson);
		try{
			OntModel ontModel = Parameters.getInstance().getModel();
			Gson gson = new Gson();
			SensoreBuildingBean sensoreBean = gson.fromJson(sensoreStringGson, SensoreBuildingBean.class);
			String idApp = sensoreBean.getIdApp();
			System.out.print("ID app: "+sensoreBean.getIdApp());
			String idSensore = sensoreBean.getIdLocalSensor();
			String monitoredFeature = sensoreBean.getMonitoredFeature();//quantity
			String sensorType = sensoreBean.getSensorType();//valueType ES "Temperatura", "Umidità"
			String valueType = sensoreBean.getValueType(); //Es decibel unita di misura
			String characteristic = sensoreBean.getCharacteristic();

			//prima controllo che esiste l'applicazione id
			String uriApplicazione = URINameSpace.applicazione+"/"+idApp;
			if(ontModel.getIndividual(uriApplicazione)==null)
				return message = "Failure: the application doesn't exist";
			else
			{//todo
				if(checkExistingMAC(idSensore)>0)
					return message = "Failure: the sensor is already existing";
			{
			
			//devo creare la classe con il valore sensorType (Power, Noise, Humidity...) 
			//se nn esiste, altr la prendo dal modello
			OntClass sensorType_class;
			Individual sensorType_individual;
			System.out.println("pippo "+URINameSpace.NS+sensorType);
			if(ontModel.getOntClass(URINameSpace.NS+idApp+"/"+sensorType)==null){
				//creo Classe
				sensorType_class = ontModel.createClass(URINameSpace.NS+idApp+"/"+sensorType);
			     sensorType_individual = sensorType_class.createIndividual(URINameSpace.NS+idApp+"/"+sensorType+"Sensor_"+idSensore);
				OntClass sensoreSpecifico_class = ontModel.getOntClass(URINameSpace.sensore);
				sensoreSpecifico_class.addSubClass(sensorType_class);
			}
			else {
				System.out.println("pluto "+URINameSpace.NS+idApp+"/"+sensorType);
				sensorType_class = ontModel.getOntClass(URINameSpace.NS+idApp+"/"+sensorType);
				 sensorType_individual = sensorType_class.createIndividual(URINameSpace.NS+idApp+"/"+sensorType+"Sensor_"+idSensore);
			}
			
					//tripla URI SensorType Property "associatoAdApp" URI Applicazione
					ObjectProperty associatoAdApp_property = ontModel.getObjectProperty(URINameSpace.associatoAdApp);
					Individual applicationName_individual = ontModel.getIndividual(uriApplicazione);
					sensorType_individual.addProperty(associatoAdApp_property, applicationName_individual);
					
					//tripla URI Sensore Property  "idSensoreLocale" val
					DatatypeProperty idSensoreLocale_property = ontModel.getDatatypeProperty(URINameSpace.idSensoreLocale);
					Literal idSensoreLocale_literal = ontModel.createLiteral(idSensore);
					sensorType_individual.addProperty(idSensoreLocale_property, idSensoreLocale_literal);

					
					//tripla URI Sensore Property "associatoAdApp" literal valore del parametro characteristic
					DatatypeProperty caratteristiche_property = ontModel.getDatatypeProperty(URINameSpace.caratteristicheSensore);
					Literal caratteristicheApp_literal = ontModel.createLiteral(characteristic);
					sensorType_individual.addProperty(caratteristiche_property, caratteristicheApp_literal);
					
					//tripla URI Sensore  "hasQuantity" DataObject Quantity
					if(monitoredFeature!=null){
						OntClass quantity_class = ontModel.getOntClass(URINameSpace.quantity);
						Individual monitoredFeature_individual = quantity_class.createIndividual(quantity_class+"/"+monitoredFeature);
						ObjectProperty hasQuantity_property = ontModel.getObjectProperty(URINameSpace.hasQuantity);
						sensorType_individual.addProperty(hasQuantity_property, monitoredFeature_individual);
					}
					
					
					//tripla URI Sensore  "hasMeasuredBy" DataObject UnitedOfMeasure
					if(sensorType!=null){
						OntClass unitedOfMeasure_class = ontModel.getOntClass(URINameSpace.unitOfMeasure);
						Individual unitedOfMeasure_individual = unitedOfMeasure_class.createIndividual(unitedOfMeasure_class+"/"+valueType);
						ObjectProperty  hasMeasure_property = ontModel.getObjectProperty(URINameSpace.hasMeasure);
						sensorType_individual.addProperty(hasMeasure_property, unitedOfMeasure_individual);
					}
					
					message = "Successful";
				}
			}
		
		}
		catch (Exception e){
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println("Error into method registraSensoreBuilding: "+exceptionAsString);
			//Log.info("Error into method registraSensoreBuilding: "+exceptionAsString);
			return message = "Failure INTO METHOD registraSensoreBuilding::"+exceptionAsString+ " and JSON decoded string IS  " + sensoreStringGson;
		}
		return message;
	}
	
	@POST 
	@Path("/sensorRoomAssociation")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({"application/json","application/xml"})
	public String  associaSensoriStanza(@FormParam("sensoriStanzaGson") String sensoriStanzaGson){
		String message ="Successful";
		try{
			System.out.println("sensoriStanzaGson"+sensoriStanzaGson);
			OntModel ontModel = Parameters.getInstance().getModel();
			Gson gson = new Gson();
			AssociaSensoriStanzaBean sensoriStanzaBean = gson.fromJson(sensoriStanzaGson, AssociaSensoriStanzaBean.class);
			System.out.println("sensoriStanzaGson::: "+sensoriStanzaGson);
			String idStanza = sensoriStanzaBean.getIdStanza();
			String idApp = sensoriStanzaBean.getIdApp();
			ArrayList<String> sensoriIDList = sensoriStanzaBean.getSensoriList();
			
			for(int j = 0; j<sensoriIDList.size(); j++){
				String idSensore = sensoriIDList.get(j);
				String sensorURI = findUriSensore(idSensore);
				if(sensorURI!=null){
					Individual sensoreSpecifico_individual = ontModel.getIndividual(sensorURI);
					ObjectProperty monitora_property = ontModel.getObjectProperty(URINameSpace.monitora);
					String uriRoom = findURIRoom(idStanza);
					if(uriRoom!=null)
					{ Individual room_individual = ontModel.getIndividual(uriRoom);
					 sensoreSpecifico_individual.addProperty(monitora_property, room_individual);
					}
					else message = "Failure: the id room "+ idStanza +" doesn't exist";
				}
				else
					 message = "Failure: the id sensor "+ idSensore +" doesn't exist";
			}
			return message;
		}
		catch (Exception e){
			System.out.println("Error into method associaSensoreStanza: "+e);
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println("exceptionAsString associaSensoriStanza: "+exceptionAsString);
			return message = "Failure into method associaSensoriStanza::"+exceptionAsString+ " and JSON decoded string IS  " + sensoriStanzaGson;
		}
	}



	@POST 
	@Path("/sensorSlotsAssociation")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({"application/json","application/xml"})
	public String  associaSensoreStallo(@FormParam("sensoreStalloGson") String sensoreStalloGson){
		String message ="";
		try{
			OntModel ontModel = Parameters.getInstance().getModel();
			Gson gson = new Gson();
			SensoreStalliBean sensoreStalloBean = gson.fromJson(sensoreStalloGson, SensoreStalliBean.class);
			String idSensore = sensoreStalloBean.getIdLocalSensor();
			String idApp = sensoreStalloBean.getIdApp();

			ArrayList<Integer> stalliList = sensoreStalloBean.getStalliList();
			String uriSensore = URINameSpaceEnglish.sensoreSpecifico+"/"+idApp+"/idSensore_"+idSensore;
			if(ontModel.getIndividual(uriSensore)!=null){	
				Individual  sensoreSpecifico_individual = ontModel.getIndividual(uriSensore);
				DatatypeProperty idSensoreLocale_property = ontModel.getDatatypeProperty(URINameSpaceEnglish.idSensoreLocale);
				sensoreSpecifico_individual.addProperty(idSensoreLocale_property, idSensore);
				ObjectProperty monitoraST_property = ontModel.getObjectProperty(URINameSpaceEnglish.monitoraSt);
				
				for(int j = 0; j<stalliList.size(); j++){
					Integer idStallo = stalliList.get(j);
					String idAreaSettoreStallo[] = findAreaSettoreStallo(String.valueOf(idStallo));
					//http://smartcampus.isti.cnr.it/resource/Stallo/id03001005
					String idStalloCalcolato = idAreaSettoreStallo[0]+idAreaSettoreStallo[1]+idAreaSettoreStallo[2];
					Individual stallo_individual = ontModel.getIndividual(URINameSpaceEnglish.stallo+"/id"+idStalloCalcolato);

					//http://smartcampus.isti.cnr.it/resource/InsiemeDiSettori/id06
					String idInsiemeDiSettoriCalcolato = idAreaSettoreStallo[0];
					Individual insiemeDiSettori_individual = ontModel.getIndividual(URINameSpaceEnglish.insiemeSettori+"/id"+idInsiemeDiSettoriCalcolato);
					//settore http://smartcampus.isti.cnr.it/resource/Settore/id06009
					String idSettoreCalcolato = idAreaSettoreStallo[0]+idAreaSettoreStallo[1];
					Individual settore_individual = ontModel.getIndividual(URINameSpaceEnglish.settore+"/id"+idSettoreCalcolato);
					if(stallo_individual != null & settore_individual!= null & insiemeDiSettori_individual!=null ){
						sensoreSpecifico_individual.addProperty(monitoraST_property, stallo_individual);
						message = "Successful";
					}
					else message = "Failure: doesn't exist the resource";

				}
			}
			else message = "Failure: the id sensor doesn't exist";
		}
		catch (Exception e){
			System.out.println("Error into method associaSensoreStallo: "+e);
			e.printStackTrace();
		}
		return message;
	}

	public String[] findAreaSettoreStallo(String idSlot){
		String areaSettoreStallo[] =new String[3];
		NumberFormat formatter = new DecimalFormat("000");

		try{

			int idSettoreInt = Integer.valueOf(idSlot);
			if((idSettoreInt>=601) && (idSettoreInt<=618)){
				int idStalloCalcolato = (idSettoreInt-(601-1));
				areaSettoreStallo[0] = "03";
				areaSettoreStallo[1] = "001";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 

			}
			if((idSettoreInt>=184) && (idSettoreInt<=220)){
				int idStalloCalcolato = (idSettoreInt-(184-1));
				areaSettoreStallo[0] = "03";
				areaSettoreStallo[1] = "002";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			if((idSettoreInt>=221) && (idSettoreInt<=259)){
				int idStalloCalcolato = (idSettoreInt-(221-1));
				areaSettoreStallo[0] = "03";
				areaSettoreStallo[1] = "003";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			if((idSettoreInt>=262) && (idSettoreInt<=296)){
				int idStalloCalcolato = (idSettoreInt-(262-1));
				areaSettoreStallo[0] = "03";
				areaSettoreStallo[1] = "004";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			if((idSettoreInt>=297) && (idSettoreInt<=331)){
				int idStalloCalcolato = (idSettoreInt-(297-1));
				areaSettoreStallo[0] = "03";
				areaSettoreStallo[1] = "005";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			//area 4
			if((idSettoreInt>=651) && (idSettoreInt<=669)){
				int idStalloCalcolato = (idSettoreInt-(651-1));
				areaSettoreStallo[0] = "04";
				areaSettoreStallo[1] = "001";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			if((idSettoreInt>=332) && (idSettoreInt<=370)){
				int idStalloCalcolato = (idSettoreInt-(332-1));
				areaSettoreStallo[0] = "04";
				areaSettoreStallo[1] = "002";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			if((idSettoreInt>=371) && (idSettoreInt<=416)){
				int idStalloCalcolato = (idSettoreInt-(371-1));
				areaSettoreStallo[0] = "04";
				areaSettoreStallo[1] = "003";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			if((idSettoreInt>=417) && (idSettoreInt<=462)){
				int idStalloCalcolato = (idSettoreInt-(417-1));
				areaSettoreStallo[0] = "04";
				areaSettoreStallo[1] = "004";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}
			if((idSettoreInt>=463) && (idSettoreInt<=508)){
				int idStalloCalcolato = (idSettoreInt-(463-1));
				areaSettoreStallo[0] = "04";
				areaSettoreStallo[1] = "005";
				areaSettoreStallo[2] = formatter.format(idStalloCalcolato); 
			}

		}

		catch (Exception e){
			System.out.println("Error into method findAreaSettoreStallo: "+e);
			e.printStackTrace();
		}
		return areaSettoreStallo;
	}


	@POST 
	@Path("/loadData")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({"application/json","application/xml"})
	public String caricaDati(@FormParam("caricaDatiGson") String caricaDatiGson){  
		String message = "";
		String maxIdEvento;
		try{
			Gson gson = new Gson();
			CaricaDatiSensoriStalloBean sensoreStalliBean = gson.fromJson(caricaDatiGson, CaricaDatiSensoriStalloBean.class);
			String idApp = sensoreStalliBean.getIdApp();
			ArrayList<DatiSensoriBean> datiSensore_list = sensoreStalliBean.getDatiSensoriList();


			OntModel ontModel = Parameters.getInstance().getModel();
			String uriApplicazione = URINameSpaceEnglish.applicazione+"/"+idApp;
			if(ontModel.getIndividual(uriApplicazione)==null)
				return message = "Failure: the application is not registrered";
			
			else{
				//devo cercare max evento per l'incremento
				maxIdEvento = chechMaxIdEvento();
				System.out.println("maxIdEvento "+maxIdEvento);
				OntClass eventoDelloStatoStallo_class = ontModel.getOntClass(URINameSpaceEnglish.eventoStatoStallo);
				
				
				
				ObjectProperty eAssociatoStallo_property = ontModel.getObjectProperty(URINameSpaceEnglish.eAssociatoStallo);
				DatatypeProperty endDataTime_property = ontModel.getDatatypeProperty(URINameSpaceEnglish.endDataTime);
				DatatypeProperty startDataTime_property = ontModel.getDatatypeProperty(URINameSpaceEnglish.startDataTime);
				for(int i=0; i<datiSensore_list.size(); i++){
					
					maxIdEvento = chechMaxIdEvento();
					int maxIdEvento_int = Integer.parseInt(maxIdEvento);
					System.out.println("maxIdEvento_int: "+maxIdEvento_int);
					Individual eventoURI_individual = eventoDelloStatoStallo_class.createIndividual(URINameSpaceEnglish.eventoStatoStallo+"/ev_"+(maxIdEvento_int));
					System.out.println("eventoURI_individual: "+eventoURI_individual);
					ObjectProperty trasmessa_property = ontModel.getObjectProperty(URINameSpaceEnglish.trasmette);
					Individual applicationName_individual = ontModel.getIndividual(uriApplicazione);
					applicationName_individual.addProperty(trasmessa_property, eventoURI_individual);
					
					
					DatiSensoriBean datoSensore = datiSensore_list.get(i);
					String idSlot = datoSensore.getIdSlot();
					String idAreaSettoreStallo[] = findAreaSettoreStallo(String.valueOf(idSlot));;
					//devo prendere literal idslot -- http://smartcampus.isti.cnr.it/resource/Stallo/id03001005
					String idStallo = idAreaSettoreStallo[0]+idAreaSettoreStallo[1]+idAreaSettoreStallo[2];
					if(ontModel.getIndividual(URINameSpaceEnglish.stallo+"/id"+idStallo)==null)
						return "Failure: doesn't exist the resource idSlot::: "+idSlot;
					Individual stallo_individual = ontModel.getIndividual(URINameSpaceEnglish.stallo+"/id"+idStallo);


					String endRest = datoSensore.getEndRest();
					String startRest = datoSensore.getStartRest();

					 Literal endRest_literal = ontModel.createTypedLiteral(endRest,XSDDatatype.XSDdateTime);
					 Literal startRest_literal = ontModel.createTypedLiteral(startRest,XSDDatatype.XSDdateTime);

					eventoURI_individual.addProperty(eAssociatoStallo_property, stallo_individual);				
					eventoURI_individual.addProperty(startDataTime_property, startRest_literal);
					eventoURI_individual.addProperty(endDataTime_property, endRest_literal);
				}

				message = "Successful";

			}
		}
		catch (Exception e){
			message = "Failure";
			System.out.println("Error into method caricaDati: "+e);
			e.printStackTrace();
		}
		return message;
	}
	
	
	@POST 
	@Path("/loadDataBuilding")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({"application/json","application/xml"})
	public String caricaDatiBuilding(@FormParam("caricaDatiBuildingGson") String caricaDatiGson){  
		String message = "";
		String maxIdObs;
		try{
			System.out.println("caricaDatiBuildingGson: "+caricaDatiGson);
			Gson gson = new Gson();
			CaricaDatiBuildingBean sensoreStanzaBean = gson.fromJson(caricaDatiGson, CaricaDatiBuildingBean.class);
			String idApp = sensoreStanzaBean.getIdApp();
			String sensoreId = sensoreStanzaBean.getIdSensore();
			ArrayList<DatiSensoriBuildingBean> datiSensore_list = sensoreStanzaBean.getDatiSensoriList();


			OntModel ontModel = Parameters.getInstance().getModel();
			String uriSensore = findURISensore(sensoreId);
			String uriApplicazione = URINameSpace.applicazione+"/"+idApp;
			Individual sensoreURI_class=  ontModel.getIndividual(uriSensore);
			System.out.println("usiSensore: "+uriSensore);
			if(ontModel.getIndividual(uriApplicazione)==null)
				return message = "Failure: the application is not registrered";
			
			else{
				if(uriSensore==null)
					return message = "Failure: the idSensor is not registrered";
				else{
				//devo cercare max evento per l'incremento
				//	maxIdObs = chechMaxObservation(uriApplicazione);
				//System.out.println("maxIdObs "+maxIdObs);
				OntClass observation_class = ontModel.getOntClass(URINameSpace.observation);
				
				DatatypeProperty valueSensor_property = ontModel.getDatatypeProperty(URINameSpace.value);
				DatatypeProperty timestamp_property = ontModel.getDatatypeProperty(URINameSpace.timeStamp);
				ObjectProperty trasmessa_property = ontModel.getObjectProperty(URINameSpace.trasmette);
				for(int i=0; i<datiSensore_list.size(); i++){
					System.out.println("i: "+i +">>>>>><datiSensore_list.size() "+ datiSensore_list.size());
					maxIdObs = chechMaxIdEvento();
					System.out.println("maxIdObs: "+maxIdObs);
					Individual observationURI_individual = observation_class.createIndividual(URINameSpace.observation+"/ob_"+maxIdObs);
					System.out.println("eventoURI_individual: "+observationURI_individual);
					sensoreURI_class.addProperty(trasmessa_property, observationURI_individual);
					
					DatiSensoriBuildingBean datoSensore = datiSensore_list.get(i);
					String time = datoSensore.getTimestamp();
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
				    SimpleDateFormat outputDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				    Date parsed = sdf.parse(time);
				    String timeVirtuosoFormat = outputDate.format(parsed);
					String measuredValue = datoSensore.getMeasuredValue();
					
					 Literal value_literal = ontModel.createTypedLiteral(measuredValue,XSDDatatype.XSDdouble);
					 Literal time_literal = ontModel.createTypedLiteral(timeVirtuosoFormat,XSDDatatype.XSDdateTime);
					 observationURI_individual.addProperty(timestamp_property, time_literal);
					 observationURI_individual.addProperty(valueSensor_property, value_literal);
				}
				message = "Successful";
			}
		}
		}
		catch (Exception e){
			System.out.println("errore "+e);
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println("exceptionAsString loadDataBuilding: "+exceptionAsString);
			return message = "Failure INTO METHOD loadDataBuilding::"+exceptionAsString+ " and JSON decoded string IS  " + caricaDatiGson;
		}
		return message;
	}

	@GET  
	@Path("/scaricaDati")  
	@Produces("application/json")  
	public String scaricaDati(@PathParam("nomeApplicazione")String nomeApplicazione){  

		String uriApplicazione = "";
		return uriApplicazione;        
	}


	public String clearStringResult(String count){
		int lastOccurence = count.indexOf("^^");
		String newCount = count.substring(0, (lastOccurence));
		return newCount;
	}
	
	public  String findURISensore(String idSensore){
		String uriSensore = null;
		try{
			VirtGraph set = Parameters.getInstance().getVirtGraph();
			String searchExistingIdSensor = DataUtil.prefix+ " SELECT ?uriSensore FROM " 
					+ DataUtil.IRISmartCampus
					+" WHERE {"
					+ "?uriSensore campus:idMacAddress  "+ "\""+idSensore+ "\"."
					+" }";
			System.out.println("findURISensore:: "+searchExistingIdSensor);
			VirtuosoQueryExecution virtQueryExistingMAC = VirtuosoQueryExecutionFactory.create(searchExistingIdSensor, set);
			ResultSet idSensor = virtQueryExistingMAC.execSelect();
			while (idSensor.hasNext()) {
				QuerySolution resultIdSensor = idSensor.nextSolution();
				uriSensore = resultIdSensor.get("uriSensore").toString();
			}
		} 
		catch (Exception e){
			System.out.println("Error into method findURISensore: "+e);
			e.printStackTrace();
		}
		return uriSensore;
	}
	
	
	
	public int checkExistingMAC(String idSensore){
		String maxIdEv = null ;
		int maxIdInt = 0;
		try{
			VirtGraph set = Parameters.getInstance().getVirtGraph();
			String searchExistingIdSensor = DataUtil.prefix+ " SELECT  (COUNT(?uriSensore) AS ?count) FROM " 
					+ DataUtil.IRISmartCampus
					+" WHERE {"
					+ "?uriSensore campus:idMacAddress  "+ "\""+idSensore+ "\"."
					+" }";
			System.out.println("searchExistingIdSensor:: "+searchExistingIdSensor);
			VirtuosoQueryExecution virtQueryExistingMAC = VirtuosoQueryExecutionFactory.create(searchExistingIdSensor, set);
			ResultSet idSensor = virtQueryExistingMAC.execSelect();
			while (idSensor.hasNext()) {
				QuerySolution resultIdSensor = idSensor.nextSolution();
				maxIdEv = resultIdSensor.get("count").toString();
			}
			maxIdInt = (Integer.parseInt(clearStringResult(maxIdEv)));
		} 
		catch (Exception e){
			System.out.println("Error into method checkExistingIdMAC: "+e);
			e.printStackTrace();
		}
		return maxIdInt;//String.valueOf(maxIdInt);
	}
	
	public String findUriSensore(String idSensore){
		String uriSensore = null ;
		try{
			VirtGraph set = Parameters.getInstance().getVirtGraph();
			String searchURISensore = DataUtil.prefix+ " SELECT ?uriSensore FROM " 
					+ DataUtil.IRISmartCampus
					+" WHERE {"
					+ "?uriSensore campus:idMacAddress  "+ "\""+idSensore+ "\"."
					+" }";
			System.out.println("searchURISensore:: "+searchURISensore);
			VirtuosoQueryExecution virtQueryFindUriSensore = VirtuosoQueryExecutionFactory.create(searchURISensore, set);
			ResultSet idSensor = virtQueryFindUriSensore.execSelect();
			while (idSensor.hasNext()) {
				QuerySolution resultIdSensor = idSensor.nextSolution();
				uriSensore = resultIdSensor.get("uriSensore").toString();
			}
		} 
		catch (Exception e){
			System.out.println("Error into method checkExistingMAC: "+e);
			e.printStackTrace();
		}
		return uriSensore;//String.valueOf(maxIdInt);
	}
	
	
	public String findURIRoom(String idStanza){
		String uriRoom = null ;
		try{
			VirtGraph set = Parameters.getInstance().getVirtGraph();
			String searchURIStanza = DataUtil.prefix+ " SELECT DISTINCT ?room FROM " 
					+ DataUtil.IRISmartCampus
					+" WHERE {"
					+ "?room campus:roomName  "+ "\""+idStanza+ "\"^^xsd:string."
					+" }";
			System.out.println("searchURIStanza:: "+searchURIStanza);
			VirtuosoQueryExecution virtQueryFindUriStanza = VirtuosoQueryExecutionFactory.create(searchURIStanza, set);
			ResultSet idStanzaSet = virtQueryFindUriStanza.execSelect();
			while (idStanzaSet.hasNext()) {
				QuerySolution resultURIRoom = idStanzaSet.nextSolution();
				uriRoom = resultURIRoom.get("room").toString();
			}
		} 
		catch (Exception e){
			System.out.println("Error into method findURIRoom: "+e);
			e.printStackTrace();
		}
		return uriRoom;
	}
}


