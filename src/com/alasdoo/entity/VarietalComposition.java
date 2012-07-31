package com.alasdoo.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.alasdoo.propertyValidators.NotSpecial;

/**
 * Varietal composition defines the composition of two or more type of wines.
 * @author Vinofil
 *
 */
public class VarietalComposition implements Serializable {
	
	private static final long serialVersionUID = 7897004438254041021L;

	@NotSpecial
	private String varietal;
	
	@Range(min=1,max=100)
	@NumberFormat(style=Style.NUMBER)
	private BigDecimal percentage;
	
	
	public String getVarietal() {
		return varietal;
	}
	public void setVarietal(String varietal) {
		this.varietal = varietal;
	}
		
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result
				+ ((varietal == null) ? 0 : varietal.hashCode());
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
		VarietalComposition other = (VarietalComposition) obj;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		if (varietal == null) {
			if (other.varietal != null)
				return false;
		} else if (!varietal.equals(other.varietal))
			return false;
		return true;
	}

	
}