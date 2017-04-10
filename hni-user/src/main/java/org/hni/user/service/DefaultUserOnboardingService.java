package org.hni.user.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import org.hni.common.HNIUtils;
import org.hni.common.dao.BaseDAO;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.UserOnboardingDAO;
import org.hni.user.om.Invitation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultUserOnboardingService extends AbstractService<Invitation> implements UserOnboardingService{
	
	@Inject
	private UserOnboardingDAO invitationDAO;

	public DefaultUserOnboardingService(BaseDAO<Invitation> dao) {
		super(dao);
	}

	@Override
	public Collection<Invitation> validateInvitationCode(String invitationCode) {
		return invitationDAO.validateInvitationCode(invitationCode);
	}


	@Override
	public String buildInvitationAndSave(Long orgId) {
		String UUID = HNIUtils.getUUID();
		Invitation invitation = new Invitation();
		invitation.setOrganizationId(orgId.toString());
		invitation.setInvitationCode(UUID);
		invitation.setCreatedDate(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		invitation.setExpirationDate(cal.getTime());
		invitationDAO.save(invitation);
		return UUID;
	}
	
	
	 
}
