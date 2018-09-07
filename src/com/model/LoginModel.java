package com.model;

import com.responsemodel.CommonResponseModel;

public class LoginModel extends CommonResponseModel{
	private String LastLoginTime;
	private String Name;
	private String Profession;
	private String UID;
	private String email;
	private String mobileNo;
	private String OTP;
	private String ProfessionId;
	private String RegionId;
	private String DistricId=null;
	private String StateId=null;
    private String ImageUrl;
	
    
    
	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	public String getStateId() {
		return StateId;
	}

	public void setStateId(String stateId) {
		StateId = stateId;
	}

	private String RoleId=null;
	
	public String getRoleId() {
		return RoleId;
	}

	public void setRoleId(String roleId) {
		RoleId = roleId;
	}

	public String getDistricId() {
		return DistricId;
	}

	public void setDistricId(String districId) {
		DistricId = districId;
	}

	public String getRegionId() {
		return RegionId;
	}

	public void setRegionId(String regionId) {
		RegionId = regionId;
	}

	public String getProfessionId() {
		return ProfessionId;
	}

	public void setProfessionId(String professionId) {
		ProfessionId = professionId;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public String getLastLoginTime() {
		return LastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		LastLoginTime = lastLoginTime;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getProfession() {
		return Profession;
	}

	public void setProfession(String profession) {
		Profession = profession;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String UID) {
		this.UID = UID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



}
