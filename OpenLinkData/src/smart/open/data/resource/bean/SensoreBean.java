package smart.open.data.resource.bean;


/*
 * contiene le info che riguardano i sensori
 * Un sensore monitora piu' slot
 * questo metodo è chiamato all'init dell'applicazione, oppure quando una telecamera va giù es. si resetta
 */
public class SensoreBean {
	
	private String idLocalSensor;
	private String geoPosition;
	private String characteristic;
	private String idApp;
	

	public String getIdLocalSensor() {
		return idLocalSensor;
	}


	public void setIdLocalSensor(String idLocalSensor) {
		this.idLocalSensor = idLocalSensor;
	}


	public String getGeoPosition() {
		return geoPosition;
	}


	public void setGeoPosition(String geoPosition) {
		this.geoPosition = geoPosition;
	}


	public String getCharacteristic() {
		return characteristic;
	}


	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}


	public String getIdApp() {
		return idApp;
	}


	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}

}