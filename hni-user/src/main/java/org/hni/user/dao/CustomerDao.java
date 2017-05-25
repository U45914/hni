package org.hni.user.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.common.dao.DefaultGenericDAO;
import org.hni.type.HNIRoles;
import org.hni.user.om.User;
import org.springframework.stereotype.Component;

@Component
public class CustomerDao extends DefaultGenericDAO {
	
	
	public List<Map> getAllCustomersByRole() {
		List<Map> customers = new ArrayList<>();
		Long role = HNIRoles.CLIENT.getRole();

		List<Object[]> user = em
				//.createNativeQuery("select distinct u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email,c.race,ad.address_line1, COUNT(o.id)  as ordCount from users u LEFT JOIN user_organization_role x ON u.id=x.user_id LEFT JOIN `client` c ON c.user_id=u.id LEFT JOIN user_address uad ON uad.user_id=u.id LEFT JOIN addresses ad ON ad.id=uad.address_id LEFT JOIN orders o ON o.user_id=u.id where x.role_id=:roleId")
				.createNativeQuery("SELECT u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email,c.race,ad.address_line1, COUNT(o.id) AS ordCount "
						+ "FROM client c "
						+ "LEFT JOIN users u ON u.id = c.user_id "
						+ "LEFT JOIN user_organization_role uor ON u.id=uor.user_id "
						+ "LEFT JOIN user_address uad ON u.id=uad.user_id "
						+ "LEFT JOIN addresses ad ON ad.id=uad.address_id "
						+ "LEFT JOIN orders o ON o.user_id=u.id "
						+ "WHERE uor.role_id=:roleId GROUP BY c.id")
				.setParameter("roleId", role).getResultList();
		for (Object[] u : user) {
			Map<String,String> map=new HashMap<String,String>();
			map.put("firstName",getValue(u[0]));
			map.put("lastName",getValue(u[1]));
			map.put("mobilePhone",getValue(u[3]));
			map.put("race",getValue(u[5]));
			map.put("address",getValue(u[6]));
			map.put("orders", getValue(u[7]));	
			customers.add(map);
		}
		return customers;

	}
	

	public List<Map> getAllCustomersUnderOrganisation(User u) {
		List<Map> customers = new ArrayList<>();
		Long role = HNIRoles.CLIENT.getRole();
		Long userId = null;
		if (u != null) {
			userId = u.getId();
			List<Object[]> user = em
					.createNativeQuery(
							"select distinct u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email,c.race,ad.address_line1,"
							+ " COUNT(o.id)  as ordCount from users u INNER JOIN user_organization_role x ON u.id=x.user_id "
							+ "INNER JOIN `client` c ON c.user_id=u.id INNER JOIN user_address uad ON uad.user_id=u.id "
							+ "INNER JOIN addresses ad ON ad.id=uad.address_id INNER JOIN orders o ON o.user_id=u.id"
							+ " where x.role_id=:role")
					.setParameter("role", role).getResultList();
			for (Object[] usr : user) {
				Map<String,String> map=new HashMap<String,String>();
				map.put("firstName",getValue(usr[0]));
				map.put("lastName",getValue(usr[1]));
				map.put("mobilePhone",getValue(usr[3]));
				map.put("race",getValue(usr[5]));
				map.put("address",getValue(usr[6]));
				map.put("orders", getValue(usr[7]));	
				customers.add(map);
				
			}
		}
		return customers;
	}

	public List<Map> getAllCustomersEnrolledByNgo(User user) {
		List<Map> customers = new ArrayList<>();
		Long role = HNIRoles.NGO.getRole();
		Long userId = null;
		if (user != null) {
			userId = user.getId();

			Query q = em
					.createQuery(
							"select  x from UserOrganizationRole x where x.id.roleId=:roleId and x.id.userId=:userId")
					.setParameter("roleId", role).setParameter("userId", userId);
			List<Long> userOrganizationRole = q.getResultList();
			if (!((userOrganizationRole.isEmpty()) || (userOrganizationRole == null))) {
				List<Object[]> users = em
						.createNativeQuery(
								"select distinct u.first_name,u.last_name,u.gender_code,u.mobile_phone,u.email,c.race,"
								+ "ad.address_line1, COUNT(o.id)  from users u INNER JOIN user_organization_role x"
								+ " ON u.id=x.user_id INNER JOIN `client` c ON c.user_id=u.id "
								+ "INNER JOIN user_address uad ON uad.user_id=u.id "
								+ "INNER JOIN addresses ad ON ad.id=uad.address_id "
								+ "INNER JOIN orders o ON o.user_id=u.id where x.role_id=:role")
						.setParameter("userId", userId).getResultList();
				for (Object[] usr : users) {
					Map<String,String> map=new HashMap<String,String>();
					map.put("firstName",getValue(usr[0]));
					map.put("lastName",getValue(usr[1]));
					map.put("mobilePhone",getValue(usr[3]));
					map.put("race",getValue(usr[5]));
					map.put("address",getValue(usr[6]));
					map.put("orders", getValue(usr[7]));	
					customers.add(map);
				}
			}
		}
		return customers;
	}
}