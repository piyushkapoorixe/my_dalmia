package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

	Logger log = LoggerFactory.getLogger(getClass());
	public static void main(String[] args) {
		//String reqData = "{\"a\":\"2\",\"b\":\"5\"}";
		//String[] reqData = {"a","2","b","5"};
		//String reqData = "{\"userId\":\"testing\", \"isActive\":\"testing\", \"address\":\"testing\", \"appointment\":\"testing\", \"associateEngg\":\"testing\", \"associateDealer\":\"testing\", \"districtId\":\"testing\", \"brandPreference\":\"testing\", \"contactNo\":\"2435\", \"convertedTo\":\"fssfd\", \"dealerId\":\"testsdfing\", \"dob\":\"1995-06-25\", \"email\":\"m@tc.com\", \"existingBrand\":\"asdf\", \"frimName\":\"turningCloud\", \"location\":\"gurgaon\", \"leadGiven\":\"testing\", \"isMember\":true, \"monthlyPotential\":\"asfd\", \"monthlyVolume\":\"35\", \"name\":\"mak\", \"bags\":\"6\", \"dalmiaSites\":\"7\", \"dealers\":\"3\", \"enggs\":\"6\", \"floors\":\"12\", \"headMasons\":\"testing\", \"manpowers\":\"testing\", \"ongoingSites\":\"testing\", \"ofcAddress\":\"gurgaon sector 50\", \"percentageCompletion\":\"80\", \"bagPrice\":\"400\", \"profileImg\":\"localhost:8484/DalmiaProject/img/0.jpg\", \"qualification\":\"btech\", \"reason\":\"testing\", \"religion\":\"testing\", \"remark\":\"testing\", \"saleAchieved\":\"testing\", \"salesOfficerName\":\"testing\", \"salesTarget\":\"testing\", \"scquareFeet\":\"testing\", \"constructionStage\":\"testing\", \"status\":\"uncomplete\", \"type\":\"testing\", \"brandType\":\"testing\", \"contractType\":\"testing\", \"productType\":\"testing\", \"visitType\":\"dailyviist\", \"comments\":[{\"comment\":\"this is comment1\", \"url\":\"1\"},  	 {\"comment\":\"this is comment2\", \"url\":\"1\"},  	 {\"comment\":\"this is comment3\", \"url\":\"1\"},  	 {\"comment\":\"this is comment4\", \"url\":\"1\"},  	 {\"comment\":\"this is comment5\", \"url\":\"1\"},  	 {\"comment\":\"this is comment6\", \"url\":\"1\"}], \"tokenNo\":\"abcHkl7900@8Uyhkj\"}";
		//System.out.println("reqData ::::::::: "+reqData);
		//JsonObject reqObj = new Gson().fromJson(reqData, JsonObject.class);
		//String test = reqObj.get("tokenNo").getAsString();
		System.out.println("tst  :::::: ");
		new Test().testing();
	}
	private void testing() {
		log.info("this is logger ");
	}
}
