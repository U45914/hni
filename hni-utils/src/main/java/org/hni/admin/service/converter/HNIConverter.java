package org.hni.admin.service.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.dto.HniServicesDto;
import org.hni.common.Constants;
import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.organization.om.HniServices;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HNIConverter {
	private static final Logger logger = LoggerFactory.getLogger(HNIConverter.class);

	public static final String USERID = "userId";

	private static ObjectMapper mapper = new ObjectMapper();

	public static Collection<HniServicesDto> convertToServiceDtos(Collection<HniServices> hniServices) {
		Collection<HniServicesDto> hniServicesDtoList = new ArrayList<>();
		for (HniServices hniService : hniServices) {
			HniServicesDto hniServiceDto = new HniServicesDto();
			hniServiceDto.setServiceName(hniService.getServiceName());
			hniServiceDto.setServicePath(hniService.getServicePath());
			hniServiceDto.setServiceImg(hniService.getServiceImg());
			hniServiceDto.setActive(hniService.getActive());

			hniServicesDtoList.add(hniServiceDto);
		}
		return hniServicesDtoList;
	}

	/**
	 * Method For Building NGO object from JSON
	 * 
	 * @param objectNode
	 * @return
	 */
	public static Ngo getNGOFromJson(ObjectNode objectNode) {
		JsonNode overviewNode = objectNode.get("overview");
		JsonNode serviceNode = objectNode.get("service");
		JsonNode clientNode = objectNode.get("client");

		Ngo ngo = new Ngo();
			ngo.setUserId(2L);
			ngo.setAddressId(1L);
		ngo.setWebsite(overviewNode.get("website").asText());
		ngo.setOverview(overviewNode.get("overview").asText());
		ngo.setMission(overviewNode.get("mission").asText());

		ngo.setMonthlyBudget(serviceNode.get("monthlyBudget").asInt());
		ngo.setOperatingCost(serviceNode.get("operatingCost").asInt());
		ngo.setPersonalCost(serviceNode.get("personalCost").asInt());
		ngo.setKitchenVolunteers(serviceNode.get("volunteerNbr").asInt());
		ngo.setFoodStampAssist(serviceNode.get("foodStamp").asInt());
		ngo.setFoodBank(serviceNode.get("foodBankSelect").asInt());

			ngo.setResourcesToClients(1);
		ngo.setIndidualsServedDaily(clientNode.get("individualsServedDaily").asInt());
		ngo.setIndividualsServedMonthly(clientNode.get("individualsServedMonthly").asInt());
		ngo.setIndividualsServedAnnual(clientNode.get("individualsServedAnnually").asInt());
		ngo.setClientInfo(clientNode.get("individualClientInfoCollected").asInt());
			ngo.setStoreClientInfo("");
		ngo.setClientsUnSheltered(clientNode.get("unshelteredClientPercentage").asInt());
		ngo.setClientsEmployed(clientNode.get("employeedClientPercentage").asInt());
		ngo.setCreated(new Date());
		ngo.setCreatedBy((Long) ThreadContext.get(Constants.USERID));

		return ngo;
	}

	/**
	 * Method For Building BoardMember from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<BoardMember> getBoardMembersFromJson(ObjectNode objectNode, Long ngoId) {
		List<BoardMember> boardMembers = new ArrayList<>();
		JsonNode boardMemberArray = objectNode.get("stakeHolder").get("boardMembers");
		if (boardMemberArray.isArray()) {
			Iterator<JsonNode> boardMemberItr = boardMemberArray.iterator();
			while (boardMemberItr.hasNext()) {
				JsonNode boardMemberJSON = boardMemberItr.next();

				BoardMember boardMember = new BoardMember();
				boardMember.setNgo_id(ngoId);
				boardMember.setFirstName(boardMemberJSON.get("name").asText());
					boardMember.setLastName("");
				boardMember.setCompany(boardMemberJSON.get("company").asText());
				boardMember.setCreated(new Date());
				boardMember.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				boardMember.setNgo_id(ngoId);
				boardMembers.add(boardMember);
			}
		}
		return boardMembers;
	}

	/**
	 * Method For Building BrandPartners from JSON
	 * 
	 * @param objectNode
	 * @param ngo_id
	 * @return
	 */
	public static List<BrandPartner> getBrandPartnersFromJson(ObjectNode objectNode, Long ngo_id) {
		List<BrandPartner> brandPartners = new ArrayList<>();
		JsonNode brandPartnerArray = objectNode.get("stakeHolder").get("brandPartners");
		if (brandPartnerArray.isArray()) {
			Iterator<JsonNode> brandPartnerItr = brandPartnerArray.iterator();
			while (brandPartnerItr.hasNext()) {
				JsonNode brandPartnerJSON = brandPartnerItr.next();

				BrandPartner brandPartner = new BrandPartner();
				brandPartner.setNgo_id(ngo_id);
				brandPartner.setPhone(String.valueOf(brandPartnerJSON.get("phoneNumber").asInt()));
				brandPartner.setCompany(brandPartnerJSON.get("company").asText());
				brandPartner.setCreated(new Date());
				brandPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				brandPartners.add(brandPartner);
			}
		}
		return brandPartners;
	}

	/**
	 * Method For Building LocalPartners from JSON
	 * 
	 * @param objectNode
	 * @param ngo
	 *            Id
	 * @return
	 */
	public static List<LocalPartner> getLocalPartnersFromJson(ObjectNode objectNode, Long ngoId) {
		List<LocalPartner> localPartners = new ArrayList<>();
		JsonNode localPartnerArray = objectNode.get("stakeHolder").get("localPartners");
		if (localPartnerArray.isArray()) {
			Iterator<JsonNode> localPartnerItr = localPartnerArray.iterator();
			while (localPartnerItr.hasNext()) {
				JsonNode localPartnerJSON = localPartnerItr.next();

				LocalPartner localPartner = new LocalPartner();
				localPartner.setNgo_id(ngoId);
				localPartner.setPhone(String.valueOf(localPartnerJSON.get("phoneNumber").asInt()));
				localPartner.setCompany(localPartnerJSON.get("company").asText());
				localPartner.setCreated(new Date());
				localPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				localPartners.add(localPartner);
			}
		}
		return localPartners;
	}

	/**
	 * Method For Building FoodBank from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<FoodBank> getFoodBankFromJson(ObjectNode objectNode, Long ngoId) {
		List<FoodBank> foodBanks = new ArrayList<>();
		JsonNode serviceNode = objectNode.get("service");

		if (serviceNode.get("foodBankSelect").asBoolean()) {
			JsonNode foodBankArray = serviceNode.get("foodBankValue");
			if (foodBankArray.isArray()) {
				Iterator<JsonNode> foodBankItr = foodBankArray.iterator();
				while (foodBankItr.hasNext()) {
					FoodBank foodBank = new FoodBank();
					foodBank.setNgoId(ngoId);
					foodBank.setFoodBankName(foodBankItr.next().asText());
					foodBank.setCreated(new Date());
					foodBank.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
					foodBanks.add(foodBank);
				}
			}
		}
		return foodBanks;
	}

	/**
	 * Method For Building FoodService from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<FoodService> getFoodServicesFromJson(ObjectNode objectNode, Long ngoId) {
		List<FoodService> foodServices = new ArrayList<>();
		JsonNode serviceNode = objectNode.get("service");

		if (serviceNode.get("brkfstChk").asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.BREAKFAST_ID);
			foodService.setTotalCount(serviceNode.get("brkfstQty").asLong());
			foodService.setWeekdays(serviceNode.get("brkfstAvailabilty").asText());
				foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}
		if (serviceNode.get("lunchChk").asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.LUNCH_ID);
			foodService.setTotalCount(serviceNode.get("lunchQty").asLong());
			foodService.setWeekdays(serviceNode.get("lunchAvailabilty").asText());
				foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}
		if (serviceNode.get("dinnerChk").asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.DINNER_ID);
			foodService.setTotalCount(serviceNode.get("dinnerQty").asLong());
			foodService.setWeekdays(serviceNode.get("dinnerAvailabilty").asText());
				foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}

		return foodServices;
	}

	/**
	 * Method For Building MealDonationSource from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<MealDonationSource> getMealDonationSourceFromJson(ObjectNode objectNode, Long ngoId) {
		List<MealDonationSource> mealDonationSources = new ArrayList<>();
		JsonNode mealDonationSourceArray = objectNode.get("funding").get("mealDonation");
		if (mealDonationSourceArray.isArray()) {
			Iterator<JsonNode> mealDonationSourceItr = mealDonationSourceArray.iterator();
			while (mealDonationSourceItr.hasNext()) {
				JsonNode mealDonationSourceJSON = mealDonationSourceItr.next();
				MealDonationSource mealDonationSource = new MealDonationSource();
				mealDonationSource.setNgoId(ngoId);
				mealDonationSource.setSource(mealDonationSourceJSON.get("source").asText());
				mealDonationSource.setFrequency(mealDonationSourceJSON.get("frequency").asText());
				mealDonationSource.setCreated(new Date());
				mealDonationSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				mealDonationSources.add(mealDonationSource);
			}
		}
		return mealDonationSources;
	}

	/**
	 * Method For Building MealFundingSource from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<MealFundingSource> getMealFundingSourcesFromJson(ObjectNode objectNode, Long ngoId) {
		List<MealFundingSource> mealFundingSources = new ArrayList<>();
		JsonNode mealFundingSourceArray = objectNode.get("funding").get("mealFunding");
		if (mealFundingSourceArray.isArray()) {
			Iterator<JsonNode> mealFundingSourceItr = mealFundingSourceArray.iterator();
			while (mealFundingSourceItr.hasNext()) {
				JsonNode mealFundingSourceJSON = mealFundingSourceItr.next();
				MealFundingSource mealFundingSource = new MealFundingSource();
				mealFundingSource.setNgoId(ngoId);
				mealFundingSource.setAmount(mealFundingSourceJSON.get("amount").asDouble());
				mealFundingSource.setSource(mealFundingSourceJSON.get("source").asText());
				mealFundingSource.setCreated(new Date());
				mealFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				mealFundingSources.add(mealFundingSource);
			}
		}
		return mealFundingSources;
	}

	/**
	 * Method For Building NgoFundingSource from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<NgoFundingSource> getNgoFundingSourcesFromJson(ObjectNode objectNode, Long ngoId) {
		List<NgoFundingSource> ngoFundingSources = new ArrayList<>();
		JsonNode ngoFundingSourceArray = objectNode.get("funding").get("fundingSource");
		if (ngoFundingSourceArray.isArray()) {
			Iterator<JsonNode> ngoFundingSourceItr = ngoFundingSourceArray.iterator();
			while (ngoFundingSourceItr.hasNext()) {
				JsonNode ngoFundingSourceJSON = ngoFundingSourceItr.next();
				NgoFundingSource ngoFundingSource = new NgoFundingSource();
				ngoFundingSource.setNgoId(ngoId);
				ngoFundingSource.setAmount(ngoFundingSourceJSON.get("amount").asDouble());
				ngoFundingSource.setSource(ngoFundingSourceJSON.get("source").asText());
				ngoFundingSource.setCreated(new Date());
				ngoFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				ngoFundingSources.add(ngoFundingSource);
			}
		}
		return ngoFundingSources;
	}

	/**
	 * Method For Building NGO object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertNGOToJSON(Ngo ngo, ObjectNode parentJSON) {
		ObjectNode overview = mapper.createObjectNode();
		overview.put("website", ngo.getWebsite());
		overview.put("overview", ngo.getOverview());
		overview.put("mission", ngo.getMission());
		parentJSON.set("overview", overview);

		ObjectNode service = mapper.createObjectNode();
		service.put("monthlyBudget", ngo.getMonthlyBudget());
		service.put("operatingCost", ngo.getOperatingCost());
		service.put("personalCost", ngo.getPersonalCost());
		service.put("volunteerNbr", ngo.getKitchenVolunteers());
		service.put("foodBankSelect", ngo.getFoodBank());
		service.put("foodStamp", ngo.getFoodStampAssist());
		service.put("foodBankSelect", ngo.getFoodBank());
		parentJSON.set("service", service);

		ObjectNode client = mapper.createObjectNode();
		client.put("individualsServedDaily", ngo.getIndidualsServedDaily());
		client.put("individualsServedMonthly", ngo.getIndividualsServedMonthly());
		client.put("individualsServedAnnually", ngo.getIndividualsServedAnnual());
		client.put("individualClientInfoCollected", ngo.getClientInfo());
		client.put("unshelteredClientPercentage", ngo.getClientsUnSheltered());
		client.put("employeedClientPercentage", ngo.getClientsEmployed());
		parentJSON.set("client", client);

		return parentJSON;
	}

	/**
	 * Method For Building BoardMember object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertBoardMembersToJSON(List<BoardMember> boardMembers, ObjectNode parentJSON) {
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get("stakeHolder");
		ArrayNode boardMemberJSONArray = mapper.createArrayNode();
		boardMembers.forEach(bm -> {
			ObjectNode boardMemberJSON = mapper.createObjectNode();
			boardMemberJSON.put("company", bm.getCompany());
			boardMemberJSON.put("name", bm.getFirstName());
			boardMemberJSONArray.add(boardMemberJSON);
		});
		stakeHolderNode.set("boardMembers", boardMemberJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building BrandPartner object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertBrandPartnersToJSON(List<BrandPartner> brandPartners, ObjectNode parentJSON) {
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get("stakeHolder");
		ArrayNode brandPartnerJSONArray = mapper.createArrayNode();
		brandPartners.forEach(bp -> {
			ObjectNode brandPartnerJSON = mapper.createObjectNode();
			brandPartnerJSON.put("company", bp.getCompany());
			brandPartnerJSON.put("phoneNumber", bp.getPhone());
			brandPartnerJSONArray.add(brandPartnerJSON);
		});
		stakeHolderNode.set("brandPartners", brandPartnerJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building LocalPartner object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertLocalPartnerToJSON(List<LocalPartner> localPartners, ObjectNode parentJSON) {
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get("stakeHolder");
		ArrayNode localPartnerJSONArray = mapper.createArrayNode();
		localPartners.forEach(lp -> {
			ObjectNode localPartnerJSON = mapper.createObjectNode();
			localPartnerJSON.put("company", lp.getCompany());
			localPartnerJSON.put("phoneNumber", lp.getPhone());
			localPartnerJSONArray.add(localPartnerJSON);
		});
		stakeHolderNode.set("localPartners", localPartnerJSONArray);
		return parentJSON;
	}


	/**
	 * Method For Building FoodBank object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertFoodBanksToJSON(List<FoodBank> foodBanks, ObjectNode parentJSON) {
		ObjectNode serviceNode = (ObjectNode) parentJSON.get("service");
		if (!foodBanks.isEmpty()) {
			serviceNode.put("foodBankSelect", Boolean.TRUE);
			ArrayNode foodBankArray = new ObjectMapper().createArrayNode();
			for (int i = 0; i < foodBanks.size(); i++) {
				foodBankArray.add(foodBanks.get(i).getFoodBankName());
			}
			serviceNode.set("foodBankValue", foodBankArray);
		}
		return parentJSON;
	}

	/**
	 * Method For Building FoodService object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertFoodServiceToJSON(List<FoodService> foodServices, ObjectNode parentJSON) {
		ObjectNode serviceNode = (ObjectNode) parentJSON.get("service");
		foodServices.forEach(fs -> {
			if (fs.getServiceType().equals(1L)) {
				serviceNode.put("brkfstChk", true);
				serviceNode.put("brkfstQty", fs.getTotalCount());
				serviceNode.put("brkfstAvailabilty", fs.getWeekdays());
			}
			if (fs.getServiceType().equals(2L)) {
				serviceNode.put("lunchChk", true);
				serviceNode.put("lunchQty", fs.getTotalCount());
				serviceNode.put("lunchAvailabilty", fs.getWeekdays());
			}
			if (fs.getServiceType().equals(3L)) {
				serviceNode.put("dinnerChk", true);
				serviceNode.put("dinnerQty", fs.getTotalCount());
				serviceNode.put("dinnerAvailabilty", fs.getWeekdays());
			}
			if (fs.getServiceType().equals(4L)) {
				serviceNode.put("baggedChk", true);
				serviceNode.put("baggedQty", fs.getTotalCount());
			}
		});
		return parentJSON;
	}

	/**
	 * Method For Building MealDonationSource object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertMealDonationToJSON(List<MealDonationSource> mealDonations, ObjectNode parentJSON) {
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get("funding");
		ArrayNode mealDonationJSONArray = mapper.createArrayNode();
		mealDonations.forEach(md -> {
			ObjectNode mealDonationJSON = mapper.createObjectNode();
			mealDonationJSON.put("source", md.getSource());
			mealDonationJSON.put("frequency", md.getFrequency());
			mealDonationJSONArray.add(mealDonationJSON);
		});

		fundingJSON.set("mealDonation", mealDonationJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building MealFundingSource object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertMealFundingToJSON(List<MealFundingSource> mealFundings, ObjectNode parentJSON) {
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get("funding");
		ArrayNode mealFundingJSONArray = mapper.createArrayNode();
		mealFundings.forEach(mf -> {
			ObjectNode mealFundingJSON = mapper.createObjectNode();
			mealFundingJSON.put("source", mf.getSource());
			mealFundingJSON.put("amount", mf.getAmount());
			mealFundingJSONArray.add(mealFundingJSON);
		});

		fundingJSON.set("mealFunding", mealFundingJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building NgoFundingSource object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertFundingSourceToJSON(List<NgoFundingSource> fundingSources, ObjectNode parentJSON) {
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get("funding");
		ArrayNode fundingSourceJSONArray = mapper.createArrayNode();
		fundingSources.forEach(fs -> {
			ObjectNode fundingSourceJSON = mapper.createObjectNode();
			fundingSourceJSON.put("source", fs.getSource());
			fundingSourceJSON.put("amount", fs.getAmount());
			fundingSourceJSONArray.add(fundingSourceJSON);
		});

		fundingJSON.set("fundingSource", fundingSourceJSONArray);
		return parentJSON;
	}

}
