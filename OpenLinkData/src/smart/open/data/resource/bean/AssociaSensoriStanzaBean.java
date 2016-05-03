package smart.open.data.resource.bean;

import java.util.ArrayList;

public class AssociaSensoriStanzaBean {
	
	private String idApp;
	private String idStanza;
	private ArrayList<String> sensoriList;
	
	
	public String getIdApp() {
		return idApp;
	}
	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}
	public String getIdStanza() {
		return idStanza;
	}
	public void setIdStanza(String idStanza) {
		this.idStanza = idStanza;
	}
	public ArrayList<String> getSensoriList() {
		return sensoriList;
	}
	public void setSensoriList(ArrayList<String> sensoriList) {
		this.sensoriList = sensoriList;
	}
	
	


}
