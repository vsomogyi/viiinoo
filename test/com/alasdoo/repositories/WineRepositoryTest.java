package com.alasdoo.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alasdoo.service.LocalDatastoreTest;

import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.entity.Winery;
import com.alasdoo.repositories.WineRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class WineRepositoryTest extends LocalDatastoreTest{

	@Autowired
	private WineRepository wineRepository;

	@Autowired
	private WineEnthusiastRepository wineEnthusiastRepository;
	
	Winery winery;
	Wine wine;

	@Override
	public void setUp() {
		super.setUp();
		winery = new Winery("name", "address", "city", "phone", "email", "website","name", "about");
		wine = new Wine("name",12,new BigDecimal(12),new BigDecimal(78));
	}



	@Test
	public void testSaveWinery(){
		//need to find a way to test this
			wineRepository.saveWinery(winery);
		
	}
	
//	@Test
//	public void testGetAllWinery(){
//		try{
//		wineRepository.saveWinery(winery);
//		List<Winery> listWinery =  wineRepository.getAllWinery();
//		assertEquals(winery,listWinery.get(0));
//		}catch(Exception ex){
//			fail("Database exception. Test failed.");
//		}
//	}
	
	@Test
	public void testSaveWine(){
		try{
			wineRepository.saveWine(wine);
		}catch(Exception ex){
			fail("Database Exception.class Test failed.");
		}
	}
	

	@Test
	public void testGetAllWine(){
		try{			
		wineRepository.saveWine(wine);
		List<Wine> listWine =  wineRepository.getAllWines();
		Wine wine2 = listWine.get(0);
		//wine.equals(wine2);
		assertEquals("Error in the equals function",wine,wine2);
		
		assertEquals(wine,wine2);
		}catch(Exception ex){
			fail("Database exception. Test failed.");
		}
	}
	
	@Test
	public void testGetPartialDiaryWinesListByReviewDate(){
		WineEnthusiast we = wineEnthusiastRepository.getAll().get(0);
		System.out.println(we);
		wineRepository.getPartialDiaryWinesListByReviewDate(null, 10, we.getKey());
		
	}
}
