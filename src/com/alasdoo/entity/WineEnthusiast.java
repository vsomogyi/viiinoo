package com.alasdoo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import com.alasdoo.propertyValidators.EmailWithTld;
import com.alasdoo.propertyValidators.NotSpecial;
import com.alasdoo.propertyValidators.UrlVal;
import com.alasdoo.security.AppRole;
import com.alasdoo.service.HasPicture;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

/**
 * Registrated google user
 * @author Vinofil
 *
 */
@PersistenceCapable
public class WineEnthusiast implements Serializable,HasPicture {

	private static final long serialVersionUID = 2311047574660437820L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@NotSpecial
	@NotEmpty
	@Persistent
	private String name;

	@EmailWithTld
	@Persistent
	private String email;
		
	@UrlVal
	@Persistent
	private String website;

	//TODO: store as blob
	@NotSpecial
	@Persistent(serialized="true")
	private String bio;

	@NotSpecial
	@Persistent
	private String address;
	
	@Persistent
	private Set<AppRole> authorities;
	
	@Persistent
	private boolean enabled;
	
	// RELATIONSHIPS
	
	/**
	 * Favorite wine character
	 */
	// due to GAE limitations, the unowned one-to-many relationship needs to be a Set<Key>
	//	private List<Character> characters = new ArrayList<Character>();
	@Persistent	
	private Set<Key> characters;
	
	@Persistent
	private List<Long> pictures = new ArrayList<Long>();
	
	@Persistent
	private Long profilePicture;
	
	//TODO: a relationship to USER
	@Unique
	@Persistent
	private User user;
	
	
	
	public WineEnthusiast() {
		this.authorities = EnumSet.of(AppRole.NEW_USER);
		this.enabled = true;
	}
	
	// Constructor for new user
	public WineEnthusiast(String name, String email, User user) {
	
		
		this.name = name;
		this.email = email;
		this.user = user;
		this.website = null;
		this.bio = null;
		this.address = null;
		this.authorities = EnumSet.of(AppRole.NEW_USER);
		this.enabled = true;
	}


	public WineEnthusiast(String name, String email, String website,
			String bio, String address) {
		super();
		this.name = name;
		this.email = email;
		this.website = website;
		this.bio = bio;
		this.address = address;
		this.authorities = EnumSet.of(AppRole.USER);
		this.enabled = true;
	}
	//TODO check that the authorities is need to be in the hashCode or not? 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + ((characters == null) ? 0 : characters.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
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
		WineEnthusiast other = (WineEnthusiast) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (bio == null) {
			if (other.bio != null)
				return false;
		} else if (!bio.equals(other.bio))
			return false;
		if (characters == null || characters.size() == 0) {
			if (other.characters != null && other.characters.size() != 0)
				return false;
		} else if (!characters.equals(other.characters))
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
		if (pictures == null || pictures.size() == 0) {
			if (other.pictures != null && other.pictures.size() != 0)
				return false;
		} else if (!pictures.equals(other.pictures))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Long> getPictures() {
		return pictures;
	}

	public void setPictures(List<Long> pictures) {
		this.pictures = pictures;
	}
	
	public void addPicture(Long long1) {
		if (!pictures.contains(long1)){
			this.pictures.add(long1);
		}
	
	}

	public Set<Key> getCharacters() {
		return characters;
	}

	public void setCharacters(Set<Key> characters) {
		this.characters = characters;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<AppRole> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public Long getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Long profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public String getPictureName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return (" Name: " + name  + " Email: " + email);
	}	
	
	
}