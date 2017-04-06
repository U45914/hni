package org.hni.user.service;

import java.util.Collection;

import org.hni.common.service.BaseService;
import org.hni.user.om.Invitation;

public interface UserOnboardingService extends BaseService<Invitation>{
	String buildInvitationAndSave(Long orgId);
	Collection<Invitation> validateInvitationCode(String invitationCode);
}
