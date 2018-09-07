package com.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.constants.MessageConstants;
import com.dao.DailyVisitDAO;
import com.db.DBUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.model.ActivityModel;
import com.model.CSTModel;
import com.model.CommentModel;
import com.model.ComplaintModel;
import com.model.DailyVisitModel;
import com.model.TechExpMonVehicleModel;

@Stateless
public class DailyVisitDAOImpl implements DailyVisitDAO {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private Connection con = null;

	private int visitId ;

	private int userId;

	private int activityId;
	
	private int cstId;
	
	private int expertServiceId;
	
	private int isVerId;

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

	@Override
	public int insertDailyVisitData(JsonNode reqObj) {
		log.debug("insertDailyVisitData");
		
		DailyVisitModel visitModel = new DailyVisitModel();
		
		/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd mm:hh");
		Date appointment = new Date();
		Date dob = new Date();*/
		
		userId = 0;
		visitId = 0;
		
		try {

			/*if(reqObj.has("appointment") && (reqObj.get("appointment").asText() != null))
				appointment = formatter.parse(reqObj.get("appointment").asText() + " 00:00");
			else
				appointment = formatter.parse("0000-00-00 00:00");
			
			if(reqObj.has("dob") && (reqObj.get("dob").asText() != null))
				dob = formatter.parse(reqObj.get("dob").asText() + " 00:00");
			else
				dob = formatter.parse("0000-00-00 00:00");*/

			if (reqObj.has("userId")) {
				userId = reqObj.get("userId").asInt();
				visitModel.setUserId(reqObj.get("userId").asInt());
			}
			
			/*if (reqObj.has("isActive"))
				visitModel.setActive(reqObj.get("isActive").asBoolean());*/
				
			if (reqObj.has("address"))
				visitModel.setAddress(reqObj.get("address").asText());
				
			if (reqObj.has("appointment"))
				visitModel.setAppointment(reqObj.get("appointment").asText());
				
			if (reqObj.has("associateEngg"))
				visitModel.setAssociateEngg(reqObj.get("associateEngg").asText());
				
			if (reqObj.has("associateDealer"))
				visitModel.setAssociateDealer(reqObj.get("associateDealer").asText());
				
			if (reqObj.has("districId"))
				visitModel.setDistricId(reqObj.get("districId").asInt());
				
			if (reqObj.has("brandPreference"))
				visitModel.setBrandPreference(reqObj.get("brandPreference").asText());
				
			if (reqObj.has("contactNo"))
				visitModel.setContactNo(reqObj.get("contactNo").asText());
				
			if (reqObj.has("convertedTo"))
				visitModel.setConvertedTo(reqObj.get("convertedTo").asText());
				
			if (reqObj.has("dealerId"))
				visitModel.setDealerId(reqObj.get("dealerId").asInt());
				
			if (reqObj.has("dob"))
				visitModel.setDob(reqObj.get("dob").asText());
			
			if (reqObj.has("dom"))
				visitModel.setDom(reqObj.get("dom").asText());
				
			if (reqObj.has("email"))
				visitModel.setEmail(reqObj.get("email").asText());
				
			if (reqObj.has("existingBrand"))
				visitModel.setExistingBrand(reqObj.get("existingBrand").asText());
				
			if (reqObj.has("firmName"))
				visitModel.setFirmName(reqObj.get("firmName").asText());
				
			if (reqObj.has("location"))
				visitModel.setLocation(reqObj.get("location").asText());
				
			if (reqObj.has("leadGiven"))
				visitModel.setLeadGiven(reqObj.get("leadGiven").asText());
				
			if (reqObj.has("isMember"))
				visitModel.setMember(reqObj.get("isMember").asBoolean());
				
			if (reqObj.has("monthlyPotential"))
				visitModel.setMonthlyPotential(reqObj.get("monthlyPotential").asText());
				
			if (reqObj.has("monthlyVolume"))
				visitModel.setMonthlyVolume(reqObj.get("monthlyVolume").asText());
				
			if (reqObj.has("name"))
				visitModel.setName(reqObj.get("name").asText());
				
			if (reqObj.has("bags"))
				visitModel.setBags(reqObj.get("bags").asInt());
				
			if (reqObj.has("dalmiaSites"))
				visitModel.setDalmiaSites(reqObj.get("dalmiaSites").asInt());
				
			if (reqObj.has("dealers"))
				visitModel.setDealers(reqObj.get("dealers").asInt());
				
			if (reqObj.has("enggs"))
				visitModel.setEnggs(reqObj.get("enggs").asInt());
				
			if (reqObj.has("floors"))
				visitModel.setFloors(reqObj.get("floors").asInt());
				
			if (reqObj.has("headMasons"))
				visitModel.setHeadMasons(reqObj.get("headMasons").asInt());
				
			if (reqObj.has("manpowers"))
				visitModel.setManpowers(reqObj.get("manpowers").asInt());
				
			if (reqObj.has("ongoingSites"))
				visitModel.setOngoingSites(reqObj.get("ongoingSites").asInt());
				
			if (reqObj.has("ofcAddress"))
				visitModel.setOfcAddress(reqObj.get("ofcAddress").asText());
				
			if (reqObj.has("percentageCompletion"))
				visitModel.setPercentageCompletion(reqObj.get("percentageCompletion").asText());
				
			if (reqObj.has("bagPrice"))
				visitModel.setBagPrice(reqObj.get("bagPrice").asText());
				
			if (reqObj.has("profileImg"))
				visitModel.setProfileImg(reqObj.get("profileImg").asText());
				
			if (reqObj.has("qualification"))
				visitModel.setQualification(reqObj.get("qualification").asText());
				
			if (reqObj.has("reason"))
				visitModel.setReason(reqObj.get("reason").asText());
				
			if (reqObj.has("religion"))
				visitModel.setReligion(reqObj.get("religion").asText());
				
			if (reqObj.has("remark"))
				visitModel.setRemark(reqObj.get("remark").asText());
				
			if (reqObj.has("saleAchieved"))
				visitModel.setSaleAchieved(reqObj.get("saleAchieved").asText());
				
			if (reqObj.has("salesOfficerName"))
				visitModel.setSalesOfficerName(reqObj.get("salesOfficerName").asText());
				
			if (reqObj.has("salesTarget"))
				visitModel.setSalesTarget(reqObj.get("salesTarget").asText());
				
			if (reqObj.has("squareFeet"))
				visitModel.setSquareFeet(reqObj.get("squareFeet").asText());
				
			if (reqObj.has("constructionStage"))
				visitModel.setConstructionStage(reqObj.get("constructionStage").asText());
				
			if (reqObj.has("status"))
				visitModel.setStatus(reqObj.get("status").asText());
				
			if (reqObj.has("type"))
				visitModel.setType(reqObj.get("type").asText());
				
			if (reqObj.has("brandType"))
				visitModel.setBrandType(reqObj.get("brandType").asText());
				
			if (reqObj.has("contractType"))
				visitModel.setContractType(reqObj.get("contractType").asText());
				
			if (reqObj.has("productType"))
				visitModel.setProductType(reqObj.get("productType").asText());
				
			/*
			 * if(reqObj.has("createdOn"))
			 * visitModel.setCreatedOn(reqObj.get("createdOn").asText());
			 * if(reqObj.has("updatedOn"))
			 * visitModel.setUpdatedOn(reqObj.get("updatedOn").asText());
			 */
			if (reqObj.has("visitType"))
				visitModel.setVisitType(reqObj.get("visitType").asText());
			
			visitModel.setActive(true);
			
			persistDataInDailyVisit(visitModel);

			visitModel.setId(visitId);

			if (reqObj.has("comments"))
				visitModel.setComments(convertReqDataToCommentModel(reqObj, MessageConstants.DAILYVISIT));

			if (visitId != 0) {
				persistComments(visitModel.getComments(), MessageConstants.DAILYVISIT);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return visitId;
	}

	@Override
	public List<DailyVisitModel> getDailyVisitData(int userId, String visitType) {
		log.debug("getDailyVisitData");
		
		List<DailyVisitModel> visitList = new ArrayList<>();
		
		if(MessageConstants.ENGINEERVIST.equalsIgnoreCase(visitType))
			visitType = MessageConstants.ENGINEERVIST;
		
		else if(MessageConstants.HEADMASONVISIT.equalsIgnoreCase(visitType))
			visitType = MessageConstants.HEADMASONVISIT;
		
		else if(MessageConstants.CONTRACTORVISIT.equalsIgnoreCase(visitType))
			visitType = MessageConstants.CONTRACTORVISIT;
		
		else if(MessageConstants.DEALERVISIT.equalsIgnoreCase(visitType))
			visitType = MessageConstants.DEALERVISIT;
		
		else if(MessageConstants.OTHERVISIT.equalsIgnoreCase(visitType))
			visitType = MessageConstants.OTHERVISIT;
		
		else return null;
		
		String QUERY = "SELECT * FROM daily_visits WHERE userId = '" + userId
				+ "' AND visitType = '"+visitType +"' AND isActive = true ORDER BY createdOn DESC";
		
		ResultSet rset = null;
		
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd mm:hh");

		try {
			
			con = DBUtils.generateConnection();
			
			if (con != null) {
				
				Statement stmt = con.createStatement();
				
				rset = stmt.executeQuery(QUERY);
				
				while (rset != null && rset.next()) {
					
					DailyVisitModel visitModel = new DailyVisitModel();

					visitModel.setId(rset.getInt("id"));

					visitModel.setUserId(rset.getInt("userId"));

					//visitModel.setActive(rset.getBoolean("isActive"));

					visitModel.setAddress(rset.getString("address") != null ? rset.getString("address") : "n/a");

					visitModel.setEmail(rset.getString("email") != null ? rset.getString("email") : "n/a");
					
					visitModel.setCreatedOn(rset.getString("createdOn") != null ? rset.getString("createdOn") : "n/a");
					
					visitModel.setProfileImg(rset.getString("profileImg") != null ? rset.getString("profileImg") : "n/a");
					
					visitModel.setVisitType(rset.getString("visitType") != null ? rset.getString("visitType") : "n/a");
					
					visitModel.setContactNo(rset.getString("contactNo") != null ? rset.getString("contactNo") : "n/a");

					/*
					visitModel.setAppointment(rset.getString("appointment") != null ? rset.getString("appointment") : "n/a");

					visitModel.setAssociateEngg(
							rset.getString("associateEngg") != null ? rset.getString("associateEngg") : "n/a");

					visitModel.setAssociateDealer(
							rset.getString("associateDealer") != null ? rset.getString("associateDealer") : "n/a");

					visitModel.setDistricId(rset.getInt("districId"));

					visitModel.setBrandPreference(
							rset.getString("brandPreference") != null ? rset.getString("brandPreference") : "n/a");

					visitModel.setContactNo(rset.getString("contactNo") != null ? rset.getString("contactNo") : "n/a");

					visitModel.setConvertedTo(
							rset.getString("convertedTo") != null ? rset.getString("convertedTo") : "n/a");

					visitModel.setDealerId(rset.getInt("dealerId"));

					visitModel.setDob(rset.getString("dob") != null ? rset.getString("dob") : null);

					visitModel.setDom(rset.getString("dom") != null ? rset.getString("dom") : null);
					

					visitModel.setExistingBrand(
							rset.getString("existingBrand") != null ? rset.getString("existingBrand") : "n/a");

					visitModel.setFirmName(rset.getString("firmName") != null ? rset.getString("firmName") : "n/a");

					visitModel.setLocation(rset.getString("location") != null ? rset.getString("location") : "n/a");

					visitModel.setLeadGiven(rset.getString("leadGiven") != null ? rset.getString("leadGiven") : "n/a");

					visitModel.setMember(rset.getBoolean("isMember"));

					visitModel.setMonthlyPotential(
							rset.getString("monthlyPotential") != null ? rset.getString("monthlyPotential") : "n/a");

					visitModel.setMonthlyVolume(
							rset.getString("monthlyVolume") != null ? rset.getString("monthlyVolume") : "n/a");
					
					visitModel.setEmail(rset.getString("email") != null ? rset.getString("email") : "n/a");

					visitModel.setName(rset.getString("name") != null ? rset.getString("name") : "n/a");

					visitModel.setBags(rset.getInt("bags"));

					visitModel.setDalmiaSites(rset.getInt("dalmiaSites"));

					visitModel.setDealers(rset.getInt("dealers"));

					visitModel.setEnggs(rset.getInt("enggs"));

					visitModel.setFloors(rset.getInt("floors"));

					visitModel.setHeadMasons(rset.getInt("headMasons"));

					visitModel.setManpowers(rset.getInt("manpowers"));

					visitModel.setOngoingSites(rset.getInt("ongoingSites"));

					visitModel
							.setOfcAddress(rset.getString("ofcAddress") != null ? rset.getString("ofcAddress") : "n/a");

					visitModel.setPercentageCompletion(
							rset.getString("percentageCompletion") != null ? rset.getString("percentageCompletion")
									: "n/a");

					visitModel.setBagPrice(rset.getString("bagPrice") != null ? rset.getString("bagPrice") : "n/a");

					visitModel.setProfileImg(rset.getString("profileImg") != null ? rset.getString("profileImg") : "n/a");

					visitModel.setQualification(
							rset.getString("qualification") != null ? rset.getString("qualification") : "n/a");

					visitModel.setReason(rset.getString("reason") != null ? rset.getString("reason") : "n/a");

					visitModel.setReligion(rset.getString("religion") != null ? rset.getString("religion") : "n/a");

					visitModel.setRemark(rset.getString("remark") != null ? rset.getString("remark") : "n/a");

					visitModel.setSaleAchieved(
							rset.getString("saleAchieved") != null ? rset.getString("saleAchieved") : "n/a");

					visitModel.setSalesOfficerName(
							rset.getString("salesOfficerName") != null ? rset.getString("salesOfficerName") : "n/a");

					visitModel.setSalesTarget(
							rset.getString("salesTarget") != null ? rset.getString("salesTarget") : "n/a");

					visitModel.setSquareFeet(
							rset.getString("squareFeet") != null ? rset.getString("squareFeet") : "n/a");

					visitModel.setConstructionStage(
							rset.getString("constructionStage") != null ? rset.getString("constructionStage") : "n/a");

					visitModel.setStatus(rset.getString("status") != null ? rset.getString("status") : "n/a");

					visitModel.setType(rset.getString("type") != null ? rset.getString("type") : "n/a");

					visitModel.setBrandType(rset.getString("brandType") != null ? rset.getString("brandType") : "n/a");

					visitModel.setContractType(
							rset.getString("contractType") != null ? rset.getString("contractType") : "n/a");

					visitModel.setProductType(
							rset.getString("productType") != null ? rset.getString("productType") : "n/a");

					visitModel.setCreatedOn(rset.getString("createdOn") != null ? rset.getString("createdOn") : "n/a");

					visitModel.setUpdatedOn(rset.getString("updatedOn") != null ? rset.getString("updatedOn") : "n/a");

					visitModel.setVisitType(rset.getString("visitType") != null ? rset.getString("visitType") : "n/a");

					//visitModel.setComments(getCommentModel(rset.getInt("id"), MessageConstants.DAILYVISIT)); 
					 */
					visitList.add(visitModel);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return visitList;
	}

	private List<CommentModel> getCommentModel(int id, String commentType) {

		String query = null;

		if (MessageConstants.DAILYVISIT.equalsIgnoreCase(commentType))
			query = "SELECT * FROM comments WHERE visitId = '" + id
					+ "' AND isActive = true ORDER BY createdOn DESC";

		else if (MessageConstants.CRMACTIVITY.equalsIgnoreCase(commentType))
			query = "SELECT * FROM comments WHERE activityId = '" + id
					+ "' AND isActive = true ORDER BY createdOn DESC";
		
		else if (MessageConstants.CST.equalsIgnoreCase(commentType))
			query = "SELECT * FROM comments WHERE cstId = '" + id
					+ "' AND isActive = true ORDER BY createdOn DESC";
		
		else if (MessageConstants.TECHEXPERT.equalsIgnoreCase(commentType))
			query = "SELECT * FROM comments WHERE expertServiceId = '" + id
					+ "' AND isActive = true ORDER BY createdOn DESC";
		
		else if (MessageConstants.ISVEREIFICATION.equalsIgnoreCase(commentType))
			query = "SELECT * FROM comments WHERE isVerId = '" + id
					+ "' AND isActive = true ORDER BY createdOn DESC";

		if(query == null)return null;
		
		List<CommentModel> commentList = new ArrayList<>();

			try {
				con = DBUtils.generateConnection();

				Statement stmt = con.createStatement();

				ResultSet rset = stmt.executeQuery(query);


				while (rset != null && rset.next()) {

					CommentModel commentModel = new CommentModel();

					commentModel.setCommentId(rset.getInt("commentId"));

					commentModel.setVisitId(rset.getInt("visitId"));

					commentModel.setUserId(rset.getInt("userId"));

					commentModel.setActivityId(rset.getInt("activityId"));
					
					commentModel.setCstId(rset.getInt("cstId"));

					commentModel.setExpertServiceId(rset.getInt("expertServiceId"));

					commentModel.setIsVerId(rset.getInt("isVerId"));

					commentModel.setActive(rset.getBoolean("isActive"));

					commentModel.setComment(rset.getString("comment") != null ? rset.getString("comment") : "n/a");

					commentModel
							.setCommentTitle(rset.getString("commentTitle") != null ? rset.getString("commentTitle") : "n/a");

					commentModel.setCommentType(rset.getString("commentType") != null ? rset.getString("commentType") : "n/a");

					commentModel.setCreatedOn(rset.getString("createdOn") != null ? rset.getString("createdOn") : "n/a");

					commentModel.setUpdatedOn(rset.getString("updatedOn") != null ? rset.getString("updatedOn") : "n/a");

					commentList.add(commentModel);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
				return null;
			}finally
			{
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		return commentList;
	}

	/*
	 * public DailyVisitModel convertReqDataToVisitModel(String reqData) {
	 * DailyVisitModel visitModel = new DailyVisitModel(); JsonObject reqObj = new
	 * Gson().fromJson(reqData, JsonObject.class); try { if(reqObj.has("id")) userId
	 * = reqObj.get("id").getAsInt(); visitModel.setId(reqObj.get("id").getAsInt());
	 * if(reqObj.has("userId"))
	 * visitModel.setUserId(reqObj.get("userId").getAsInt());
	 * if(reqObj.has("isActive"))
	 * visitModel.setActive(reqObj.get("isActive").getAsBoolean());
	 * if(reqObj.has("address"))
	 * visitModel.setAddress(reqObj.get("address").getAsString());
	 * if(reqObj.has("appointment"))
	 * visitModel.setAppointment(reqObj.get("appointment").getAsString());
	 * if(reqObj.has("associateEngg"))
	 * visitModel.setAssociateEngg(reqObj.get("associateEngg").getAsString());
	 * if(reqObj.has("associateDealer"))
	 * visitModel.setAssociateDealer(reqObj.get("associateDealer").getAsString());
	 * if(reqObj.has("districId"))
	 * visitModel.setDistricId(reqObj.get("districId").getAsString());
	 * if(reqObj.has("brandPreference"))
	 * visitModel.setBrandPreference(reqObj.get("brandPreference").getAsString());
	 * if(reqObj.has("contactNo"))
	 * visitModel.setContactNo(reqObj.get("contactNo").getAsString());
	 * if(reqObj.has("convertedTo"))
	 * visitModel.setConvertedTo(reqObj.get("convertedTo").getAsString());
	 * if(reqObj.has("dealerId"))
	 * visitModel.setDealerId(reqObj.get("dealerId").getAsString());
	 * if(reqObj.has("dob")) visitModel.setDob(reqObj.get("dob").getAsString());
	 * if(reqObj.has("email"))
	 * visitModel.setEmail(reqObj.get("email").getAsString());
	 * if(reqObj.has("existingBrand"))
	 * visitModel.setExistingBrand(reqObj.get("existingBrand").getAsString());
	 * if(reqObj.has("firmName"))
	 * visitModel.setFirmName(reqObj.get("firmName").getAsString());
	 * if(reqObj.has("location"))
	 * visitModel.setLocation(reqObj.get("location").getAsString());
	 * if(reqObj.has("leadGiven"))
	 * visitModel.setLeadGiven(reqObj.get("leadGiven").getAsString());
	 * if(reqObj.has("isMember"))
	 * visitModel.setMember(reqObj.get("isMember").getAsBoolean());
	 * if(reqObj.has("monthlyPotential"))
	 * visitModel.setMonthlyPotential(reqObj.get("monthlyPotential").getAsString());
	 * if(reqObj.has("monthlyVolume"))
	 * visitModel.setMonthlyVolume(reqObj.get("monthlyVolume").getAsString());
	 * if(reqObj.has("name")) visitModel.setName(reqObj.get("name").getAsString());
	 * if(reqObj.has("bags")) visitModel.setBags(reqObj.get("bags").getAsString());
	 * if(reqObj.has("dalmiaSites"))
	 * visitModel.setDalmiaSites(reqObj.get("dalmiaSites").getAsString());
	 * if(reqObj.has("dealers"))
	 * visitModel.setDealers(reqObj.get("dealers").getAsString());
	 * if(reqObj.has("enggs"))
	 * visitModel.setEnggs(reqObj.get("enggs").getAsString());
	 * if(reqObj.has("floors"))
	 * visitModel.setFloors(reqObj.get("floors").getAsString());
	 * if(reqObj.has("headMasons"))
	 * visitModel.setHeadMasons(reqObj.get("headMasons").getAsString());
	 * if(reqObj.has("manpowers"))
	 * visitModel.setManpowers(reqObj.get("manpowers").getAsString());
	 * if(reqObj.has("ongoingSites"))
	 * visitModel.setOngoingSites(reqObj.get("ongoingSites").getAsString());
	 * if(reqObj.has("ofcAddress"))
	 * visitModel.setOfcAddress(reqObj.get("ofcAddress").getAsString());
	 * if(reqObj.has("percentageCompletion"))
	 * visitModel.setPercentageCompletion(reqObj.get("percentageCompletion").
	 * getAsString()); if(reqObj.has("bagPrice"))
	 * visitModel.setBagPrice(reqObj.get("bagPrice").getAsString());
	 * if(reqObj.has("profileImg"))
	 * visitModel.setProfileImg(reqObj.get("profileImg").getAsString());
	 * if(reqObj.has("qualification"))
	 * visitModel.setQualification(reqObj.get("qualification").getAsString());
	 * if(reqObj.has("reason"))
	 * visitModel.setReason(reqObj.get("reason").getAsString());
	 * if(reqObj.has("religion"))
	 * visitModel.setReligion(reqObj.get("religion").getAsString());
	 * if(reqObj.has("remark"))
	 * visitModel.setRemark(reqObj.get("remark").getAsString());
	 * if(reqObj.has("saleAchieved"))
	 * visitModel.setSaleAchieved(reqObj.get("saleAchieved").getAsString());
	 * if(reqObj.has("salesOfficerName"))
	 * visitModel.setSalesOfficerName(reqObj.get("salesOfficerName").getAsString());
	 * if(reqObj.has("salesTarget"))
	 * visitModel.setSalesTarget(reqObj.get("salesTarget").getAsString());
	 * if(reqObj.has("squareFeet"))
	 * visitModel.setSquareFeet(reqObj.get("squareFeet").getAsString());
	 * if(reqObj.has("constructionStage"))
	 * visitModel.setConstructionStage(reqObj.get("constructionStage").getAsString()
	 * ); if(reqObj.has("status"))
	 * visitModel.setStatus(reqObj.get("status").getAsString());
	 * if(reqObj.has("type")) visitModel.setType(reqObj.get("type").getAsString());
	 * if(reqObj.has("brandType"))
	 * visitModel.setBrandType(reqObj.get("brandType").getAsString());
	 * if(reqObj.has("contractType"))
	 * visitModel.setContractType(reqObj.get("contractType").getAsString());
	 * if(reqObj.has("productType"))
	 * visitModel.setProductType(reqObj.get("productType").getAsString());
	 * if(reqObj.has("createdOn"))
	 * visitModel.setCreatedOn(reqObj.get("createdOn").getAsString());
	 * if(reqObj.has("updatedOn"))
	 * visitModel.setUpdatedOn(reqObj.get("updatedOn").getAsString());
	 * if(reqObj.has("visitType"))
	 * visitModel.setVisitType(reqObj.get("visitType").getAsString());
	 * 
	 * persistDataInDailyVisit(visitModel);
	 * 
	 * visitModel.setId(visitId);
	 * 
	 * if(reqObj.has("comments"))
	 * visitModel.setComments(convertReqDataToCommentModel(reqObj));
	 * 
	 * if(visitId != 0) { persistComments(visitModel); } }catch(Exception ex) {
	 * ex.printStackTrace(); }
	 * 
	 * return visitModel; }
	 */

	private void persistDataInDailyVisit(DailyVisitModel visitModel) throws SQLException {
		log.debug("persistDataInDailyVisit");
		
		String QUERY = "INSERT INTO daily_visits " + "(userId, isActive, address, appointment, associateEngg, "
				+ "associateDealer, districId, brandPreference, contactNo, convertedTo, dealerId, dob,"
				+ " email, existingBrand, firmName, location, leadGiven, isMember, monthlyPotential, monthlyVolume, "
				+ "name, bags, dalmiaSites, dealers, enggs, floors, headMasons, manpowers, ongoingSites, "
				+ "ofcAddress, percentageCompletion, bagPrice, profileImg, qualification, reason, religion, "
				+ "remark, saleAchieved, salesOfficerName, salesTarget, squareFeet, constructionStage, status, "
				+ "type, brandType, contractType, productType, visitType)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		/*
		 * if (con == null) { con = DBUtils.generateConnection(); }else {
		 */
		con = DBUtils.generateConnection();
		if (con != null) {

			PreparedStatement ps = con.prepareStatement(QUERY, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, visitModel.getUserId());

			ps.setBoolean(2, visitModel.isActive());

			ps.setString(3, visitModel.getAddress());

			ps.setString(4, String.valueOf(visitModel.getAppointment()));

			ps.setString(5, visitModel.getAssociateEngg());

			ps.setString(6, visitModel.getAssociateDealer());

			ps.setInt(7, visitModel.getDistricId());

			ps.setString(8, visitModel.getBrandPreference());

			ps.setString(9, visitModel.getContactNo());

			ps.setString(10, visitModel.getConvertedTo());

			ps.setInt(11, visitModel.getDealerId());

			ps.setString(12, String.valueOf(visitModel.getDob()));

			ps.setString(13, visitModel.getEmail());

			ps.setString(14, visitModel.getExistingBrand());

			ps.setString(15, visitModel.getFirmName());

			ps.setString(16, visitModel.getLocation());

			ps.setString(17, visitModel.getLeadGiven());

			ps.setBoolean(18, visitModel.isMember());

			ps.setString(19, visitModel.getMonthlyPotential());

			ps.setString(20, visitModel.getMonthlyVolume());

			ps.setString(21, visitModel.getName());

			ps.setInt(22, visitModel.getBags());

			ps.setInt(23, visitModel.getDalmiaSites());

			ps.setInt(24, visitModel.getDealers());

			ps.setInt(25, visitModel.getEnggs());

			ps.setInt(26, visitModel.getFloors());

			ps.setInt(27, visitModel.getHeadMasons());

			ps.setInt(28, visitModel.getManpowers());

			ps.setInt(29, visitModel.getOngoingSites());

			ps.setString(30, visitModel.getOfcAddress());

			ps.setString(31, visitModel.getPercentageCompletion());

			ps.setString(32, visitModel.getBagPrice());

			ps.setString(33, visitModel.getProfileImg());

			ps.setString(34, visitModel.getQualification());

			ps.setString(35, visitModel.getReason());

			ps.setString(36, visitModel.getReligion());

			ps.setString(37, visitModel.getRemark());

			ps.setString(38, visitModel.getSaleAchieved());

			ps.setString(39, visitModel.getSalesOfficerName());

			ps.setString(40, visitModel.getSalesTarget());

			ps.setString(41, visitModel.getSquareFeet());

			ps.setString(42, visitModel.getConstructionStage());

			ps.setString(43, visitModel.getStatus());

			ps.setString(44, visitModel.getType());

			ps.setString(45, visitModel.getBrandType());

			ps.setString(46, visitModel.getContractType());

			ps.setString(47, visitModel.getProductType());

			ps.setString(48, visitModel.getVisitType());

			int affectedRows = ps.executeUpdate();

			if (affectedRows == 0) {

				throw new SQLException("Daily Visit persisiting failed , no row affected");

			} else {

				try (ResultSet genVisitId = ps.getGeneratedKeys()) {

					if (genVisitId.next()) {

						setVisitId(genVisitId.getInt(1));

					} else {

						throw new SQLException("Daily Visit persisiting failed , no row affected");

					}
				}
			}
		}
	}

	private List<CommentModel> convertReqDataToCommentModel(JsonNode reqObj, String commentType) {

		log.debug("convertReqDataToCommentModel");
		
		List<CommentModel> comModelList = new ArrayList<>();

		if (reqObj != null && reqObj.has("comments")) {

			JsonNode comObj = reqObj.get("comments");

			for (int i = 0; i < comObj.size(); i++) {

				CommentModel comModel = new CommentModel();

				JsonNode jObj = comObj.get(i);

				if (jObj.has("comment"))
					comModel.setComment(jObj.get("comment").asText());

				if (jObj.has("commentTitile"))
					comModel.setCommentTitle(jObj.get("commentTitile").asText());

				comModel.setUserId(userId);

				if (MessageConstants.DAILYVISIT.equalsIgnoreCase(commentType))
					comModel.setVisitId(visitId);

				else if (MessageConstants.CRMACTIVITY.equalsIgnoreCase(commentType))
					comModel.setActivityId(activityId);
				
				else if (MessageConstants.CST.equalsIgnoreCase(commentType))
					comModel.setCstId(cstId);
				
				else if (MessageConstants.TECHEXPERT.equalsIgnoreCase(commentType))
					comModel.setCstId(expertServiceId);
				
				else if (MessageConstants.ISVEREIFICATION.equalsIgnoreCase(commentType))
					comModel.setCstId(isVerId);
				
				comModel.setCommentType(commentType);

				comModel.setActive(true);

				comModelList.add(comModel);
			}
		}
		return comModelList;
	}

	private int persistComments(List<CommentModel> comModelList, String commentType) {
		// List<DailyVisitCommentModel> comModelList = visitModel.getComments();
		String query = null; 
		/*if(MessageConstants.DAILYVISIT.equalsIgnoreCase(commentType))
			query = "INSERT INTO comments (userId, visitId,activityId,cstId,expertServiceId, isVerId,comment,commentTitle, "
					+ "commentType ,isActive) VALUES(?,?,?,?,?,?,?,?,?,?)";*/
		
		if(MessageConstants.DAILYVISIT.equalsIgnoreCase(commentType))
			query = "INSERT INTO comments (userId, visitId ,comment,commentTitle, "
					+ "commentType ,isActive) VALUES(?,?,?,?,?,?)";
		
		else if(MessageConstants.CST.equalsIgnoreCase(commentType))
			query = "INSERT INTO comments (userId,cstId,comment,commentTitle, "
					+ "commentType ,isActive) VALUES(?,?,?,?,?,?)";
		
		else if(MessageConstants.TECHEXPERT.equalsIgnoreCase(commentType))
			query = "INSERT INTO comments (userId,expertServiceId,comment,commentTitle, "
					+ "commentType ,isActive) VALUES(?,?,?,?,?,?)";
		
		else if(MessageConstants.CRMACTIVITY.equalsIgnoreCase(commentType))
			query = "INSERT INTO comments (userId,activityId,comment,commentTitle, "
					+ "commentType ,isActive) VALUES(?,?,?,?,?,?)";
		
		else if(MessageConstants.ISVEREIFICATION.equalsIgnoreCase(commentType))
			query = "INSERT INTO comments (userId,isVerId,comment,commentTitle, "
					+ "commentType ,isActive) VALUES(?,?,?,?,?,?)";
		
		else return 0;
		
		int totalComments = 0;

		try {

			if (comModelList != null) {

				DBUtils.generateConnection();

				 if(con != null) {

					PreparedStatement ps = con.prepareStatement(query);

					for (CommentModel comModel : comModelList) {

						ps.setInt(1, comModel.getUserId());
						
						if(MessageConstants.DAILYVISIT.equalsIgnoreCase(commentType))
							ps.setInt(2, comModel.getVisitId());

						else if(MessageConstants.CST.equalsIgnoreCase(commentType))
							ps.setInt(2, comModel.getCstId());

						else if(MessageConstants.CRMACTIVITY.equalsIgnoreCase(commentType))
							ps.setInt(3, comModel.getActivityId());
						
						else if(MessageConstants.TECHEXPERT.equalsIgnoreCase(commentType))
							ps.setInt(5, comModel.getExpertServiceId());
						
						else if(MessageConstants.ISVEREIFICATION.equalsIgnoreCase(commentType))
							ps.setInt(6, comModel.getIsVerId());

						ps.setString(3, comModel.getComment());

						ps.setString(4, comModel.getCommentTitle());

						ps.setString(5, comModel.getCommentType());

						ps.setBoolean(6, comModel.isActive());

						ps.addBatch();

					}
					int[] rows = ps.executeBatch();

					totalComments = rows.length;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}

		return totalComments;
	}

	@Override
	public int insertDailyVisitComment(JsonNode reqObj) {
		int cmtCount = 0;
		int refId = 0;
		String commentType = null;
		
		if (reqObj.has("id")) refId = reqObj.get("id").asInt();
		
		if(reqObj.has("userId")) userId = reqObj.get("userId").asInt();

		if(reqObj.has("commentType")) commentType = reqObj.get("commentType").asText();
		
		if (MessageConstants.DAILYVISIT.equalsIgnoreCase(commentType)) visitId = refId;

		else if (MessageConstants.CRMACTIVITY.equalsIgnoreCase(commentType)) activityId = refId;
		
		else if (MessageConstants.CST.equalsIgnoreCase(commentType)) cstId = refId;
		
		else if (MessageConstants.TECHEXPERT.equalsIgnoreCase(commentType)) expertServiceId = refId;
		
		else if (MessageConstants.ISVEREIFICATION.equalsIgnoreCase(commentType)) isVerId = refId;
		
		else return 0;
		
		/*if(reqObj.has("userId")) userId = reqObj.get("userId").asInt();
		
		if(reqObj.has("visitId")) visitId = reqObj.get("visitId").asInt();

		if(reqObj.has("expertServiceId")) expertServiceId = reqObj.get("expertServiceId").asInt();

		if(reqObj.has("isVerId")) isVerId = reqObj.get("isVerId").asInt();
		
		if(reqObj.has("cstId")) cstId = reqObj.get("cstId").asInt();
*/
		
		List<CommentModel> comModelList = convertReqDataToCommentModel(reqObj, commentType);
		
		cmtCount = persistComments(comModelList, commentType);
		
		/*try {
			// cmtCount = persistComments(comModelList) ;
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
		return cmtCount;
	}

	@Override
	public int insertComplaint(JsonNode reqObj) {

		int rowCount = 0;

		ComplaintModel comModel = new ComplaintModel();

		if (reqObj.has("userId"))
			comModel.setUserId(reqObj.get("userId").asInt());

		if (reqObj.has("complaintType"))
			comModel.setComplaintType(reqObj.get("complaintType").asText());

		if (reqObj.has("brandName"))
			comModel.setBrandName(reqObj.get("brandName").asText());

		if (reqObj.has("productType"))
			comModel.setProductType(reqObj.get("productType").asText());

		if (reqObj.has("plant"))
			comModel.setPlant(reqObj.get("plant").asText());

		if (reqObj.has("districId"))
			comModel.setDistricId(reqObj.get("districId").asInt());

		if (reqObj.has("remark"))
			comModel.setRemark(reqObj.get("remark").asText());

		if (reqObj.has("location"))
			comModel.setLocation(reqObj.get("location").asText());
		
		comModel.setActive(true);

		String query = "INSERT INTO complaint (userId,complaintType,brandName,productType,plant,districId,remark,location,isActive)"
				+ "VALUES (?,?,?,?,?,?,?,?,?);";

		con = DBUtils.generateConnection();

		if (con != null) {

			try {

				PreparedStatement ps = con.prepareStatement(query);

				ps.setInt(1, comModel.getUserId());

				ps.setString(2, comModel.getComplaintType());

				ps.setString(3, comModel.getBrandName());

				ps.setString(4, comModel.getProductType());

				ps.setString(5, comModel.getPlant());

				ps.setInt(6, comModel.getDistricId());

				ps.setString(7, comModel.getRemark());

				ps.setString(8, comModel.getLocation());
				
				ps.setBoolean(9, comModel.isActive());

				rowCount = ps.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				return 0;

			} finally {

				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return rowCount;
	}

	@Override
	public Object getDailyVisitById(int visitId) {

		String QUERY = "SELECT * FROM daily_visits WHERE id = '" + visitId
				+ "' AND isActive = true ORDER BY createdOn DESC";
		ResultSet rset = null;
		DailyVisitModel visitModel = new DailyVisitModel();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd mm:hh");

		try {
			
			con = DBUtils.generateConnection();
			
			if (con != null) {
				
				Statement stmt = con.createStatement();
				
				rset = stmt.executeQuery(QUERY);
				
				while (rset != null && rset.next()) {

					visitModel.setId(rset.getInt("id"));

					visitModel.setUserId(rset.getInt("userId"));

					visitModel.setActive(rset.getBoolean("isActive"));

					visitModel.setAddress(rset.getString("address") != null ? rset.getString("address") : "n/a");

					visitModel.setAppointment(
							rset.getString("appointment") != null ? rset.getString("appointment") : "n/a");

					visitModel.setAssociateEngg(
							rset.getString("associateEngg") != null ? rset.getString("associateEngg") : "n/a");

					visitModel.setAssociateDealer(
							rset.getString("associateDealer") != null ? rset.getString("associateDealer") : "n/a");

					visitModel.setDistricId(rset.getInt("districId"));

					visitModel.setBrandPreference(
							rset.getString("brandPreference") != null ? rset.getString("brandPreference") : "n/a");

					visitModel.setContactNo(rset.getString("contactNo") != null ? rset.getString("contactNo") : "n/a");

					visitModel.setConvertedTo(
							rset.getString("convertedTo") != null ? rset.getString("convertedTo") : "n/a");

					visitModel.setDealerId(rset.getInt("dealerId"));

					visitModel.setDob(rset.getString("dob") != null ? rset.getString("dob") : "n/a");

					visitModel.setEmail(rset.getString("email") != null ? rset.getString("email") : "n/a");

					visitModel.setExistingBrand(
							rset.getString("existingBrand") != null ? rset.getString("existingBrand") : "n/a");

					visitModel.setFirmName(rset.getString("firmName") != null ? rset.getString("firmName") : "n/a");

					visitModel.setLocation(rset.getString("location") != null ? rset.getString("location") : "n/a");

					visitModel.setLeadGiven(rset.getString("leadGiven") != null ? rset.getString("leadGiven") : "n/a");

					visitModel.setMember(rset.getBoolean("isMember"));

					visitModel.setMonthlyPotential(
							rset.getString("monthlyPotential") != null ? rset.getString("monthlyPotential") : "n/a");

					visitModel.setMonthlyVolume(
							rset.getString("monthlyVolume") != null ? rset.getString("monthlyVolume") : "n/a");

					visitModel.setName(rset.getString("name") != null ? rset.getString("name") : "n/a");

					visitModel.setBags(rset.getInt("bags"));

					visitModel.setDalmiaSites(rset.getInt("dalmiaSites"));

					visitModel.setDealers(rset.getInt("dealers"));

					visitModel.setEnggs(rset.getInt("enggs"));

					visitModel.setFloors(rset.getInt("floors"));

					visitModel.setHeadMasons(rset.getInt("headMasons"));

					visitModel.setManpowers(rset.getInt("manpowers"));

					visitModel.setOngoingSites(rset.getInt("ongoingSites"));

					visitModel
							.setOfcAddress(rset.getString("ofcAddress") != null ? rset.getString("ofcAddress") : "n/a");

					visitModel.setPercentageCompletion(
							rset.getString("percentageCompletion") != null ? rset.getString("percentageCompletion")
									: "n/a");

					visitModel.setBagPrice(rset.getString("bagPrice") != null ? rset.getString("bagPrice") : "n/a");

					visitModel
							.setProfileImg(rset.getString("profileImg") != null ? rset.getString("profileImg") : "n/a");

					visitModel.setQualification(
							rset.getString("qualification") != null ? rset.getString("qualification") : "n/a");

					visitModel.setReason(rset.getString("reason") != null ? rset.getString("reason") : "n/a");

					visitModel.setReligion(rset.getString("religion") != null ? rset.getString("religion") : "n/a");

					visitModel.setRemark(rset.getString("remark") != null ? rset.getString("remark") : "n/a");

					visitModel.setSaleAchieved(
							rset.getString("saleAchieved") != null ? rset.getString("saleAchieved") : "n/a");

					visitModel.setSalesOfficerName(
							rset.getString("salesOfficerName") != null ? rset.getString("salesOfficerName") : "n/a");

					visitModel.setSalesTarget(
							rset.getString("salesTarget") != null ? rset.getString("salesTarget") : "n/a");

					visitModel.setSquareFeet(
							rset.getString("squareFeet") != null ? rset.getString("squareFeet") : "n/a");

					visitModel.setConstructionStage(
							rset.getString("constructionStage") != null ? rset.getString("constructionStage") : "n/a");

					visitModel.setStatus(rset.getString("status") != null ? rset.getString("status") : "n/a");

					visitModel.setType(rset.getString("type") != null ? rset.getString("type") : "n/a");

					visitModel.setBrandType(rset.getString("brandType") != null ? rset.getString("brandType") : "n/a");

					visitModel.setContractType(
							rset.getString("contractType") != null ? rset.getString("contractType") : "n/a");

					visitModel.setProductType(
							rset.getString("productType") != null ? rset.getString("productType") : "n/a");

					visitModel.setCreatedOn(rset.getString("createdOn") != null ? rset.getString("createdOn") : "n/a");

					visitModel.setUpdatedOn(rset.getString("updatedOn") != null ? rset.getString("updatedOn") : "n/a");

					visitModel.setVisitType(rset.getString("visitType") != null ? rset.getString("visitType") : "n/a");

					visitModel.setComments(getCommentModel(rset.getInt("id"), MessageConstants.DAILYVISIT));

				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return visitModel;
	}

	@Override
	public int insertCRMActivity(JsonNode reqObj) {
		log.debug("insertCRMActivity");
		ActivityModel actModel = new ActivityModel();
		int rowCount = 0;
		activityId = 0;
		if (reqObj.has("userId"))
			actModel.setUserId(reqObj.get("userId").asInt());
		if (reqObj.has("meetingType"))

			actModel.setMeetingType(reqObj.get("meetingType").asText());
		if (reqObj.has("activityDate"))

			actModel.setActivityDate(reqObj.get("activityDate").asText());
		if (reqObj.has("activityTyp"))

			actModel.setActivityTyp(reqObj.get("activityTyp").asText());
		if (reqObj.has("dealerCode"))

			actModel.setDealerCode(reqObj.get("dealerCode").asText());
		if (reqObj.has("dealerName"))

			actModel.setDealerName(reqObj.get("dealerName").asText());
		if (reqObj.has("districId"))

			actModel.setDistricId(reqObj.get("districId").asText());
		if (reqObj.has("venue"))

			actModel.setVenue(reqObj.get("venue").asText());
		if (reqObj.has("inviteesNo"))

			actModel.setInviteesNo(reqObj.get("inviteesNo").asText());
		if (reqObj.has("attendeesNo"))

			actModel.setAttendeesNo(reqObj.get("attendeesNo").asText());
		if (reqObj.has("giftDistributionNo"))

			actModel.setGiftDistributionNo(reqObj.get("giftDistributionNo").asText());
		if (reqObj.has("giftType"))

			actModel.setGiftType(reqObj.get("giftType").asText());
		if (reqObj.has("expenseByEmp"))

			actModel.setExpenseByEmp(reqObj.get("expenseByEmp").asText());
		if (reqObj.has("expenseByPart"))

			actModel.setExpenseByPart(reqObj.get("expenseByPart").asText());
		if (reqObj.has("isMtgInBdg"))

			actModel.setMtgInBdg(reqObj.get("isMtgInBdg").asBoolean());
		if (reqObj.has("isActive"))

			actModel.setActive(reqObj.get("isActive").asBoolean());
		if (reqObj.has("reason"))

			actModel.setReason(reqObj.get("reason").asText());
		if (reqObj.has("vendorCode"))

			actModel.setVendorCode(reqObj.get("vendorCode").asText());
		if (reqObj.has("imgUrl"))

			actModel.setImgUrl(reqObj.get("imgUrl").asText());
		if (reqObj.has("location"))

			actModel.setLocation(reqObj.get("location").asText());

		int row = persistDataInCRMActiviy(actModel);

		actModel.setActivityId(activityId);

		if (reqObj.has("comments"))
			actModel.setComments(convertReqDataToCommentModel(reqObj, MessageConstants.DAILYVISIT));

		if (activityId != 0) {
			persistComments(actModel.getComments(), MessageConstants.DAILYVISIT);
			return activityId;
		}
		return row;
	}

	private int persistDataInCRMActiviy(ActivityModel actModel) {

		int rowCount = 0;
		String query = "INSERT INTO crm_acitvity (userId,meetingType,activityDate,activityTyp,dealerCode,dealerName,"
				+ "districId,venue,inviteesNo,attendeesNo,giftDistributionNo,giftType,expenseByEmp,expenseByPart,"
				+ "isMtgInBdg,isActive,reason,vendorCode,imgUrl,location)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			con = DBUtils.generateConnection();

			if (con != null) {

				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

				ps.setInt(1, actModel.getUserId());

				ps.setString(2, actModel.getMeetingType());

				ps.setString(3, actModel.getActivityDate());

				ps.setString(4, actModel.getActivityTyp());

				ps.setString(5, actModel.getDealerCode());

				ps.setString(6, actModel.getDealerName());

				ps.setString(7, actModel.getDistricId());

				ps.setString(8, actModel.getVenue());

				ps.setString(9, actModel.getInviteesNo());

				ps.setString(10, actModel.getAttendeesNo());

				ps.setString(11, actModel.getGiftDistributionNo());

				ps.setString(12, actModel.getGiftType());

				ps.setString(13, actModel.getExpenseByEmp());

				ps.setString(14, actModel.getExpenseByPart());

				ps.setBoolean(15, actModel.isMtgInBdg());

				ps.setBoolean(16, actModel.isActive());

				ps.setString(17, actModel.getReason());

				ps.setString(18, actModel.getVendorCode());

				ps.setString(19, actModel.getImgUrl());

				ps.setString(20, actModel.getLocation());

				rowCount = ps.executeUpdate();

				ResultSet rsKey = ps.getGeneratedKeys();

				while (rsKey != null && rsKey.next()) {
					activityId = rsKey.getInt(1);
				}

				persistComments(actModel.getComments(), MessageConstants.CRMACTIVITY);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;

		} finally {

			try {
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return rowCount;
	}

	@Override
	public int insertDataInCST(JsonNode reqObj) {
		log.debug("insetDataInCST");
		
		cstId = 0;
		int rowCount = 0;
		
		CSTModel cstModel = new CSTModel();
		
		if(reqObj.has("userId"))
			cstModel.setUserId(reqObj.get("userId").asInt());

		if(reqObj.has("attendedBy"))
			cstModel.setAttendedBy(reqObj.get("attendedBy").asText());

		if(reqObj.has("empCode"))
			cstModel.setEmpCode(reqObj.get("empCode").asText());

		if(reqObj.has("empName"))
			cstModel.setEmpName(reqObj.get("empName").asText());

		if(reqObj.has("ihb"))
			cstModel.setIhb(reqObj.get("ihb").asText());

		if(reqObj.has("siltTest"))
			cstModel.setSiltTest(reqObj.get("siltTest").asText());

		if(reqObj.has("mixDesign"))
			cstModel.setMixDesign(reqObj.get("mixDesign").asText());

		if(reqObj.has("tips"))
			cstModel.setTips(reqObj.get("tips").asBoolean());

		if(reqObj.has("blockService"))
			cstModel.setBlockService(reqObj.get("blockService").asBoolean());

		if(reqObj.has("maskingTape"))
			cstModel.setMaskingTape(reqObj.get("maskingTape").asBoolean());

		if(reqObj.has("concerted"))
			cstModel.setConcerted(reqObj.get("concerted").asText());

		if(reqObj.has("confertedFrom"))
			cstModel.setConfertedFrom(reqObj.get("confertedFrom").asText());

		if(reqObj.has("bagUsed"))
			cstModel.setBagUsed(reqObj.get("bagUsed").asText());

		if(reqObj.has("prductUsed"))
			cstModel.setPrductUsed(reqObj.get("prductUsed").asText());

		if(reqObj.has("others"))
			cstModel.setOthers(reqObj.get("others").asText());

		if(reqObj.has("giftDistribution"))
			cstModel.setGiftDistribution(reqObj.get("giftDistribution").asText());

		if(reqObj.has("imgUrl"))
			cstModel.setImgUrl(reqObj.get("imgUrl").asText());

		if(reqObj.has("checklistUrl"))
			cstModel.setChecklistUrl(reqObj.get("checklistUrl").asText());

		if(reqObj.has("appointmentScheduled"))
			cstModel.setAppointmentScheduled(reqObj.get("appointmentScheduled").asText());

		if(reqObj.has("deliteCodeTo"))
			cstModel.setDeliteCodeTo(reqObj.get("deliteCodeTo").asText());

		if(reqObj.has("deliteCode"))
			cstModel.setDeliteCode(reqObj.get("deliteCode").asText());

		if(reqObj.has("location"))
			cstModel.setLocation(reqObj.get("location").asText());
		
		cstModel.setActive(true);
		
		int row = persistDataInCST(cstModel);
		
		if(cstId != 0) {
			cstModel.setComments(convertReqDataToCommentModel(reqObj, MessageConstants.CST));
		}
		//if(reqObj.has("comments"))
			//cstModel.setComments(reqObj.get("comments").asText());

		return 0;
	}

	private int persistDataInCST(CSTModel cstModel) {
		log.debug("persisDataInCST");
		
		int rowCount = 0;
		
		String query = "INSERT INTO cst (userId,attendedBy,empCode,empName,ihb,siltTest,mixDesign,tips,"
				+ "blockService,maskingTape,concerted,confertedFrom,bagUsed,prductUsed,others,"
				+ "giftDistribution,comments,imgUrl,checklistUrl,appointmentScheduled,"
				+ "deliteCodeTo,deliteCode,location" + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			con = DBUtils.generateConnection();
			if (con != null) {
				
				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

				ps.setInt(1, cstModel.getUserId());

				ps.setString(2, cstModel.getAttendedBy());

				ps.setString(3, cstModel.getEmpCode());

				ps.setString(4, cstModel.getEmpName());

				ps.setString(5, cstModel.getIhb());

				ps.setString(6, cstModel.getSiltTest());

				ps.setString(7, cstModel.getMixDesign());

				ps.setBoolean(8, cstModel.isTips());

				ps.setBoolean(9, cstModel.isBlockService());

				ps.setBoolean(10, cstModel.isMaskingTape());

				ps.setString(11, cstModel.getConcerted());

				ps.setString(12, cstModel.getConfertedFrom());

				ps.setString(13, cstModel.getBagUsed());

				ps.setString(14, cstModel.getPrductUsed());

				ps.setString(15, cstModel.getOthers());

				ps.setString(16, cstModel.getGiftDistribution());

				ps.setString(17, cstModel.getImgUrl());

				ps.setString(18, cstModel.getChecklistUrl());

				ps.setString(19, cstModel.getAppointmentScheduled());

				ps.setString(20, cstModel.getDeliteCodeTo());

				ps.setString(21, cstModel.getDeliteCode());

				ps.setString(22, cstModel.getLocation());
				
				ps.setBoolean(23, cstModel.isActive());
				
				rowCount = ps.executeUpdate();
				
				ResultSet rkeys = ps.getGeneratedKeys();
				if(rkeys != null && rkeys.next()) {
					cstId = rkeys.getInt(1);
				}
				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowCount;
	}

	@Override
	public List<ComplaintModel> getComplaintList(int userId) {
		log.debug("getComplaintList");
		
		String query = "SELECT * FROM complaint WHERE userId = '"+userId+"' AND isActive = true";
		
		List<ComplaintModel> respList = new ArrayList<>();
		
		try {
		
			con = DBUtils.generateConnection();
			
			if(con != null) {
			
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs != null && rs.next()) {
					
					ComplaintModel compModel = new ComplaintModel();
					
					compModel.setComplaintId(rs.getInt("complaintId"));

					compModel.setUserId(rs.getInt("userId"));

					compModel.setComplaintType(rs.getString("complaintType") != null ? rs.getString("complaintType") : "n/a");

					compModel.setBrandName(rs.getString("brandName") != null ? rs.getString("brandName") : "n/a");

					compModel.setProductType(rs.getString("productType") != null ? rs.getString("productType") : "n/a");

					compModel.setPlant(rs.getString("plant") != null ? rs.getString("plant") : "n/a");

					compModel.setDistricId(rs.getInt("districId"));

					compModel.setRemark(rs.getString("remark") != null ? rs.getString("remark") : "n/a");

					compModel.setLocation(rs.getString("location") != null ? rs.getString("location") : "n/a");
					
					compModel.setActive(rs.getBoolean("isActive"));

					compModel.setCreatedOn(rs.getString("createdOn") != null ? rs.getString("createdOn") : "n/a");

					compModel.setUpdatedOn(rs.getString("updatedOn") != null ? rs.getString("updatedOn") : "n/a");
				
					respList.add(compModel);
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}finally {
			
			try {
				con.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return respList;
	}

	@Override
	public int insertDataInTechExpMonitoringVehicle(JsonNode reqObj) {
		log.debug("insertDataInTechExpMonitoringVehicle");
		
		int rowCount = 0;
		
		TechExpMonVehicleModel expVehiModel = new TechExpMonVehicleModel();
		
		if(reqObj.has("userId"))
			expVehiModel.setUserId(reqObj.get("userId").asInt());

		if(reqObj.has("vehicleId"))
			expVehiModel.setVehicleId(reqObj.get("vehicleId").asInt());

		if(reqObj.has("date"))
			expVehiModel.setDate(reqObj.get("date").asText());

		if(reqObj.has("openingKm"))
			expVehiModel.setOpeningKm(reqObj.get("openingKm").asText());

		if(reqObj.has("closingKm"))
			expVehiModel.setClosingKm(reqObj.get("closingKm").asText());

		if(reqObj.has("kmRun"))
			expVehiModel.setKmRun(reqObj.get("kmRun").asText());

		if(reqObj.has("cumulativeKm"))
			expVehiModel.setCumulativeKm(reqObj.get("cumulativeKm").asText());

		if(reqObj.has("location"))
			expVehiModel.setLocation(reqObj.get("location").asText());

		String query = "INSERT INTO techexp_monvehicle (userId,vehicleId,date,openingKm,"
				+ "closingKm,kmRun,cumulativeKm,location) VALUES (?,?,?,?,?,?,?,?)";
		try {
			con = DBUtils.generateConnection();
			
			if(con != null) {
				
				PreparedStatement ps = con.prepareStatement(query);

				ps.setInt(1, expVehiModel.getUserId());

				ps.setInt(2, expVehiModel.getVehicleId());

				ps.setString(3, expVehiModel.getDate());

				ps.setString(4, expVehiModel.getOpeningKm());

				ps.setString(5, expVehiModel.getClosingKm());

				ps.setString(6, expVehiModel.getKmRun());

				ps.setString(7, expVehiModel.getCumulativeKm());

				ps.setString(8, expVehiModel.getLocation());
				
				rowCount = ps.executeUpdate();
				
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return 0;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rowCount;
	}

	@Override
	public List<CommentModel> getCommentList(int id, String commentType) {
		log.debug("getCommentList");
		log.debug("::: commentType :::::::::"+commentType);
		return getCommentModel(id, commentType);
	}

	@Override
	public List<TechExpMonVehicleModel> getTechExpMonVehicleList(int userId) {
		log.debug("getTechExpMonVehicleList");
		
		String query = "SELECT * FROM techexp_monvehicle WHERE userId = '"+userId+"' AND isActive = true";
		
		List<TechExpMonVehicleModel> respList = new ArrayList<>();
		
		try {
			
			con = DBUtils.generateConnection();
			
			if(con != null) {
			
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs != null && rs.next()) {
					
					TechExpMonVehicleModel texpModel = new TechExpMonVehicleModel();
					
					texpModel.setId(rs.getInt("id"));

					texpModel.setUserId(rs.getInt("userId"));

					texpModel.setVehicleId(rs.getInt("vehicleId"));

					texpModel.setDate(rs.getString("date") != null ? rs.getString("date") : "n/a");

					texpModel.setOpeningKm(rs.getString("openingKm") != null ? rs.getString("openingKm") : "n/a");

					texpModel.setClosingKm(rs.getString("closingKm") != null ? rs.getString("closingKm") : "n/a");

					texpModel.setKmRun(rs.getString("kmRun") != null ? rs.getString("kmRun") : "n/a");

					texpModel.setCumulativeKm(rs.getString("cumulativeKm") != null ? rs.getString("cumulativeKm") : "n/a");

					texpModel.setLocation(rs.getString("location") != null ? rs.getString("location") : "n/a");

					texpModel.setCreatedOn(rs.getString("createdOn") != null ? rs.getString("createdOn") : "n/a");

					texpModel.setUpdatedOn(rs.getString("updatedOn") != null ? rs.getString("updatedOn") : "n/a");
					
					respList.add(texpModel);
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return respList;
	}

	@Override
	public List<CSTModel> getCSTList(int userId) {
		log.debug("getCSTList");
		String query = "SELECT * FROM cst WHERE userId = '"+userId+"' AND isActive = true";
		List<CSTModel> respList = new ArrayList<>();
		try {
			con = DBUtils.generateConnection();
			if(con != null) {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs != null && rs.next()) {
					CSTModel cstModel = new CSTModel();
					
					cstModel.setCstId(rs.getInt("cstId"));

					cstModel.setUserId(rs.getInt("userId"));

					cstModel.setAttendedBy(rs.getString("attendedBy") != null ? rs.getString("attendedBy") : "n/a");

					cstModel.setEmpCode(rs.getString("empCode") != null ? rs.getString("empCode") : "n/a");

					cstModel.setEmpName(rs.getString("empName") != null ? rs.getString("empName") : "n/a");

					cstModel.setIhb(rs.getString("ihb") != null ? rs.getString("ihb") : "n/a");

					cstModel.setSiltTest(rs.getString("siltTest") != null ? rs.getString("siltTest") : "n/a");

					cstModel.setMixDesign(rs.getString("mixDesign") != null ? rs.getString("mixDesign") : "n/a");

					cstModel.setTips(rs.getBoolean("tips"));

					cstModel.setBlockService(rs.getBoolean("blockService"));

					cstModel.setMaskingTape(rs.getBoolean("maskingTape"));

					cstModel.setConcerted(rs.getString("concerted") != null ? rs.getString("concerted") : "n/a");

					cstModel.setConfertedFrom(rs.getString("confertedFrom") != null ? rs.getString("confertedFrom") : "n/a");

					cstModel.setBagUsed(rs.getString("bagUsed") != null ? rs.getString("bagUsed") : "n/a");

					cstModel.setPrductUsed(rs.getString("prductUsed") != null ? rs.getString("prductUsed") : "n/a");

					cstModel.setOthers(rs.getString("others") != null ? rs.getString("others") : "n/a");

					cstModel.setGiftDistribution(rs.getString("giftDistribution") != null ? rs.getString("giftDistribution") : "n/a");

					cstModel.setComments(getCommentList(rs.getInt("cstId"), MessageConstants.CST));

					cstModel.setImgUrl(rs.getString("imgUrl") != null ? rs.getString("imgUrl") : "n/a");

					cstModel.setChecklistUrl(rs.getString("checklistUrl") != null ? rs.getString("checklistUrl") : "n/a");

					cstModel.setAppointmentScheduled(rs.getString("appointmentScheduled") != null ? rs.getString("appointmentScheduled") : "n/a");

					cstModel.setDeliteCodeTo(rs.getString("deliteCodeTo") != null ? rs.getString("deliteCodeTo") : "n/a");

					cstModel.setDeliteCode(rs.getString("deliteCode") != null ? rs.getString("deliteCode") : "n/a");

					cstModel.setLocation(rs.getString("location") != null ? rs.getString("location") : "n/a");
					
					cstModel.setActive(rs.getBoolean("isActive"));

					cstModel.setCreatedOn(rs.getString("createdOn") != null ? rs.getString("createdOn") : "n/a");

					cstModel.setUpdatedOn(rs.getString("updatedOn") != null ? rs.getString("updatedOn") : "n/a");
					
					respList.add(cstModel);
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return respList;
	}

	@Override
	public Object getCSTById(int cstId) {
		log.debug("getCSTById");
		String query = "SELECT * FROM cst WHERE userId = '"+userId+"' AND isActive = true";
		CSTModel cstModel = new CSTModel();
		try {
			con = DBUtils.generateConnection();
			
			if(con != null) {
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs != null && rs.next()) {
					cstModel.setCstId(rs.getInt("cstId"));

					cstModel.setUserId(rs.getInt("userId"));

					cstModel.setAttendedBy(rs.getString("attendedBy") != null ? rs.getString("attendedBy") : "n/a");

					cstModel.setEmpCode(rs.getString("empCode") != null ? rs.getString("empCode") : "n/a");

					cstModel.setEmpName(rs.getString("empName") != null ? rs.getString("empName") : "n/a");

					cstModel.setIhb(rs.getString("ihb") != null ? rs.getString("ihb") : "n/a");

					cstModel.setSiltTest(rs.getString("siltTest") != null ? rs.getString("siltTest") : "n/a");

					cstModel.setMixDesign(rs.getString("mixDesign") != null ? rs.getString("mixDesign") : "n/a");

					cstModel.setTips(rs.getBoolean("tips"));

					cstModel.setBlockService(rs.getBoolean("blockService"));

					cstModel.setMaskingTape(rs.getBoolean("maskingTape"));

					cstModel.setConcerted(rs.getString("concerted") != null ? rs.getString("concerted") : "n/a");

					cstModel.setConfertedFrom(rs.getString("confertedFrom") != null ? rs.getString("confertedFrom") : "n/a");

					cstModel.setBagUsed(rs.getString("bagUsed") != null ? rs.getString("bagUsed") : "n/a");

					cstModel.setPrductUsed(rs.getString("prductUsed") != null ? rs.getString("prductUsed") : "n/a");

					cstModel.setOthers(rs.getString("others") != null ? rs.getString("others") : "n/a");

					cstModel.setGiftDistribution(rs.getString("giftDistribution") != null ? rs.getString("giftDistribution") : "n/a");

					cstModel.setComments(getCommentList(rs.getInt("cstId"), MessageConstants.CST));

					cstModel.setImgUrl(rs.getString("imgUrl") != null ? rs.getString("imgUrl") : "n/a");

					cstModel.setChecklistUrl(rs.getString("checklistUrl") != null ? rs.getString("checklistUrl") : "n/a");

					cstModel.setAppointmentScheduled(rs.getString("appointmentScheduled") != null ? rs.getString("appointmentScheduled") : "n/a");

					cstModel.setDeliteCodeTo(rs.getString("deliteCodeTo") != null ? rs.getString("deliteCodeTo") : "n/a");

					cstModel.setDeliteCode(rs.getString("deliteCode") != null ? rs.getString("deliteCode") : "n/a");

					cstModel.setLocation(rs.getString("location") != null ? rs.getString("location") : "n/a");

					cstModel.setCreatedOn(rs.getString("createdOn") != null ? rs.getString("createdOn") : "n/a");

					cstModel.setUpdatedOn(rs.getString("updatedOn") != null ? rs.getString("updatedOn") : "n/a");
					
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return cstModel;
	}

	@Override
	public List<ActivityModel> getCRMActivity(int userId) {
		log.debug("getCRMActivity");

		String query = "SELECT * FROM crm_acitvity WHERE userId = '" + userId + "' AND isActive = true";

		List<ActivityModel> respList = new ArrayList<>();

		try {

			con = DBUtils.generateConnection();

			if (con != null) {

				Statement stmt = con.createStatement();

				ResultSet rs = stmt.executeQuery(query);

				while (rs != null && rs.next()) {
					
					ActivityModel actModel = new ActivityModel();
					
					actModel.setActivityId(rs.getInt("activityId"));

					actModel.setUserId(rs.getInt("userId"));

					actModel.setMeetingType(rs.getString("meetingType") != null ? rs.getString("meetingType") : "n/a");

					actModel.setActivityDate(rs.getString("activityDate") != null ? rs.getString("activityDate") : "n/a");

					actModel.setActivityTyp(rs.getString("activityTyp") != null ? rs.getString("activityTyp") : "n/a");

					actModel.setDealerCode(rs.getString("dealerCode") != null ? rs.getString("dealerCode") : "n/a");

					actModel.setDealerName(rs.getString("dealerName") != null ? rs.getString("dealerName") : "n/a");

					actModel.setDistricId(rs.getString("districId") != null ? rs.getString("districId") : "n/a");

					actModel.setVenue(rs.getString("venue") != null ? rs.getString("venue") : "n/a");

					actModel.setInviteesNo(rs.getString("inviteesNo") != null ? rs.getString("inviteesNo") : "n/a");

					actModel.setAttendeesNo(rs.getString("attendeesNo") != null ? rs.getString("attendeesNo") : "n/a");

					actModel.setGiftDistributionNo(rs.getString("giftDistributionNo") != null ? rs.getString("giftDistributionNo") : "n/a");

					actModel.setGiftType(rs.getString("giftType") != null ? rs.getString("giftType") : "n/a");

					actModel.setExpenseByEmp(rs.getString("expenseByEmp") != null ? rs.getString("expenseByEmp") : "n/a");

					actModel.setExpenseByPart(rs.getString("expenseByPart") != null ? rs.getString("expenseByPart") : "n/a");

					actModel.setMtgInBdg(rs.getBoolean("isMtgInBdg"));

					actModel.setActive(rs.getBoolean("isActive"));

					actModel.setReason(rs.getString("reason") != null ? rs.getString("reason") : "n/a");

					actModel.setVendorCode(rs.getString("vendorCode") != null ? rs.getString("vendorCode") : "n/a");

					actModel.setImgUrl(rs.getString("imgUrl") != null ? rs.getString("imgUrl") : "n/a");

					actModel.setLocation(rs.getString("location") != null ? rs.getString("location") : "n/a");

					//actModel.setComments(getCommentModel(rs.getInt("activityId"), MessageConstants.CRMACTIVITY));

					actModel.setCreatedOn(rs.getString("createdOn") != null ? rs.getString("createdOn") : "n/a");

					actModel.setUpdatedOn(rs.getString("updatedOn") != null ? rs.getString("updatedOn") : "n/a");
					
					respList.add(actModel);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;

		} finally {

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return respList;
	}

	@Override
	public Object getCRMActivityById(int activityId) {
		log.debug("getCSTById");
		String query = "SELECT * FROM crm_activity WHERE activityId = '"+activityId+"' AND isActive = true";
		ActivityModel actModel = new ActivityModel();
		try {
			con = DBUtils.generateConnection();
			
			if(con != null) {
				
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs != null && rs.next()) {
					
					actModel.setActivityId(rs.getInt("activityId"));

					actModel.setUserId(rs.getInt("userId"));

					actModel.setMeetingType(rs.getString("meetingType") != null ? rs.getString("meetingType") : "n/a");

					actModel.setActivityDate(rs.getString("activityDate") != null ? rs.getString("activityDate") : "n/a");

					actModel.setActivityTyp(rs.getString("activityTyp") != null ? rs.getString("activityTyp") : "n/a");

					actModel.setDealerCode(rs.getString("dealerCode") != null ? rs.getString("dealerCode") : "n/a");

					actModel.setDealerName(rs.getString("dealerName") != null ? rs.getString("dealerName") : "n/a");

					actModel.setDistricId(rs.getString("districId") != null ? rs.getString("districId") : "n/a");

					actModel.setVenue(rs.getString("venue") != null ? rs.getString("venue") : "n/a");

					actModel.setInviteesNo(rs.getString("inviteesNo") != null ? rs.getString("inviteesNo") : "n/a");

					actModel.setAttendeesNo(rs.getString("attendeesNo") != null ? rs.getString("attendeesNo") : "n/a");

					actModel.setGiftDistributionNo(rs.getString("giftDistributionNo") != null ? rs.getString("giftDistributionNo") : "n/a");

					actModel.setGiftType(rs.getString("giftType") != null ? rs.getString("giftType") : "n/a");

					actModel.setExpenseByEmp(rs.getString("expenseByEmp") != null ? rs.getString("expenseByEmp") : "n/a");

					actModel.setExpenseByPart(rs.getString("expenseByPart") != null ? rs.getString("expenseByPart") : "n/a");

					actModel.setMtgInBdg(rs.getBoolean("isMtgInBdg"));

					actModel.setActive(rs.getBoolean("isActive"));

					actModel.setReason(rs.getString("reason") != null ? rs.getString("reason") : "n/a");

					actModel.setVendorCode(rs.getString("vendorCode") != null ? rs.getString("vendorCode") : "n/a");

					actModel.setImgUrl(rs.getString("imgUrl") != null ? rs.getString("imgUrl") : "n/a");

					actModel.setLocation(rs.getString("location") != null ? rs.getString("location") : "n/a");

					actModel.setComments(getCommentModel(activityId, MessageConstants.CRMACTIVITY));

					actModel.setCreatedOn(rs.getString("createdOn") != null ? rs.getString("createdOn") : "n/a");

					actModel.setUpdatedOn(rs.getString("updatedOn") != null ? rs.getString("updatedOn") : "n/a");
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return actModel;
	}
	
	/*
	 * copy
	 * public List<ComplaintModel> getComplaintList(int userId) {
		log.debug("getComplaintList");
		
		String query = "SELECT * FROM complaint WHERE userId = '"+userId+"' AND isActive = true";
		
		List<ComplaintModel> respList = new ArrayList<>();
		
		try {
		
			con = DBUtils.generateConnection();
			
			if(con != null) {
			
				Statement stmt = con.createStatement();
				
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs != null && rs.next()) {
					
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return respList;
	}*/

}
