package org.hni.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HNIUtils {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").trim();
	}

	public static List<Map<String, String>> getHeader(Integer roleId) {

		List<Map<String, String>> headers = new ArrayList<>();

		if (roleId == Constants.USER_TYPES.get("ngo")) {

			headers.add(addField("name", "Full Name"));
			headers.add(addField("phone", "Phone Number"));
			headers.add(addField("website", "Website URL"));
			headers.add(addField("createdUsers", "No of Created Users"));
		} else if (roleId == Constants.USER_TYPES.get("customer")) {

			headers.add(addField("firstName", "First Name"));
			headers.add(addField("lastName", "Last Name"));
			headers.add(addField("mobilePhone", "Phone Number"));
			headers.add(addField("email", "Email Id"));
			headers.add(addField("genderCode", "Gender"));

		} else if(roleId == Constants.USER_TYPES.get("volunteer")){
			headers.add(addField("name", "Name"));
			headers.add(addField("gender", "Gender"));
			headers.add(addField("email", "Email ID"));
			
		}
		return headers;
	}

	private static Map<String, String> addField(String field, String label) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("field", field);
		header.put("label", label);
		return header;
	}
}