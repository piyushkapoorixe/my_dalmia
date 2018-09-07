package com.model;

public class TSOModel {
	private String Id;
	private String name;
	private String email;
	private String mobile;
	private String rh_id;
	private String rh_name;
	private String stateId;
	private String distric_id;
	private String isactive;
	private String state;
	private String distric;
	
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistric() {
		return distric;
	}
	public void setDistric(String distric) {
		this.distric = distric;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRh_id() {
		return rh_id;
	}
	public void setRh_id(String rh_id) {
		this.rh_id = rh_id;
	}
	public String getRh_name() {
		return rh_name;
	}
	public void setRh_name(String rh_name) {
		this.rh_name = rh_name;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getDistric_id() {
		return distric_id;
	}
	public void setDistric_id(String distric_id) {
		this.distric_id = distric_id;
	}
}
