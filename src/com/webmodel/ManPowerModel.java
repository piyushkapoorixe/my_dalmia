package com.webmodel;

public class ManPowerModel {
	private String Id;
	private String ManpowerName;
	private String IsActive;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getManpowerName() {
		return ManpowerName;
	}
	public void setManpowerName(String manpowerName) {
		ManpowerName = manpowerName;
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
