package org.hni.common.om;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "food_services_availability")
public class FoodServiceAvailability {
	private static final long serialVersionUID = 7553475738921092329L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "food_services_id")
	private Long foodServicesId;
	
	@Column(name = "week_day")
	private Long weekDay;
	
	@Column(name = "created")
	private Date created;

	@Column(name = "created_by")
	private Long createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFoodServicesId() {
		return foodServicesId;
	}

	public void setFoodServicesId(Long foodServicesId) {
		this.foodServicesId = foodServicesId;
	}

	public Long getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(Long weekDay) {
		this.weekDay = weekDay;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	
}
