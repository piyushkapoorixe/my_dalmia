package com.model;

import com.responsemodel.CommonResponseModel;

public class TollFreeModel extends CommonResponseModel{
	private String Region;
	private String TollFreeNo;
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getTollFreeNo() {
		return TollFreeNo;
	}
	public void setTollFreeNo(String tollFreeNo) {
		TollFreeNo = tollFreeNo;
	}
}
