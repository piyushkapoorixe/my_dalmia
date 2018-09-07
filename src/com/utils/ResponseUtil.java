package com.utils;

import java.io.PrintWriter;

import org.apache.poi.ss.formula.functions.T;

import com.google.gson.Gson;
import com.model.RestResponseModel;
import com.responsemodel.CommonResponseModel;

public class ResponseUtil {
	
	
	public static void setResponse(PrintWriter mWriter,Object obj)
	{
		Gson mGson=new Gson();
		mWriter.print(mGson.toJson(obj));
		mWriter.flush();
		mGson=null;
	}
	
	public static void  seterror(PrintWriter mWriter,String msg,String status) {
		Gson mGson=new Gson();
		CommonResponseModel model=new CommonResponseModel();
		model.setMessage(msg);
		model.setStatus(status);
		mWriter.print(mGson.toJson(model));
		mWriter.flush();
		mGson=null;
	}

	/*error response for rest api*/
	public static String setResponse(String msg, String status) {
		
		CommonResponseModel model=new CommonResponseModel();
		model.setMessage(msg);
		model.setStatus(status);
		return new Gson().toJson(model);
	}
	
	/*public static RestResponseModel<T> getResponseModel(Class<T> T){
		return new RestResponseModel<T>();
	}*/
	public static RestResponseModel getResponseModel(){
		return new RestResponseModel<>();
	}
}
