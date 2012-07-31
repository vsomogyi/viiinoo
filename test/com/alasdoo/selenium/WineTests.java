package com.alasdoo.selenium;

import org.junit.Test;

public class WineTests extends BaseTests {

	@Test
	public void T03_WinesTests() throws Exception {
		
		selenium.open("/");
		
		selenium.click("link=Wines");
		selenium.waitForPageToLoad("30000");
		
		TestUtils.NewUser(selenium, "Vilmos Somogyi", "http://vilmoss.com", "my address 2", "something about myselfe", PICTURE1);
		
		// verify if the user is returned to the wineries page after registration
		stb.verifyEquals("Wines", selenium.getTitle());
		stb.verifyEquals(true, selenium.isTextPresent("List of wines"));
		
		// -------- ADD NEW WINE -----------
		
		// add new wine
		TestUtils.AddButton(selenium);
		
		// verify required fields
		TestUtils.SubmitButton(selenium);
		
		stb.verifyTrue(selenium.isTextPresent("Field may not be empty"));
		selenium.isElementPresent("css=.input-error[id=wine.name]");
		
		stb.verifyTrue(selenium.isTextPresent("You need to choose a winery, or make a new one."));
		selenium.isElementPresent("css=.input-error[id=wineryName]");
		
		// check if the fields are empty
		TestUtils.VerifyWineData(selenium, stb, "", "--Please Select", "", "Red", "", "", "", "", "0", "Unknown", "", "", "Unknown", "", "", "", "Unknown", "");
		
		// invalid data and validate
		TestUtils.TypeWineData(selenium, "", "--Please Select", "", "Red", "asdf", "asdf", "asdf", "", "asdf", "Unknown", "", "", "Unknown", "", "asdf", "", "Unknown", "");
		
		TestUtils.SubmitButton(selenium);
		
		// TODO check this
		stb.verifyEquals("A numeric value was expected!", selenium.getText("wine.vintage.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("wine.averagePrice.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("wine.alcohol.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("agedList0.months.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("compositionList0.percentage.errors"));
		
		// TODO check this
		selenium.type("wine.vintage", "123123123");
		selenium.type("wine.averagePrice", "123123123123123");
		selenium.type("wine.alcohol", "123");
		selenium.type("agedList0.months", "123123123123");
		selenium.type("compositionList0.percentage", "123");
		
		TestUtils.SubmitButton(selenium);
		
		// TODO check this
		stb.verifyEquals("Yera of vintage must be in the past.", selenium.getText("wine.vintage.errors"));
		stb.verifyEquals("The value must be between 0 and 100.", selenium.getText("wine.alcohol.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("agedList0.months.errors"));
		stb.verifyEquals("The value must be between 1 and 100.", selenium.getText("compositionList0.percentage.errors"));
		
		// fill in the data
		String wineName = TestUtils.randString("Wine");
		String wineryName = TestUtils.randString("Winery");
		
		TestUtils.TypeWineData(selenium, wineName, "--Please Select", wineryName, "White", "2010", "12", "14", "750 ml", "12", "Glass", "aged in glass", "5000 l", "Closure1", "Cabernet", "100", "A very nice wine!", "Color", "Very nice color!");
//		selenium.select("aged.container", "label=AAA");

		TestUtils.SubmitButton(selenium);
		
		// see if the name is changed in the list
		stb.verifyTrue(selenium.isTextPresent(wineName));
		// TODO also verify other data shown in the listing. is text present is not enough for them.
		
		// -------- EDIT WINE -----------

		selenium.click(wineName.replace(" ", "-")+"-edit");
		selenium.waitForPageToLoad("30000");
		
		// verify the saved data is shown in the edit form
		TestUtils.VerifyWineData(selenium, stb, wineName, wineryName, "", "White", "2010", "12", "14", "750 ml", "12", "Glass", "aged in glass", "5000 l", "Closure1", "Cabernet", "100", "A very nice wine!", "Color", "Very nice color!");
		
		// enter new data
		String wineName2 = TestUtils.randString("Wine");

		TestUtils.TypeWineData(selenium, wineName2, wineryName, "", "Rose", "2009", "15", "12", "0.75 l", "15", "Glass", "aged in glass 2", "15000 l", "Closure2", "Cabernet B", "50", "A very nice wine! 2", "Color", "Very nice color! 2");
		
		// see if the name is changed in the list
		stb.verifyTrue(selenium.isTextPresent(wineName2));
		// TODO also verify other data shown in the listing. is text present is not enough for them.
		
		// verify if the data is in the winery view page
		
		// TODO verify view page
	}
}
