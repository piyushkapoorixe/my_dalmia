package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class RegionalBannerModelList  extends CommonResponseModel{
	
	private ArrayList<RegionalBannerModel> BannerList;

	public ArrayList<RegionalBannerModel> getBannerList() {
		return BannerList;
	}

	public void setBannerList(ArrayList<RegionalBannerModel> bannerList) {
		BannerList = bannerList;
	}

}
