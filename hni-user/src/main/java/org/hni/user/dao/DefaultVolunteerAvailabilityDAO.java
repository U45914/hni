package org.hni.user.dao;

import org.hni.common.dao.AbstractDAO;
import org.hni.user.om.VolunteerAvailability;
import org.springframework.stereotype.Component;

@Component
public class DefaultVolunteerAvailabilityDAO extends AbstractDAO<VolunteerAvailability> implements VolunteerAvailabilityDAO {

	protected DefaultVolunteerAvailabilityDAO() {
		super(VolunteerAvailability.class);
	}

}
