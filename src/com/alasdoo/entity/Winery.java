package com.alasdoo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.hibernate.validator.constraints.NotBlank;

import com.alasdoo.propertyValidators.EmailWithTld;
import com.alasdoo.propertyValidators.NotSpecial;
import com.alasdoo.propertyValidators.UrlVal;
import com.alasdoo.service.HasPicture;
import com.google.appengine.api.datastore.Key;

/**
 * The winery where the wine is produced
 * @author Vinofil
 *
 */
@PersistenceCapable
@Searchable
public class Winery implements Serializable,HasPicture {

	private static final long serialVersionUID = 3779031094811084355L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@SearchableId(accessor = "property", converter = "keyToString")
	private Key key;

	@Persistent
	private Date date;
	
	@NotSpecial
	@NotBlank
	@Persistent
	@SearchableProperty
	private String name;

	@Persistent
	@SearchableProperty
	private String url_name;

	@NotSpecial
	@Persistent
	@SearchableProperty
	private String address;

	@NotSpecial
	@Persistent
	@SearchableProperty
	private String city;

	@NotSpecial
	@Persistent
	private String phone;

	@EmailWithTld
	@Persistent
	private String email;


	@UrlVal
	@Persistent
	private String website;


	@NotSpecial
	@Persistent(serialized="true")
	private String about;

	@Persistent
	private List<Long> pictures = new ArrayList<Long>();

	@Persistent
	private Long profilePicture;
	// due to GAE limitations, the unowned one-to-one relationship needs to be a Set<Key>
	//	private RegionCountry country;
	@Persistent	
	private Key regionCountry;

//	@Persistent
//	private Set<String> fts = new HashSet<String>();

	public Winery(String name, String url_name,String address, String city, String phone,
			String email, String website, String about) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.email = email;
		this.website = website;
		this.about = about;
		this.url_name = url_name;
	}


	public Winery(Key key, String name, String url_name, String address,
			String city, String phone, String email, String website,
			String about, List<Long> pictures, Long profilePicture,
			Key regionCountry) {
		this.key = key;
		this.name = name;
		this.url_name = url_name;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.email = email;
		this.website = website;
		this.about = about;
		this.pictures = pictures;
		this.profilePicture = profilePicture;
		this.regionCountry = regionCountry;
	}


	public Winery() {
		// FIXME: delete this constructor and refactor the functions which depends on it
	}

//	/**
//	 * Prepares the index for the search function
//	 */
//	public void prepareIndex(){
//		SearchJanitor.updateFTSStuffForWinery(this);
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((about == null) ? 0 : about.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((regionCountry == null) ? 0 : regionCountry.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url_name == null) ? 0 : url_name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
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
		Winery other = (Winery) obj;
		if (about == null) {
			if (other.about != null)
				return false;
		} else if (!about.equals(other.about))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (regionCountry == null) {
			if (other.regionCountry != null)
				return false;
		} else if (!regionCountry.equals(other.regionCountry))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url_name == null) {
			if (other.url_name != null)
				return false;
		} else if (!url_name.equals(other.url_name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (pictures == null || pictures.size() == 0) {
			if (other.pictures != null && other.pictures.size() != 0)
				return false;
		} else if (!pictures.equals(other.pictures))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
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



	public String getUrl_name() {
		return url_name;
	}



	public void setUrl_name(String url_name) {
		this.url_name = url_name;
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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getWebsite() {
		return website;
	}



	public void setWebsite(String website) {
		this.website = website;
	}



	public String getAbout() {
		return about;
	}



	public void setAbout(String about) {
		this.about = about;
	}



	public List<Long> getPictures() {
		return pictures;
	}



	public void setPictures(List<Long> pictures) {
		this.pictures = pictures;
	}



	public Key getRegionCountry() {
		return regionCountry;
	}



	public void setRegionCountry(Key regionCountry) {
		this.regionCountry = regionCountry;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

	public void addPicture(Long pictureId) {
		//  if the picID is not in the list add to list
		if (!pictures.contains(pictureId)){
			pictures.add(pictureId);
		}
	}

	public Long getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Long profilePicture) {
		this.profilePicture = profilePicture;
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



}