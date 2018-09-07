package com.model;


public class RegionalBannerModel {
	
	private String Id;
	private String ImageUrl;
	private String Title;
	private String RegionId;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getRegionId() {
		return RegionId;
	}
	public void setRegionId(String regionId) {
		RegionId = regionId;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	

}
