package org.hni.user.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.Volunteer;

public class DefaultVolunteerDao extends AbstractDAO<Volunteer> implements VolunteerDao  {

	public DefaultVolunteerDao() {
		super(Volunteer.class);
	}
}
