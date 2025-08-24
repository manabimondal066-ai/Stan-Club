package api.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;

public class Club {

	Faker faker;
	public Logger logger;
	public String accessToken;
	public String phone="+919800982281";
	public String otp="1234";
	

	@BeforeClass
	public void setupData() {
		faker = new Faker();
		logger = LogManager.getLogger(this.getClass());//FOR iNitialise the log.
	}
	
	@Test(priority = 1)
	public void testSendOTP()
	{
		Response response = UserEndPoints.sendOTP(phone);
		String message = response.jsonPath().getString("message");
		
        Assert.assertEquals(message, "Otp Sent");
        Assert.assertEquals(200, response.getStatusCode());
        logger.info("Assertion Passed: Response message = {}", message);
        logger.info("===== Send OTP API Test Completed Successfully =====");
        response.then().log().body();
	}
	
	@Test(priority =2)
	public void verifyOTP()
	{
		logger.info("===== Starting Verify OTP API Test =====");
		Response response=UserEndPoints.testVerifyOtp(phone, otp);
		
		logger.info("API executed. Status Code: {}", response.getStatusCode());

        // Assertions using TestNG
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("Assertion Passed: Status code is 200");

        accessToken = response.jsonPath().getString("access_token");
        Assert.assertNotNull(accessToken, "Access token should not be null");
        logger.info("Assertion Passed: Access token received");

        String fetchedphoneNo = response.jsonPath().getString("user.phone");
        Assert.assertEquals(phone, fetchedphoneNo);
        logger.info("Assertion Passed: Phone number matched");

        boolean isNewUser = response.jsonPath().getBoolean("isNewUser");
        Assert.assertFalse(isNewUser, "User should not be new");
        logger.info("Assertion Passed: isNewUser is false");

        int userId = response.jsonPath().getInt("user.id");
        Assert.assertTrue(userId > 0, "User id should be greater than 0");
        logger.info("Assertion Passed: User id is valid: {}", userId);

        logger.info("===== Verify OTP API Test Completed Successfully =====");

        // Print full response in logs and console
        response.then().log().body();
	}
	
	@Test(priority=3)
    public void testClubCategories() {
		logger.info("===== Starting Club Categories API Test =====");
		Response response = UserEndPoints.getClubCategories(accessToken);
		
		response.then().log().body();
		// Validate Status Code
        int statusCode = response.getStatusCode();
        logger.info("Status Code: {}", statusCode);
        Assert.assertEquals(statusCode, 200, "Expected 200 status code");


        SoftAssert softAssert = new SoftAssert();

       List<String> listOfId = response.jsonPath().getList("id");
        softAssert.assertTrue(listOfId.contains("ALL"), "All category not found");
        softAssert.assertTrue(listOfId.contains("TRENDING"), "TRENDING category not found");
        softAssert.assertTrue(listOfId.contains("MINECRAFT"), "MINECRAFT category not found");
        softAssert.assertTrue(listOfId.contains("LUDO"), "LUDO category not found");
        softAssert.assertTrue(listOfId.contains("FREEFIRE"), "FREEFIRE category not found");
        softAssert.assertTrue(listOfId.contains("BGMI"), "BGMI category not found");
        softAssert.assertTrue(listOfId.contains("MOBILEGAMES"), "MOBILEGAMES category not found");
        softAssert.assertTrue(listOfId.contains("FAU-G"), "FAU-G category not found");
        softAssert.assertTrue(listOfId.contains("SnakesLadders"), "SnakesLadders category not found");
        softAssert.assertTrue(listOfId.contains("ANIME"), "ANIME category not found");
        softAssert.assertTrue(listOfId.contains("MUSIC"), "MUSIC category not found");
        softAssert.assertTrue(listOfId.contains("REWARDS"), "REWARDS category not found");

        // Collate all soft assertion results
        softAssert.assertAll();
        
        logger.info("Verified all required categories (All, TRENDING, MINECRAFT, LUDO, FREEFIRE, BGMI, MOBILEGAMES, FAU-G, SnakesLadders, ANIME, MUSIC, REWARDS) are present ");
	}
	
	@Test(priority=4)
    public void testGetLiveClubs() {
Response response = UserEndPoints.getLiveClubReq(accessToken);
Assert.assertEquals( response.getStatusCode(),200);

//Extract all titles into a List
List<String> titles = response.jsonPath().getList("title");



// validations

// 1. Titles list is not empty
Assert.assertFalse(titles.isEmpty(), "Titles list should not be empty");

// 2. No title should be null or blank
for (String title : titles) {
    Assert.assertNotNull(title, "Title should not be null");
    Assert.assertFalse(title.trim().isEmpty(), "Title should not be empty");
}
//Print full response in logs and console
response.then().log().body();
System.out.println("Titles: " + titles);
logger.info("Verified the testGetLiveClubs");


	}
}
	
