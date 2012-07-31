package com.alasdoo.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;

import com.alasdoo.entity.Winery;
import com.google.appengine.api.datastore.DatastoreNeedIndexException;
import com.google.appengine.api.datastore.DatastoreTimeoutException;

@Service
public class SearchJanitor extends JdoDaoSupport{
	
	private static final Logger log = Logger.getLogger(SearchJanitor.class.getName());
	
	public static final int MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH = 5;
	
	public static final int MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX = 200;
	
	
	@Autowired
	protected PersistenceManagerFactory persistenceManagerFactory;

	@PostConstruct
	public void init() {

		super.setPersistenceManagerFactory(persistenceManagerFactory);

	}



	@SuppressWarnings("unchecked")
	public List<Winery> searchGuestBookEntries(
			String queryString) {

		StringBuffer queryBuffer = new StringBuffer();

		queryBuffer.append("SELECT FROM " + Winery.class.getName() + " WHERE ");

		Set<String> queryTokens = SearchJanitorUtils
				.getTokensForIndexingOrQuery(queryString,
						MAXIMUM_NUMBER_OF_WORDS_TO_SEARCH);

		List<String> parametersForSearch = new ArrayList<String>(queryTokens);

		StringBuffer declareParametersBuffer = new StringBuffer();

		int parameterCounter = 0;

		while (parameterCounter < queryTokens.size()) {

			queryBuffer.append("fts == param" + parameterCounter);
			declareParametersBuffer.append("String param" + parameterCounter);

			if (parameterCounter + 1 < queryTokens.size()) {
				queryBuffer.append(" && ");
				declareParametersBuffer.append(", ");

			}

			parameterCounter++;

		}

		
		PersistenceManager pm = getPersistenceManager(true);		
		
		Query query = pm.newQuery(queryBuffer.toString());

		query.declareParameters(declareParametersBuffer.toString());

		List<Winery> result = null;
		
		try {
			result = (List<Winery>) query.executeWithArray(parametersForSearch
				.toArray());
		
		} catch (DatastoreTimeoutException e) {
			log.severe(e.getMessage());
			log.severe("datastore timeout at: " + queryString);// + " - timestamp: " + discreteTimestamp);
		} catch(DatastoreNeedIndexException e) {
			log.severe(e.getMessage());
			log.severe("datastore need index exception at: " + queryString);// + " - timestamp: " + discreteTimestamp);
		}

		return result;

	}
	
	
	
	
	
	public static void updateFTSStuffForWinery(
			Winery winery) {

		StringBuffer sb = new StringBuffer();
		
		sb.append(winery.getName());
			
		Set<String> new_ftsTokens = SearchJanitorUtils.getTokensForIndexingOrQuery(
				sb.toString(),
				MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX);
		
		
		Set<String> ftsTokens = winery.getFts();
	
			ftsTokens.clear();

			for (String token : new_ftsTokens) {
				ftsTokens.add(token);

			}		
	}
	
}
