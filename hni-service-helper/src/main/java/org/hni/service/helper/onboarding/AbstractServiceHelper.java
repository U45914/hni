/**
 * 
 */
package org.hni.service.helper.onboarding;

import javax.inject.Inject;

import org.hni.common.service.HniTemplateService;
import org.hni.sms.service.provider.PushMessageService;
import org.hni.user.om.User;
import org.hni.user.service.UserOnboardingService;

/**
 * @author Rahul
 *
 */
public abstract class AbstractServiceHelper {
	
	@Inject
	protected UserOnboardingService userOnBoardingService;
	@Inject
	protected PushMessageService smsMessageService;
	@Inject
	protected HniTemplateService hniTemplateService;
	
	protected boolean isUserExists(String email) {
		return getUserByEmail(email) != null;
	}
	
	protected User getUserByEmail(String email) {
		return userOnBoardingService.getUserByEmail(email);
	}

	protected String getFromNumber(String stateCode) {
		return smsMessageService.getProviderNumberForState(stateCode);
	}
}
