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
import org.hni.common.om.FoodServiceAvailability;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HNIConverter {
	private static final Logger logger = LoggerFactory.getLogger(HNIConverter.class);

	public static Collection<HniServicesDto> convertToServiceDtos(
			Collection<HniServices> hniServices) {

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

	public static List<BrandPartner> getBrandPartnersFromJson(ObjectNode objectNode, Long ngo_id) {
		List<BrandPartner> brandPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("stakeHolder").get("brandPartners");
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterateJson = jsonNode.iterator();
			while (iterateJson.hasNext()) {
				JsonNode brand = jsonNode.iterator().next();
				BrandPartner brandPartner = new BrandPartner();
				brandPartner.setNgo_id(ngo_id);
				brandPartner.setCompany(brand.get("company").textValue());
				brandPartner.setCreated(new Date());
				brandPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				// brandPartner.setId(brand.get("id").asLong());
				brandPartner.setPhone(brand.get("phoneNumber").textValue());

				brandPartners.add(brandPartner);
				iterateJson.next();
			}
		}
		return brandPartners;
	}
 
	public static Ngo getNGOFromJson(ObjectNode objectNode) {
		JsonNode jsonNode = objectNode.get("overview");
		JsonNode serviceNode = objectNode.get("service");
		JsonNode clientNode = objectNode.get("client");

		Ngo ngo = new Ngo();

		ngo.setCreated(new Date());
		ngo.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
		ngo.setMission(jsonNode.get("mission").textValue());
		ngo.setName(jsonNode.get("name").textValue());
		ngo.setOverview(jsonNode.get("overview").textValue());
		ngo.setPhone(jsonNode.get("phone").textValue());
		ngo.setWebsite(jsonNode.get("website").textValue());
		ngo.setFte(jsonNode.get("employees").asInt());

		ngo.setFoodBank(serviceNode.get("foodBankSelect").asBoolean());
		ngo.setKitchenVolunteers(serviceNode.get("volunteerNbr").asLong());
		ngo.setMonthlyBudget(serviceNode.get("monthlyBudget").asLong());
		ngo.setOperatingCost(serviceNode.get("operatingCost").asLong());
		ngo.setPersonalCost(serviceNode.get("personnelCost").asLong());

		ngo.setIndServAnnual(clientNode.get("individualsServedAnnually").asLong());
		ngo.setIndServDaily(clientNode.get("individualsServedDaily").asLong());
		ngo.setIndServMonthly(clientNode.get("individualsServedMonthly").asLong());
		ngo.setClientInfo(clientNode.get("individualClientInfoCollected").asBoolean());
		ngo.setClientsUnsheltered(clientNode.get("unshelteredClientPercentage").asLong());
		ngo.setClientsEmployed(clientNode.get("employeedClientPercentage").asLong());

		// Cannot retrieve data from json
		/*
		 * ngo.setEndorsementId(jsonNode.get("endorsementId").asLong());
		 * ngo.setFoodStampAssist(jsonNode.get("foodStampAssist").asBoolean());
		 * ngo
		 * .setResourcesToClients(jsonNode.get("resourcesToClients").asLong());
		 * ngo.setStoreClientInfo(jsonNode.get("storeClientInfo").textValue());
		 */

		return ngo;
	}

	public static List<BoardMember> getBoardMembersFromJson(ObjectNode objectNode, Long ngoId) {
		List<BoardMember> boardMembers = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("stakeHolder").get("boardMembers");
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterateJson = jsonNode.iterator();
			while (iterateJson.hasNext()) {
				JsonNode board = iterateJson.next();
				BoardMember boardMember = new BoardMember();
				boardMember.setNgo_id(ngoId);
				boardMember.setFirstName(board.get("name").textValue());
				boardMember.setCompany(board.get("company").textValue());
				boardMember.setCreated(new Date());
				boardMember.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				boardMember.setNgo_id(ngoId);
				boardMembers.add(boardMember);
			}
		}
		return boardMembers;
	}

	public static List<LocalPartner> getLocalPartnersFromJson(ObjectNode objectNode, Long ngoId) {
		List<LocalPartner> localPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("stakeHolder").get("localPartners");
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterateJson = jsonNode.iterator();
			while (iterateJson.hasNext()) {
				JsonNode local = iterateJson.next();
				LocalPartner localPartner = new LocalPartner();
				localPartner.setNgo_id(ngoId);
				localPartner.setCompany(local.get("company").textValue());
				localPartner.setCreated(new Date());
				localPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				localPartner.setPhone(local.get("phoneNumber").textValue());
				localPartner.setNgo_id(ngoId);
				localPartners.add(localPartner);
			}
		}
		return localPartners;
	}

	public static List<NgoFundingSource> getNgoFundingSourcesFromJson(
			ObjectNode objectNode, Long ngoId) {
		List<NgoFundingSource> ngoFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("funding").get("fundingSource");
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterateJson = jsonNode.iterator();
			while (iterateJson.hasNext()) {
				JsonNode fundSource = iterateJson.next();
				NgoFundingSource ngoFundingSource = new NgoFundingSource();
				ngoFundingSource.setNgoId(ngoId);
				ngoFundingSource.setCreated(new Date());
				ngoFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				ngoFundingSource.setAmount(fundSource.get("amount").asDouble());
				ngoFundingSource.setSource(fundSource.get("source").textValue());
				ngoFundingSource.setNgoId(ngoId);
				ngoFundingSources.add(ngoFundingSource);
			}
		}
		return ngoFundingSources;
	}

	public static List<MealFundingSource> getMealFundingSourcesFromJson(
			ObjectNode objectNode, Long ngoId) {
		List<MealFundingSource> mealFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("funding").get("mealFunding");
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterateJson = jsonNode.iterator();
			while (iterateJson.hasNext()) {
				JsonNode mealSource = iterateJson.next();
				MealFundingSource mealFundingSource = new MealFundingSource();
				mealFundingSource.setNgoId(ngoId);
				mealFundingSource.setCreated(new Date());
				mealFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				mealFundingSource.setAmount(mealSource.get("amount").asDouble());
				mealFundingSource.setSource(mealSource.get("source").textValue());
				mealFundingSource.setNgoId(ngoId);
				mealFundingSources.add(mealFundingSource);
			}
		}
		return mealFundingSources;
	}

	public static List<MealDonationSource> getMealDonationSourceFromJson(
			ObjectNode objectNode, Long ngoId) {
		List<MealDonationSource> MealDonationSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("funding").get("mealDonation");
		if (jsonNode.isArray()) {
			Iterator<JsonNode> iterateJson = jsonNode.iterator();
			while (iterateJson.hasNext()) {
				JsonNode mealSource = iterateJson.next();
				MealDonationSource mealDonationSource = new MealDonationSource();
				mealDonationSource.setNgoId(ngoId);
				mealDonationSource.setCreated(new Date());
				mealDonationSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				mealDonationSource.setSource(mealSource.get("source").textValue());
				// attribute 'mealQty' is missing here.
				mealDonationSource.setFrequency(mealSource.get("frequency").textValue());
				mealDonationSource.setNgoId(ngoId);
				MealDonationSources.add(mealDonationSource);
			}
		}
		return MealDonationSources;
	}

	public static List<FoodService> getFoodServicesFromJson(ObjectNode objectNode, Long ngoId) {
		List<FoodService> foodServices = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("service");
		
		if(jsonNode.get("brkfstChk").asBoolean()){
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long)ThreadContext.get(Constants.USERID));
			foodService.setTotalCount(jsonNode.get("brkfstQty").asLong());
			foodService.setServiceType(Constants.BREAKFAST_ID);
			foodService.setNgoId(ngoId);
			foodServices.add(foodService);
		}
		if(jsonNode.get("lunchChk").asBoolean()){
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long)ThreadContext.get(Constants.USERID));
			foodService.setTotalCount(jsonNode.get("lunchQty").asLong());
			foodService.setServiceType(Constants.LUNCH_ID);
			foodService.setNgoId(ngoId);
			foodServices.add(foodService);
		}
		if(jsonNode.get("dinnerChk").asBoolean()){
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long)ThreadContext.get(Constants.USERID));
			foodService.setTotalCount(jsonNode.get("dinnerQty").asLong());
			foodService.setServiceType(Constants.DINNER_ID);
			foodService.setNgoId(ngoId);
			foodServices.add(foodService);
		}
		
		return foodServices;
	}

	public static List<FoodServiceAvailability> getFoodServiceAvailabilityFromJson(
			ObjectNode objectNode, Long ngoId) {
		List<FoodServiceAvailability> foodServiceAvailability = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("service").get("serviceCalender");
		
		if(jsonNode.has("Breakfast")){
			int arrayLength = jsonNode.get("Breakfast").size();
			for(int index = 0; index< arrayLength ; index++){
				FoodServiceAvailability foodAvailability = new FoodServiceAvailability();
				foodAvailability.setCreated(new Date());
				foodAvailability.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				foodAvailability.setFoodServicesId(1L);
				foodAvailability.setWeekDay(jsonNode.get("Breakfast").get(index).toString().replace('"', ' ').trim());
				foodServiceAvailability.add(foodAvailability);
			}
			
		}
		if(jsonNode.has("Lunch")){
			int arrayLength = jsonNode.get("Lunch").size();
			for(int index = 0; index< arrayLength ; index++){
				FoodServiceAvailability foodAvailability = new FoodServiceAvailability();
				foodAvailability.setCreated(new Date());
				foodAvailability.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				foodAvailability.setFoodServicesId(2L);
				foodAvailability.setWeekDay(jsonNode.get("Lunch").get(index).toString().replace('"', ' ').trim());
				foodServiceAvailability.add(foodAvailability);
			}
			
		}
		if(jsonNode.has("Dinner")){
			int arrayLength = jsonNode.get("Dinner").size();
			for(int index = 0; index< arrayLength ; index++){
				FoodServiceAvailability foodAvailability = new FoodServiceAvailability();
				foodAvailability.setCreated(new Date());
				foodAvailability.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				foodAvailability.setFoodServicesId(3L);
				foodAvailability.setWeekDay(jsonNode.get("Dinner").get(index).toString().replace('"', ' ').trim());
				foodServiceAvailability.add(foodAvailability);
			}
			
		}
		return foodServiceAvailability;
	}


	public static List<FoodBank> getFoodBankFromJson(ObjectNode objectNode, Long ngoId) {
		List<FoodBank> foodBanks = new ArrayList<>();
		JsonNode serviceNode = objectNode.get("service");
		
		if(serviceNode.get("foodBankSelect").asBoolean()){
			JsonNode jsonNode = serviceNode.get("foodBankValue");
			if (jsonNode.isArray()) {
				Iterator<JsonNode> iterateJson = jsonNode.iterator();
				while (iterateJson.hasNext()) {
					FoodBank foodBank = new FoodBank();
					foodBank.setCreated(new Date());
					foodBank.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
					foodBank.setNgoId(ngoId);
					foodBank.setFoodBankName(iterateJson.next().textValue());
					foodBanks.add(foodBank);
				}
			}
		}
		return foodBanks;
	}


}
