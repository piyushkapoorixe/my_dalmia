package com.model;

public class CallHistoryModel {
	private String Caller;
	private String Id;
	private String CallerAddress;
	private String Called;
	private String CalledTime;
	public String getCaller() {
		return Caller;
	}
	public void setCaller(String caller) {
		Caller = caller;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getCallerAddress() {
		return CallerAddress;
	}
	public void setCallerAddress(String callerAddress) {
		CallerAddress = callerAddress;
	}
	public String getCalled() {
		return Called;
	}
	public void setCalled(String called) {
		Called = called;
	}
	public String getCalledTime() {
		return CalledTime;
	}
	public void setCalledTime(String calledTime) {
		CalledTime = calledTime;
	}
	

}
