package com.utils;

import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
public class GeneralRequestFormat {
	public static JSONObject getParam(HttpServletRequest request, HttpServletResponse response,String url) {
		String param=null;
		try{
			StringBuilder sb = new StringBuilder();
			BufferedReader br = request.getReader();
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			param=sb.toString();
			JSONObject obj=new JSONObject(param);
			return obj;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
