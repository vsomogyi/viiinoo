package com.alasdoo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.compass.core.CompassHits;
import org.compass.core.CompassSearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alasdoo.entity.Wine;
import com.alasdoo.entity.Winery;
import com.alasdoo.repositories.CompassSearcher;


/**
 * Search service
 * @author turzait
 *
 */
@Repository
public class SearchService {

		@Autowired
		CompassSearcher compassSearcher;

		public Map<String,Object> search(String searchString){
			Map<String,Object> result = new HashMap<String,Object>();
			List<Winery> wineryResults = new ArrayList<Winery>();
			List<Wine> wineResults = new ArrayList<Wine>();
	
			CompassSearchSession search = compassSearcher.getCompass().openSearchSession();
			
			if (searchString != null){
				CompassHits hits = search.find(searchString);
				int hitsNum = hits.getLength();			
				for(int i=0;i <hitsNum; i++){
					if (hits.data(i) instanceof Winery ){
						Winery winery = (Winery) hits.data(i);
						wineryResults.add(winery);
					}else
						if (hits.data(i) instanceof Wine){
							Wine wine = (Wine) hits.data(i);
							wineResults.add(wine);
						}
				}
			
				result.put("didYouMean", hits.getSuggestedQuery());
				result.put("hitsNum", hitsNum);
	
			}
			search.close();
			result.put("Wines", wineResults);
			result.put("Wineries", wineryResults);
			return result;
		}

}
