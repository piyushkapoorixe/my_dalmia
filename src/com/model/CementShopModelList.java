package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class CementShopModelList extends CommonResponseModel{
	
	private ArrayList<CementShopModel> shopList=null;

	public ArrayList<CementShopModel> getShopList() {
		return shopList;
	}

	public void setShopList(ArrayList<CementShopModel> shopList) {
		this.shopList = shopList;
	}

}
