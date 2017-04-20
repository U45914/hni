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
import static org.hni.common.Constants.*;

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
		JsonNode overviewNode = objectNode.get(OVERVIEW);
		JsonNode serviceNode = objectNode.get(SERVICE);
		JsonNode clientNode = objectNode.get(CLIENT_NODE);

		Ngo ngo = new Ngo();
		ngo.setUserId((Long) ThreadContext.get(Constants.USERID));
			ngo.setAddressId(1L);
		ngo.setWebsite(overviewNode.get(WEBSITE).asText());
		ngo.setOverview(overviewNode.get(OVERVIEW).asText());
		ngo.setMission(overviewNode.get(MISSION).asText());

		ngo.setMonthlyBudget(serviceNode.get(MONTHLY_BUDGET).asInt());
		ngo.setOperatingCost(serviceNode.get(OPERATING_COST).asInt());
		ngo.setPersonalCost(serviceNode.get(PERSONAL_COST).asInt());
		ngo.setKitchenVolunteers(serviceNode.get(VOLUNTEER_NBR).asInt());
		ngo.setFoodStampAssist(serviceNode.get(FOOD_STAMP).asInt());
		ngo.setFoodBank(serviceNode.get(FOOD_BANK_SELECT).asInt());

			ngo.setResourcesToClients(1);
		ngo.setIndidualsServedDaily(clientNode.get(INDIVIDUALS_SERVED_DAILY).asInt());
		ngo.setIndividualsServedMonthly(clientNode.get(INDIVIDUALS_SERVED_MONTHLY).asInt());
		ngo.setIndividualsServedAnnual(clientNode.get(INDIVIDUALS_SERVED_ANNUALLY).asInt());
		ngo.setClientInfo(clientNode.get(INDIVIDUAL_CLIENT_INFO_COLLECTED).asInt());
			ngo.setStoreClientInfo("");
		ngo.setClientsUnSheltered(clientNode.get(UNSHELTERED_CLIENT_PERCENTAGE).asInt());
		ngo.setClientsEmployed(clientNode.get(EMPLOYEED_CLIENT_PERCENTAGE).asInt());
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
		JsonNode boardMemberArray = objectNode.get(STAKE_HOLDER).get(BOARD_MEMBERS);
		if (boardMemberArray.isArray()) {
			Iterator<JsonNode> boardMemberItr = boardMemberArray.iterator();
			while (boardMemberItr.hasNext()) {
				JsonNode boardMemberJSON = boardMemberItr.next();

				BoardMember boardMember = new BoardMember();
				boardMember.setNgo_id(ngoId);
				boardMember.setFirstName(boardMemberJSON.get(NAME).asText());
					boardMember.setLastName("");
				boardMember.setCompany(boardMemberJSON.get(COMPANY).asText());
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
		JsonNode brandPartnerArray = objectNode.get(STAKE_HOLDER).get(BRAND_PARTNERS);
		if (brandPartnerArray.isArray()) {
			Iterator<JsonNode> brandPartnerItr = brandPartnerArray.iterator();
			while (brandPartnerItr.hasNext()) {
				JsonNode brandPartnerJSON = brandPartnerItr.next();

				BrandPartner brandPartner = new BrandPartner();
				brandPartner.setNgo_id(ngo_id);
				brandPartner.setPhone(String.valueOf(brandPartnerJSON.get(PHONE_NUMBER).asInt()));
				brandPartner.setCompany(brandPartnerJSON.get(COMPANY).asText());
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
		JsonNode localPartnerArray = objectNode.get(STAKE_HOLDER).get(LOCAL_PARTNERS);
		if (localPartnerArray.isArray()) {
			Iterator<JsonNode> localPartnerItr = localPartnerArray.iterator();
			while (localPartnerItr.hasNext()) {
				JsonNode localPartnerJSON = localPartnerItr.next();

				LocalPartner localPartner = new LocalPartner();
				localPartner.setNgo_id(ngoId);
				localPartner.setPhone(String.valueOf(localPartnerJSON.get(PHONE_NUMBER).asInt()));
				localPartner.setCompany(localPartnerJSON.get(COMPANY).asText());
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
		JsonNode serviceNode = objectNode.get(SERVICE);

		if (serviceNode.get(FOOD_BANK_SELECT).asBoolean()) {
			JsonNode foodBankArray = serviceNode.get(FOOD_BANK_VALUE);
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
		JsonNode serviceNode = objectNode.get(SERVICE);

		if (serviceNode.get(BRKFST_CHK).asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.BREAKFAST_ID);
			foodService.setTotalCount(serviceNode.get(BRKFST_QTY).asLong());
			foodService.setWeekdays(serviceNode.get(BRKFST_AVAILABILTY).asText());
				foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}
		if (serviceNode.get(LUNCH_CHK).asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.LUNCH_ID);
			foodService.setTotalCount(serviceNode.get(LUNCH_QTY).asLong());
			foodService.setWeekdays(serviceNode.get(LUNCH_AVAILABILTY).asText());
				foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}
		if (serviceNode.get(DINNER_CHK).asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.DINNER_ID);
			foodService.setTotalCount(serviceNode.get(DINNER_QTY).asLong());
			foodService.setWeekdays(serviceNode.get(DINNER_AVAILABILTY).asText());
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
		JsonNode mealDonationSourceArray = objectNode.get(FUNDING).get(MEAL_DONATION);
		if (mealDonationSourceArray.isArray()) {
			Iterator<JsonNode> mealDonationSourceItr = mealDonationSourceArray.iterator();
			while (mealDonationSourceItr.hasNext()) {
				JsonNode mealDonationSourceJSON = mealDonationSourceItr.next();
				MealDonationSource mealDonationSource = new MealDonationSource();
				mealDonationSource.setNgoId(ngoId);
				mealDonationSource.setSource(mealDonationSourceJSON.get(SOURCE).asText());
				mealDonationSource.setFrequency(mealDonationSourceJSON.get(FREQUENCY).asText());
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
		JsonNode mealFundingSourceArray = objectNode.get(FUNDING).get(MEAL_FUNDING);
		if (mealFundingSourceArray.isArray()) {
			Iterator<JsonNode> mealFundingSourceItr = mealFundingSourceArray.iterator();
			while (mealFundingSourceItr.hasNext()) {
				JsonNode mealFundingSourceJSON = mealFundingSourceItr.next();
				MealFundingSource mealFundingSource = new MealFundingSource();
				mealFundingSource.setNgoId(ngoId);
				mealFundingSource.setAmount(mealFundingSourceJSON.get(AMOUNT).asDouble());
				mealFundingSource.setSource(mealFundingSourceJSON.get(SOURCE).asText());
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
		JsonNode ngoFundingSourceArray = objectNode.get(FUNDING).get(FUNDING_SOURCE);
		if (ngoFundingSourceArray.isArray()) {
			Iterator<JsonNode> ngoFundingSourceItr = ngoFundingSourceArray.iterator();
			while (ngoFundingSourceItr.hasNext()) {
				JsonNode ngoFundingSourceJSON = ngoFundingSourceItr.next();
				NgoFundingSource ngoFundingSource = new NgoFundingSource();
				ngoFundingSource.setNgoId(ngoId);
				ngoFundingSource.setAmount(ngoFundingSourceJSON.get(AMOUNT).asDouble());
				ngoFundingSource.setSource(ngoFundingSourceJSON.get(SOURCE).asText());
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
		overview.put(WEBSITE, ngo.getWebsite());
		overview.put(OVERVIEW, ngo.getOverview());
		overview.put(MISSION, ngo.getMission());
		parentJSON.set(OVERVIEW, overview);

		ObjectNode service = mapper.createObjectNode();
		service.put(MONTHLY_BUDGET, ngo.getMonthlyBudget());
		service.put(OPERATING_COST, ngo.getOperatingCost());
		service.put(PERSONAL_COST, ngo.getPersonalCost());
		service.put(VOLUNTEER_NBR, ngo.getKitchenVolunteers());
		service.put(FOOD_BANK_SELECT, ngo.getFoodBank());
		service.put(FOOD_STAMP, ngo.getFoodStampAssist());
		service.put(FOOD_BANK_SELECT, ngo.getFoodBank());
		parentJSON.set(SERVICE, service);

		ObjectNode client = mapper.createObjectNode();
		client.put(INDIVIDUALS_SERVED_DAILY, ngo.getIndidualsServedDaily());
		client.put(INDIVIDUALS_SERVED_MONTHLY, ngo.getIndividualsServedMonthly());
		client.put(INDIVIDUALS_SERVED_ANNUALLY, ngo.getIndividualsServedAnnual());
		client.put(INDIVIDUAL_CLIENT_INFO_COLLECTED, ngo.getClientInfo());
		client.put(UNSHELTERED_CLIENT_PERCENTAGE, ngo.getClientsUnSheltered());
		client.put(EMPLOYEED_CLIENT_PERCENTAGE, ngo.getClientsEmployed());
		parentJSON.set(CLIENT_NODE, client);

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
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get(STAKE_HOLDER);
		ArrayNode boardMemberJSONArray = mapper.createArrayNode();
		boardMembers.forEach(bm -> {
			ObjectNode boardMemberJSON = mapper.createObjectNode();
			boardMemberJSON.put(COMPANY, bm.getCompany());
			boardMemberJSON.put(NAME, bm.getFirstName());
			boardMemberJSONArray.add(boardMemberJSON);
		});
		stakeHolderNode.set(BOARD_MEMBERS, boardMemberJSONArray);
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
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get(STAKE_HOLDER);
		ArrayNode brandPartnerJSONArray = mapper.createArrayNode();
		brandPartners.forEach(bp -> {
			ObjectNode brandPartnerJSON = mapper.createObjectNode();
			brandPartnerJSON.put(COMPANY, bp.getCompany());
			brandPartnerJSON.put(PHONE_NUMBER, bp.getPhone());
			brandPartnerJSONArray.add(brandPartnerJSON);
		});
		stakeHolderNode.set(BRAND_PARTNERS, brandPartnerJSONArray);
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
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get(STAKE_HOLDER);
		ArrayNode localPartnerJSONArray = mapper.createArrayNode();
		localPartners.forEach(lp -> {
			ObjectNode localPartnerJSON = mapper.createObjectNode();
			localPartnerJSON.put(COMPANY, lp.getCompany());
			localPartnerJSON.put(PHONE_NUMBER, lp.getPhone());
			localPartnerJSONArray.add(localPartnerJSON);
		});
		stakeHolderNode.set(LOCAL_PARTNERS, localPartnerJSONArray);
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
		ObjectNode serviceNode = (ObjectNode) parentJSON.get(SERVICE);
		if (!foodBanks.isEmpty()) {
			serviceNode.put(FOOD_BANK_SELECT, Boolean.TRUE);
			ArrayNode foodBankArray = new ObjectMapper().createArrayNode();
			for (int i = 0; i < foodBanks.size(); i++) {
				foodBankArray.add(foodBanks.get(i).getFoodBankName());
			}
			serviceNode.set(FOOD_BANK_VALUE, foodBankArray);
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
		ObjectNode serviceNode = (ObjectNode) parentJSON.get(SERVICE);
		foodServices.forEach(fs -> {
			if (fs.getServiceType().equals(1L)) {
				serviceNode.put(BRKFST_CHK, true);
				serviceNode.put(BRKFST_QTY, fs.getTotalCount());
				serviceNode.put(BRKFST_AVAILABILTY, fs.getWeekdays());
			}
			if (fs.getServiceType().equals(2L)) {
				serviceNode.put(LUNCH_CHK, true);
				serviceNode.put(LUNCH_QTY, fs.getTotalCount());
				serviceNode.put(LUNCH_AVAILABILTY, fs.getWeekdays());
			}
			if (fs.getServiceType().equals(3L)) {
				serviceNode.put(DINNER_CHK, true);
				serviceNode.put(DINNER_QTY, fs.getTotalCount());
				serviceNode.put(DINNER_AVAILABILTY, fs.getWeekdays());
			}
			if (fs.getServiceType().equals(4L)) {
				serviceNode.put(BAGGED_CHK, true);
				serviceNode.put(BAGGED_QTY, fs.getTotalCount());
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
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get(FUNDING);
		ArrayNode mealDonationJSONArray = mapper.createArrayNode();
		mealDonations.forEach(md -> {
			ObjectNode mealDonationJSON = mapper.createObjectNode();
			mealDonationJSON.put(SOURCE, md.getSource());
			mealDonationJSON.put(FREQUENCY, md.getFrequency());
			mealDonationJSONArray.add(mealDonationJSON);
		});

		fundingJSON.set(MEAL_DONATION, mealDonationJSONArray);
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
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get(FUNDING);
		ArrayNode mealFundingJSONArray = mapper.createArrayNode();
		mealFundings.forEach(mf -> {
			ObjectNode mealFundingJSON = mapper.createObjectNode();
			mealFundingJSON.put(SOURCE, mf.getSource());
			mealFundingJSON.put(AMOUNT, mf.getAmount());
			mealFundingJSONArray.add(mealFundingJSON);
		});

		fundingJSON.set(MEAL_FUNDING, mealFundingJSONArray);
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
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get(FUNDING);
		ArrayNode fundingSourceJSONArray = mapper.createArrayNode();
		fundingSources.forEach(fs -> {
			ObjectNode fundingSourceJSON = mapper.createObjectNode();
			fundingSourceJSON.put(SOURCE, fs.getSource());
			fundingSourceJSON.put(AMOUNT, fs.getAmount());
			fundingSourceJSONArray.add(fundingSourceJSON);
		});

		fundingJSON.set(FUNDING_SOURCE, fundingSourceJSONArray);
		return parentJSON;
	}

}
