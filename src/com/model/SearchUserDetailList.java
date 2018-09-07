package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class SearchUserDetailList extends CommonResponseModel {
	
	private ArrayList<UserDetailModel> usersResult;

	public ArrayList<UserDetailModel> getUsersResult() {
		return usersResult;
	}

	public void setUsersResult(ArrayList<UserDetailModel> usersResult) {
		this.usersResult = usersResult;
	}

}
