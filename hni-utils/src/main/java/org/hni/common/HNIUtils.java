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

	private static Map<String, Object> getFieldDefMap(String fieldName, String displayName,
			Boolean resizable, Boolean enableCellEdit) {
		Map<String, Object> fieldDef = new HashMap<>();
		fieldDef.put("field", fieldName);
		fieldDef.put("displayName", displayName);
		fieldDef.put("resizable", resizable);
		fieldDef.put("enableCellEdit", enableCellEdit);
		//fieldDef.put("field", fieldName);
		
		return fieldDef;
	}
	

	public static List<Map<String, Object>> getReportHeaders(Integer reportId, Boolean canEdit) {

		List<Map<String, Object>> headers = new ArrayList<>();
		if (Constants.REPORT_ALL_NGO.equals(reportId)) {

			headers.add(getFieldDefMap("name", "NGO Name", true, canEdit));
			headers.add(getFieldDefMap("phone", "NGO Phone", true, canEdit));
			headers.add(getFieldDefMap("address", "NGO Address", true, canEdit));
			headers.add(getFieldDefMap("createdUsers", "Total number of Clients", true, canEdit));
		} else if (Constants.REPORT_ALL_CUSTOMER.equals(reportId)) {

			headers.add(getFieldDefMap("firstName", "First Name", true, canEdit));
			headers.add(getFieldDefMap("lastName", "Last Name", true, canEdit));
			headers.add(getFieldDefMap("mobilePhone", "Phone Number", true, canEdit));
			headers.add(getFieldDefMap("address", "Address", true, canEdit));
			headers.add(getFieldDefMap("orders", "No of Orders", true, canEdit));
			headers.add(getFieldDefMap("active", "Status", true, canEdit));
			headers.add(getFieldDefMap("sheltered", "Sheltered", true, canEdit));

		} else if(Constants.REPORT_ALL_VOLUNTEER.equals(reportId)){
			headers.add(getFieldDefMap("firstName", "First Name", true, canEdit));
			headers.add(getFieldDefMap("lastName", "Last Name", true, canEdit));
			headers.add(getFieldDefMap("address", "Address", true, canEdit));
			headers.add(getFieldDefMap("phone", "Phone Number", true, canEdit));
			headers.add(getFieldDefMap("email", "Email", true, canEdit));
		}
		else if(Constants.REPORT_ALL_ORDER.equals(reportId)){
			headers.add(getFieldDefMap("orderDate", "Order date", true, canEdit));
			headers.add(getFieldDefMap("readyDate", "Ready date", true, canEdit));
			headers.add(getFieldDefMap("name", "Orderd By", true, canEdit));
			headers.add(getFieldDefMap("orderstatus", "Order status", true, canEdit));
			headers.add(getFieldDefMap("total", "Total", true, canEdit));
			//headers.add(addField("orderItems", "Ordered Items"));
		}
		else if (Constants.REPORT_ALL_PROVIDER.equals(reportId)) {

			headers.add(getFieldDefMap("name", "Provider Name", true, canEdit));
			headers.add(getFieldDefMap("address", "Address", true, canEdit));
			headers.add(getFieldDefMap("website", "Website", true, canEdit));
			headers.add(getFieldDefMap("createdOn", "Created On", true, canEdit));
			headers.add(getFieldDefMap("createdBy", "Created By", true, canEdit));	
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