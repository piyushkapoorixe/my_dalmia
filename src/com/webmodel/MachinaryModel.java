package com.webmodel;

public class MachinaryModel {
	private String Id;
	private String MachinName;
	private String IsActive;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getMachinName() {
		return MachinName;
	}
	public void setMachinName(String machinName) {
		MachinName = machinName;
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
