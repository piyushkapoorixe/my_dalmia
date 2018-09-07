package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class CiityModelList extends CommonResponseModel {
	private ArrayList<CityModel> CityDetails;

	public ArrayList<CityModel> getCityDetails() {
		return CityDetails;
	}

	public void setCityDetails(ArrayList<CityModel> cityDetails) {
		CityDetails = cityDetails;
	}

	
}
