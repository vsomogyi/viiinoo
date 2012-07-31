package com.alasdoo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.alasdoo.propertyValidators.NotSpecial;
import com.alasdoo.service.CommonsService;
import com.google.appengine.api.datastore.Key;

/**
 * 
 * @author Vinofil
 *
 */
@PersistenceCapable(detachable="true")
public class ProductionDetails implements Serializable {
	

	private static final long serialVersionUID = -3470464990884560666L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@NotSpecial
	@Persistent
	private String bottleSize;
	
	@NotSpecial
	@Persistent
	private String amountProduced;
	
	@Persistent(serialized="true")
	private String closure;
	
	@Persistent(serialized="true")
	private List<Aged> aged = new ArrayList<Aged>();
	
	
	public ProductionDetails(){
		closure = EClosure.Unknown.toString();
	}
	public String getBottleSize() {
		return bottleSize;
	}

	public void setBottleSize(String bottleSize) {
		this.bottleSize = bottleSize;
	}

	public String getAmountProduced() {
		return amountProduced;
	}

	public void setAmountProduced(String amountProduced) {
		this.amountProduced = amountProduced;
	}
	
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}
	public EClosure getClosure() {

		return CommonsService.deserialize(EClosure.class, closure);
	}

	public void setClosure(EClosure closure) {
		this.closure = CommonsService.getSerializedForm(closure);
	}
	
	public List<Aged> getAged() {
		return aged;
	}

	public void setAged(List<Aged> aged) {
		this.aged = aged;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aged == null) ? 0 : aged.hashCode());
		result = prime * result
				+ ((amountProduced == null) ? 0 : amountProduced.hashCode());
		result = prime * result
				+ ((bottleSize == null) ? 0 : bottleSize.hashCode());
		result = prime * result + ((closure == null) ? 0 : closure.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionDetails other = (ProductionDetails) obj;
		if (aged == null || aged.size() == 0) {
			if (other.aged != null && other.aged.size() != 0)
				return false;
		} else if (!aged.equals(other.aged))
			return false;
		if (amountProduced == null) {
			if (other.amountProduced != null)
				return false;
		} else if (!amountProduced.equals(other.amountProduced))
			return false;
		if (bottleSize == null) {
			if (other.bottleSize != null)
				return false;
		} else if (!bottleSize.equals(other.bottleSize))
			return false;
		if (closure == null) {
			if (other.closure != null)
				return false;
		} else if (!closure.equals(other.closure))
			return false;
		return true;
	}

}