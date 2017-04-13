package org.hni.admin.service.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.dto.HniServicesDto;

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
	public static final String USERID = "userId";
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
	
	
	public static List<BrandPartner> getBrandPartnersFromJson(ObjectNode objectNode, Long long1) {
		List<BrandPartner> brandPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("brandPartners");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode brand = jsonNode.iterator().next();
				BrandPartner brandPartner = new BrandPartner();
				brandPartner.setCompany(brand.get("company").textValue());
				brandPartner.setCreated(new Date());
				brandPartner.setCreatedBy((Long) ThreadContext.get(USERID));
				brandPartner.setId(brand.get("id").asLong());
				brandPartner.setPhone(brand.get("phone").textValue());
				
				brandPartners.add(brandPartner);
			}
		}
		return brandPartners;
	}
	
	public static Ngo getNGOFromJson(ObjectNode objectNode) {
		JsonNode jsonNode = objectNode.get("ngo");
		Ngo ngo = new Ngo();
		if(jsonNode!=null){
		ngo.setAddressId(jsonNode.get("addressId").asLong());	
		ngo.setClientInfo(jsonNode.get("clientInfo").asBoolean());
		ngo.setClientsEmployed(jsonNode.get("clientsEmployed").asLong());
		ngo.setClientsUnsheltered(jsonNode.get("clientsUnsheltered").asLong());
		ngo.setContactFirstName(jsonNode.get("contactFirstName").textValue());
		ngo.setContactLastName(jsonNode.get("contactLastName").textValue());
		ngo.setCreated(new Date());
		ngo.setCreatedBy((Long) ThreadContext.get(USERID));
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
		}
		
		return ngo;
	}
	
	public static List<BoardMember> getBoardMembersFromJson(ObjectNode objectNode,Long Ngoid) {
		List<BoardMember> boardMembers = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("boardMembers");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode board = jsonNode.iterator().next();
				BoardMember boardMember = new BoardMember();
				boardMember.setFirstName(board.get("firstName").textValue());
				boardMember.setLastName(board.get("lastName").textValue());
				boardMember.setCompany(board.get("company").textValue());
				boardMember.setCreated(new Date());
				boardMember.setCreatedBy((Long) ThreadContext.get(USERID));
				boardMember.setId(board.get("id").asLong());
				
				boardMembers.add(boardMember);
			}
		}
		return boardMembers;
	}
	
	public static List<LocalPartner> getLocalPartnersFromJson(ObjectNode objectNode, Long long1) {
		List<LocalPartner> localPartners = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("localPartners");
		if (jsonNode != null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode local = jsonNode.iterator().next();
				LocalPartner localPartner = new LocalPartner();
				localPartner.setCompany(local.get("company").textValue());
				localPartner.setCreated(new Date());
				localPartner.setCreatedBy((Long) ThreadContext.get(USERID));
				localPartner.setId(local.get("id").asLong());
				localPartner.setPhone(local.get("phone").textValue());
				
				localPartners.add(localPartner);
			}
		}
		return localPartners;
	}
	
	public static List<NgoFundingSource> getNgoFundingSourcesFromJson(ObjectNode objectNode, Long long1) {
		List<NgoFundingSource> ngoFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("ngoFundingSources");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode fundSource = jsonNode.iterator().next();
				NgoFundingSource ngoFundingSource = new NgoFundingSource();
				ngoFundingSource.setCreated(new Date());
				ngoFundingSource.setCreatedBy((Long) ThreadContext.get(USERID));
				ngoFundingSource.setId(fundSource.get("id").asLong());
				ngoFundingSource.setAmount(fundSource.get("amount").asDouble());
				ngoFundingSource.setSource(fundSource.get("source").textValue());
				
				ngoFundingSources.add(ngoFundingSource);
			}
		}
		return ngoFundingSources;
	}
	
	public static List<MealFundingSource> getMealFundingSourcesFromJson(ObjectNode objectNode, Long long1) {
		List<MealFundingSource> mealFundingSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("mealFundingSources");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode mealSource = jsonNode.iterator().next();
				MealFundingSource mealFundingSource = new MealFundingSource();
				mealFundingSource.setCreated(new Date());
				mealFundingSource.setCreatedBy((Long) ThreadContext.get(USERID));
				mealFundingSource.setId(mealSource.get("id").asLong());
				mealFundingSource.setAmount(mealSource.get("amount").asDouble());
				mealFundingSource.setSource(mealSource.get("source").textValue());
				
				mealFundingSources.add(mealFundingSource);
			}
		}
		return mealFundingSources;
	}
	
	public static List<FoodService> getFoodServicesFromJson(ObjectNode objectNode, Long long1) {
		List<FoodService> foodServices = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("foodServices");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodServiz = jsonNode.iterator().next();
				FoodService foodService = new FoodService();
				foodService.setCreated(new Date());
				foodService.setCreatedBy((Long) ThreadContext.get(USERID));
				foodService.setId(foodServiz.get("id").asLong());
				foodService.setOther(foodServiz.get("other").textValue());
				foodService.setTotalCount(foodServiz.get("totalCount").asLong());
				foodService.setServiceType(foodServiz.get("serviceType").asLong());
				
				foodServices.add(foodService);
			}
		}
		return foodServices;
	}
	
	public static List<FoodServiceAvailability> getFoodServiceAvailabilityFromJson(ObjectNode objectNode, Long long1) {
		List<FoodServiceAvailability> foodServiceAvailability = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("foodServiceAvailability");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodAvail = jsonNode.iterator().next();
				FoodServiceAvailability foodAvailability = new FoodServiceAvailability();
				foodAvailability.setCreated(new Date());
				foodAvailability.setCreatedBy((Long) ThreadContext.get(USERID));
				foodAvailability.setId(foodAvail.get("id").asLong());
				foodAvailability.setWeekDay(foodAvail.get("weekDay").asLong());
				
				foodServiceAvailability.add(foodAvailability);
			}
		}
		return foodServiceAvailability;
	}
	
	public static List<FoodBank> getFoodBankFromJson(ObjectNode objectNode, Long long1) {
		List<FoodBank> foodBanks = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("foodBanks");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode foodbanq = jsonNode.iterator().next();
				FoodBank foodBank = new FoodBank();
				foodBank.setCreated(new Date());
				foodBank.setCreatedBy((Long) ThreadContext.get(USERID));
				foodBank.setId(foodbanq.get("id").asLong());
				foodBank.setFoodBankName(foodbanq.get("foodBankName").asLong());
				
				foodBanks.add(foodBank);
			}
		}
		return foodBanks;
	}
	
	public static List<MealDonationSource> getMealDonationSourceFromJson(ObjectNode objectNode, Long long1) {
		List<MealDonationSource> MealDonationSources = new ArrayList<>();
		JsonNode jsonNode = objectNode.get("mealDonationSources");
		if (jsonNode!=null && jsonNode.isArray()) {
			while (jsonNode.iterator().hasNext()) {
				JsonNode mealSource = jsonNode.iterator().next();
				MealDonationSource mealDonationSource = new MealDonationSource();
				mealDonationSource.setCreated(new Date());
				mealDonationSource.setCreatedBy((Long) ThreadContext.get(USERID));
				mealDonationSource.setId(mealSource.get("id").asLong());
				mealDonationSource.setSource(mealSource.get("source").textValue());
				mealDonationSource.setFrequency(mealSource.get("frequency").textValue());
				
				MealDonationSources.add(mealDonationSource);
			}
		}
		return MealDonationSources;
	}
	
}
