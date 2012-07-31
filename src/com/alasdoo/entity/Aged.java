package com.alasdoo.entity;

import java.io.Serializable;

import javax.validation.constraints.Min;

import com.alasdoo.propertyValidators.NotSpecial;

/**
 * Wines that are fermented in containers such as stainless steel, and then 
 * placed into wooden barrels for maturation. It may also refer to wines that are both fermented and aged in the barrel.
 * @author Vinofil
 *
 */
public class Aged implements Serializable {
	
	private static final long serialVersionUID = 5963355247058426451L;

	@Min(0)
	private Integer months;

		
	private EContainer container;

	@NotSpecial
	private String comment;
	
	
	public Aged() {
		this.months = 0;
		this.container = EContainer.Unknown;
		this.comment = "";
	}
	
	public Aged(Integer months, EContainer container, String comment) {
		this.months = months;
		this.container = container;
		this.comment = comment;
	}
	public Integer getMonths() {
		return months;
	}
	public void setMonths(Integer months) {
		this.months = months;
	}
	public String getContainer() {
		 String res ="";	
			
			try{
				res = container.toString();
			}catch(Exception ex){
				
			}
			return res;
	}
	public void setContainer(EContainer container) {
		this.container = container;
	}
	public void setContainer(String container) {
		this.container = EContainer.valueOf(container);
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result
				+ ((container == null) ? 0 : container.hashCode());
		result = prime * result + ((months == null) ? 0 : months.hashCode());
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
		Aged other = (Aged) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (container == null) {
			if (other.container != null)
				return false;
		} else if (!container.equals(other.container))
			return false;
		if (months == null) {
			if (other.months != null)
				return false;
		} else if (!months.equals(other.months))
			return false;
		return true;
	}

	
}