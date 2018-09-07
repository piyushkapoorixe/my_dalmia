package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class ConstructionTipsModelList extends CommonResponseModel {
	
	
	
	private ArrayList<ConstructionTipsModel> listConst;

	public ArrayList<ConstructionTipsModel> getListConst() {
		return listConst;
	}

	public void setListConst(ArrayList<ConstructionTipsModel> listConst) {
		this.listConst = listConst;
	}
 
}
