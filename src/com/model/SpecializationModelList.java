package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class SpecializationModelList extends CommonResponseModel {
	private ArrayList<SpecializationModel> SpecializationDetails;

	public ArrayList<SpecializationModel> getSpecializationDetails() {
		return SpecializationDetails;
	}

	public void setSpecializationDetails(ArrayList<SpecializationModel> specializationDetails) {
		SpecializationDetails = specializationDetails;
	}
}
