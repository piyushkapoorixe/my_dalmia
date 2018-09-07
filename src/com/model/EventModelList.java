package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class EventModelList extends CommonResponseModel{

	private ArrayList<EventModel> listEvent;

	public ArrayList<EventModel> getListEvent() {
		return listEvent;
	}

	public void setListEvent(ArrayList<EventModel> listEvent) {
		this.listEvent = listEvent;
	} 
	
}
