package com.webmodel;

public class MasterSpecialization {
	private String Id;
	private String SpecializationName;
	private String IsActive;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getSpecializationName() {
		return SpecializationName;
	}
	public void setSpecializationName(String specializationName) {
		SpecializationName = specializationName;
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
