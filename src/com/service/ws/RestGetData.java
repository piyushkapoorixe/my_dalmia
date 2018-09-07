package com.service.ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.constants.MessageConstants;
import com.dao.DailyVisitDAO;
import com.dao.MasterDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.model.ActivityModel;
import com.model.BrandModel;
import com.model.CSTModel;
import com.model.CityModel;
import com.model.CommentModel;
import com.model.ComplaintModel;
import com.model.DailyVisitModel;
import com.model.DistricModel;
import com.model.RegionModel;
import com.model.RestResponseModel;
import com.model.StateModel;
import com.model.TechExpMonVehicleModel;
import com.utils.ResponseUtil;
import com.utils.WebProjectLIstUtils;
import com.webmodel.AdminListModel;

@Path("/data")
public class RestGetData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	DailyVisitDAO visitDAO;

	@Inject
	MasterDAO master;

	@POST
	@Path("/test")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTestDetails(String reqData, @QueryParam("TokenNo") String tokenNo ) {
		log.debug("\n:::::::::::::reqData :::::::::::::::\n\n\n "
	+reqData+"\n\n\n::::::::::token No:::::::\n\n\n"
	+tokenNo);
		JsonNode reqNode = null;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("userId"))
			return Response.ok(ResponseUtil.setResponse(reqNode.get("userId").asText()+"\n tokenNo "+tokenNo, MessageConstants.StatusSuccess),
					MediaType.APPLICATION_JSON).build();
		else
			return Response.ok(ResponseUtil.setResponse("some error occurred", MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Path("/get/dailyvisits")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetDailyVisitData(String reqData) {
		log.debug("callgetDailyVisitData");
		JsonNode reqNode = null;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		int userId = 0;
		String visitType = null;
		
		if (reqNode.has("userId"))
			userId = reqNode.get("userId").asInt();
		
		if (reqNode.has("visitType"))
			visitType = reqNode.get("visitType").asText();
		
		if(visitType == null ) {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidVisitType);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			
			List<DailyVisitModel> respDataList = visitDAO.getDailyVisitData(userId, visitType);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();
	
	}

	@POST
	@Path("/get/dailyvisitbyid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetVisitById(String reqData) {
		// RestResponseModel<StateModel> model = new RestResponseModel<>();
		JsonNode reqNode = null;
		int visitId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			if (reqNode.has("visitId"))
				visitId = reqNode.get("visitId").asInt();
			List<Object> respDataList = new ArrayList<>();

			Object respObj = visitDAO.getDailyVisitById(visitId);
			if (respObj instanceof DailyVisitModel) {
				respDataList.add(respObj);
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);

			} else {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			}
			model.setRecords(respDataList.size());
			model.setRespData(respDataList);
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/get/state")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllState(String reqData) {
		// RestResponseModel<StateModel> model = new RestResponseModel<>();
		JsonNode reqNode = null;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<StateModel> respDataList = master.getStateList();
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/get/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllComments(String reqData) {
		JsonNode reqNode = null;
		int id = 0;
		String commentType = null;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		
		if (reqNode.has("id"))
			id = reqNode.get("id").asInt();
		
		if (reqNode.has("commentType"))
			commentType = reqNode.get("commentType").asText();

		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<CommentModel> respDataList = visitDAO.getCommentList(id,commentType);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/get/distric")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllDistrict(String reqData) {
		JsonNode reqNode = null;
		int stateId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("stateId"))
			stateId = reqNode.get("stateId").asInt();

		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<DistricModel> respDataList = master.getDistricList(stateId);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/get/city")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllCity(String reqData) {
		// List<>dailyVisit.getAllState();
		JsonNode reqNode = null;
		int districId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("districId"))
			districId = reqNode.get("districId").asInt();
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<CityModel> respDataList = master.getCityList(districId);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Path("/get/region")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllRegion(String reqData) {
		JsonNode reqNode = null;
		// int districId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		/*
		 * if(reqNode.has("districId")) districId = reqNode.get("districId").asInt();
		 */
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<RegionModel> respDataList = master.getRegionList();
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Path("/get/brands")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllBrands(String reqData) {
		JsonNode reqNode = null;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		/*
		 * if(reqNode.has("districId")) districId = reqNode.get("districId").asInt();
		 */
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<BrandModel> respDataList = master.getBrandList();
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}

	/*@POST
	@Path("/get/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllMeetingTypes() {
		// List<>dailyVisit.getAllState();
		return Response.ok(ResponseUtil.setResponse("testing rest", MessageConstants.StatusSuccess),
				MediaType.APPLICATION_JSON).build();
	}*/

	@POST
	@Path("/get/dealers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllDealers(String reqData) {
		JsonNode reqNode = null;
		int regionId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("districId"))
			regionId = reqNode.get("regionId").asInt();
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			// TODO temp method
			List<CityModel> respDataList = master.getCityList(regionId);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}

	@POST
	@Path("/get/products")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetAllProducts() {
		// List<>dailyVisit.getAllState();
		return Response.ok(ResponseUtil.setResponse("testing rest", MessageConstants.StatusSuccess),
				MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/get/tsologin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callTSOLogin(String reqData) {
		JsonNode reqNode = null;
		// int regionId = 0;
		String userId = null;
		String pass = null;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}

		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			if (reqNode.has("MobileNo"))
				userId = reqNode.get("MobileNo").asText();
			if (reqNode.has("Password"))
				pass = reqNode.get("Password").asText();
			List<Object> respDataList = new ArrayList<>();
			Object respObj = WebProjectLIstUtils.validateUser(userId, pass);
			if (respObj instanceof AdminListModel) {
				if (MessageConstants.TSO.equalsIgnoreCase(((AdminListModel) respObj).getRole()))
					respDataList.add(respObj);
				else
					respDataList.add(MessageConstants.invalidTSO);
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			} else {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} 
		}
		else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}
	
	@POST
	@Path("/get/complaint")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetComplaintData(String reqData) {
		log.debug("callGetComplaintData");
		JsonNode reqNode = null;
		int userId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("userId"))
			userId = reqNode.get("userId").asInt();

		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<ComplaintModel> respDataList = visitDAO.getComplaintList(userId);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}
	
	@POST
	@Path("/get/techexpmonvehicle")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetTechExpertMoniVehicle(String reqData) {
		log.debug("callGetTechExpertMoniVehicle");
		JsonNode reqNode = null;
		int userId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("userId"))
			userId = reqNode.get("userId").asInt();

		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<TechExpMonVehicleModel> respDataList = visitDAO.getTechExpMonVehicleList(userId);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}
	
	@POST
	@Path("/get/cst")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetCST(String reqData) {
		log.debug("callGetTechExpertMoniVehicle");
		JsonNode reqNode = null;
		int userId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("userId"))
			userId = reqNode.get("userId").asInt();

		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<CSTModel> respDataList = visitDAO.getCSTList(userId);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}
	
	@POST
	@Path("/get/cstbyid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetCSTById(String reqData) {
		// RestResponseModel<StateModel> model = new RestResponseModel<>();
		JsonNode reqNode = null;
		int cstId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			if (reqNode.has("cstId"))
				cstId = reqNode.get("cstId").asInt();
			
			List<Object> respDataList = new ArrayList<>();

			Object respObj = visitDAO.getCSTById(cstId);
			
			if (respObj instanceof CSTModel) {
				respDataList.add(respObj);
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);

			} else {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			}
			model.setRecords(respDataList.size());
			model.setRespData(respDataList);
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/get/crmactivity")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetCRMActivity(String reqData) {
		log.debug("callGetTechExpertMoniVehicle");
		JsonNode reqNode = null;
		int userId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("userId"))
			userId = reqNode.get("userId").asInt();

		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			List<ActivityModel> respDataList = visitDAO.getCRMActivity(userId);
			if (respDataList == null) {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			} else {
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);
				model.setRecords(respDataList.size());
				model.setRespData(respDataList);
			}
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();

	}
	
	@POST
	@Path("/get/crmactivitybyid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callGetCRMActivityById(String reqData) {
		// RestResponseModel<StateModel> model = new RestResponseModel<>();
		JsonNode reqNode = null;
		int cstId = 0;
		RestResponseModel model = ResponseUtil.getResponseModel();
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (Exception ex) {
			ex.printStackTrace();
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.ErrorMessage);
			return Response.ok(new Gson().toJson(model)).build();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			if (reqNode.has("cstId"))
				cstId = reqNode.get("cstId").asInt();
			
			List<Object> respDataList = new ArrayList<>();

			Object respObj = visitDAO.getCRMActivityById(cstId);
			
			if (respObj instanceof ActivityModel) {
				respDataList.add(respObj);
				model.setStatus(MessageConstants.StatusSuccess);
				model.setMessage(MessageConstants.SuccessMessage);

			} else {
				model.setStatus(MessageConstants.SuccessMessage);
				model.setMessage(MessageConstants.ErrorNoData);
			}
			model.setRecords(respDataList.size());
			model.setRespData(respDataList);
		}else {
			model.setStatus(MessageConstants.StatusFail);
			model.setMessage(MessageConstants.InvalidToken);
		}
		return Response.ok(new Gson().toJson(model), MediaType.APPLICATION_JSON).build();
	}
	
}
