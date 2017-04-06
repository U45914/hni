package org.hni.user.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Invitation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserOnboardingDAO extends AbstractDAO<Invitation> implements UserOnboardingDAO{
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserOnboardingDAO.class);

	public DefaultUserOnboardingDAO() {
		super(Invitation.class);
	}

	public Collection<Invitation> validateInvitationCode(String invitationCode){
		try {
			Query q = em.createQuery("SELECT x FROM Invitation x WHERE x.invitationCode = :invitationCode and x.expirationDate>=:today")
						.setParameter("invitationCode", invitationCode)
						.setParameter("today", new Date(),TemporalType.DATE);
			return q.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}
