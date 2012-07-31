package com.alasdoo.selenium;

import org.junit.Test;

public class AccountTests extends BaseTests {

	@Test
	public void T01_AccountTests() throws Exception {	
		
		selenium.open("/");
		selenium.click("link=Account");
		selenium.waitForPageToLoad("30000");
		
		String email = TestUtils.Login(selenium);
		
		// verify empty data
		TestUtils.VerifyAccountData(selenium, stb, email, "", "", "", "");

		// required data missing
		TestUtils.SubmitButton(selenium);
		
		stb.verifyTrue(selenium.isTextPresent("Field may not be empty"));
		selenium.isElementPresent("css=.input-error[id=name]");
		
		// fill in data
		TestUtils.TypeAccountData(selenium, "Vilmos Somogyi", "http://vilmoss.com", "my address 2", "something about myselfe", PICTURE1);
		
		// verify the filled in data is saved
		TestUtils.VerifyAccountData(selenium, stb, email, "Vilmos Somogyi", "http://vilmoss.com", "my address 2", "something about myselfe");
		
		// open up the account page again and check if the data is present
		selenium.click("link=Account");
		selenium.waitForPageToLoad("30000");
		
		TestUtils.VerifyAccountData(selenium, stb, email, "Vilmos Somogyi", "http://vilmoss.com", "my address 2", "something about myselfe");
		
		// fill in new data
		TestUtils.TypeAccountData(selenium, "Mr. Wine Enthusiast", "http://viiinoo.com", "address street 123", "Very keen wine enthusiast", PICTURE2);
		TestUtils.VerifyAccountData(selenium, stb, email, "Mr. Wine Enthusiast", "viiinoo.com", "address street 123", "Very keen wine enthusiast");
	}
	

}
