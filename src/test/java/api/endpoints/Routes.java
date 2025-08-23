package api.endpoints;

public class Routes {
	public static String base_url="https://stage-api.getstan.app";

	//user  module

	public static String send_OTP_URL=base_url+"/api/v1/auth/otp/send";
	public static String verify_OTP_URL=base_url+"/api/v4/verify/otp";
	public static String get_categories_URL=base_url+"/api/v5/club/categories";
	public static String get_LiveClub_URL=base_url+"/api/v5/get/live-clubs";
}
