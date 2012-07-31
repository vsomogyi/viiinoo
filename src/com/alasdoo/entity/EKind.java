package com.alasdoo.entity;

/**
 * The kind of a characteristic
 * 
 * Available values: Type, Color, Taste, Acid
 */
public enum EKind {
	Unknown("Unknown"),Type("Type"), Color("Color"), Taste("Taste"), Acid("Acid");	
	
	private final String label;

	private EKind(String label) {
	     this.label = label;
	   }

	   public String getLabel() {
	     return label;
	   }
}
