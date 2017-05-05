package org.hni.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.dao.DefaultGenericDAO;
import org.hni.common.om.Persistable;
import org.hni.provider.om.Provider;
import org.hni.type.HNIRoles;
import org.hni.user.om.Address;
import org.hni.user.om.User;
import org.hni.user.om.UserPartialData;
import org.hni.user.om.Volunteer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Component
public class NGOGenericDAO extends DefaultGenericDAO {
public NGOGenericDAO()
{
	super();
	}

public <T extends Persistable>List<T> saveBatch(Class<T> clazz, List<T> objList) {
	for(T obj:objList){
	if ( null == obj ) {
		return null;
	}
	if ( obj.getId() != null ) {
		obj = update(clazz, obj);
	} else {
		obj = insert(clazz, obj);
	}
	}
	return objList;
}

public List<NgoBasicDto> getAllNgo()
{
	List<NgoBasicDto> ngos = new ArrayList<>();
	Long ngoRoleId =  HNIRoles.NGO.getRole();
	List<Object[]> userOrganizationRoles=em.createNativeQuery("select u.id,u.first_name,u.last_name,u.mobile_phone,n.website,ad.address_line1,ad.city,ad.state"
			+ " from user_organization_role x INNER JOIN users u ON u.id = x.user_id "
			+ "LEFT OUTER join user_address ua on ua.user_id= x.user_id  LEFT OUTER join addresses ad on ad.id=ua.address_id "
			+ "LEFT OUTER JOIN ngo n ON n.id=u.id where x.role_id=:roleId").setParameter("roleId", ngoRoleId).getResultList();
	for(Object[] u:userOrganizationRoles)
	{
		 NgoBasicDto ngoBasicDto = new NgoBasicDto();
		 Long userId = Long.valueOf(getValue(u[0]));
		
		 ngoBasicDto.setUserId(userId);
		 ngoBasicDto.setName(getValue(u[1])+" "+getValue(u[2]));
		 ngoBasicDto.setPhone(getValue(u[3]));
		 ngoBasicDto.setWebsite(u[4]!=null?(String) u[4]:"");
		 ngoBasicDto.setAddress(getValue(u[5])+","+getValue(u[6])+","+getValue(u[7]));
		 ngoBasicDto.setCreatedUsers((Long) em.createQuery("select count(id) from Client where createdBy=:userId").setParameter("userId", userId).getSingleResult());
		 ngos.add(ngoBasicDto);
	}
	return ngos;
	 
	
}

public List<Volunteer> getAllVolunteers(Long loggedInUserId) {

	List<Volunteer> volunteerList = new ArrayList<>();
	Long volunteerRoleId =  HNIRoles.VOLUNTEERS.getRole();	
		List<Object[]> userDetails = em.createNativeQuery("SELECT "
				+ "u.id, u.first_name, u.last_name, u.gender_code, u.email,u.mobile_phone, ad.address_line1,ad.city,ad.state "
				+ "FROM user_organization_role uo INNER JOIN users u   on   u.id = uo.user_id "
				+ "INNER JOIN user_address uad on uad.user_id=uo.user_id inner join addresses ad on ad.id = uad.address_id "
				+ "WHERE uo.role_id=:roleId").setParameter("roleId", volunteerRoleId).getResultList();
		
		for (Object[] user : userDetails) {			
			Volunteer volunteer = new Volunteer();	
			Address address = new Address();
			volunteer.setId(Long.valueOf(getValue(user[0])));
			volunteer.setFirstName(getValue(user[1]));
			volunteer.setLastName(getValue(user[2]));
			volunteer.setSex(getValue(user[3]));
			volunteer.setEmail(getValue(user[4]));
			volunteer.setPhoneNumber(getValue(user[5]));
			address.setAddress1(getValue(user[6]));
			address.setCity(getValue(user[7]));
			address.setState(getValue(user[8]));
			volunteer.setAddress(address);
			volunteerList.add(volunteer);
		}		
	
	return volunteerList;
}
public void updateStatus(Long userId) {
	Long id = (Long) em.createQuery("select x.id from UserPartialData x where x.userId =:userId").setParameter("userId", userId).getSingleResult();
	if(id!=null){
		UserPartialData user = get(UserPartialData.class,id);
		user.setStatus("Y");
		save(UserPartialData.class,user);
	}
}

public List<ObjectNode> getAllProviders(User user) {
	List<ObjectNode> providers= new ArrayList<>();
	Long userId=user.getId();
	List<Object[]> result=em.createNativeQuery("select p.name as provider_name,p.website_url,p.created,u.first_name,a.name from providers p INNER JOIN users u  ON p.created_by =u.id INNER JOIN addresses a ON p.address_id=a.id and p.created_by=:uId").setParameter("uId",userId).getResultList();
	for(Object[] prov:result){
		ObjectNode provider=new ObjectMapper().createObjectNode();
		provider.put("name", getValue(prov[0]));
		provider.put("website",getValue(prov[1]));
		provider.put("createdOn",getValue(prov[2]));
		provider.put("createdBy",getValue(prov[3]));
		provider.put("address",getValue(prov[4]));
		providers.add(provider);
		
	}
	return providers;
}
}
