package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class LandMarkModelList extends CommonResponseModel{
	
	private ArrayList<LandMarkModel> listLandMarkProjects;

	public ArrayList<LandMarkModel> getListLandMarkProjects() {
		return listLandMarkProjects;
	}

	public void setListLandMarkProjects(ArrayList<LandMarkModel> listLandMarkProjects) {
		this.listLandMarkProjects = listLandMarkProjects;
	}

}
