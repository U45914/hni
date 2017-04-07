package org.hni.common;

import java.util.UUID;

public class HNIUtils {
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "").trim();
	}
}
