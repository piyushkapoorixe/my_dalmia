package com.model;

import java.io.Serializable;

public class CommentModel implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int commentId;
	 
	private int visitId;
	
	private int userId;
	
	private int activityId;
	
	private int cstId;
	
	private int expertServiceId;
	
	private int isVerId;

	private String comment;
	
	private String commentTitle;
	
	private String imgUrl;
	
	private String commentType;

	private boolean isActive;

	private String createdOn;

	private String updatedOn;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getVisitId() {
		return visitId;
	}

	public void setVisitId(int visitId) {
		this.visitId = visitId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getCstId() {
		return cstId;
	}

	public void setCstId(int cstId) {
		this.cstId = cstId;
	}

	public int getExpertServiceId() {
		return expertServiceId;
	}

	public void setExpertServiceId(int expertServiceId) {
		this.expertServiceId = expertServiceId;
	}

	public int getIsVerId() {
		return isVerId;
	}

	public void setIsVerId(int isVerId) {
		this.isVerId = isVerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentTitle() {
		return commentTitle;
	}

	public void setCommentTitle(String commentTitle) {
		this.commentTitle = commentTitle;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
