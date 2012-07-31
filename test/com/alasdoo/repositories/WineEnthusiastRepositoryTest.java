package com.alasdoo.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alasdoo.service.LocalDatastoreTest;

import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.repositories.WineEnthusiastRepository;

/**
 * @author 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class WineEnthusiastRepositoryTest extends LocalDatastoreTest{


	@Autowired
	private WineEnthusiastRepository wineEnthusiastRepository;
	
	private WineEnthusiast we;
	private WineEnthusiast we2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Override
	public void setUp()  {
		super.setUp();
		 we = new WineEnthusiast("vili","email@email.com","vilmoss.com","some bio info","address");
		 we2 = new WineEnthusiast("vili","email@email.com","vilmoss.com","some bio info","sserdda");		
	}
 
	
//	@Test
//	public void testExists() {
//		try{
//		wineEnthusiastRepository.save(we);
//		assertEquals(true,wineEnthusiastRepository.exists(we));
//		}catch(Exception ex){
//			fail("Database Exception");
//		}
//	}
	
	/**
	 * Test method for {@link com.alasdoo.repositories.GenericDAOImpl#getAll()}.
	 */
	@Test
	public void testGetAll() {
		try{
			wineEnthusiastRepository.getAll();		
			} catch(Exception ex){
				fail("Database exception.");
			}
		}
	
//
//	/**
//	 * Test method for {@link com.alasdoo.repositories.GenericDAOImpl#getAllDistinct()}.
//	 */
//	@Test
//	public void testGetAllDistinct() {
//		fail("Not yet implemented");
//	}
//
	/**
	 * Test method for {@link com.alasdoo.repositories.GenericDAOImpl#remove(java.io.Serializable)}.
	 */
//	@Test
//	public void testRemove() {
//		try{
//			wineEnthusiastRepository.save(we);	
//			wineEnthusiastRepository.remove(we);
//			} catch(Exception ex){
//				fail("Database exception.");
//			}
//	}

	
		
	
	/**
	 * Test method for saveWineEnthusiast
	 */
	@Test
	public void testSave() {
		try{
		wineEnthusiastRepository.save(we);		
		} catch(Exception ex){
			fail("Database exception.");
		}
	}

	
}
