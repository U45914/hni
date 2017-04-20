package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hni.common.dao.DefaultGenericDAO;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.type.HNIRoles;
import org.hni.user.om.User;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class CustomerDao extends DefaultGenericDAO {

	public List<User> getAllCustomersByRole() {
		List<User> customers = new ArrayList<>();
		Long role = HNIRoles.CLIENT.getRole();
		List<Long> userOrganizationRole = em
				.createQuery("select distinct x.id.userId  from UserOrganizationRole x where x.id.roleId=:roleId")
				.setParameter("roleId", role).getResultList();
		for (Long userOrgRole : userOrganizationRole) {
			User user = (User) em.createQuery("select x from User x where x.id=:userId")
					.setParameter("userId", userOrgRole).getSingleResult();
			customers.add(user);
		}
		return customers;

	}

	public List<User> getAllCustomersUnderOrganisation(User u) {
		List<User> customers = new ArrayList<>();
		Long role = HNIRoles.CLIENT.getRole();
		Long userId = u.getId();
		Long org = (Long) em.createQuery("select x.id.orgId from UserOrganizationRole x where x.id.userId=:userId")
				.setParameter("userId", userId).getSingleResult();

		Query q = em.createQuery("select x from UserOrganizationRole x where x.id.roleId=:roleId and x.id.orgId=:orgId")
				.setParameter("roleId", role).setParameter("orgId", org);
		List<UserOrganizationRole> userOrganizationRole = q.getResultList();
		for (UserOrganizationRole userOrgRole : userOrganizationRole) {
			Long id = userOrgRole.getId().getUserId();
			User user = (User) em.createQuery("select x from User x where x.id=:userId").setParameter("userId", id)
					.getSingleResult();
			customers.add(user);
		}
		return customers;
	}
}