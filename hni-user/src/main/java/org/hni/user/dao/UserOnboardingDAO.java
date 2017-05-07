package org.hni.user.dao;

import java.util.Collection;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;

public interface UserOnboardingDAO extends BaseDAO<Invitation>{
	Collection<Invitation> validateInvitationCode(String invitationCode);
	Invitation getInvitedBy(String email);
	Invitation updateInvitationStatus(String activationCode);
}
