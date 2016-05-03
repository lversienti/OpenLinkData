package smart.open.data.resource.util;
	
public class URINameSpace {
	//Base Name Space
	public static final String NS = "http://smartcampus.isti.cnr.it/resource/";
	
	//@@@@@@@SUPERTYPE#########
	//super class
	public static final String digitalDataEntities = NS+"DigitalDataEntities";
	public static final String agent = NS+"Agent";
	public static final String sensore = NS+"Sensor";
	public static final String quantity = NS+"Quantity";
	public static final String unitOfMeasure = NS+"UnitOfMeasure";
	public static final String regioneDelloSpazio = NS+"RegionOfSpace";
	public static final String entitaGeografica = NS+"ArchitecturalEntity";
			public static final String haParte = NS+"hasPart";
	public static final String evento = NS+"Event";
	public static final String bigData = NS+"BigData";
	public static final String mappa = NS+"Map";
	public static final String linkedDataSet = NS+"LinkedDataSet";
	public static final String applicazione = NS+"Application";
	public static final String observation = NS+"Observation";
	
	//super property Name
	public static final String hasState = NS+"hasState";//Agent attribute
	public static final String trasmette = NS+"generates";
	
	//super class attribute
	public static final String caratteristicheSensore = NS +"hasCaratteristic";//Camera
	public static final String longitudine = NS+"hasLongitude";//RegionOfSpace
	public static final String latitudine = NS+"hasLatitude";
	public static final String categoria = NS+"hasCategory";
	public static final String statoApp = NS+"hasAppState";//Application
	public static final String nomeApp = NS+"hasAppName";
	public static final String descrizioneApp = NS+"hasAppDesc";
	public static final String idApp = NS+"hasAppId";
	public static final String responsabileApp = NS+"hasAppMan";
	public static final String daDataSet = NS+"hasFromData";//LinkedDataset
	public static final String finoADataSet = NS+"hasToAData";
	public static final String descrizioneDataSet = NS+"hasDescription";
	public static final String fileDataSet = NS+"hasLinkToData";
	public static final String formatoFileDataSet = NS+"hasFormat";
	public static final String areaSlotDiRiferimentoDatSet = NS+"hasArea";//SmartParkingDataset
		
	//generic class
	public static final String ingresso = NS+"Gateway";
	public static final String punto3d = NS+"Point";
	
	//generic property Name
	public static final String inMappa = NS+"inMap";
	
	//generic class attribute
	public static final String haFormatoMappa = NS+"hasMapFormat";
	public static final String haRisoluzioneMappa = NS+"hasMapResolution";
	public static final String haFileMappa = NS+"hasMapFile";
	public static final String haZonaDellaMappa = NS+"hasMapArea";
	public static final String altezzaPunto3d = NS+"hasZ";
	
	//@@@@@@@EXTERNAL#########
	//external class
	public static final String eventoStatoStallo = NS+"SlotEngagedEvent";
	public static final String settore = NS+"Sector";
	public static final String insiemeSettori = NS+"SectorSet";
	public static final String strada = NS+"Road";
	public static final String incrocioStradale = NS+"Crossroad";
	public static final String sensoreSpecifico = NS+"Camera";
	public static final String stallo = NS+"Slot";
	public static final String smartParkingDataSet = NS+"smartParkingDataSet";
	
	//external class attribute
	public static final String idSensoreLocale = NS+"idMacAddress";//Sensor
	public static final String endDataTime = NS+"endDataTime";//SlotEngagedEvent
	public static final String startDataTime = NS+"startDataTime";
	public static final String tipologiaStallo = NS+"hasTypology";//slot
	public static final String idStallo = NS+"idSlot";
	public static final String nomeSettore = NS+"hasSlotName";
	public static final String fotoSettore = NS+"hasPhoto";//Sector
	public static final String hasSectorName = NS+"hasSectorName";
	
	//external properties Name
	public static final String monitora = NS+"monitors";
	public static final String eAssociatoStallo = NS+"isEventOf";
	public static final String associatoAdApp = NS+"isSensorOf";
	
	//@@@@@@@INTERNAL#########
	//internal class
	public static final String corridoio = NS+"Hallway";
	public static final String incrocio = NS+"Incrocio";
	public static final String edificio = NS+"Building";
	public static final String camera = NS+"Camera";
	public static final String persona = NS+"Person";
	public static final String stanza = NS+"Room";
		public static final String roomType = NS+"roomType";
	public static final String istitutoCNR = NS+"CNRInstitute";
	public static final String piano = NS+"Floor";
	public static final String scala = NS+"Stair";
	public static final String eventoCNR = NS+"CNREvent";
	public static final String ascensore = NS+"Elevator";
	public static final String atrio = NS+"Atrium";
	
	//internal class attribute
	public static final String interno = NS+"hasInternalPhone";
	public static final String telefono = NS+"hasOtherTelephone";
	public static final String internoAlt = NS+"hasAlternativeInternalPhone";
	public static final String nome = NS+"hasName";
	public static final String cognome = NS+"hasSurname";
	public static final String haNomeStanza = NS+"roomName";
	public static final String haNomePiano = NS+"floorName";
	public static final String haNomeEdificio = NS+"buildingName";
	
	//Internal property Name
	public static final String archEntityHavePoint = NS+"archEntityHavePoint";
	public static final String pianoHaScala = NS+"hasStair";
	public static final String pianoHaAscensore = NS+"hasElevator";
	public static final String conduceA = NS+"galeadsToSt";
	public static final String conduceAA = NS+"galeadsToElv";
	public static final String conduceAC = NS+"leadsToHa";
	public static final String competeA = NS+"responsibleFor";
	public static final String haPer = NS+"hasFor";
	public static final String ospita = NS+"hold";
	public static final String lavorano = NS+"working";
	public static final String partecipano = NS+"participate";
	
	//@@@@@@@BUILDING#########
	//Building class
	public static final String specificSensorType = NS+"SpecificSensorType";
	public static final String humanPresencePir = NS+"HumanPresencePir";
	public static final String noiseQuantitiesSensor = NS+"NoiseQuantitiesSensor";
	public static final String humiditySensor = NS+"HumiditySensor";
	public static final String temperatureSensor = NS+"TemperatureSensor";
	public static final String lightCompsumptionSensor = NS+"LightCompsumptionSensor";
	public static final String electricalCompsumptionSensor = NS+"ElectricalCompsumptionSensor";
	
	public static final String humanPresence = NS+"HumanPresence";
	public static final String noiseQuantities = NS+"NoiseQuantities";
	public static final String humidity = NS+"Humidity";
	public static final String temperature = NS+"Temperature";
	public static final String lightCompsumption = NS+"LightCompsumption";
	public static final String electricalCompsumption = NS+"ElectricalCompsumption";
	
	public static final String humanPresenceValueType = NS+"Boolean";
	public static final String noiseQuantitiesValueType = NS+"noiseQuantityIndex";
	public static final String humidityValueType = NS+"percentage";
	public static final String temperatureValueType = NS+"CelsiusDegree";
	public static final String eletriLightCompsumptionValueType = NS+"Watt";

	public static final String smartBuildingDataset = NS+"SmartBuildingDataset";
	
	//Building attribute (Data Type property)
	public static final String timeStamp = NS+"timeStamp";
	public static final String value = NS+"Value";
	
	//Building property Name (Object property)
	public static final String hasMeasure = NS+"hasMeasure";
	public static final String hasQuantity = NS+"hasQuantity";
	public static final String observationType = NS+"observationType";

}