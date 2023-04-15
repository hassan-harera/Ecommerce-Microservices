package com.harera.ecommerce.authorization.util;

public class ErrorCode {

    public static final String MANDATORY_SIGNUP_OAUTH_TOKEN = "signup_001";
    public static final String MANDATORY_SIGNUP_MOBILE = "signup_002";
    public static final String MANDATORY_SIGNUP_FIRST_NAME = "signup_003";
    public static final String MANDATORY_SIGNUP_OTP = "signup_004";
    public static final String FORMAT_SIGNUP_OTP = "signup_005";
    public static final String MANDATORY_SIGNUP_PASSWORD = "signup_006";
    public static final String MANDATORY_SIGNUP_LAST_NAME = "signup_007";
    public static final String FORMAT_SIGNUP_EMAIL = "signup_008";
    public static final String FORMAT_SIGNUP_PASSWORD = "signup_009";
    public static final String INVALID_FIREBASE_TOKEN = "signup_010";
    public static final String FORMAT_SIGNUP_MOBILE = "signup_011";
    public static final String FORMAT_SIGNUP_FIRST_NAME = "signup_012";
    public static final String FORMAT_SIGNUP_LAST_NAME = "signup_013";
    public static final String UNIQUE_SIGNUP_MOBILE = "signup_014";
    public static final String UNIQUE_SIGNUP_EMAIL = "signup_015";


    public static final String NOT_FOUND_USERNAME_OR_PASSWORD = "login_001";
    public static final String MANDATORY_LOGIN_SUBJECT = "login_002";
    public static final String MANDATORY_LOGIN_PASSWORD = "login_003";
    public static final String UNIQUE_EMAIL = "email_001";
    public static final String FORMAT_LOGIN_SUBJECT = "login_003";
    public static final String MANDATORY_LOGIN_OAUTH_TOKEN = "login_004";

    public static final String MANDATORY_RESET_PASSWORD_MOBILE = "reset_password_001";
    public static final String MANDATORY_RESET_PASSWORD_OTP = "reset_password_002";
    public static final String MANDATORY_RESET_PASSWORD_NEW_PASSWORD = "reset_password_003";
    public static final String FORMAT_RESET_PASSWORD_NEW_PASSWORD = "reset_password_004";
    public static final String FORMAT_RESET_PASSWORD_MOBILE = "reset_password_005";
    public static final String FORMAT_RESET_PASSWORD_OTP = "reset_password_006";
    public static final String NOT_FOUND_RESET_PASSWORD_MOBILE = "reset_password_007";

}
