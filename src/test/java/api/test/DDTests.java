//package api.test;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import api.endpoints.UserEndPoints;
//import api.payloads.User;
//import api.utilities.DataProviders;
//import api.utilities.RetryAnalyzer;
//import io.restassured.response.Response;
//
//public class DDTests {
//
//	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class, retryAnalyzer = RetryAnalyzer.class)
//	public void testPostUser(String userID, String userName, String fname, String lname, String useremail, String pwd,
//			String ph) throws InterruptedException {
//		User userPayload = new User();
//		userPayload.setId(Integer.parseInt(userID));
//		userPayload.setUsername(userName);
//		userPayload.setFirstName(fname);
//		userPayload.setLastName(lname);
//		userPayload.setEmail(useremail);
//		userPayload.setPassword(pwd);
//		userPayload.setPhone(ph);
//
//		Response response = UserEndPoints.createUser(userPayload);
//		System.out.println("CREATE RESPONSE: " + response.getBody().asString());
//		Assert.assertEquals(response.getStatusCode(), 200);
//
//	}
//
//	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class, retryAnalyzer = RetryAnalyzer.class)
//	public void testDeleteUserByName(String un) throws InterruptedException {
//		Response response = UserEndPoints.deleteUser(un);
//		System.out.println("DELETE RESPONSE: " + response.getBody().asString());
//		Assert.assertEquals(response.getStatusCode(), 200);
//	}
//}
