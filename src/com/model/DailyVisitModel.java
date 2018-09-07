package com.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DailyVisitModel implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private int id;

	private int userId;

	private boolean isActive;

	private String address;

	private String appointment;

	private String associateEngg;

	private String associateDealer;

	private int districId;

	private String brandPreference;

	private List<CommentModel> comments;

	private String contactNo;

	private String convertedTo;

	private int dealerId;

	private String dob;

	private String dom;

	private String email;

	private String existingBrand;

	private String firmName;

	private String location;

	private String leadGiven;

	private boolean isMember;

	private String monthlyPotential;

	private String monthlyVolume;

	private String name;

	private int bags;

	private int dalmiaSites;

	private int dealers;

	private int enggs;

	private int floors;

	private int headMasons;

	private int manpowers;

	private int ongoingSites;

	private String ofcAddress;

	private String percentageCompletion;

	private String bagPrice;

	private String profileImg;
	
	private String img1;
	
	private String img2;

	private String qualification;

	private String reason;

	private String religion;

	private String remark;

	private String saleAchieved;

	private String salesOfficerName;

	private String salesTarget;

	private String squareFeet;

	private String constructionStage;

	private String status;

	private String type;

	private String brandType;

	private String contractType;

	private String productType;

	private String createdOn;

	private String updatedOn;

	private String visitType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAppointment() {
		return appointment;
	}

	public void setAppointment(String appointment) {
		this.appointment = appointment;
	}

	public String getAssociateEngg() {
		return associateEngg;
	}

	public void setAssociateEngg(String associateEngg) {
		this.associateEngg = associateEngg;
	}

	public String getAssociateDealer() {
		return associateDealer;
	}

	public void setAssociateDealer(String associateDealer) {
		this.associateDealer = associateDealer;
	}

	public int getDistricId() {
		return districId;
	}

	public void setDistricId(int districId) {
		this.districId = districId;
	}

	public String getBrandPreference() {
		return brandPreference;
	}

	public void setBrandPreference(String brandPreference) {
		this.brandPreference = brandPreference;
	}

	public List<CommentModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getConvertedTo() {
		return convertedTo;
	}

	public void setConvertedTo(String convertedTo) {
		this.convertedTo = convertedTo;
	}

	public int getDealerId() {
		return dealerId;
	}

	public void setDealerId(int dealerId) {
		this.dealerId = dealerId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDom() {
		return dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExistingBrand() {
		return existingBrand;
	}

	public void setExistingBrand(String existingBrand) {
		this.existingBrand = existingBrand;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLeadGiven() {
		return leadGiven;
	}

	public void setLeadGiven(String leadGiven) {
		this.leadGiven = leadGiven;
	}

	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	public String getMonthlyPotential() {
		return monthlyPotential;
	}

	public void setMonthlyPotential(String monthlyPotential) {
		this.monthlyPotential = monthlyPotential;
	}

	public String getMonthlyVolume() {
		return monthlyVolume;
	}

	public void setMonthlyVolume(String monthlyVolume) {
		this.monthlyVolume = monthlyVolume;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBags() {
		return bags;
	}

	public void setBags(int bags) {
		this.bags = bags;
	}

	public int getDalmiaSites() {
		return dalmiaSites;
	}

	public void setDalmiaSites(int dalmiaSites) {
		this.dalmiaSites = dalmiaSites;
	}

	public int getDealers() {
		return dealers;
	}

	public void setDealers(int dealers) {
		this.dealers = dealers;
	}

	public int getEnggs() {
		return enggs;
	}

	public void setEnggs(int enggs) {
		this.enggs = enggs;
	}

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public int getHeadMasons() {
		return headMasons;
	}

	public void setHeadMasons(int headMasons) {
		this.headMasons = headMasons;
	}

	public int getManpowers() {
		return manpowers;
	}

	public void setManpowers(int manpowers) {
		this.manpowers = manpowers;
	}

	public int getOngoingSites() {
		return ongoingSites;
	}

	public void setOngoingSites(int ongoingSites) {
		this.ongoingSites = ongoingSites;
	}

	public String getOfcAddress() {
		return ofcAddress;
	}

	public void setOfcAddress(String ofcAddress) {
		this.ofcAddress = ofcAddress;
	}

	public String getPercentageCompletion() {
		return percentageCompletion;
	}

	public void setPercentageCompletion(String percentageCompletion) {
		this.percentageCompletion = percentageCompletion;
	}

	public String getBagPrice() {
		return bagPrice;
	}

	public void setBagPrice(String bagPrice) {
		this.bagPrice = bagPrice;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSaleAchieved() {
		return saleAchieved;
	}

	public void setSaleAchieved(String saleAchieved) {
		this.saleAchieved = saleAchieved;
	}

	public String getSalesOfficerName() {
		return salesOfficerName;
	}

	public void setSalesOfficerName(String salesOfficerName) {
		this.salesOfficerName = salesOfficerName;
	}

	public String getSalesTarget() {
		return salesTarget;
	}

	public void setSalesTarget(String salesTarget) {
		this.salesTarget = salesTarget;
	}

	public String getSquareFeet() {
		return squareFeet;
	}

	public void setSquareFeet(String squareFeet) {
		this.squareFeet = squareFeet;
	}

	public String getConstructionStage() {
		return constructionStage;
	}

	public void setConstructionStage(String constructionStage) {
		this.constructionStage = constructionStage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrandType() {
		return brandType;
	}

	public void setBrandType(String brandType) {
		this.brandType = brandType;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	
	
}
