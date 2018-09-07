package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class RegionModelList extends CommonResponseModel{
	private ArrayList<RegionModel> RegionDetails;

	public ArrayList<RegionModel> getRegionDetails() {
		return RegionDetails;
	}

	public void setRegionDetails(ArrayList<RegionModel> regionDetails) {
		RegionDetails = regionDetails;
	}
}
