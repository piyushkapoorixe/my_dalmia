package com.webmodel;

public class MasterQueryFeedback {
	private String Id;
	private String Query;
	private String Type;
	private String IsActive;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getQuery() {
		return Query;
	}
	public void setQuery(String query) {
		Query = query;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		if(type.equals("Q"))
		{
			Type = "Query";	
		}
		else
		{
			Type = "Feedback";	
		}
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
