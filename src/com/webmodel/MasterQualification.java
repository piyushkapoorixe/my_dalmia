package com.webmodel;

public class MasterQualification {
	private String Id;
	private String Qualification;
	private String IsActive;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getQualification() {
		return Qualification;
	}
	public void setQualification(String qualification) {
		Qualification = qualification;
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
