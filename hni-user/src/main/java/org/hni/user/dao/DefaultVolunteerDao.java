package org.hni.user.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Volunteer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultVolunteerDao extends AbstractDAO<Volunteer> implements VolunteerDao  {
 
	private static final Logger _LOGGER = LoggerFactory.getLogger(DefaultVolunteerDao.class);

	public DefaultVolunteerDao() {
		super(Volunteer.class);
	}

	@Override
	public Volunteer getByUserId(Long userId) {
		try {
			Query q = em.createQuery("SELECT x FROM Volunteer x WHERE x.userId = :userId").setParameter("userId", userId);
			List<Volunteer> volunteers = q.getResultList();
			if (volunteers.isEmpty()) {
				return null;
			} else {
				return volunteers.get(0);
			}
		} catch (NoResultException e) {
			_LOGGER.error("Error in volunteer Query:" + e.getMessage(), e);
			return null;
		}
	}
}
