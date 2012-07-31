package com.alasdoo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alasdoo.entity.ProductionDetails;
import com.alasdoo.entity.Review;
import com.alasdoo.entity.VarietalComposition;
import com.alasdoo.entity.Wine;
import com.alasdoo.entity.Winery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import java.util.Date;
import com.google.appengine.api.datastore.Blob;

/**
 * Converts GAE Entity classes to real Entity classes 
 * @author warden
 *
 */
public class EntityAdapter {

	public static List<Review> convertEntitiesToReviews(List<Entity> entities){
		List<Review> result= new ArrayList<Review>();
		for (Entity e : entities){
			Key key =  e.getKey();
			Date date = (Date)e.getProperty("date"); 
			Integer score = (e.getProperty("score")!=null)?((Long)e.getProperty("score")).intValue():0;				
			Integer aromaScore = (e.getProperty("aromaScore")!=null)?((Long)e.getProperty("aromaScore")).intValue():0;
			Integer tasteScore = (e.getProperty("tasteScore")!=null)?((Long)e.getProperty("tasteScore")).intValue():0;
			Integer colorScore = (e.getProperty("colorScore")!=null)?((Long)e.getProperty("colorScore")).intValue():0; 
			String reviewText = (String)e.getProperty("reviewText");
			Key wineUser = (Key)e.getProperty("wineUser");
			Key wineKey = (Key)e.getProperty("wineKey");
			Review review = new Review(key, date, score, aromaScore, tasteScore, colorScore, reviewText, wineUser, wineKey);
			result.add(review);
		}
		return result;
	}
	
	public static List<Wine> convertEntitiesToWines(List<Entity> entities){
		List<Wine> result= new ArrayList<Wine>();
		for (Entity e : entities){
			Key key =  e.getKey();
			String name = (String)e.getProperty("name");
			String url_name = (String)e.getProperty("url_name");
			Integer vintage = (e.getProperty("vintage")!=null)?((Long)e.getProperty("vintage")).intValue():0;
			BigDecimal alcohol = (e.getProperty("alcohol")!=null)?new BigDecimal((Double)e.getProperty("alcohol")):null; 
			Set<String> awards =(Set<String>)e.getProperty("awards");
			BigDecimal averagePrice = (e.getProperty("averagePrice")!=null)?BigDecimal.valueOf((Double)e.getProperty("averagePrice")):null;
			Integer averageRating = (e.getProperty("averageRating")!=null)?((Long)e.getProperty("averageRating")).intValue():0;		
			Integer numberOfReviews = (e.getProperty("numberOfReviews")!=null)?((Long)e.getProperty("numberOfReviews")).intValue():0;
			String notes = (String)e.getProperty("notes");
			List<Long> pictures = (List<Long>)e.getProperty("pictures");
			Key winery = (Key)e.getProperty("winery");
			Key regionCountry = (Key)e.getProperty("regionCountry");
			List<Key> character = (List<Key>)e.getProperty("character");
			// TODO: 
			// GAE gives back a Blob object, and i didnt find a way to convert it into List<VarietalComposition>
			//List<VarietalComposition> varietalComposition = (List<VarietalComposition>)e.getProperty("varietalComposition");
			List<VarietalComposition> varietalComposition = new ArrayList<VarietalComposition>();
			ProductionDetails productionDetails = (ProductionDetails)e.getProperty("productionDetails");
			Long profilePicture = (Long)e.getProperty("profilePicture");
			
			Wine wine = new Wine(key, name, url_name, vintage, alcohol, awards, averagePrice, averageRating, numberOfReviews, notes, pictures, winery, regionCountry, character, varietalComposition, productionDetails, profilePicture);
				result.add(wine);
		}
		return result;
	}
	
	public static List<Winery> convertEntitiesToWineries(List<Entity> entities){
		List<Winery> result= new ArrayList<Winery>();
		for (Entity e : entities){
			Key key =  e.getKey();
			String name = (String)e.getProperty("name");
			String url_name = (String)e.getProperty("url_name");
			String address = (String)e.getProperty("address");
			String city = (String)e.getProperty("city");
			String phone = (String)e.getProperty("phone");
			String email = (String)e.getProperty("email");
			String website = (String)e.getProperty("website");			
			String about = (e.getProperty("about")!=null)?((Blob)e.getProperty("about")).toString():"";
			Long  profilePicture = (Long)e.getProperty("profilePicture");
			List<Long> pictures = (List<Long>)e.getProperty("pictures"); 
			Key regionCountry = (Key)e.getProperty("regionCountry");
				
			Winery winery = new Winery(key, name, url_name, address, city, phone, email, website, about, pictures, profilePicture, regionCountry);
				result.add(winery);
		}
		return result;
	}
}
