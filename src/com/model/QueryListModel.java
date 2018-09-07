package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class QueryListModel extends CommonResponseModel{
	  private ArrayList<QueryModel> QueryResDetails;

	public ArrayList<QueryModel> getQueryResDetails() {
		return QueryResDetails;
	}

	public void setQueryResDetails(ArrayList<QueryModel> queryResDetails) {
		QueryResDetails = queryResDetails;
	}

}
