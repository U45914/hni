package org.hni.common;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

public class HNIUtils {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").trim();
	}

	public static List<Map<String, String>> getReportHeaders(Integer reportId) {

		List<Map<String, String>> headers = new ArrayList<>();
		if (Constants.REPORT_ALL_NGO.equals(reportId)) {

			headers.add(addField("name", "NGO Name"));
			headers.add(addField("phone", "NGO Phone"));
			headers.add(addField("address", "NGO Address"));
			headers.add(addField("createdUsers", "Total number of Clients "));
		} else if (Constants.REPORT_ALL_CUSTOMER.equals(reportId)) {

			headers.add(addField("firstName", "First Name"));
			headers.add(addField("lastName", "Last Name"));
			headers.add(addField("mobilePhone", "Phone Number"));
			headers.add(addField("race", "Race"));
			headers.add(addField("address", "Address"));
			headers.add(addField("orders", "No of Orders"));

		} else if(Constants.REPORT_ALL_VOLUNTEER.equals(reportId)){
			headers.add(addField("firstName", "First Name"));
			headers.add(addField("lastName", "Last Name"));
			headers.add(addField("address", "Address"));
			headers.add(addField("phone", "Phone Number"));
			headers.add(addField("email", "Email"));
		}
		else if(Constants.REPORT_ALL_ORDER.equals(reportId)){
			headers.add(addField("orderDate", "Order date"));
			headers.add(addField("readyDate", "Ready date"));
			headers.add(addField("name", "Orderd By"));
			headers.add(addField("orderstatus", "Order status"));
			headers.add(addField("total", "Total"));
			//headers.add(addField("orderItems", "Ordered Items"));
		}
		else if (Constants.REPORT_ALL_PROVIDER.equals(reportId)) {

			headers.add(addField("name", "Provider Name"));
			headers.add(addField("address", "Address"));
			headers.add(addField("website", "Website"));
			headers.add(addField("createdOn", "Created On"));
			headers.add(addField("createdBy", "Created By"));	
			}
		return headers;
	}

	private static Map<String, String> addField(String field, String label) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("field", field);
		header.put("label", label);
		return header;
	}
	
	public static String getHash(String authCode, Object salt) {
		ByteSource slt = new SimpleByteSource(org.apache.commons.codec.binary.Base64.decodeBase64((String) salt));
		Hash h = new SimpleHash("SHA-256", authCode, slt, 1024);
		Base64.Encoder enc = Base64.getEncoder();
		return new String(enc.encode(h.getBytes()));
	}

	public static String getSalt() {
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(salt);
	}
	
	public static boolean isPositiveNumeric(String text) {
		boolean isValid = false;
		try {
			Integer val = Integer.parseInt(text);
			if (Integer.signum(val.intValue()) != -1) {
				isValid = true;
			} 
		} catch (Exception e) {
			// Nothing to do with exception
		}
		
		return isValid;
	}
	
	public static boolean isPositiveNumeric(Integer num) {
		return Integer.signum(num) != -1;
	}
	
	public static Integer getNumber(String text) {
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			// Nothing to do with exception
			return null;
		}
	}
	
}