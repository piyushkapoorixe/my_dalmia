package com.webmodel;

import java.util.ArrayList;

import com.model.ManPowerModelList;


public class UsersProjectListItem {
	
	private String ProjectName;
	private String ProjectId;
	private String ProjectPrice;
	private String ProjectLocation;
	private String Area;
	private String Is_Active;
	
	private String StateId;
	private String CityId;
	
	private String StateName;
	private String CityName;
	
	private String Distric;
	private String DistricId;
	public String getDistric() {
		return Distric;
	}
	public void setDistric(String distric) {
		Distric = distric;
	}
	public String getDistricId() {
		return DistricId;
	}
	public void setDistricId(String districId) {
		DistricId = districId;
	}
	public String getStateName() {
		return StateName;
	}
	public void setStateName(String stateName) {
		StateName = stateName;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getStateId() {
		return StateId;
	}
	public void setStateId(String stateId) {
		StateId = stateId;
	}
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getIs_Active() {
		return Is_Active;
	}
	public void setIs_Active(String is_Active) {
		Is_Active = is_Active;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	private ArrayList<com.model.MachinaryModel> machinary;
	private ArrayList<com.model.ManPowerModel> manPower;
	private ArrayList<String> imageData;
	public ArrayList<String> getImageData() {
		return imageData;
	}
	public void setImageData(ArrayList<String> imageData) {
		this.imageData = imageData;
	}
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public String getProjectId() {
		return ProjectId;
	}
	public void setProjectId(String projectId) {
		ProjectId = projectId;
	}
	public String getProjectPrice() {
		return ProjectPrice;
	}
	public void setProjectPrice(String projectPrice) {
		ProjectPrice = projectPrice;
	}
	public String getProjectLocation() {
		return ProjectLocation;
	}
	public void setProjectLocation(String projectLocation) {
		ProjectLocation = projectLocation;
	}
	public ArrayList<com.model.MachinaryModel> getMachinary() {
		return machinary;
	}
	public void setMachinary(ArrayList<com.model.MachinaryModel> machinary) {
		this.machinary = machinary;
	}
	public ArrayList<com.model.ManPowerModel> getManPower() {
		return manPower;
	}
	public void setManPower(ArrayList<com.model.ManPowerModel> manPower) {
		this.manPower = manPower;
	}



	
	

}
