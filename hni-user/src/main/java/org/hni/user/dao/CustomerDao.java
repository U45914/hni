package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.h2.command.dml.Select;
import org.hni.common.dao.DefaultGenericDAO;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.type.HNIRoles;
import org.hni.user.om.Client;
import org.hni.user.om.User;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class CustomerDao extends DefaultGenericDAO {

	public List<User> getAllCustomersByRole() {
		List<User> customers = new ArrayList<>();
		Long role =HNIRoles.CLIENT.getRole();
		
			List<Object[]> user =  em.createNativeQuery("select distinct u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email from users u INNER JOIN user_organization_role x ON u.id=x.user_id where x.role_id=:roleId")
					.setParameter("roleId",role).getResultList();
			for(Object[] u:user){
				User us=new User();
				us.setFirstName(u[0].toString());
				us.setLastName(u[1].toString());
				us.setGenderCode(u[2].toString());
				us.setMobilePhone(u[3].toString());
				us.setEmail(u[4].toString());
				customers.add(us);
			}
			return customers;

	}

	public List<User> getAllCustomersUnderOrganisation(User u) {
		List<User> customers = new ArrayList<>();
		Long role =HNIRoles.CLIENT.getRole();
		Long userId = u.getId();
		List<Object[]> user =  em.createNativeQuery("select distinct u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email from users u INNER JOIN user_organization_role x ON u.id=x.user_id where x.role_id=:role  and x.organization_id=(select x.organization_id from user_organization_role where user_id=:userId);")
				.setParameter("role",role)
				.setParameter("userId",userId).getResultList();
		for(Object[] usr:user){
			User us=new User();
			us.setFirstName(usr[0].toString());
			us.setLastName(usr[1].toString());
			us.setGenderCode(usr[2].toString());
			us.setMobilePhone(usr[3].toString());
			us.setEmail(usr[4].toString());
			customers.add(us);
		}		return customers;
	}

	public List<User> getAllCustomersEnrolledByNgo(User user) {
		List<User> customers = new ArrayList<>();
		Long role = HNIRoles.NGO.getRole();
		Long userId = 4L;

		Query q = em
				.createQuery("select  x from UserOrganizationRole x where x.id.roleId=:roleId and x.id.userId=:userId")
				.setParameter("roleId", role).setParameter("userId", userId);
		List<Long> userOrganizationRole = q.getResultList();
		if (!((userOrganizationRole.isEmpty()) || (userOrganizationRole == null))) {
			List<Object[]> users =  em.createNativeQuery("select distinct u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email from users u INNER JOIN `client` x ON u.id=x.user_id where x.created_by=:userId")
					.setParameter("userId",userId).getResultList();
			for(Object[] usr:users){
				User us=new User();
				us.setFirstName(usr[0].toString());
				us.setLastName(usr[1].toString());
				us.setGenderCode(usr[2].toString());
				us.setMobilePhone(usr[3].toString());
				us.setEmail(usr[4].toString());
				customers.add(us);
			}
		}
		return customers;
	}
}