package com.alasdoo.service;

/**
 * Common service class
 * @author Turzai Tihomir
 *
 */

public class CommonsService {

	public enum OrderBy{WINECREATIONDATE, REVIEWCREATIONDATE} 
	
	
	
	public static OrderBy convertStringToSortOrder(String orderBy){
		if (orderBy == null)
			return OrderBy.WINECREATIONDATE;
		if (orderBy.contains("REVIEW"))
			return OrderBy.REVIEWCREATIONDATE;
		return OrderBy.WINECREATIONDATE;
	}
	/**
	 * Returns a new string resulting from replacing all occurrences of Char ' ' in this string with Char '-'. 
	 * @param name
	 * @return
	 */
	public static String nameToFancyURL(String name){
	    name = name.replace(' ', '-');
		name = name.replaceAll("[^a-zA-Z0-9_-]", "");
		if (name.equals(""))
			name = "no-name";	
		return name;
	}
	
	/**
	 * Makes string from Enum
	 * @param enumVal
	 * @return
	 */
	 public static String getSerializedForm(Enum<?> enumVal) {
	        String name = enumVal.name();
	        // possibly quote value?
	        return name;
	    }
/**
 * MAkes Enum from String
 * @param <E>
 * @param enumType
 * @param dbVal
 * @return
 */
	    public static <E extends Enum<E>> E deserialize(Class<E> enumType, String dbVal) {
//	    	if (dbVal == null)
//	    	 return 
	        // possibly handle unknown values, below throws IllegalArgEx
	        return Enum.valueOf(enumType, dbVal.trim());
	    }

}
