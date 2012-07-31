package com.alasdoo.selenium;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class DummyContentCreation extends BaseTests {
	
	static List<String> wineries = Arrays.asList("First Winery", "Second", "Some Other Winery", "The Fourth W", "5 winery");
	static List<String> wines = Arrays.asList("Wine First", "Wine Second", "Vino Some Other", "Fourth Wine", "Fine wine");

	@Test
	public void T01_CreateDummyData() throws Exception {
		
		selenium.open("/");
		
		selenium.click("link=Wineries");
		selenium.waitForPageToLoad("30000");
		
		TestUtils.NewUser(selenium, "Vilmos Somogyi", "http://vilmoss.com", "my address 2", "something about myselfe", PICTURE1);
		
		
		// --- WINERIES ---
		
		// add 2 random wineries
		AddWinery(TestUtils.randString("Winery"));
		AddWinery(TestUtils.randString("Cellar"));
		
		// add 5 wineries that will have wines associated to them
		for (String winery : wineries) {
			AddWinery(winery);
		}
		
		// add 3 more random wineries
		AddWinery(TestUtils.randString("Winery"));
		AddWinery(TestUtils.randString("Boraszat"));
		AddWinery(TestUtils.randString("Podrum"));
		
		
		// --- WINES ---
		
		// add 2 random wines with new wineries
		AddWine(TestUtils.randString("Wine"), "--Please Select", TestUtils.randString("Winery"));
		AddWine(TestUtils.randString("Wine"), "--Please Select", TestUtils.randString("Winery"));
		
		// add 5 random wines to each winery from the list 
		for (String winery : wineries) {
			AddWine(TestUtils.randString("Vino"), winery, "");
		}
		
		// add 5 wines to each winery from the list 
		for (String wine : wines) {
			AddWine(wine, "--Please Select", TestUtils.randString("Vino"));
		}
		
		// add 3 more random wines with new wineries
		AddWine(TestUtils.randString("Bor"), "--Please Select", TestUtils.randString("Winery"));
		AddWine(TestUtils.randString("WW"), "--Please Select", TestUtils.randString("Vinory"));
		AddWine(TestUtils.randString("Vino"), "--Please Select", TestUtils.randString("Winery"));
		
		
		// --- REVIEWS ---
		
		// add 5 random reviews with new wines and existing wineries
		for (String winery : wineries) {
			AddReview(TestUtils.randString("UjBor"), winery, "");
		}
		
		for (String wine : wines) {
			AddReviewExistingWine(wine);
		}
	}
	
	public void AddWinery(String winery) {

		selenium.open("/");
		
		selenium.click("link=Wineries");
		selenium.waitForPageToLoad("30000");
		
		// add new winery
		TestUtils.AddButton(selenium);
		
		TestUtils.TypeWineryData(selenium, winery, TestUtils.randomEmail(), "http://testwinery.com", "Test Street 88", "Testingvile", "123456789", "About test winery");
		
	}
	
	public void AddWine(String wineName, String wineryName, String newWineryName) {
		
		selenium.open("/");
		
		selenium.click("link=Wines");
		selenium.waitForPageToLoad("30000");
		
		// add new wine
		TestUtils.AddButton(selenium);
		
		TestUtils.TypeWineData(selenium, wineName, wineryName, newWineryName, "White", "2010", "12", "14", "750 ml", "12", "Glass", "aged in glass", "5000 l", "Closure1", "Cabernet", "100", "A very nice wine!", "Color", "Very nice color!");
		
		TestUtils.SubmitButton(selenium);
	}
	
	public void AddReview(String wineName, String wineryName, String newWineryName) {
		
		selenium.open("/");
		
		selenium.click("link=Reviews");
		selenium.waitForPageToLoad("30000");
		
		// add new review
		TestUtils.AddButton(selenium);
		
		TestUtils.TypeWineData(selenium, wineName, wineryName, newWineryName, "White", "2010", "12", "14", "750 ml", "12", "Glass", "aged in glass", "5000 l", "Closure1", "Cabernet", "100", "A very nice wine!", "Color", "Very nice color!");
		TestUtils.TypeReviewData(selenium, "3", "1", "2", "3", "This is the text of the review");
		
		TestUtils.SubmitButton(selenium);
		
	}

	public void AddReviewExistingWine(String wineName) {
		
		selenium.open("/");
		
		selenium.click("link=Reviews");
		selenium.waitForPageToLoad("30000");
		
		// add new review
		TestUtils.AddButton(selenium);
		
		TestUtils.SelectWine(selenium, wineName);
		TestUtils.TypeReviewData(selenium, "3", "1", "2", "3", "This is the text of the review" + new Date());
		
		TestUtils.SubmitButton(selenium);
		
	}

}
