package com.alasdoo.entity;

/**
 * Type of the container
 * @author warden
 *
 */
public enum EContainer {
	
	//TODO: Snooth: "Hungarian Oak Barrel","Canadian Oak Barrel","Glass"
	Unknown("Unknown"),Hungarian_Oak_Barrel("Hungarian Oak Barrel"),Canadian_Oak_Barrel("Canadian Oak Barrel"),Glass("Glass");
	
	private final String label;

	private EContainer(String label) {
	     this.label = label;
	   }

	   public String getLabel() {
	     return label;
	   }
}
