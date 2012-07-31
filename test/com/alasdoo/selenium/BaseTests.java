package com.alasdoo.selenium;

import org.junit.After;
import org.junit.Before;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Selenium;

public class BaseTests {

	protected static final String PICTURE1 = "/Users/zeus/Pictures/Vili/Vili_avatar.jpg";
	protected static final String PICTURE2 = "/Users/zeus/Pictures/Vili/Vili_avatar.png";

	protected static Selenium selenium;
	protected SeleneseTestBase stb = new SeleneseTestBase();
	
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*googlechrome", "http://localhost:8888/");
		selenium.start();
		selenium.windowMaximize();
	}
	
	@After
	public void tearDown() throws Exception {
		selenium.close();
		selenium.stop();
	}
}






