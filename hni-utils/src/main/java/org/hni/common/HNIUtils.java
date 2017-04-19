package org.hni.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HNIUtils {
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "").trim();
	}
	
public static List<Map<String, String>> getHeader(Integer roleId) {
	
	List<Map<String,String>> header = new ArrayList<>();
	
	if(roleId==Constants.USER_TYPES.get("ngo")){
	
		Map<String,String> field1 = new HashMap<String,String>();
		Map<String,String> field2 = new HashMap<String,String>();
		Map<String,String> field3 = new HashMap<String,String>();
		Map<String,String> field4 = new HashMap<String,String>();
		
		field1.put("field", "name");field1.put("label", "Full Name");header.add(field1);
		field2.put("field", "phone");field2.put("label", "Phone Number");header.add(field2);
		field3.put("field", "website");field3.put("label", "Website URL");header.add(field3);
		field4.put("field", "createdUsers");field4.put("label", "No of Created Users");header.add(field4);
		
		
	}
	return header;
	}
}
