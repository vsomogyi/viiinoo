package com.alasdoo.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The chemical properties of the wine.
 * @author Vinofil
 *
 */
public class ChemicalAnalysis implements Serializable {
	
	private static final long serialVersionUID = 132032339796386932L;

	private String sugar;
	private BigDecimal alcohol;
	private float pH;
	private float totalAcidity;
	
	
	
	public String getSugar() {
		return sugar;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}

	public BigDecimal getAlcohol() {
		return alcohol;
	}
	public void setAlcohol(BigDecimal alcohol) {
		this.alcohol = alcohol;
	}
	public float getpH() {
		return pH;
	}
	public void setpH(float pH) {
		this.pH = pH;
	}
	public float getTotalAcidity() {
		return totalAcidity;
	}
	public void setTotalAcidity(float totalAcidity) {
		this.totalAcidity = totalAcidity;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
	//	result = prime * result + ((alcohol == null) ? 0 : alcohol.hashCode());
		result = prime * result + Float.floatToIntBits(pH);
		result = prime * result + ((sugar == null) ? 0 : sugar.hashCode());
		result = prime * result + Float.floatToIntBits(totalAcidity);
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
		ChemicalAnalysis other = (ChemicalAnalysis) obj;
//		if (alcohol == null) {
//			if (other.alcohol != null)
//				return false;
//		} else if (!alcohol.equals(other.alcohol))
//			return false;
		if (Float.floatToIntBits(pH) != Float.floatToIntBits(other.pH))
			return false;
		if (sugar == null) {
			if (other.sugar != null)
				return false;
		} else if (!sugar.equals(other.sugar))
			return false;
		if (Float.floatToIntBits(totalAcidity) != Float
				.floatToIntBits(other.totalAcidity))
			return false;
		return true;
	}
	
	
}