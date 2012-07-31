package com.alasdoo.repositories;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Repository;

import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineEnthusiast;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@Repository
public class WineEnthusiastRepositoryImpl extends JdoDaoSupport implements WineEnthusiastRepository {



	@Autowired
	protected PersistenceManagerFactory persistenceManagerFactory;


	@PostConstruct
	public void init() {

		super.setPersistenceManagerFactory(persistenceManagerFactory);

	}


	public WineEnthusiastRepositoryImpl() {
		super();
	}

	/**
	 * Saves the Wine Enthusiast 
	 * 
	 * @param object - The WineEnthusiast object that will be saved
	 */
	public void save(WineEnthusiast we) {


		PersistenceManager pm = getPersistenceManager(true);		
		try {
			pm.makePersistent(we);
		}
		catch (Exception e) {
			//TODO:  logging
			e.printStackTrace();
		}
		finally {
			releasePersistenceManager(pm);
		}
	}

	/**
	 * Finds the Wine Enthusiast object based on the logged User
	 * 
	 * @param user - A google user
	 * @return List of Wine Enthusiast objects
	 */
	public WineEnthusiast get(User user){		

		PersistenceManager pm = getPersistenceManager(true);

		Query query = pm.newQuery("select from "+WineEnthusiast.class.getName() +" where user == userParam");
		query.declareParameters("String userParam");

		@SuppressWarnings("unchecked")
		List<WineEnthusiast> res = (List<WineEnthusiast>) query.execute(user);

		if (res.size() == 0) {
			return null;
		}

		return res.get(0);

	}

	/**
	 * Finds all Wine Enthusiast
	 * 
	 * @return List of Wine Enthusiast objects
	 */
	public List<WineEnthusiast> getAll() {

		PersistenceManager pm = getPersistenceManager(true);

		String query = "select from "+WineEnthusiast.class.getName();

		@SuppressWarnings("unchecked")
		List<WineEnthusiast> res = (List<WineEnthusiast>) pm.newQuery(query).execute();

		return res;
	}

	/**
	 * Deletes every WineEnthusiast
	 */
	@Deprecated
	public void deleteAll() {
		// Delete all instances from the DB
		PersistenceManager pm = getPersistenceManager(true);
		String query = "select from "+WineEnthusiast.class.getName();
		@SuppressWarnings("unchecked")
		List<WineEnthusiast> res = (List<WineEnthusiast>) pm.newQuery(query).execute();
		pm.deletePersistentAll(res);
		System.out.println("DELETED");
	}

	/**
	 * Checks if an username is already in use
	 */
	public boolean usernameInUse(String username) {
		PersistenceManager pm = getPersistenceManager(true);

		try{
			javax.jdo.Query query = pm.newQuery(WineEnthusiast.class,
			"name == paramName ");
			query.declareParameters("String paramName");
			@SuppressWarnings("unchecked")
			List<WineEnthusiast> res = (List<WineEnthusiast>) query.execute(username);

			if ((res.size() > 0)) {			
				return true;
			}else
				return false;
		}catch (Exception e) {
			e.printStackTrace();
			return true;
		} finally {
			releasePersistenceManager(pm);
		}
	}


	/**
	 * 
	 */
	//@Override
//	public void remove(WineEnthusiast we) {
//		// TODO Auto-generated method stub
//	}

	/**
	 * Get's the key of the profile picture
	 * 
	 */
	@Override	
	public Long getProfilePictureId(WineEnthusiast we) {
		// Only one profile picture is permitted (for now)
		long result = -1;
		try{
			@SuppressWarnings("rawtypes")
			Iterator it = we.getPictures().iterator();
			if (it.hasNext())
				result  = (Long) it.next();
			we.getPictures();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}


	@Override
	public WineEnthusiast getByKey(Key key) {
		PersistenceManager pm = getPersistenceManager(true);
		WineEnthusiast result= null;
		try{
			result  = pm.getObjectById(WineEnthusiast.class, key);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			releasePersistenceManager(pm);
		}
		return result;
	}

}