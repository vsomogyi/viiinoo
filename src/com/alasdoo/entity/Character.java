package com.alasdoo.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.alasdoo.service.CommonsService;
import com.google.appengine.api.datastore.Key;

/**
 * The characteristics of a particular grape variety, or the characteristics of a wine that come from the grape variety from which it is made.
 * @author Vinofil
 *
 */
@PersistenceCapable
public class Character implements Serializable {

	private static final long serialVersionUID = -8044806948210827123L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	//TODO: find a way to use multi field primary keys
	
	/**
	 * pl. ez a bor: barrique, testes, sava, rubint
	 */
	@Persistent
	private String character;
	
	@Persistent
	private EKind kind;
	
	public Character(){
	}
	
	
	public Character(String characterKind, String character2) {
		character = character2;
		kind = CommonsService.deserialize(EKind.class, characterKind);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((character == null) ? 0 : character.hashCode());
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
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
		Character other = (Character) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;
		return true;
	}


	public Key getKey() {
		return key;
	}


	public void setKey(Key key) {
		this.key = key;
	}


	public String getCharacter() {
		return character;
	}


	public void setCharacter(String character) {
		this.character = character;
	}


	public EKind getKind() {
		return kind;
	}


	public void setKind(EKind kind) {
		this.kind = kind;
	}
	

	public void setKind(String kind) {
		this.kind = CommonsService.deserialize(EKind.class, kind);
		
	}
	
}