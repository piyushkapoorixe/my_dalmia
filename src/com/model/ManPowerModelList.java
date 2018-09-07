package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class ManPowerModelList extends CommonResponseModel{
	
	private ArrayList<ManPowerModel> ManpowerDetails;

	public ArrayList<ManPowerModel> getManpowerDetails() {
		return ManpowerDetails;
	}

	public void setManpowerDetails(ArrayList<ManPowerModel> manpowerDetails) {
		ManpowerDetails = manpowerDetails;
	}

}
