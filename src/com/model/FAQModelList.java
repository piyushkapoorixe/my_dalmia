package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;

public class FAQModelList extends CommonResponseModel {
	
	private ArrayList<FAQModel> faqList;

	public ArrayList<FAQModel> getFaqList() {
		return faqList;
	}

	public void setFaqList(ArrayList<FAQModel> faqList) {
		this.faqList = faqList;
	}

}
