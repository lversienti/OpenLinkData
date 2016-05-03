package smart.open.data.resource.bean;

import java.util.ArrayList;

public class CaricaDatiBuildingBean {
	
	String idApp;
	String idSensore;
	private ArrayList<DatiSensoriBuildingBean> datiSensoriList;
	
	public String getIdApp() {
		return idApp;
	}
	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}
	public String getIdSensore() {
		return idSensore;
	}
	public void setIdSensore(String idSensore) {
		this.idSensore = idSensore;
	}
	public ArrayList<DatiSensoriBuildingBean> getDatiSensoriList() {
		return datiSensoriList;
	}
	public void setDatiSensoriList(
			ArrayList<DatiSensoriBuildingBean> datiSensoriList) {
		this.datiSensoriList = datiSensoriList;
	}
	
	

}
