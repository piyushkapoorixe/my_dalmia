package com.model;

public class PinModel {
	
	private String Id;
	private String PinCode;
	private String CityId;
	private String City;
	private String isactive;
	private String DistricId;
	private String Distric;
	public String getDistric() {
		return Distric;
	}
	public void setDistric(String distric) {
		Distric = distric;
	}
	private String StateId;
	
	
	public String getDistricId() {
		return DistricId;
	}
	public void setDistricId(String districId) {
		DistricId = districId;
	}
	public String getStateId() {
		return StateId;
	}
	public void setStateId(String stateId) {
		StateId = stateId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPinCode() {
		return PinCode;
	}
	public void setPinCode(String pinCode) {
		PinCode = pinCode;
	}
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

}
