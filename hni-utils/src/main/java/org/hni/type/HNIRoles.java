/**
 * 
 */
package org.hni.type;

/**
 * @author Rahul
 *
 */
public enum HNIRoles {

	SUPER_ADMIN(1),
	NGO_ADMIN(2),
	NGO(3),
	CUSTOMER(4),
	VOLUNTEERS(5),
	ORGANIZATION(6);
	
	int role;
	
	HNIRoles(int role) {
		this.role = role;
	}
}
