package com.model;

import com.responsemodel.CommonResponseModel;

/**
 * Created by Netcomm on 4/26/2017.
 */

public class ProfessionDetailItemModel{
    private String ProfessionId;
    private String ProfessionType;

    public void setProfessionId(String professionId) {
        ProfessionId = professionId;
    }

    public void setProfessionType(String professionType) {
        ProfessionType = professionType;
    }

    public String getProfessionId() {
        return ProfessionId;
    }

    public String getProfessionType() {
        return ProfessionType;
    }
}
