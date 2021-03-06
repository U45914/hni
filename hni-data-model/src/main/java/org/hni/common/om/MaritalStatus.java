package org.hni.common.om;

// Generated Apr 19, 2017 11:39:46 PM by Hibernate Tools 3.6.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MaritalStatus generated by hbm2java
 */
@Entity
@Table(name = "marital_status")
public class MaritalStatus implements java.io.Serializable {

	private Integer id;
	private String maritalStatusDesc;

	public MaritalStatus() {
	}

	public MaritalStatus(String maritalStatusDesc) {
		this.maritalStatusDesc = maritalStatusDesc;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "marital_status_desc", nullable = false, length = 50)
	public String getMaritalStatusDesc() {
		return this.maritalStatusDesc;
	}

	public void setMaritalStatusDesc(String maritalStatusDesc) {
		this.maritalStatusDesc = maritalStatusDesc;
	}

}
