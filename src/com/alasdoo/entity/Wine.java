package com.alasdoo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.Min;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.alasdoo.propertyValidators.NotSpecial;
import com.alasdoo.propertyValidators.VintageCheck;
import com.alasdoo.service.HasPicture;
import com.google.appengine.api.datastore.Key;

/**
 *  Wine is an alcoholic beverage, made of fermented fruit juice, usually from grapes.
 * @author Vinofil
 *
 */
@PersistenceCapable(detachable="true")
@Searchable
public class Wine implements Serializable,HasPicture {

	private static final long serialVersionUID = -1753220836744958386L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@SearchableId(accessor = "property", converter = "keyToString")
	private Key key;

	@NotSpecial
	@NotBlank
	@Persistent
	@SearchableProperty
	private String name;

	@Persistent	
	private String url_name;

	@VintageCheck
	@Persistent
	@SearchableProperty
	private Integer vintage;

	@Range (min=0 ,max=100)
	@NumberFormat(style=Style.NUMBER)
	@Persistent
	private BigDecimal alcohol;

	@Persistent
	private Set<String> awards;

	@Min(0)
	@NumberFormat(style=Style.NUMBER)
	@Persistent
	private BigDecimal averagePrice;
	
	@Persistent
	private Integer averageRating;

	@Persistent
	private Integer numberOfReviews;
	
	//@Persistent(serialized="true")
	// The Blob to String was problematic 
	private String notes;

	//TODO: PICTURE
	@Persistent
	private List<Long> pictures = new ArrayList<Long>();


	// RELATIONSHIPS

	// due to GAE limitations, the unowned one-to-one relationship needs to be a Set<Key>
	//	private Winery winery;
	@Persistent	
	private Key winery;

	// due to GAE limitations, the unowned one-to-one relationship needs to be a Set<Key>
	//	private RegionCountry country;
	@Persistent	
	private Key regionCountry;

	// due to GAE limitations, the unowned one-to-many relationship needs to be a Set<Key>
	//	private List<Character> character = new ArrayList<Character>();
	@Persistent	
	private List<Key> character = new ArrayList<Key>();

	// List can't be embedded, so the whole attribute will be serialized, and stored as a blob
	@Persistent(serialized = "true")
	private List<VarietalComposition> varietalComposition = new ArrayList<VarietalComposition>();

	@Persistent
	private ProductionDetails productionDetails ;

	@Persistent
	private Long profilePicture;
	
	@Persistent
	private Date date;
	
	private Date reviewLastModified;
	
	private Boolean hasReviews;
	public Wine(String name, int vintage, BigDecimal alcohol,
			BigDecimal averagePrice) {
		this.name = name;
		this.vintage = vintage;
		this.alcohol = alcohol;
		this.averagePrice = averagePrice;
		this.averageRating = 0;
		this.numberOfReviews = 0;
	}
	public Wine() {
		this.averageRating = 0;
		this.numberOfReviews = 0;
	}
	
	
	public Wine(Key key, String name, String url_name, Integer vintage,
			BigDecimal alcohol, Set<String> awards, BigDecimal averagePrice,
			Integer averageRating, Integer numberOfReviews, String notes,
			List<Long> pictures, Key winery, Key regionCountry,
			List<Key> character, List<VarietalComposition> varietalComposition,
			ProductionDetails productionDetails, Long profilePicture) {
		super();
		this.key = key;
		this.name = name;
		this.url_name = url_name;
		this.vintage = vintage;
		this.alcohol = alcohol;
		this.awards = awards;
		this.averagePrice = averagePrice;
		this.averageRating = averageRating;
		this.numberOfReviews = numberOfReviews;
		this.notes = notes;
		this.pictures = pictures;
		this.winery = winery;
		this.regionCountry = regionCountry;
		this.character = character;
		this.varietalComposition = varietalComposition;
		this.productionDetails = productionDetails;
		this.profilePicture = profilePicture;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alcohol == null) ? 0 : alcohol.hashCode());
		result = prime * result + ((averagePrice == null) ? 0 : averagePrice.hashCode());
		result = prime * result + ((averageRating == null) ? 0 : averageRating.hashCode());
		result = prime * result + ((numberOfReviews == null) ? 0 : numberOfReviews.hashCode());
		result = prime * result + ((awards == null) ? 0 : awards.hashCode());
		result = prime * result + ((character == null) ? 0 : character.hashCode());
		result = prime * result + ((regionCountry == null) ? 0 : regionCountry.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url_name == null) ? 0 : url_name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
		result = prime * result + ((productionDetails == null) ? 0 : productionDetails.hashCode());
		result = prime * result + ((varietalComposition == null) ? 0 : varietalComposition.hashCode());
		result = prime * result + ((vintage == null) ? 0 : vintage.hashCode());
		result = prime * result + ((winery == null) ? 0 : winery.hashCode());
		result = prime * result + ((profilePicture == null) ? 0 : profilePicture.hashCode());
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
		Wine other = (Wine) obj;
		if (alcohol == null) {
			if (other.alcohol != null)
				return false;
		} else if (!alcohol.equals(other.alcohol))
			return false;
		if (url_name == null) {
			if (other.url_name != null)
				return false;
		} else if (!url_name.equals(other.url_name))
			return false;
		if (averagePrice == null) {
			if (other.averagePrice != null)
				return false;
		} else if (!averagePrice.equals(other.averagePrice))
			return false;
		if (numberOfReviews == null) {
			if (other.numberOfReviews != null)
				return false;
		} else if (!numberOfReviews.equals(other.numberOfReviews))
			return false;
		if (averageRating == null) {
			if (other.averageRating != null)
				return false;
		} else if (!averageRating.equals(other.averageRating))
			return false;
		if (awards == null || awards.size() == 0) {
			if (other.awards != null && other.awards.size() != 0)
				return false;
		} else if (!awards.equals(other.awards))
			return false;
		if (character == null || character.size() == 0) {
			if (other.character != null  && other.character.size() != 0)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (regionCountry == null) {
			if (other.regionCountry != null)
				return false;
		} else if (!regionCountry.equals(other.regionCountry))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (pictures == null || pictures.size() == 0) {
			if (other.pictures != null && other.pictures.size() != 0)
				return false;
		} else if (!pictures.equals(other.pictures))
			return false;
		if (productionDetails == null) {
			if (other.productionDetails !=null)
				return false;
		} else if (!productionDetails.equals(other.productionDetails))
			return false;
		if (varietalComposition == null || varietalComposition.size() == 0) {
			if (other.varietalComposition != null  && other.varietalComposition.size() != 0)
				return false;
		} else if (!varietalComposition.equals(other.varietalComposition))
			return false;		
		if (vintage == null) {
			if (other.vintage !=null)
				return false;
		} else if (!vintage.equals(other.vintage))
			return false;
		if (winery == null) {
			if (other.winery != null)
				return false;
		} else if (!winery.equals(other.winery))
			return false;
		if (profilePicture == null) {
			if (other.profilePicture != null)
				return false;
		} else if (!profilePicture.equals(other.profilePicture))
			return false;
		return true;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl_name() {
		return url_name;
	}
	public void setUrl_name(String url_name) {
		this.url_name = url_name;
	}
	public Integer getVintage() {
		return vintage;
	}
	public void setVintage(Integer vintage) {
		this.vintage = vintage;
	}
	public BigDecimal getAlcohol() {
		return alcohol;
	}
	public void setAlcohol(BigDecimal alcohol) {
		this.alcohol = alcohol;
	}
	public Set<String> getAwards() {
		return awards;
	}
	public void setAwards(Set<String> awards) {
		this.awards = awards;
	}
	public BigDecimal getAveragePrice() {
		return averagePrice;
	}
	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}
	public Integer getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(Integer averageRating) {
		this.averageRating = averageRating;
	}
	public Integer getNumberOfReviews() {
		return numberOfReviews;
	}
	public void setNumberOfReviews(Integer numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<Long> getPictures() {
		return pictures;
	}
	public void setPictures(List<Long> pictures) {
		this.pictures = pictures;
	}
	public Key getWinery() {
		return winery;
	}
	public void setWinery(Key winery) {
		this.winery = winery;
	}
	public Key getRegionCountry() {
		return regionCountry;
	}
	public void setRegionCountry(Key regionCountry) {
		this.regionCountry = regionCountry;
	}
	public List<Key> getCharacter() {
		return character;
	}
	public void setCharacter(List<Key> character) {
		this.character = character;
	}
	public List<VarietalComposition> getVarietalComposition() {
		return varietalComposition;
	}
	public void setVarietalComposition(List<VarietalComposition> varietalComposition) {
		this.varietalComposition = varietalComposition;
	}
	public ProductionDetails getProductionDetails() {
		return productionDetails;
	}
	public void setProductionDetails(ProductionDetails productionDetails) {
		this.productionDetails = productionDetails;
	}
	public Long getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(Long profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	public void addPicture(Long pictureId) {
		//  if the picID is not in the list add to list
		if (!pictures.contains(pictureId)){
			pictures.add(pictureId);
		}
	}
	@Override
	public String getPictureName() {
		
		return url_name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getReviewLastModified() {
		return reviewLastModified;
	}
	public void setReviewLastModified(Date reviewLastModified) {
		this.reviewLastModified = reviewLastModified;
	}
	public Boolean getHasReviews() {
		return hasReviews;
	}
	public void setHasReviews(Boolean hasReviews) {
		this.hasReviews = hasReviews;
	}
	
	

}