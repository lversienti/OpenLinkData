package smart.open.data.resource.util;
	
public class URINameSpaceOLD {
	//Base Name Space
	public static final String NS = "http://smartcampus.isti.cnr.it/resource/";
	
	//@@@@@@@SUPERTYPE#########
	//super class
	public static final String sensore = NS+"Sensore";
	public static final String regioneDelloSpazio = NS+"RegioneDelloSpazio";
	public static final String entitaGeografica = NS+"EntitaGeografica";
	public static final String evento = NS+"Evento";
	public static final String bigData = NS+"BigData";	
	public static final String mappa = NS+"Mappa";
	public static final String linkedDataSet = NS+"LinkedDataSet";
	public static final String applicazione = NS+"Applicazione";
		
	//super property Name
	public static final String misura = NS+"misura";
	public static final String eRilevato = NS+"eRilevato";
	public static final String eAssociataA = NS+"eAssociataA";
	public static final String riempie = NS+"riempie";
	public static final String comprende = NS+"comprende";
	public static final String eAttribuitaA = NS+"eAttribuitaA";
	public static final String haMappa = NS+"haMappa";
	public static final String trasmette = NS+"trasmette";
	public static final String eTrasmessoDa = NS+"eTrasmessoDa";
	
	//super class attribute
	public static final String statoSensore = NS+"haStato";
	public static final String caratteristicheSensore = NS +"haCaratteristiche";
	public static final String longitudine = NS+"haLongitudine";
	public static final String latitudine = NS+"haLatitudine";
	public static final String categoria = NS+"haCategoria";
	public static final String statoApp = NS+"haStatoApp";
	public static final String nomeApp = NS+"haNomeApp";
	public static final String descrizioneApp = NS+"haDescrizioneApp";
	public static final String idApp = NS+"haIdApp";
	public static final String responsabileApp = NS+"haResponsabileApp";
	public static final String daDataSet = NS+"haDaData";
	public static final String finoADataSet = NS+"haFinoAData";
	public static final String descrizioneDataSet = NS+"haDescrizioneDataSet";
	public static final String fileDataSet = NS+"haLinkToData";
	public static final String formatoFileDataSet = NS+"haformatoFileDataSet";
	public static final String areaSlotDiRiferimentoDatSet = NS+"haAreaSlotDiRiferimentoDatSet";
		
	//generic class
	//public static final String risorsaFile = NS+"RisorsaFile";
	public static final String ingresso = NS+"Ingresso";
	public static final String punto3d = NS+"Punto3d";
	
	//generic property Name
	public static final String eParte = NS+"eParte";
	public static final String haParte = NS+"haParte";
	public static final String termina = NS+"termina";
	public static final String inFile = NS+"inFile";
	public static final String inMappa = NS+"inMap";
	//public static final String conduceA = NS+"conduceA";
	
	//generic class attribute
	public static final String haFormatoMappa = NS+"haFormatoMappa";
	public static final String haRisoluzioneMappa = NS+"haRisoluzioneMappa";
	public static final String haFileMappa = NS+"haURLFileMappa";
	public static final String haZonaDellaMappa = NS+"haZonaDellaMappa";
	public static final String altezzaPunto3d = NS+"haZ";
	
	//@@@@@@@EXTERNAL#########
	//external class
	public static final String eventoStatoStallo = NS+"EventoDellOccupazioneStallo";
	public static final String forma = NS+"Forma";
	public static final String settore = NS+"Settore";
	public static final String insiemeSettori = NS+"InsiemeDiSettori";
	public static final String strada = NS+"Strada";
	public static final String incrocioStradale = NS+"IncrocioStradale";
	//il nome della classe è "Punto3d" così che sia la stessa delle entità interne restando effettivamente una classe generica
	public static final String sensoreSpecifico = NS+"SensoreSpecifico";
	public static final String stallo = NS+"Stallo";
	public static final String smartParkingDataSet = NS+"smartParkingDataSet";
	public static final String areaDelCampusPisa = NS+"areaDelCampusPisa";//individual
	
	//external class attribute
	public static final String idSensoreLocale = NS+"idSensoreLocale";
	public static final String endDataTime = NS+"endDataTime";
	public static final String startDataTime = NS+"startDataTime";	
	public static final String orientamento = NS+"haOrientamento";
	public static final String altezzaForma = NS+"haAltezza";
	public static final String lunghezzaStrada = NS+"haLunghezza";
	public static final String tipologiaStallo = NS+"haTipologia";
	public static final String idStallo = NS+"idStallo";
	public static final String nomeSettore = NS+"haNomeSettore";
	public static final String fotoSettore = NS+"haFoto";
	
	//external properties Name
	public static final String monitora = NS+"monitora";
	public static final String monitoratoDa = NS+"monitoratoDa";
	public static final String daAccessoA = NS+"daAccessoA";
	public static final String siAffacciaVerso = NS+"siAffacciaVerso";
	public static final String continuaCon = NS+"continuaCon";
	public static final String conduce = NS+"conduce";
	public static final String haSettore = NS+"haSettore";
	public static final String haStallo = NS+"haStallo";
	public static final String haForma = NS+"haForma";
	public static final String staInUn = NS+"staInUn";
	public static final String possiedeEvento = NS+"possiedeEvento";
	public static final String eAssociatoStallo = NS+"eAssociatoStallo";
	public static final String possiedeSensore = NS+"possiedeSensore";
	public static final String associatoAdApp = NS+"associatoAdApp";
		
	//@@@@@@@INTERNAL#########
	//internal class
	public static final String corridoio = NS+"Corridoio";
	public static final String bagno = NS+"Bagno";
	public static final String incrocio = NS+"Incrocio";
	public static final String edificio = NS+"Edificio";
	public static final String puntoInterno = NS+"PuntoInterno";
	public static final String smartCamera = NS+"SmartCamera";
	public static final String telecamera = NS+"Telecamera";
	public static final String camera = NS+"Camera";
	public static final String persona = NS+"Persona";
	public static final String stanza = NS+"Stanza";
	public static final String istitutoCNR = NS+"IstitutoCNR";
	public static final String piano = NS+"Piano";
	public static final String scala = NS+"Scala";
	public static final String porta = NS+"Porta";
	public static final String varco = NS+"Varco";
	public static final String passaggio = NS+"Passaggio";
	public static final String eventoCNR = NS+"EventoCNR";
	public static final String ascensore = NS+"Ascensore";
	public static final String atrio = NS+"Atrio";
	
	//internal class attribute
	public static final String interno = NS+"haInterno";
	public static final String telefono = NS+"haAltroTelefono";
	public static final String internoAlt = NS+"haInternoAlt";
	public static final String nome = NS+"haNome";
	public static final String cognome = NS+"haCognome";
	public static final String lunghezzaCorridoio = NS+"haLunghezza";
	public static final String haNomeStanza = NS+"haNomeStanza";
	public static final String haNomePiano = NS+"haNomePiano";
	public static final String haNomeEdificio = NS+"haNomeEdificio";
	
	//Internal property Name
	public static final String tRiempieP = NS+"tRiempieP";
	public static final String sRiempieP = NS+"sRiempieP";
	public static final String monitoraSt = NS+"monitoraSt";
	public static final String monitoratoDaCam = NS+"monitoratoDaCam";
	public static final String eAttribuitoAE = NS+"eAttribuitoAE";
	public static final String haSc = NS+"haSc";
	public static final String haAsc = NS+"haAsc";
	public static final String haParteP = NS+"haParteP";
	public static final String pianoHaScala = NS+"haScala";
	public static final String pianoHaAscensore = NS+"haAscensore";
	public static final String haParteA = NS+"haParteA";
	public static final String haParteC = NS+"haParteC";
	public static final String haParteS = NS+"haParteS";
	public static final String daAccessoAIn = NS+"daAccessoAIn";
	public static final String conduceA = NS+"conduceA";
	public static final String conduceAA = NS+"conduceAA";
	public static final String conduceAC = NS+"conduceAC";
	public static final String inConduceAC = NS+"inConduceAC";
	public static final String daSu = NS+"daSu";
	public static final String siAffacciaSu = NS+"siAffacciaSu";
	public static final String faAccedere = NS+"faAccedere";
	public static final String competeA = NS+"competeA";
	public static final String haPer = NS+"haPer";
	public static final String ospita = NS+"ospita";
	public static final String lavorano = NS+"lavorano";
	public static final String partecipano = NS+"partecipano";
	
	//old and inverse property
	public static final String haAfferenza = NS+"haAfferenza";
	public static final String associato = NS+"associato";
	public static final String staIn = NS+"staIn";
	//public static final String possiede = NS+"Possiede";
	public static final String contiene = NS+"contiene";
	//public static final String appartieneA = NS+"AppartieneA";
	public static final String possiedeIn = NS+"possiedeIn";
	public static final String haAssegnata = NS+"haAssegnata";
	public static final String collega = NS+"collega";
	public static final String collegaContiguamente = NS+"collegaContiguamente";
	public static final String costituito = NS+"costituito";
	public static final String siIncontrano = NS+"siIncontrano";
	public static final String annette = NS+"annette";
	public static final String portaA = NS+"portaA";
	public static final String finisce = NS+"finisce";
	public static final String daAccesso = NS+"daAccesso";
	public static final String haAssociata = NS+"haAssociata";
	public static final String recaA = NS+"recaA";
	public static final String faParte = NS+"faParte";
	
}