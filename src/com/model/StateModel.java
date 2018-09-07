package com.model;

public class StateModel {
	
	private String StateId;
	private String StateName;
	private String RegionId;
	private String RegionName;
	public String getRegionId() {
		return RegionId;
	}
	public void setRegionId(String regionId) {
		RegionId = regionId;
	}
	public String getRegionName() {
		return RegionName;
	}
	public void setRegionName(String regionName) {
		RegionName = regionName;
	}
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

}
