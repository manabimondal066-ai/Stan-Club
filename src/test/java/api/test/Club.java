package api.test;

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

	@BeforeClass
	public void setupData() {
		faker = new Faker();
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testSendOTP()
	{
		Response response = UserEndPoints.sendOTP("+919800982289");
		String message = response.jsonPath().getString("message");
		
        Assert.assertEquals(message, "Otp Sent");
        logger.info("Assertion Passed: Response message = {}", message);
        logger.info("===== Send OTP API Test Completed Successfully =====");
        response.then().log().body();
	}
	
	@Test(priority =2)
	public void verifyOTP()
	{
		logger.info("===== Starting Verify OTP API Test =====");
		Response response=UserEndPoints.testVerifyOtp("+919800982289", "1234");
		
		logger.info("API executed. Status Code: {}", response.getStatusCode());

        // Assertions using TestNG
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("Assertion Passed: Status code is 200");

        accessToken = response.jsonPath().getString("access_token");
        Assert.assertNotNull(accessToken, "Access token should not be null");
        logger.info("Assertion Passed: Access token received");

        String phone = response.jsonPath().getString("user.phone");
        Assert.assertEquals(phone, "+919800982289");
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
        logger.debug("Full API Response: {}", response.asPrettyString());
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

        // Validate Response is Array
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0, "Response array should not be empty");
        logger.info("Response returned {} categories", response.jsonPath().getList("$").size());

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(response.jsonPath().getList("id").contains("ALL"), "All category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("TRENDING"), "TRENDING category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("MINECRAFT"), "MINECRAFT category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("LUDO"), "LUDO category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("FREEFIRE"), "FREEFIRE category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("BGMI"), "BGMI category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("MOBILEGAMES"), "MOBILEGAMES category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("FAU-G"), "FAU-G category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("SnakesLadders"), "SnakesLadders category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("ANIME"), "ANIME category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("MUSIC"), "MUSIC category not found");
        softAssert.assertTrue(response.jsonPath().getList("id").contains("REWARDS"), "REWARDS category not found");

        // Collate all soft assertion results
        softAssert.assertAll();
        
        logger.info("Verified all required categories (All, TRENDING, MINECRAFT, LUDO, FREEFIRE, BGMI, MOBILEGAMES, FAU-G, SnakesLadders, ANIME, MUSIC, REWARDS) are present ✅");
	}
	
	@Test(priority=4)
    public void testGetLiveClubs() {
Response response = UserEndPoints.getLiveClubReq(accessToken);
//Print full response in logs and console
response.then().log().body();
logger.debug("Full API Response: {}", response.asPrettyString());
	}
}
	
