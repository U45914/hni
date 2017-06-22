/**
 * 
 */
package org.hni.service.helper.onboarding;

import javax.inject.Inject;

import org.hni.user.om.User;
import org.hni.user.service.UserOnboardingService;

/**
 * @author Rahul
 *
 */
public abstract class AbstractServiceHelper {
	
	@Inject
	protected UserOnboardingService userOnBoardingService;
	
	protected boolean isUserExists(String email) {
		return getUserByEmail(email) != null;
	}
	
	protected User getUserByEmail(String email) {
		return userOnBoardingService.getUserByEmail(email);
	}

}
