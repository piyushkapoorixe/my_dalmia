package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class DistricModelList extends CommonResponseModel {
	private ArrayList<DistricModel> districDetails;

	public ArrayList<DistricModel> getDistricDetails() {
		return districDetails;
	}

	public void setDistricDetails(ArrayList<DistricModel> districDetails) {
		this.districDetails = districDetails;
	}
}
