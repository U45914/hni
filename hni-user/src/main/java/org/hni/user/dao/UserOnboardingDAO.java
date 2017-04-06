package org.hni.user.dao;

import java.util.Collection;

import org.hni.common.dao.BaseDAO;
import org.hni.user.om.Invitation;

public interface UserOnboardingDAO extends BaseDAO<Invitation>{
	Collection<Invitation> validateInvitationCode(String invitationCode);
}
