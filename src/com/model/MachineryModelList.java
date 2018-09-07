package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class MachineryModelList extends CommonResponseModel {
	
	private ArrayList<MachinaryModel> MachineriesDetails;

	public ArrayList<MachinaryModel> getMachineriesDetails() {
		return MachineriesDetails;
	}

	public void setMachineriesDetails(ArrayList<MachinaryModel> machineriesDetails) {
		MachineriesDetails = machineriesDetails;
	}

}
