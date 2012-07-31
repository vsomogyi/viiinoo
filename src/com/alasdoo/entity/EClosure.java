package com.alasdoo.entity;



/**
 * Type of the closure
 * @author Vinofil
 *
 */
public enum EClosure {
	//TODO: steal from snooth: corck, screw...
	Unknown("Unknown"),Closure1("Corck"),Closure2("Screw"),Closure3("Closure3");
	
	private final String label;

	private EClosure(String label) {
	     this.label = label;
	   }

	   public String getLabel() {
	     return label;
	   } 
}