package com.alasdoo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alasdoo.repositories.MockWineRepositoryTest;

import com.alasdoo.entity.Wine;
import com.alasdoo.entity.WineForm;
import com.alasdoo.service.WineService;

public class WineServiceTest {

	static WineService wineService;
	static Wine wine;
	
	@BeforeClass
	static public void setUp()  {
		wineService = new WineService(new MockWineRepositoryTest());
	    wine = new Wine();
	    //TODO make a more complex wine
	    wine.setName("Wine");
	}
	@Test
	public void testConvertWineToWineForm() {
		//TODO extend this test
		WineForm wineForm = wineService.convertWineToWineForm(wine);
		assertEquals(wine,wineForm.getWine());
	}

	@Test
	public void testExtractWineryNames() {
		List<String> names = new ArrayList<String>();
		names.add("Winery1");
		names.add("Winery2");
		List<String> result = wineService.extractWineryNames();
		assertEquals(names,result);
	}

	@Test
	public void testExtractWineNames() {
		List<String> names = new ArrayList<String>();
		names.add("Wine1");
		names.add("Wine2");
		names.add("Wine3");
		List<String> result = wineService.extractWineNames();
		assertEquals(names,result);
	}

}
