package com.alasdoo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * The review of a wine
 * @author Vinofil
 *
 */
@PersistenceCapable
public class Review implements Serializable {
	
	private static final long serialVersionUID = -3821858480028109042L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Date date;
	
	@NotNull
	@Range(min=1,max=5)
	@Persistent
	private Integer score;
	
	@Range(min=1,max=5)
	@Persistent
	private Integer aromaScore;
	
	@Range(min=1,max=5)
	@Persistent
	private Integer tasteScore;
	
	@Range(min=1,max=5)
	@Persistent
	private Integer colorScore;

	//TODO: store as blob
	//@Persistent(serialized="true")
	private String reviewText;
	
	 
	// RELATIONSHIPS
	
	// due to GAE limitations, the unowned one-to-one relationship needs to be a Set<Key>
	//	private WineEnthusiast wineUser;
	@Persistent	
	private Key wineUser;
	
	// owned one-to-one relationship
	@Persistent	
	private Key wineKey;
	
	 
	
	public Review() {
	}
	
	
	public Review(Key key, Date date, Integer score, Integer aromaScore,
			Integer tasteScore, Integer colorScore, String reviewText,
			Key wineUser, Key wineKey) {
		this.key = key;
		this.date = date;
		this.score = score;
		this.aromaScore = aromaScore;
		this.tasteScore = tasteScore;
		this.colorScore = colorScore;
		this.reviewText = reviewText;
		this.wineUser = wineUser;
		this.wineKey = wineKey; 
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aromaScore == null) ? 0 : aromaScore.hashCode());
		result = prime * result + ((colorScore == null) ? 0 : colorScore.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((reviewText == null) ? 0 : reviewText.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((tasteScore == null) ? 0 : tasteScore.hashCode());
		result = prime * result + ((wineKey == null) ? 0 : wineKey.hashCode());
		result = prime * result + ((wineUser == null) ? 0 : wineUser.hashCode());
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
		Review other = (Review) obj;
		if (aromaScore == null) {
			if (other.aromaScore != null)
				return false;
		} else if (!aromaScore.equals(other.aromaScore))
			return false;
		if (colorScore == null) {
			if (other.colorScore != null)
				return false;
		} else if (!colorScore.equals(other.colorScore))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (reviewText == null) {
			if (other.reviewText != null)
				return false;
		} else if (!reviewText.equals(other.reviewText))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (tasteScore == null) {
			if (other.tasteScore != null)
				return false;
		} else if (!tasteScore.equals(other.tasteScore))
			return false;
		if (wineKey == null) {
			if (other.wineKey != null)
				return false;
		} else if (!wineKey.equals(other.wineKey))
			return false;
		if (wineUser == null) {
			if (other.wineUser != null)
				return false;
		} else if (!wineUser.equals(other.wineUser))
			return false;
		return true;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(String key){
		this.key = KeyFactory.stringToKey(key);
	}

	public void setKey(Key key) {
		this.key = key;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getAromaScore() {
		return aromaScore;
	}
	public void setAromaScore(Integer aromaScore) {
		this.aromaScore = aromaScore;
	}
	public Integer getTasteScore() {
		return tasteScore;
	}
	public void setTasteScore(Integer tasteScore) {
		this.tasteScore = tasteScore;
	}
	public Integer getColorScore() {
		return colorScore;
	}
	public void setColorScore(Integer colorScore) {
		this.colorScore = colorScore;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public Key getWineUser() {
		return wineUser;
	}
	public void setWineUser(Key wineUser) {
		this.wineUser = wineUser;
	}
	public Key getWineKey() {
		return wineKey;
	}
	public void setWineKey(Key wineKey) {
		this.wineKey = wineKey;
	}
	

	
}