package com.bloodbank.slidingmenu;
public class AppConfig {
    // Server user login url
	public static String ip="http://dreamanimators.cloudapp.net/Blood/api/";
    public static String URL_LOGIN = ip+"login.php";
    public static String url_all_associates = ip+"get_all_associates.php";
    public static String url_all_appointments = ip+"get_all_appointments.php";
    public static String url_create_appointment = ip+"insert_appointment.php";
    // Server user register url
    public static String URL_REGISTER = ip+"register.php";
    
    public static String URL_REQUEST = ip+"insert_blood_request.php";
    
    public static String URL_DONORS = ip+"get_all_users.php";
    
    public static String URL_EDIT = ip+"edit_details.php";
    
    public static int type;
    
}