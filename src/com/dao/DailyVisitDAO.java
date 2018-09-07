package com.dao;

import java.util.List;

import javax.ejb.Local;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.model.ActivityModel;
import com.model.CSTModel;
import com.model.CommentModel;
import com.model.ComplaintModel;
import com.model.DailyVisitModel;
import com.model.TechExpMonVehicleModel;

@Local
public interface DailyVisitDAO {

	//public int insertDailyVisitData(Map<String, String> visitDataMap);

	//public int insertDailyVisitData(JsonObject reqObj);
	
	public int insertDailyVisitData(JsonNode reqObj);
	
	public List<DailyVisitModel> getDailyVisitData(int userId, String visitType);

	public int insertDailyVisitComment(JsonNode reqObj);

	public int insertComplaint(JsonNode reqObj);

	public Object getDailyVisitById(int visitId);

	public int insertCRMActivity(JsonNode jNode);

	public int insertDataInCST(JsonNode reqObj);

	public List<ComplaintModel> getComplaintList(int userId);

	public int insertDataInTechExpMonitoringVehicle(JsonNode reqObj);

	public List<CommentModel> getCommentList(int id, String commentType);

	public List<TechExpMonVehicleModel> getTechExpMonVehicleList(int userId);

	public List<CSTModel> getCSTList(int userId);

	public Object getCSTById(int cstId);

	public List<ActivityModel> getCRMActivity(int userId);

	public Object getCRMActivityById(int cstId);
}
