package org.hni.sms.service.provider;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.hni.sms.service.provider.om.SmsProvider;

public class SmsServiceLoader {
	
	static Map<String, SmsProvider> providers;
	
	@PostConstruct
	public void loadRegisteredProviders() {
		//TODO: Load providers from the database  and put things to a map
	}
	
	public Map<String, SmsProvider> getProviders() {
		return providers;
	}

}
