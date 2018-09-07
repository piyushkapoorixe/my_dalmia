package com.service.ws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.XmlCursor.TokenType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.constants.MessageConstants;
import com.dao.DailyVisitDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.utils.ResponseUtil;

@Path("/create")
public class RestCreateData  {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DailyVisitDAO visitDAO;

	public Response emptyResponse() {
		return Response.ok("{}", MediaType.APPLICATION_JSON).build();
	}

	public Response errorResponse() {
		return Response.ok("{}", MediaType.APPLICATION_JSON).build();
	}
	/*
	RestCreateData(){
		super();
	}*/

	@SuppressWarnings("unused")
	@POST
	@Path("/test")
	//@Consumes({MediaType.MULTIPART_FORM_DATA})
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//public Response callTest(@FormParam("imgData")InputStream imgData, @FormParam("formData")String reqData) {
	public Response callTest(MultipartFormDataInput data, @QueryParam("TokenNo") String tokenNo) throws IOException {
	
		//log.debug("reqData ::::::::: " + reqData);
		//log.debug("imgData ::::::::: " + imgData);

	//	JsonObject reqObj = new Gson().fromJson(reqData, JsonObject.class);
		String reqData = "{}";
		 
		Map<String, List<InputPart>> dataMap = data.getFormDataMap();
		
		List<InputPart> img = dataMap.get("imgData");
		
		log.debug("list size::::::"+img.size());
		log.debug("map size::::::"+dataMap.size());
		log.debug("Token no :::::::::: "+tokenNo);

		String fileName = null;
		
		List<InputPart> formDataPart = dataMap.get("formData");
		
		for(InputPart part : formDataPart) {
			log.debug("formData:::::::::::: "+part.getBodyAsString());
			log.debug("data header :::: " +new Gson().toJson(part.getHeaders()));
			//log.debug("data body :::::::::: "+ new Gson().toJson(part.getBody(Map.class, null)));
		}
		//log.debug("::::::::::dataMap:::::::\n"+data.getPreamble());
		for(InputPart inputPart : img) {
			try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				
				fileName = "/home/mak/Documents/turningcloud/testing/"+getFileName(header);
				
				log.debug("fileName :::::: "+fileName );
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				byte[] bytes = IOUtils.toByteArray(inputStream);
				
				//writeFile(bytes,fileName);
				
				
			}catch (Exception ex) {
				ex.printStackTrace();
				return errorResponse();
			}
		}
		
		
		return Response
				.ok(ResponseUtil.setResponse("reqData " , MessageConstants.StatusSuccess),
						MediaType.APPLICATION_JSON)
				.build();
	}
	
	private String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for(String fileName : contentDisposition) {
			if(fileName.trim().startsWith("filename")) {
				
				String[] name = fileName.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
	
	private void writeFile(byte[] content, String fileName) {
		File file = new File(fileName);
			try {
				if(!file.exists()) {

				file.createNewFile();
				}
				FileOutputStream fop = new FileOutputStream(file);
				
				fop.write(content);
				fop.flush();
				fop.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	private String convertInputPart2Str(List<InputPart> inputPartList) {
		String str = null;
		/*for(InputPart part : InputPartList){
			part.getBodyAsString();
		}*/
		try {
			return inputPartList.get(0).getBodyAsString();
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private String uploadImageData(List<InputPart> inputList) {
		String fileName = null;
		try {
			 for(InputPart inputPart : inputList) {
				 MultivaluedMap<String, String> header = inputPart.getHeaders();
				 
				 fileName = "/home/mak/Documents/turningcloud/testing/"+getFileName(header);
					
					log.debug("fileName :::::: "+fileName );
					InputStream inputStream = inputPart.getBody(InputStream.class, null);
					byte[] bytes = IOUtils.toByteArray(inputStream);
					
					//writeFile(bytes,fileName);
					
			 }
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return fileName;
	}
	
	@POST
	@Path("/dailyvisit")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response callInsertDataDailyVist(MultipartFormDataInput mData, @QueryParam("TokenNo")String tokenNo) {
		log.debug("callInsertDataDailyVist");
		Map<String,List<InputPart>> dataMap = mData.getFormDataMap();
		String reqData = convertInputPart2Str(dataMap.get("data"));
		
		
		log.debug("reqData ::::::::: " + reqData);
		// JsonObject reqObj = new Gson().fromJson(reqData, JsonObject.class);
		
		
		JsonNode reqNode = null;
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvaliRequestData, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(tokenNo)) {
			// int visitId = visitDAO.insertDailyVisitData(reqObj);
			int visitId = visitDAO.insertDailyVisitData(reqNode);
			if (visitId == 0) {
				return Response.ok(ResponseUtil.setResponse(MessageConstants.ErrorMessage, MessageConstants.StatusFail),
						MediaType.APPLICATION_JSON).build();
			}

			log.debug("success");
		} else {
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvalidToken, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		}
		return Response
				.ok(ResponseUtil.setResponse("Visit has been submited successfully", MessageConstants.StatusSuccess),
						MediaType.APPLICATION_JSON)
				.build();
	}

	@POST
	@Path("/comment")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response callInsertDataDailyVistComment(String reqData) {
		log.debug("callInsertDataDailyVistComment");
		JsonNode reqNode = null;
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvaliRequestData, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			int cmtCount = visitDAO.insertDailyVisitComment(reqNode);
		} else {
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvalidToken, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(
				ResponseUtil.setResponse("comment has been submited successfully", MessageConstants.StatusSuccess),
				MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/complaint")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response callInsertComplaint(String reqData) {
		log.debug("reqData ::::::::: " + reqData);
		JsonNode reqNode = null;
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvaliRequestData, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
			
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			int cmtCount = visitDAO.insertComplaint(reqNode);
			if(cmtCount == 0 )
				return Response.ok(ResponseUtil.setResponse(MessageConstants.ErrorMessage, MessageConstants.StatusFail),
						MediaType.APPLICATION_JSON).build();
		} else 
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvalidToken, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		
		return Response.ok(
				ResponseUtil.setResponse("complaint has been submited successfully", MessageConstants.StatusSuccess),
				MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/crmactivity")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callInsertCRMActivity(String reqData) {
		log.debug("callInsertCRMActivity");
		//log.debug("reqData ::::::::: " + reqData);
		JsonNode reqNode = null;
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			int rowCount = visitDAO.insertCRMActivity(reqNode);
			if(rowCount == 0) {
				return Response.ok(ResponseUtil.setResponse(MessageConstants.ErrorMessage, MessageConstants.StatusFail),
						MediaType.APPLICATION_JSON).build();
			}
		} else {
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvalidToken, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(
				ResponseUtil.setResponse("activity has been submited successfully", MessageConstants.StatusSuccess),
				MediaType.APPLICATION_JSON).build();
	}
	

	@POST
	@Path("/cst")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callInsertCST(String reqData) {
		log.debug("callInsertCST");

		log.debug("reqData ::::::::: " + reqData);
		JsonNode reqNode = null;
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			int rowCount = visitDAO.insertDataInCST(reqNode);
			if(rowCount == 0) {
				return Response.ok(ResponseUtil.setResponse(MessageConstants.ErrorMessage, MessageConstants.StatusFail),
						MediaType.APPLICATION_JSON).build();
			}
		} else {
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvalidToken, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(
				ResponseUtil.setResponse("CST has been submited successfully", MessageConstants.StatusSuccess),
				MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Path("/techexpmonvehicle")
	@Produces(MediaType.APPLICATION_JSON)
	public Response callInsertTechExpMonitoringVehicle(String reqData) {
		log.debug("reqData ::::::::: " + reqData);
		JsonNode reqNode = null;
		try {
			reqNode = new ObjectMapper().readTree(reqData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (reqNode.has("TokenNo") && MessageConstants.TokenValues.equals(reqNode.get("TokenNo").asText())) {
			int rowCount = visitDAO.insertDataInTechExpMonitoringVehicle(reqNode);
			if(rowCount == 0) {
				return Response.ok(ResponseUtil.setResponse(MessageConstants.ErrorMessage, MessageConstants.StatusFail),
						MediaType.APPLICATION_JSON).build();
			}
		} else {
			return Response.ok(ResponseUtil.setResponse(MessageConstants.InvalidToken, MessageConstants.StatusFail),
					MediaType.APPLICATION_JSON).build();
		}
		return Response.ok(
				ResponseUtil.setResponse("CST has been submited successfully", MessageConstants.StatusSuccess),
				MediaType.APPLICATION_JSON).build();
	}


}
