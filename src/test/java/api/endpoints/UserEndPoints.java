package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
//UserEndPoints
//Created for perform Create, Read, Update and Delete requests the user API

public class UserEndPoints {
	public static Response sendOTP(String phone)
	{
		Map<String, String> payload = new HashMap<>();
		payload.put("phone", phone);
		
		Response response= given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
		.post(Routes.send_OTP_URL);

		return response;
	}
	
	public static Response testVerifyOtp(String phone, String otp) {
	    Map<String, Object> deviceData = new HashMap<>();
	    deviceData.put("deviceUID", "58b0f5dee2d2071e");

	    Map<String, Object> deviceInfo = new HashMap<>();
	    deviceInfo.put("APP_TYPE", "android");
	    deviceInfo.put("DeviceData", deviceData);

	    Map<String, Object> payload = new HashMap<>();
	    payload.put("phone", phone);
	    payload.put("otp", otp);
	    payload.put("isFriendshipUser", false);
	    payload.put("integrityToken", null);
	    payload.put("sessionId", null);
	    payload.put("deviceInfo", deviceInfo);
	    payload.put("utmPayload", new HashMap<>());   // empty object {}
	    payload.put("campaignUrl", "");
	    
	    long timestamp = System.currentTimeMillis();
	    String sid = timestamp + "-78154";   //SID looks

	    Response response = given()
	            .contentType(ContentType.JSON)
	            .accept(ContentType.JSON)
	            // headers
	            .header("gaid", "3df78d2f-bb2d-4194-9c5d-c197970c2132")
	            .header("AppVersion", "204")
	            .header("StallionVersion", "0")
	            .header("Platform", "android")
	            .header("PlatformVersion", "15")
	            .header("SID", sid)
	            .header("TS", String.valueOf(timestamp))
	            .header("appInstanceId", "95df07472e39fbcd7a424bb33e6cb581")
	            .header("countryCode", "in")
	            .body(payload)
	    .when()
	            .post(Routes.verify_OTP_URL);
	    return response;
	}
	
	public static Response getClubCategories(String accessToken)
	{
		long timestamp = System.currentTimeMillis();
	    String sid = timestamp + "-53337";
	    
		// ✅ Headers
		Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("gaid", "3df78d2f-bb2d-4194-9c5d-c197970c2132");
        headers.put("AppVersion", "204");
        headers.put("StallionVersion", "0");
        headers.put("Platform", "android");
        headers.put("SID", sid);
        headers.put("TS", String.valueOf(timestamp));
        headers.put("countryCode", "in");
        headers.put("Authorization", "Bearer "+accessToken);

        // API Call
        Response response = given()
                .headers(headers)
                .when()
                .get(Routes.get_categories_URL);
        return response;
	}
	
	public static Response getLiveClubReq(String accessToken)
	{
		 Response response = 
		            given()
		                .header("Accept", "application/json, text/plain, */*")
		                .header("gaid", "3df78d2f-bb2d-4194-9c5d-c197970c2132")
		                .header("AppVersion", "204")
		                .header("StallionVersion", "0")
		                .header("Platform", "android")
		                .header("SID", "1755945896595-46510")
		                .header("TS", "1755945914053")
		                .header("appInstanceId", "95df07472e39fbcd7a424bb33e6cb581")
		                .header("installReferrer", "utm_source=google-play&utm_medium=organic")
		                .header("countryCode", "in")
		                .header("Authorization", "Bearer "+accessToken)
		                .queryParam("isNewUser", "undefined")
		                .queryParam("status", "live")
		                .queryParam("limit", "10")
		                .queryParam("offset", "10")
		                .queryParam("category", "All")
		            .when()
		                .get(Routes.get_LiveClub_URL);
		 
		 return response;
	}
}
