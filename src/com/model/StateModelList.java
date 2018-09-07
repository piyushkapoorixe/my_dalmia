package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class StateModelList extends CommonResponseModel{
	
	private ArrayList<StateModel> StateDetails;

	public ArrayList<StateModel> getStateDetails() {
		return StateDetails;
	}

	public void setStateDetails(ArrayList<StateModel> stateDetails) {
		StateDetails = stateDetails;
	}

}
