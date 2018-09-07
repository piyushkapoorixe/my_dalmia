package com.model;

import java.util.ArrayList;

import com.responsemodel.CommonResponseModel;
import com.webmodel.UsersProjectListItem;
import com.webmodel.UsersProjectModel;

public class UserDetailModel extends CommonResponseModel {
	private String Address;
    private String City;
    private String CityId;
    private String EmailId;
    private String ExperienceYears;
    private String FirmName;
    private String MobileNo;
    private String PinCode;
    private String Profession;
    private String ProfessionId;
    private String ProjectHandled;
    private String Qualification;
    private String QualificationId;
    private String Region;
    private String RegionId;
    private String State;
    private String StateId;
    private String CallTime;
    private String UserId;
    private String UserName;
    private String ActivationStatus;
    private String specializationString;
    private String ImageUrl;
    private String Description;
    private String DOB;
    private String ProfileVisitCount;
    
    private String PinCodeId;
    private String Distric;
    private String DistricId;
    private String Marrige_Anniversary;
    private String Websites;
    
    
    private String ActiveStatus;
    
    public String getActiveStatus() {
		return ActiveStatus;
	}

	public void setActiveStatus(String activeStatus) {
		ActiveStatus = activeStatus;
	}

	public String getMarrige_Anniversary() {
		return Marrige_Anniversary;
	}

	public void setMarrige_Anniversary(String marrige_Anniversary) {
		Marrige_Anniversary = marrige_Anniversary;
	}

	public String getWebsites() {
		return Websites;
	}

	public void setWebsites(String websites) {
		Websites = websites;
	}

	public String getPinCodeId() {
		return PinCodeId;
	}

	public void setPinCodeId(String pinCodeId) {
		PinCodeId = pinCodeId;
	}

	public String getDistric() {
		return Distric;
	}

	public void setDistric(String distric) {
		Distric = distric;
	}

	public String getDistricId() {
		return DistricId;
	}

	public void setDistricId(String districId) {
		DistricId = districId;
	}

	public String getProfileVisitCount() {
		return ProfileVisitCount;
	}

	public void setProfileVisitCount(String profileVisitCount) {
		ProfileVisitCount = profileVisitCount;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

	private ArrayList<UsersProjectListItem> projects;
    public String getActivationStatus() {
		return ActivationStatus;
	}

	public String getCallTime() {
		return CallTime;
	}

	public void setCallTime(String callTime) {
		CallTime = callTime;
	}

	public ArrayList<UsersProjectListItem> getProjects() {
		return projects;
	}

	public String getSpecialization() {
		return specializationString;
	}

	public void setSpecialization(String specialization) {
		this.specializationString = specialization;
	}

	public void setProjects(ArrayList<UsersProjectListItem> projects) {
		this.projects = projects;
	}

	public void setActivationStatus(String activationStatus) {
		ActivationStatus = activationStatus;
	}

	private String DateOfRegistration;
    public String getDateOfRegistration() {
		return DateOfRegistration;
	}

	public void setDateOfRegistration(String dateOfRegistration) {
		DateOfRegistration = dateOfRegistration;
	}

	
  

	private ArrayList<SpecializationModel> UsersSpeciliazation;

    public ArrayList<SpecializationModel> getUsersSpeciliazation() {
		return UsersSpeciliazation;
	}

	public void setUsersSpeciliazation(ArrayList<SpecializationModel> usersSpeciliazation) {
		UsersSpeciliazation = usersSpeciliazation;
	}

   

    public void setAddress(String address) {
        Address = address;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public void setExperienceYears(String experienceYears) {
        ExperienceYears = experienceYears;
    }

    public void setFirmName(String firmName) {
        FirmName = firmName;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public void setProfessionId(String professionId) {
        ProfessionId = professionId;
    }

    public void setProjectHandled(String projectHandled) {
        ProjectHandled = projectHandled;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public void setQualificationId(String qualificationId) {
        QualificationId = qualificationId;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public void setRegionId(String regionId) {
        RegionId = regionId;
    }

    public void setState(String state) {
        State = state;
    }

    public void setStateId(String stateId) {
        StateId = stateId;
    }


    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }



    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getCityId() {
        return CityId;
    }

    public String getEmailId() {
        return EmailId;
    }

    public String getExperienceYears() {
        return ExperienceYears;
    }

    public String getFirmName() {
        return FirmName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getPinCode() {
        return PinCode;
    }

    public String getProfession() {
        return Profession;
    }

    public String getProfessionId() {
        return ProfessionId;
    }

    public String getProjectHandled() {
        return ProjectHandled;
    }

    public String getQualification() {
        return Qualification;
    }

    public String getQualificationId() {
        return QualificationId;
    }

    public String getRegion() {
        return Region;
    }

    public String getRegionId() {
        return RegionId;
    }

    public String getState() {
        return State;
    }

    public String getStateId() {
        return StateId;
    }


    public String getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }


}
