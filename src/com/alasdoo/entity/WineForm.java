package com.alasdoo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;


/**
 * Support Class for Binding composite object (from form to an object)
 * @author Vinofil
 *
 */
public class WineForm {
	
	@NotNull
	@Valid
	private Wine wine;
	
	@Valid	
	private List<Aged> agedList;
	 
	private String wineName;
	private String wineryName;
	private String newWineryName;
	
	@Valid
	private List<com.alasdoo.entity.Character> characterList; 	 
		
	@Valid
	private List<VarietalComposition> compositionList; 	 
	
	@SuppressWarnings("unchecked")
	public WineForm(){
				
		agedList =  LazyList.decorate( new ArrayList<Aged>(), FactoryUtils.instantiateFactory(Aged.class));
		compositionList =  LazyList.decorate( new ArrayList<VarietalComposition>(), FactoryUtils.instantiateFactory(VarietalComposition.class));	
		characterList = LazyList.decorate( new ArrayList<com.alasdoo.entity.Character>(), FactoryUtils.instantiateFactory(com.alasdoo.entity.Character.class));
	}


	private String type;


	public Wine getWine() {
		return wine;
	}
	public void setWine(Wine wine) {
		this.wine = wine;
	}

	public String getWineryName() {
		return wineryName;
	}
	public void setWineryName(String wineryName) {
		this.wineryName = wineryName;
	}
	public String getNewWineryName() {
		return newWineryName;
	}
	public void setNewWineryName(String newWineryName) {
		this.newWineryName = newWineryName;
	}

	public String getWineName() {
		return wineName;
	}
	public void setWineName(String wineName) {
		this.wineName = wineName;
	}

	public List<Aged> getAgedList() {
		return agedList;
	}
	public void setAgedList(List<Aged> agedList) {
		this.agedList = agedList;
	}
	public List<com.alasdoo.entity.Character> getCharacterList() {
		return characterList;
	}
	public void setCharacterList(List<com.alasdoo.entity.Character> characterList) {
		this.characterList = characterList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<VarietalComposition> getCompositionList() {
		return compositionList;
	}
	public void setCompositionList(List<VarietalComposition> compositionList) {
		this.compositionList = compositionList;
	}	
	
}
