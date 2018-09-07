package com.webmodel;

public class MasterRegionModel {
	private String Id;
	private String Region;
	private String ContactNumber;
	private String stateName;
	private String IsActive;
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getContactNumber() {
		return ContactNumber;
	}
	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
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
