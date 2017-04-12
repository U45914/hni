package org.hni.admin.service.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HNIConverter {
	private static final Logger logger = LoggerFactory.getLogger(HNIConverter.class);
	
	public static Collection<HniServicesDto> convertToServiceDtos(
			Collection<HniServices> hniServices) {
		
		Collection<HniServicesDto> hniServicesDtoList = new ArrayList<>();
		for(HniServices hniService : hniServices){
			HniServicesDto hniServiceDto = new HniServicesDto();
			hniServiceDto.setServiceName(hniService.getServiceName());
			hniServiceDto.setServicePath(hniService.getServicePath());
			hniServiceDto.setServiceImg(hniService.getServiceImg());
			hniServiceDto.setActive(hniService.getActive());
			
			hniServicesDtoList.add(hniServiceDto);
		}
		return hniServicesDtoList;
	}
	
	
	public List<BrandPartner> getBrandPartnersFromJson(ObjectNode objectNode) {
		List<BrandPartner> brandPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("brandPartners");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode brand = jsonNode.iterator().next();
				BrandPartner brandPartner = new BrandPartner();
				brandPartner.setCompany(brand.get("company").textValue());
				brandPartner.setCreated(new Date());
				brandPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				brandPartner.setId(brand.get("id").asLong());
				brandPartner.setPhone(brand.get("phone").textValue());
				
				brandPartners.add(brandPartner);
			}
		}
		return brandPartners;
	}
	
	public Ngo getNGOFromJson(ObjectNode objectNode) {
		JsonNode jsonNode = objectNode.get("ngo");
		
		Ngo ngo = new Ngo();
		ngo.setAddressId(jsonNode.get("addressId").asLong());	
		ngo.setClientInfo(jsonNode.get("clientInfo").asBoolean());
		ngo.setClientsEmployed(jsonNode.get("clientsEmployed").asLong());
		ngo.setClientsUnsheltered(jsonNode.get("clientsUnsheltered").asLong());
		ngo.setContactFirstName(jsonNode.get("contactFirstName").textValue());
		ngo.setContactLastName(jsonNode.get("contactLastName").textValue());
		ngo.setCreated(new Date());
		ngo.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
		ngo.setEndorsementId(jsonNode.get("endorsementId").asLong());
		ngo.setFoodBank(jsonNode.get("foodBank").asBoolean());
		ngo.setFoodStampAssist(jsonNode.get("foodStampAssist").asBoolean());
		ngo.setFte(jsonNode.get("fte").asInt());
		ngo.setId(jsonNode.get("id").asLong());
		ngo.setIndServAnnual(jsonNode.get("indServAnnual").asLong());
		ngo.setIndServDaily(jsonNode.get("indServDaily").asLong());
		ngo.setIndServMonthly(jsonNode.get("indServMonthly").asLong());
		ngo.setKitchenVolunteers(jsonNode.get("kitchenVolunteers").asLong());
		ngo.setMission(jsonNode.get("mission").textValue());
		ngo.setMonthlyBudget(jsonNode.get("monthlyBudget").asLong());
		ngo.setName(jsonNode.get("name").textValue());
		ngo.setOperatingCost(jsonNode.get("operatingCost").asLong());
		ngo.setOverview(jsonNode.get("overview").textValue());
		ngo.setPersonalCost(jsonNode.get("personalCost").asLong());
		ngo.setPhone(jsonNode.get("phone").textValue());
		ngo.setResourcesToClients(jsonNode.get("resourcesToClients").asLong());
		ngo.setStoreClientInfo(jsonNode.get("storeClientInfo").textValue());
		ngo.setWebsite(jsonNode.get("website").textValue());
		
		return ngo;
	}
	
	public List<BoardMember> getBoardMembersFromJson(ObjectNode objectNode) {
		List<BoardMember> boardMembers = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("boardMembers");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode board = jsonNode.iterator().next();
				BoardMember boardMember = new BoardMember();
				boardMember.setFirstName(board.get("firstName").textValue());
				boardMember.setLastName(board.get("lastName").textValue());
				boardMember.setCompany(board.get("company").textValue());
				boardMember.setCreated(new Date());
				boardMember.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				boardMember.setId(board.get("id").asLong());
				
				boardMembers.add(boardMember);
			}
		}
		return boardMembers;
	}
	
	public List<LocalPartner> getLocalPartnersFromJson(ObjectNode objectNode) {
		List<LocalPartner> localPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("localPartners");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode local = jsonNode.iterator().next();
				LocalPartner localPartner = new LocalPartner();
				localPartner.setCompany(local.get("company").textValue());
				localPartner.setCreated(new Date());
				localPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				localPartner.setId(local.get("id").asLong());
				localPartner.setPhone(local.get("phone").textValue());
				
				localPartners.add(localPartner);
			}
		}
		return localPartners;
	}
	
	public List<NgoFundingSource> getNgoFundingSourcesFromJson(ObjectNode objectNode) {
		List<NgoFundingSource> ngoFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("ngoFundingSources");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode fundSource = jsonNode.iterator().next();
				NgoFundingSource ngoFundingSource = new NgoFundingSource();
				ngoFundingSource.setCreated(new Date());
				ngoFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				ngoFundingSource.setId(fundSource.get("id").asLong());
				ngoFundingSource.setAmount(fundSource.get("amount").asDouble());
				ngoFundingSource.setSource(fundSource.get("source").textValue());
				
				ngoFundingSources.add(ngoFundingSource);
			}
		}
		return ngoFundingSources;
	}
	
	public List<MealFundingSource> getMealFundingSourcesFromJson(ObjectNode objectNode) {
		List<MealFundingSource> mealFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("mealFundingSources");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode mealSource = jsonNode.iterator().next();
				MealFundingSource mealFundingSource = new MealFundingSource();
				mealFundingSource.setCreated(new Date());
				mealFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				mealFundingSource.setId(mealSource.get("id").asLong());
				mealFundingSource.setAmount(mealSource.get("amount").asDouble());
				mealFundingSource.setSource(mealSource.get("source").textValue());
				
				mealFundingSources.add(mealFundingSource);
			}
		}
		return mealFundingSources;
	}
	
	public List<FoodService> getFoodServicesFromJson(ObjectNode objectNode) {
		List<FoodService> foodServices = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("foodServices");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodServiz = jsonNode.iterator().next();
				FoodService foodService = new FoodService();
				foodService.setCreated(new Date());
				foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				foodService.setId(foodServiz.get("id").asLong());
				foodService.setOther(foodServiz.get("other").textValue());
				foodService.setTotalCount(foodServiz.get("totalCount").asLong());
				foodService.setServiceType(foodServiz.get("serviceType").asLong());
				
				foodServices.add(foodService);
			}
		}
		return foodServices;
	}
	
	public List<FoodServiceAvailability> getFoodServiceAvailabilityFromJson(ObjectNode objectNode) {
		List<FoodServiceAvailability> foodServiceAvailability = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("foodServiceAvailability");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodAvail = jsonNode.iterator().next();
				FoodServiceAvailability foodAvailability = new FoodServiceAvailability();
				foodAvailability.setCreated(new Date());
				foodAvailability.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				foodAvailability.setId(foodAvail.get("id").asLong());
				foodAvailability.setFoodServicesId(foodAvail.get("foodServiceId").asLong());
				foodAvailability.setWeekDay(getFoodServiceWeekDay(objectNode, foodAvail));
				foodAvail.get(0);
				
				foodServiceAvailability.add(foodAvailability);
			}
		}
		return foodServiceAvailability;
	}
	
	private String getFoodServiceWeekDay(ObjectNode objectNode, JsonNode foodAvail){
		final Long BREAKFAST_ID = 1L;
		final Long LUNCH_ID = 2L;
		final Long DINNER_ID = 3L;
		StringBuilder weekDays = new StringBuilder();
		JsonNode jsonNode = objectNode.get("foodServices");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodServiz = jsonNode.iterator().next();
				Long serviceType = foodServiz.get("serviceType").asLong();
				if(serviceType.equals(BREAKFAST_ID)){
					for(int index = 0 ; index < 7; index++){
						JsonNode weekDay = foodAvail.get(index);
						if(weekDay.has("Breakast")){
							weekDays.append(weekDay+",");
						}
					}
				}
				if(serviceType.equals(LUNCH_ID)){
					for(int index = 0 ; index < 7; index++){
						JsonNode weekDay = foodAvail.get(index);
						if(weekDay.has("Lunch")){
							weekDays.append(weekDay+",");
						}
					}
				}
				if(serviceType.equals(DINNER_ID)){
					for(int index = 0 ; index < 7; index++){
						JsonNode weekDay = foodAvail.get(index);
						if(weekDay.has("Dinner")){
							weekDays.append(weekDay+",");
						}
					}
				}
				
				
			}
		}		
		return weekDays.toString();
	}
	
	public List<FoodBank> getFoodBankFromJson(ObjectNode objectNode) {
		List<FoodBank> foodBanks = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("foodBanks");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodbanq = jsonNode.iterator().next();
				FoodBank foodBank = new FoodBank();
				foodBank.setCreated(new Date());
				foodBank.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				foodBank.setId(foodbanq.get("id").asLong());
				foodBank.setFoodBankName(foodbanq.get("foodBankName").asLong());
				
				foodBanks.add(foodBank);
			}
		}
		return foodBanks;
	}
	
	public List<MealDonationSource> getMealDonationSourceFromJson(ObjectNode objectNode) {
		List<MealDonationSource> MealDonationSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("mealDonationSources");
		if (jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode mealSource = jsonNode.iterator().next();
				MealDonationSource mealDonationSource = new MealDonationSource();
				mealDonationSource.setCreated(new Date());
				mealDonationSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				mealDonationSource.setId(mealSource.get("id").asLong());
				mealDonationSource.setSource(mealSource.get("source").textValue());
				mealDonationSource.setFrequency(mealSource.get("frequency").textValue());
				
				MealDonationSources.add(mealDonationSource);
			}
		}
		return MealDonationSources;
	}
	
}
