package com.alasdoo.selenium;

import java.util.Random;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Selenium;

public class TestUtils {
	
	public static String randomEmail() {
		Random generator = new Random();
		
		return "test"+generator.nextInt()+"@example.com";
	}
	
	public static String randString(String str) {
		Random gen = new Random();
		
		return str + " " + gen.nextInt();
	}
	
	public static String Login(Selenium selenium) {

		String email = randomEmail();
		
		// google login
		selenium.type("email", email);
		selenium.click("action");
		selenium.waitForPageToLoad("30000");
		
		return email;
	}
	
	public static void NewUser(Selenium selenium, String name, String website, String address, String bio, String file) {
		
		Login(selenium);
		
		TypeAccountData(selenium, name, website, address, bio, file);
	}
	
	
	public static void AddButton(Selenium selenium) {
		selenium.click("css=.add-button");
		selenium.waitForPageToLoad("30000");
	}

	public static void SubmitButton(Selenium selenium) {
		selenium.click("css=#submit-btn");
		selenium.waitForPageToLoad("30000");
	}

	public static void TypeAccountData(Selenium selenium, String name, String website, String address, String bio, String file) {
		
		selenium.type("name", name);
		selenium.type("website", website);
		selenium.type("address", address);
		selenium.type("bio", bio);
		selenium.type("file[0]", file);
		SubmitButton(selenium);
	}

	public static void VerifyAccountData(Selenium selenium, SeleneseTestBase stb, String email, String name, String website, String address, String bio) {
		
		stb.verifyEquals(email, selenium.getText("css=.email-address"));
		stb.verifyEquals(name, selenium.getValue("name"));
		stb.verifyEquals(website, selenium.getValue("website"));
		stb.verifyEquals(address, selenium.getValue("address"));
		stb.verifyEquals(bio, selenium.getValue("bio"));
		// check if default image is there or the uploaded one
		if (name.equals("") && website.equals("") && address.equals("") && bio.equals("")) {
			stb.verifyTrue(selenium.isElementPresent("css=.default-picture"));
		} else {
			stb.verifyFalse(selenium.isElementPresent("css=.default-picture"));
		}
		
		
	}

	public static void VerifyWineData(Selenium selenium, SeleneseTestBase stb, String name, String winerySelect, String newWinery, String type, String vintage,
			String price, String alcohol, String bottleSize, String agedMonths, String agedContainer, String agedComment,
			String amountProduced, String closureType, String varietal, String varietalPercentage, String note,
			String character, String characterText) {
		stb.verifyEquals(name, selenium.getValue("wine.name"));
		stb.verifyEquals(winerySelect, selenium.getSelectedLabel("wineryName"));
		stb.verifyEquals(newWinery, selenium.getValue("newWineryName"));
		stb.verifyEquals(type, selenium.getSelectedLabel("type"));
		stb.verifyEquals(vintage, selenium.getValue("wine.vintage"));
		stb.verifyEquals(price, selenium.getValue("wine.averagePrice"));
		stb.verifyEquals(alcohol, selenium.getValue("wine.alcohol"));
		stb.verifyEquals(bottleSize, selenium.getValue("wine.productionDetails.bottleSize"));
		stb.verifyEquals(agedMonths, selenium.getValue("agedList0.months"));
		stb.verifyEquals(agedContainer, selenium.getSelectedLabel("agedList0.container"));
		stb.verifyEquals(agedComment, selenium.getValue("agedList0.comment"));
		stb.verifyEquals(amountProduced, selenium.getValue("wine.productionDetails.amountProduced"));
		stb.verifyEquals(closureType, selenium.getSelectedLabel("wine.productionDetails.closure"));
		stb.verifyEquals(varietal, selenium.getValue("compositionList0.varietal"));
		stb.verifyEquals(varietalPercentage, selenium.getValue("compositionList0.percentage"));
		stb.verifyEquals(note, selenium.getValue("wine.notes"));
		stb.verifyEquals(character, selenium.getSelectedLabel("characterList0.kind"));
		stb.verifyEquals(characterText, selenium.getValue("characterList0.character"));
	}
	
	public static void TypeWineData(Selenium selenium, String name, String winerySelect, String newWinery, String type, String vintage,
			String price, String alcohol, String bottleSize, String agedMonths, String agedContainer, String agedComment,
			String amountProduced, String closureType, String varietal, String varietalPercentage, String note,
			String character, String characterText) {
		selenium.type("wine.name", name);
		selenium.type("wineryName", winerySelect);
		selenium.type("newWineryName", newWinery);
		selenium.type("type", type);
		selenium.type("wine.vintage", vintage);
		selenium.type("wine.averagePrice", price);
		selenium.type("wine.alcohol", alcohol);
		selenium.type("wine.productionDetails.bottleSize", bottleSize);
		selenium.type("agedList0.months", agedMonths);
		selenium.type("agedList0.container", agedContainer);
		selenium.type("agedList0.comment", agedComment);
		selenium.type("wine.productionDetails.amountProduced", amountProduced);
		selenium.type("wine.productionDetails.closure", closureType);
		selenium.type("compositionList0.varietal", varietal);
		selenium.type("compositionList0.percentage", varietalPercentage);
		selenium.type("wine.notes", note);
		selenium.type("characterList0.kind", character);
		selenium.type("characterList0.character", characterText);
	}
	
	public static void SelectWine(Selenium selenium, String wineName) {
		selenium.select("id=wineName", wineName);
	}

	public static void TypeWineryData(Selenium selenium, String name, String email, String website, String address, String city, String phone, String about) {
		selenium.type("name", name);
		selenium.type("email", email);
		selenium.type("website", website);
		selenium.type("address", address);
		selenium.type("city", city);
		selenium.type("phone", phone);
		selenium.type("about", about);
		SubmitButton(selenium);
	}
	
	public static void VerifyWineryData(Selenium selenium, SeleneseTestBase stb, String name, String email, String website, String address, String city, String phone, String about) {
		stb.verifyEquals(name, selenium.getValue("name"));
		stb.verifyEquals(email, selenium.getValue("email"));
		stb.verifyEquals(website, selenium.getValue("website"));
		stb.verifyEquals(address, selenium.getValue("address"));
		stb.verifyEquals(city, selenium.getValue("city"));
		stb.verifyEquals(phone, selenium.getValue("phone"));
		stb.verifyEquals(about, selenium.getValue("about"));
	}
	
	public static void VerifyWineryView(Selenium selenium, SeleneseTestBase stb, String name, String email, String website, String address, String city, String phone, String about) {
		stb.verifyEquals(name, selenium.getText("id=name"));
		stb.verifyEquals(email, selenium.getText("id=email"));
		stb.verifyEquals(website, selenium.getText("id=website"));
		stb.verifyEquals(phone, selenium.getText("id=phone"));
		stb.verifyEquals(address, selenium.getText("id=address"));
		stb.verifyEquals(about, selenium.getText("id=about"));
	}

	public static void VerifyReviewData(Selenium selenium, SeleneseTestBase stb, String score, String aromaScore, String tasteScore, String colorScore, String reviewText) {
		stb.verifyEquals(score, selenium.getValue("review.score"));
		stb.verifyEquals(aromaScore, selenium.getValue("review.aromaScore"));
		stb.verifyEquals(tasteScore, selenium.getValue("review.tasteScore"));
		stb.verifyEquals(colorScore, selenium.getValue("review.colorScore"));
		stb.verifyEquals(reviewText, selenium.getValue("review.reviewText"));
	}

	public static void TypeReviewData(Selenium selenium, String score, String aromaScore, String tasteScore, String colorScore, String reviewText) {
		selenium.type("review.score", score);
		selenium.type("review.aromaScore", aromaScore);
		selenium.type("review.tasteScore", tasteScore);
		selenium.type("review.colorScore", colorScore);
		selenium.type("review.reviewText", reviewText);
	}


}
