package com.alasdoo.selenium;

import org.junit.Test;

public class ReviewTests extends BaseTests {
	
	@Test
	public void T04_ReviewsTests() throws Exception {
		
		selenium.open("/");
		
		selenium.click("link=Reviews");
		selenium.waitForPageToLoad("30000");
		
		TestUtils.NewUser(selenium, "Vilmos Somogyi", "http://vilmoss.com", "my address 2", "something about myselfe", PICTURE1);
		
		// verify if the user is returned to the wineries page after registration
		stb.verifyEquals("Reviews", selenium.getTitle());
		stb.verifyEquals(true, selenium.isTextPresent("List of reviews"));
		
		// -------- ADD NEW REVIEW -----------
		
		TestUtils.AddButton(selenium);
		
		// check if the fields are empty
		TestUtils.VerifyWineData(selenium, stb, "", "--Please Select", "", "Red", "", "", "", "", "0", "Unknown", "", "", "Unknown", "", "", "", "Unknown", "");
		TestUtils.VerifyReviewData(selenium, stb, "", "", "", "", "");
		
		// verify required fields
		TestUtils.SubmitButton(selenium);
		
		stb.verifyTrue(selenium.isTextPresent("Field may not be empty"));
		selenium.isElementPresent("css=.input-error[id=wine.name]");
		
		stb.verifyTrue(selenium.isTextPresent("You need to choose a winery, or make a new one."));
		selenium.isElementPresent("css=.input-error[id=wineryName]");
		
		stb.verifyTrue(selenium.isTextPresent("May not be null."));
		selenium.isElementPresent("css=.input-error[id=review.score]");

		
		// invalid data and validate
		TestUtils.TypeWineData(selenium, "", "--Please Select", "", "Red", "asdf", "asdf", "asdf", "", "asdf", "Unknown", "", "", "Unknown", "", "asdf", "", "Unknown", "");
		TestUtils.TypeReviewData(selenium, "asdf", "asdf", "asdf", "asdf", "");
		
		TestUtils.SubmitButton(selenium);
		
		// TODO check this
		stb.verifyEquals("A numeric value was expected!", selenium.getText("wine.vintage.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("wine.averagePrice.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("wine.alcohol.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("agedList0.months.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("compositionList0.percentage.errors"));
		stb.verifyEquals("A numeric value expected!", selenium.getText("review.score.errors"));
		stb.verifyEquals("A numeric value expected!", selenium.getText("review.aromaScore.errors"));
		stb.verifyEquals("A numeric value expected!", selenium.getText("review.tasteScore.errors"));
		stb.verifyEquals("A numeric value expected!", selenium.getText("review.colorScore.errors"));
		
		// TODO check this
		selenium.type("wine.vintage", "123123123");
		selenium.type("wine.averagePrice", "123123123123123");
		selenium.type("wine.alcohol", "123");
		selenium.type("agedList0.months", "123123123123");
		selenium.type("compositionList0.percentage", "123");
		TestUtils.TypeReviewData(selenium, "8", "8", "8", "8", "");
		
		TestUtils.SubmitButton(selenium);
		
		// TODO check this
		stb.verifyEquals("Yera of vintage must be in the past.", selenium.getText("wine.vintage.errors"));
		stb.verifyEquals("The value must be between 0 and 100.", selenium.getText("wine.alcohol.errors"));
		stb.verifyEquals("A numeric value was expected!", selenium.getText("agedList0.months.errors"));
		stb.verifyEquals("The value must be between 1 and 100.", selenium.getText("compositionList0.percentage.errors"));
		stb.verifyEquals("The value must be between 1 and 5.", selenium.getText("review.score.errors"));
		stb.verifyEquals("The value must be between 1 and 5.", selenium.getText("review.aromaScore.errors"));
		stb.verifyEquals("The value must be between 1 and 5.", selenium.getText("review.tasteScore.errors"));
		stb.verifyEquals("The value must be between 1 and 5.", selenium.getText("review.colorScore.errors"));
		
		// fill in the data
		String wineName = TestUtils.randString("Wine");
		String wineryName = TestUtils.randString("Winery");
		
		TestUtils.TypeWineData(selenium, wineName, "--Please Select", wineryName, "White", "2010", "12", "14", "750 ml", "12", "Glass", "aged in glass", "5000 l", "Closure1", "Cabernet", "100", "A very nice wine!", "Color", "Very nice color!");
		TestUtils.TypeReviewData(selenium, "3", "1", "2", "3", "This is the text of the review");
		
		TestUtils.SubmitButton(selenium);
		
		// see if the name is changed in the list
		stb.verifyTrue(selenium.isTextPresent(wineName));
		// TODO also verify other data shown in the listing. is text present is not enough for them.
		
		// -------- EDIT REVIEW -----------

//		selenium.click(wineName.replace(" ", "-")+"-edit");
//		selenium.waitForPageToLoad("30000");
//		
//		// verify the saved data is shown in the edit form
//		VerifyWineData(wineName, wineryName, "", "White", "2010", "12", "14", "750 ml", "12", "Glass", "aged in glass", "5000 l", "Closure1", "Cabernet", "100", "A very nice wine!", "Color", "Very nice color!");
//		
//		// enter new data
//		String wineName2 = randString("Wine");
//
//		TypeWineData(wineName2, wineryName, "", "Rose", "2009", "15", "12", "0.75 l", "15", "Glass", "aged in glass 2", "15000 l", "Closure2", "Cabernet B", "50", "A very nice wine! 2", "Color", "Very nice color! 2");
//		
//		// see if the name is changed in the list
//		stb.verifyTrue(selenium.isTextPresent(wineName2));
		// TODO also verify other data shown in the listing. is text present is not enough for them.
		
		// verify if the data is in the winery view page
		
		// TODO verify view page
		

	}
}
