package com.webmodel;

public class UserQueryFeedback {
	
	private String Uid;
	private String Name;
	private String Profession;
	private String Email;
	private String Mobile;
	private String Query;
	private String Comment;
	private String Created_Date;
	private String refId;
	private String State;
	private String City;
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
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	private String Type;
	public String getUid() {
		return Uid;
	}
	public void setUid(String uid) {
		Uid = uid;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getProfession() {
		return Profession;
	}
	public void setProfession(String profession) {
		Profession = profession;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getQuery() {
		return Query;
	}
	public void setQuery(String query) {
		Query = query;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getCreated_Date() {
		return Created_Date;
	}
	public void setCreated_Date(String created_Date) {
		Created_Date = created_Date;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		
		if(type.equals("Q"))
		{
			Type = "Query";
		}else
		{
			Type = "Feedback";	
		}
		
	}

	
	
}
