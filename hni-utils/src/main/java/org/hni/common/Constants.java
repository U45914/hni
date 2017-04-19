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
}
