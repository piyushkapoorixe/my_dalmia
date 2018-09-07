package com.model;

import java.io.Serializable;
import java.util.List;

public class RestResponseModel<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int records;
	
	private String caller;
	
	private String status;
	
	private String message;
	
	private List<T> respData;

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getRespData() {
		return respData;
	}

	public void setRespData(List<T> respData) {
		this.respData = respData;
	}

	
	
	
	
}
