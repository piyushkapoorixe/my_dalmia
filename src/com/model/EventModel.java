package com.model;

public class EventModel {
	private String Id;
	private String Name;
	private String Place;
	private String State;
	private String City;
	private String StateId;
	private String CityId;
	private String Image;
	private String Distric;
	private String DistricId;
	public String getDistricId() {
		return DistricId;
	}
	public void setDistricId(String districId) {
		DistricId = districId;
	}
	public String getDistric() {
		return Distric;
	}
	public void setDistric(String distric) {
		Distric = distric;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPlace() {
		return Place;
	}
	public void setPlace(String place) {
		Place = place;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getStateId() {
		return StateId;
	}
	public void setStateId(String stateId) {
		StateId = stateId;
	}
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getCreatedOn() {
		return CreatedOn;
	}
	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}
	private String Description;
	private String CreatedOn;
}
