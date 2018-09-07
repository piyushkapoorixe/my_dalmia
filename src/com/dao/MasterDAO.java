package com.dao;

import java.util.List;

import javax.ejb.Local;

import com.model.BrandModel;
import com.model.CityModel;
import com.model.DistricModel;
import com.model.EventModel;
import com.model.MachinaryModel;
import com.model.ManPowerModel;
import com.model.MeetingModel;
import com.model.ProductModel;
import com.model.QualificationModel;
import com.model.RegionModel;
import com.model.StateModel;

@Local
public interface MasterDAO {

	public List<StateModel> getStateList();
	
	public List<DistricModel> getDistricList(int stateId);
	
	public List<CityModel> getCityList(int districId);
	
	public List<QualificationModel> getQualificationList();
	
	public List<RegionModel> getRegionList();
	
	public List<MachinaryModel> getMachinaryList();
	
	public List<ManPowerModel> getManPowerList();
	
	public List<EventModel> getEventList();
	
	public List<BrandModel> getBrandList();
	
	public List<ProductModel> getProductList();
	
	public List<MeetingModel> getMeetingList();
	
	
}
