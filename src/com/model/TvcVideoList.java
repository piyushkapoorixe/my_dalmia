package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;
public class TvcVideoList extends CommonResponseModel {
	private ArrayList<TvcVideoModel> tvcVideos;

	public ArrayList<TvcVideoModel> getTvcVideos() {
		return tvcVideos;
	}

	public void setTvcVideos(ArrayList<TvcVideoModel> tvcVideos) {
		this.tvcVideos = tvcVideos;
	}

}
