package com.alasdoo.selenium;

import org.junit.Test;

public class WineryTests extends BaseTests {

	
	@Test
	public void T02_WineriesTests() throws Exception {	
		
		selenium.open("/");
		
		selenium.click("link=Wineries");
		selenium.waitForPageToLoad("30000");
		
		TestUtils.NewUser(selenium, "Vilmos Somogyi", "http://vilmoss.com", "my address 2", "something about myselfe", PICTURE1);
		
		// verify if the user is returned to the wineries page after registration
		stb.verifyEquals("Wineries", selenium.getTitle());
		stb.verifyEquals(true, selenium.isTextPresent("List of wineries"));
		
		// -------- ADD NEW WINERY -----------
		
		// add new winery
		TestUtils.AddButton(selenium);
		
		// check if the fields are empty
		TestUtils.VerifyWineryData(selenium, stb, "", "", "", "", "", "", "");
		
		// verify required fields
		TestUtils.SubmitButton(selenium);
		
		stb.verifyTrue(selenium.isTextPresent("Field may not be empty"));
		selenium.isElementPresent("css=.input-error[id=name]");
		
		// fill in the data
		String wineryName = TestUtils.randString("Winery");
		
		TestUtils.TypeWineryData(selenium, wineryName, "test@email.com", "http://testwinery.com", "Test Street 88", "Testingvile", "123456789", "About test winery");
		
		// verify the data is saved and shows up in the listing
		// TODO check for a specific element, not just is text present
		stb.verifyEquals(true, selenium.isTextPresent(wineryName));
		// TODO also verify other data shown in the listing. is text present is not enough for them.
		
		// verify if the data is in the winery view page
		selenium.click("link="+wineryName);
		selenium.waitForPageToLoad("30000");
		
		// verify view data
		TestUtils.VerifyWineryView(selenium, stb, wineryName, "test@email.com", "http://testwinery.com", "Test Street 88", "Testingvile", "123456789", "About test winery");
		
		// -------- EDIT WINERY -----------
		
		selenium.click("link=Edit Winery");
		selenium.waitForPageToLoad("30000");
		
		// verify the saved data is shown in the edit form
		TestUtils.VerifyWineryData(selenium, stb, wineryName, "test@email.com", "http://testwinery.com", "Test Street 88", "Testingvile", "123456789", "About test winery");
		
		// enter new data
		String wineryName2 = TestUtils.randString("Winery");
		TestUtils.TypeWineryData(selenium, wineryName2, "test2@email.com", "http://testwinery2.com", "Test Street 882", "Testingvile2", "1234567892", "About test winery2");
	
		// see if the name is changed in the list
		// TODO check for a specific element, not just is text present
		stb.verifyEquals(true, selenium.isTextPresent(wineryName2));
		
		// verify if the data is in the winery view page
		// TODO verify on the list page, not the edit page
//		VerifyWineryView(wineryName, "test@email.com", "http://testwinery.com", "Test Street 88", "Testingvile", "123456789", "About test winery");
		
		// TODO check listing of the wines
		
		
	}

	// TODO: Selenium - Add picture component tests for wines and wineries with checkings on every page they appear
	
}
