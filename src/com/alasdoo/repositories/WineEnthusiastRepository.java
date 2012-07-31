package com.alasdoo.repositories;

import java.util.List;

import com.alasdoo.entity.WineEnthusiast;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

public interface WineEnthusiastRepository {

	
	public boolean usernameInUse(String username);
	
	/**
	 * Saves the Wine Enthusiast 
	 * 
	 * @param object - The WineEnthusiast object that will be saved
	 */
	public void save(WineEnthusiast we);
	
	/**
	 * Finds the Wine Enthusiast object based on the logged User
	 * 
	 * @param user - A google user
	 * @return List of Wine Enthusiast objects
	 */
	public WineEnthusiast get(User user);	
	
	/**
	 * Finds all Wine Enthusiast
	 * 
	 * @return List of Wine Enthusiast objects
	 */
	public List<WineEnthusiast> getAll();
	
	/**
	 * Deletes every WineEnthusiast
	 * An unused function used for development
	 */
	@Deprecated
	public void deleteAll();
	
	/**
	 * Remove WineEnthusisast
	 * @param we
	 */
	//public void remove(WineEnthusiast we);
	
	/**
	 * Get's the key of the profile picture
	 */
	public Long getProfilePictureId(WineEnthusiast we);
	
	/**
	 * Finds the Wine Enthusiast object by Key
	 * 
	 * @param key 
	 * @return User
	 */
	public WineEnthusiast getByKey(Key key);

}
