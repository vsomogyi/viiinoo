package com.alasdoo.entity;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Persistence Manager Factory
 * @author Vinofil
 *
 */

@Deprecated
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
  
    
    
    private PMF() {}
    
    public static PersistenceManagerFactory get() {
    		return pmfInstance;
    }
    
  
}
