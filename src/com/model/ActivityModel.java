package com.model;

import java.io.Serializable;
import java.util.List;

public class ActivityModel implements Serializable{

	private int activityId;

	private int userId;

	private String meetingType;

	private String activityDate;

	private String activityTyp;

	private String dealerCode;

	private String dealerName;

	private String districId;

	private String venue;

	private String inviteesNo;

	private String attendeesNo;

	private String giftDistributionNo;

	private String giftType;

	private String expenseByEmp;

	private String expenseByPart;

	private boolean isMtgInBdg;

	private boolean isActive;

	private String reason;

	private String vendorCode;

	private String imgUrl;

	private String location;
	
	private List<CommentModel> comments;

	private String createdOn;

	private String updatedOn;

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getActivityTyp() {
		return activityTyp;
	}

	public void setActivityTyp(String activityTyp) {
		this.activityTyp = activityTyp;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDistricId() {
		return districId;
	}

	public void setDistricId(String districId) {
		this.districId = districId;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getInviteesNo() {
		return inviteesNo;
	}

	public void setInviteesNo(String inviteesNo) {
		this.inviteesNo = inviteesNo;
	}

	public String getAttendeesNo() {
		return attendeesNo;
	}

	public void setAttendeesNo(String attendeesNo) {
		this.attendeesNo = attendeesNo;
	}

	public String getGiftDistributionNo() {
		return giftDistributionNo;
	}

	public void setGiftDistributionNo(String giftDistributionNo) {
		this.giftDistributionNo = giftDistributionNo;
	}

	public String getGiftType() {
		return giftType;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public String getExpenseByEmp() {
		return expenseByEmp;
	}

	public void setExpenseByEmp(String expenseByEmp) {
		this.expenseByEmp = expenseByEmp;
	}

	public String getExpenseByPart() {
		return expenseByPart;
	}

	public void setExpenseByPart(String expenseByPart) {
		this.expenseByPart = expenseByPart;
	}

	public boolean isMtgInBdg() {
		return isMtgInBdg;
	}

	public void setMtgInBdg(boolean isMtgInBdg) {
		this.isMtgInBdg = isMtgInBdg;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<CommentModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
	
	
}
