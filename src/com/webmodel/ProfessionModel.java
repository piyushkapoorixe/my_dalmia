package com.webmodel;

public class ProfessionModel {
	private String Id;
	private String ProfessionName;
	private String IsActive;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getProfessionName() {
		return ProfessionName;
	}
	public void setProfessionName(String professionName) {
		ProfessionName = professionName;
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
