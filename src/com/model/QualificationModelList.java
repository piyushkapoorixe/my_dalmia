package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class QualificationModelList extends CommonResponseModel{
	
	private ArrayList<QualificationModel> QualificationDetails;

	public ArrayList<QualificationModel> getQualificationDetails() {
		return QualificationDetails;
	}

	public void setQualificationDetails(ArrayList<QualificationModel> qualificationDetails) {
		QualificationDetails = qualificationDetails;
	}
}
