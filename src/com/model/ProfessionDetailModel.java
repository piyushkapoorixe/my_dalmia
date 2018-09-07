package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

/**
 * Created by Netcomm on 4/26/2017.
 */

public class ProfessionDetailModel extends CommonResponseModel {
    private ArrayList<ProfessionDetailItemModel> ProfessionDetails;

    public ArrayList<ProfessionDetailItemModel> getProfessionDetails() {
        return ProfessionDetails;
    }

	public void setProfessionDetails(ArrayList<ProfessionDetailItemModel> professionDetails) {
		ProfessionDetails = professionDetails;
	}
}
