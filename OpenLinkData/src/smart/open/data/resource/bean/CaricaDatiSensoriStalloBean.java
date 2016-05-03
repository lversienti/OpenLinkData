package smart.open.data.resource.bean;

import java.util.ArrayList;

public class CaricaDatiSensoriStalloBean {
	
	private String idApp;
	private ArrayList<DatiSensoriBean> datiSensoriList;
	
	
	
	public ArrayList<DatiSensoriBean> getDatiSensoriList() {
		return datiSensoriList;
	}
	public void setDatiSensoriList(ArrayList<DatiSensoriBean> datiSensoriList) {
		this.datiSensoriList = datiSensoriList;
	}
	public String getIdApp() {
		return idApp;
	}
	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}
	
	

}
