package org.hni.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final Long SUPER_USER = 1L;
	public static final Long ADMIN = 2L;
	public static final Long VOLUNTEER = 3L;
	public static final Long USER = 5L;  
	public static final Long CLIENT = 7L;
	
	//Service
	public static final String SUCCESS = "organizations";
	public static final String ERROR = "providers";
	public static final String RESPONSE = "organizations";
	
	// domains
	public static final String ORGANIZATION = "organizations";
	public static final String PROVIDER = "providers";
	public static final String ORDER = "orders";
	public static final String MENU = "menus";
	
	// basic permissions
	public static final String CREATE = "create";
	public static final String READ = "read";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	
	public static final String USERID = "userId";
	public static final String PERMISSIONS = "permissions";
	
	public static final String C = "C";
	public static final String N = "N";
	
	public static final Map<String, Integer> USER_TYPES = new HashMap<>();
	
	static{
		USER_TYPES.put("ngo", 1);
		USER_TYPES.put("customer", 2);
		USER_TYPES.put("volunteer", 3);
		USER_TYPES.put("restaurant", 4);
	}
	
	//hni converter
	public static final Long BREAKFAST_ID = 1L;
	public static final Long LUNCH_ID = 2L;
	public static final Long DINNER_ID = 3L;
	
	public static final String BAGGED_QTY = "baggedQty";

	public static final String BAGGED_CHK = "baggedChk";

	public static final String FUNDING_SOURCE = "fundingSource";

	public static final String AMOUNT = "amount";

	public static final String MEAL_FUNDING = "mealFunding";

	public static final String FREQUENCY = "frequency";

	public static final String SOURCE = "source";

	public static final String MEAL_DONATION = "mealDonation";

	public static final String FUNDING = "funding";

	public static final String DINNER_AVAILABILTY = "dinnerAvailabilty";

	public static final String DINNER_QTY = "dinnerQty";

	public static final String DINNER_CHK = "dinnerChk";

	public static final String LUNCH_AVAILABILTY = "lunchAvailabilty";

	public static final String LUNCH_QTY = "lunchQty";

	public static final String LUNCH_CHK = "lunchChk";

	public static final String BRKFST_AVAILABILTY = "brkfstAvailabilty";

	public static final String BRKFST_QTY = "brkfstQty";

	public static final String BRKFST_CHK = "brkfstChk";

	public static final String FOOD_BANK_VALUE = "foodBankValue";

	public static final String LOCAL_PARTNERS = "localPartners";

	public static final String PHONE_NUMBER = "phoneNumber";

	public static final String BRAND_PARTNERS = "brandPartners";

	public static final String COMPANY = "company";

	public static final String NAME = "name";

	public static final String BOARD_MEMBERS = "boardMembers";

	public static final String STAKE_HOLDER = "stakeHolder";

	public static final String EMPLOYEED_CLIENT_PERCENTAGE = "employeedClientPercentage";

	public static final String UNSHELTERED_CLIENT_PERCENTAGE = "unshelteredClientPercentage";

	public static final String INDIVIDUAL_CLIENT_INFO_COLLECTED = "individualClientInfoCollected";

	public static final String INDIVIDUALS_SERVED_ANNUALLY = "individualsServedAnnually";

	public static final String INDIVIDUALS_SERVED_MONTHLY = "individualsServedMonthly";

	public static final String INDIVIDUALS_SERVED_DAILY = "individualsServedDaily";

	public static final String FOOD_BANK_SELECT = "foodBankSelect";

	public static final String FOOD_STAMP = "foodStamp";

	public static final String VOLUNTEER_NBR = "volunteerNbr";

	public static final String PERSONAL_COST = "personalCost";

	public static final String OPERATING_COST = "operatingCost";

	public static final String MONTHLY_BUDGET = "monthlyBudget";

	public static final String MISSION = "mission";

	public static final String WEBSITE = "website";

	public static final String CLIENT_NODE = "client";

	public static final String SERVICE = "service";

	public static final String OVERVIEW = "overview";
}
