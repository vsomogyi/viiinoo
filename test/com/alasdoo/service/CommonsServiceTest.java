package com.alasdoo.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alasdoo.service.CommonsService;


public class CommonsServiceTest {

	public enum Day {
	    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, 
	    THURSDAY, FRIDAY, SATURDAY 
	}
	
	@Test
	public void nameToFancyURL_Ok(){
		String test = "gfrjdkhg";
		String result = CommonsService.nameToFancyURL(test);
		assertEquals(test,result);
	}
	
	@Test
	public void nameToFancyURL_Replace(){
		String test = "gf k g";
		String result = CommonsService.nameToFancyURL(test);
		assertEquals("gf-k-g",result);
	}
	
	@Test
	public void nameToFancyURL_Empty(){
		String test = "";
		String result = CommonsService.nameToFancyURL(test);
		assertEquals("no-name",result);
	}
	@Test
	public void nameToFancyURL_Special(){
		String test = "!@#$%^&*()sdfg  dfg";
		String result = CommonsService.nameToFancyURL(test);
		assertEquals("sdfg--dfg",result);
	}
	
	@Test
	public void getSerializedTest(){
		String test = "FRIDAY";
		String result = CommonsService.getSerializedForm(Day.FRIDAY);
		assertEquals(test,result);
	}
	
	@Test
	public void deserializeTest(){
		String test = "FRIDAY";
		Enum<Day> result = CommonsService.deserialize(Day.class, test);
		assertEquals(Day.FRIDAY,result);
	}
}
