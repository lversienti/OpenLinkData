package smart.open.data.resource.bean;

import java.util.ArrayList;

/*
 * sono i dati che legano un sensore a più stalli, in quanto un sensore monitora più stalli
 */

public class SensoreStalliBean {
	private String idApp;
	private String idLocalSensor;
	private ArrayList<Integer> stalliList;
	
	
	public String getIdApp() {
		return idApp;
	}
	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}
	
	public String getIdLocalSensor() {
		return idLocalSensor;
	}
	public void setIdLocalSensor(String idLocalSensor) {
		this.idLocalSensor = idLocalSensor;
	}
	public ArrayList<Integer> getStalliList() {
		return stalliList;
	}
	public void setStalliList(ArrayList<Integer> stalliList) {
		this.stalliList = stalliList;
	}
	

}
