package com.webmodel;

public class MasterCity {
	private String CityId;
	private String CityName;
	private String DistricId;
	private String DistricName;
	private String IsActive;
	private String StateName;
	private String StateId;
	public String getStateId() {
		return StateId;
	}
	public void setStateId(String stateId) {
		StateId = stateId;
	}
	public String getStateName() {
		return StateName;
	}
	public void setStateName(String stateName) {
		StateName = stateName;
	}
	public String getDistricId() {
		return DistricId;
	}
	public void setDistricId(String districId) {
		DistricId = districId;
	}
	public String getDistricName() {
		return DistricName;
	}
	public void setDistricName(String districName) {
		DistricName = districName;
	}
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		if(isActive.equals("0"))
		{
			IsActive = "Inactive";	
		}
		else
		{
			IsActive = "Active";	
		}
		
	}
	
}
