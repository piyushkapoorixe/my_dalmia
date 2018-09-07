package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class PinModelLIst extends CommonResponseModel {
	
	private ArrayList<PinModel> listPin;

	public ArrayList<PinModel> getListPin() {
		return listPin;
	}

	public void setListPin(ArrayList<PinModel> listPin) {
		this.listPin = listPin;
	}
	
	

}
