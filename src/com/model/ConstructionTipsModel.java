package com.model;

public class ConstructionTipsModel {
	
	private String Id;
	private String Heading;
	private String Tips;
	private String CreatedOn;
	public String getCreatedOn() {
		return CreatedOn;
	}
	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getHeading() {
		return Heading;
	}
	public void setHeading(String heading) {
		Heading = heading;
	}
	public String getTips() {
		return Tips;
	}
	public void setTips(String tips) {
		Tips = tips;
	}
}
