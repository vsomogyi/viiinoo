package com.alasdoo.service;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/com/alasdoo/testconf/testContext.xml" })
public class LocalDatastoreTest extends TestCase {

    private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
    	NamespaceManager.set(NamespaceManager.getGoogleAppsNamespace());
    	helper.setEnvAppId("wineicious");
    	helper.setEnvAuthDomain("gmail.com");

        helper.setUp();
       
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

   @Test
    public void testInitSuperTestClass(){
    	assert(true);
    }
   
//   
//   // run this test twice to prove we're not leaking any state across tests
//   private void doTest() {
//       DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//       assertEquals(0, ds.prepare(new Query("yam")).countEntities(FetchOptions.Builder.withLimit(10)));
//       ds.put(new Entity("yam"));
//       ds.put(new Entity("yam"));
//       assertEquals(2, ds.prepare(new Query("yam")).countEntities(FetchOptions.Builder.withLimit(10)));
//   }
// 
//   @Test
//   public void testInsert1() {
//       doTest();
//   }
//
//   @Test
//   public void testInsert2() {
//       doTest();
//   }

}